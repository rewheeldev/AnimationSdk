package com.example.animationsdk.ui.gl.tryThree

import android.graphics.PointF
import android.opengl.GLES20
import android.opengl.GLES20.glVertexAttribPointer
import android.opengl.Matrix
import android.util.Log
import com.example.animationsdk.ui.gl.tryThree.GLManager.A_POSITION
import com.example.animationsdk.ui.gl.tryThree.GLManager.COMPONENTS_PER_VERTEX
import com.example.animationsdk.ui.gl.tryThree.GLManager.ELEMENTS_PER_VERTEX
import com.example.animationsdk.ui.gl.tryThree.GLManager.FLOAT_SIZE
import com.example.animationsdk.ui.gl.tryThree.GLManager.STRIDE
import com.example.animationsdk.ui.gl.tryThree.GLManager.U_COLOR
import com.example.animationsdk.ui.gl.tryThree.GLManager.U_MATRIX
import com.example.animationsdk.ui.gl.tryThree.GLManager.aPositionLocation
import com.example.animationsdk.ui.gl.tryThree.GLManager.gLProgram
import com.example.animationsdk.ui.gl.tryThree.GLManager.uColorLocation
import com.example.animationsdk.ui.gl.tryThree.GLManager.uMatrixLocation
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


open class GameObject {
    var isActive: Boolean

    enum class Type {
        SHIP, ASTEROID, BORDER, BULLET, STAR
    }

    var type: Type? = null

    // How many vertices does it take to make
    // this particular game object?
    private var numElements = 0
    private var numVertices = 0

    // To hold the coordinates of the vertices that
    // define our GameObject model
    private lateinit var modelVertices: FloatArray

    // Which way is the object moving and how fast?
    private val xVelocity = 0f
    private val yVelocity = 0f
    private val speed = 0f
    private val maxSpeed = 200f

    // Where is the object centre in the game world?
    val worldLocation = PointF()

    // This will hold our vertex data that is
    // passed into the openGL glProgram
    // OPenGL likes FloatBuffer
    private var vertices: FloatBuffer? = null

    // For translating each point from the model (ship, asteroid etc)
    // to its game world coordinates
    private val modelMatrix = FloatArray(16)

    // Some more matrices for Open GL transformations
    var viewportModelMatrix = FloatArray(16)
    var rotateViewportModelMatrix = FloatArray(16)

    // Where is the GameObject facing?
    private val facingAngle = 90f

    // How fast is it rotating?
    private val rotationRate = 0f

    // Which direction is it heading?
    private val travellingAngle = 0f

    // How long and wide is the GameObject?
    private var length = 0f
    private var width = 0f
    fun setGLProgram() {
        glProgram = gLProgram
    }

    fun setSize(w: Float, l: Float) {
        width = w
        length = l
    }

    fun setWorldLocation(x: Float, y: Float) {
        worldLocation.x = x
        worldLocation.y = y
    }

    fun setVertices(objectVertices: FloatArray) {
        modelVertices = FloatArray(objectVertices.size)
        modelVertices = objectVertices

        //Log.e("objectVertices[0]",""+objectVertices[0]);
        //Log.e("modelVertices[0]",""+modelVertices[0]);

        // Store how many vertices and elements there is for future use
        numElements = modelVertices.size

        //Log.e("numElements",""+numElements);
        numVertices = numElements / ELEMENTS_PER_VERTEX

        // Initialize the vertices ByteBuffer object based on the
        // number of vertices in the ship design and the number of
        // bytes there are in the float type
        vertices = ByteBuffer.allocateDirect(
            numElements
                    * FLOAT_SIZE
        )
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        // Add the ship into the ByteBuffer object
        vertices?.put(modelVertices)
    }

    fun draw(viewportMatrix: FloatArray?) {

        // tell OpenGl to use the glProgram
        GLES20.glUseProgram(glProgram)

        // Set vertices to the first byte
        vertices!!.position(0)
        glVertexAttribPointer(
            aPositionLocation,
            COMPONENTS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertices
        )
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        // Translate model coordinates into world coordinates
        // Make an identity matrix to base our future calculations on
        Matrix.setIdentityM(modelMatrix, 0)
        // Make a translation matrix
        /*
            Parameters
            m	matrix
            mOffset	index into m where the matrix starts
            x	translation factor x
            y	translation factor y
            z	translation factor z
        */Matrix.translateM(modelMatrix, 0, worldLocation.x, worldLocation.y, 0f)

        // Combine the model with the viewport
        // into a new matrix
        Matrix.multiplyMM(viewportModelMatrix, 0, viewportMatrix, 0, modelMatrix, 0)

        /*
            Now rotate the model

            Parameters
            rm	returns the result
            rmOffset	index into rm where the result matrix starts
            a	angle to rotate in degrees
            x	X axis component
            y	Y axis component
            z	Z axis component
        */Matrix.setRotateM(modelMatrix, 0, facingAngle, 0f, 0f, 1.0f)
        Log.d("TAG_1","STEP 1 rotateViewportModelMatrix: ${rotateViewportModelMatrix.contentToString()}")
        // And multiply the rotation matrix into the model-viewport matrix
        Matrix.multiplyMM(rotateViewportModelMatrix, 0, viewportModelMatrix, 0, modelMatrix, 0)
        Log.d("TAG_1","STEP 2 rotateViewportModelMatrix: ${rotateViewportModelMatrix.contentToString()}")

        // Give the matrix to OpenGL
        // glUniformMatrix4fv(uMatrixLocation, 1, false, viewportMatrix, 0);
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, rotateViewportModelMatrix, 0)
        Log.d("TAG_1","STEP 3 rotateViewportModelMatrix: ${rotateViewportModelMatrix.contentToString()}")
        // Assign a color to the fragment shader
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        when (type) {
            Type.SHIP -> GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numVertices)
            Type.ASTEROID -> GLES20.glDrawArrays(GLES20.GL_LINES, 0, numVertices)
            Type.BORDER -> GLES20.glDrawArrays(GLES20.GL_LINES, 0, numVertices)
            Type.STAR -> GLES20.glDrawArrays(GLES20.GL_POINTS, 0, numVertices)
            Type.BULLET -> GLES20.glDrawArrays(GLES20.GL_POINTS, 0, numVertices)
            else -> {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numVertices)
            }
        }
    }

    companion object {
        private var glProgram = -1
    }

    init {
        // Only compile shaders once
        if (glProgram == -1) {
            setGLProgram()

            // tell OpenGl to use the glProgram
            GLES20.glUseProgram(glProgram)

            // Now we have a glProgram we need the locations
            // of our three GLSL variables.
            // We will use these when we call draw on the object.
            uMatrixLocation = GLES20.glGetUniformLocation(glProgram, U_MATRIX)
            aPositionLocation = GLES20.glGetAttribLocation(glProgram, A_POSITION)
            uColorLocation = GLES20.glGetUniformLocation(glProgram, U_COLOR)
        }

        // Set the object as active
        isActive = true
    }
}