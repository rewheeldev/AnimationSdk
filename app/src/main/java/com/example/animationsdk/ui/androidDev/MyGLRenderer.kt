package com.example.animationsdk.ui.androidDev

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.animationsdk.ui.gl.sdk.CameraView
import com.example.animationsdk.ui.gl.sdk.Color
import com.example.animationsdk.ui.gl.sdk.Coords
import com.example.animationsdk.ui.gl.sdk.ViewScene
import com.example.animationsdk.ui.gl.sdk.internal.CoordsPerVertex
import com.example.animationsdk.ui.gl.startAndroid.ShaderUtils.createShader
import com.example.animationsdk.ui.gl.startAndroid.fragmentShaderCode
import com.example.animationsdk.ui.gl.startAndroid.vertexShaderCode
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {
    private var scene = ViewScene()
    private lateinit var mTriangle: Triangle
    private lateinit var mTriangle2: Triangle

    var triangleProgram: Int = 0
    var vertexShader: Int = 0
    var fragmentShader: Int = 0

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.2f, 0.2f, 0.2f, 1.0f)

        // initialize a triangle
        vertexShader = createShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        triangleProgram = GLES20.glCreateProgram().also {
            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)
            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)
            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }

        mTriangle = Triangle(coords = triangleCoordsPrevData, program = triangleProgram)
        mTriangle2 = Triangle(
            coords = triangleCoordsPrevData2,
            program = triangleProgram,
            color = Color(0.5f, 1f, 0f, 0f)
        )
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        // Set the camera position (View matrix)
        val matrix = scene.update()

        mTriangle.draw(matrix)
        mTriangle2.draw(matrix)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        scene.reInitScene(width, height)
    }

    fun bindCamera(camera: CameraView) {
        scene.camera = camera
    }
}

// number of coordinates per vertex in this array
const val COORDS_PER_VERTEX = 3
var triangleCoordsPrevData = floatArrayOf(     // in counterclockwise order:
    0.0f, 0.622008459f, 0.0f,      // top
    -0.5f, -0.311004243f, 0.0f,    // bottom left
    0.5f, -0.311004243f, 0.0f      // bottom right
)
val offset = 0.2f
var triangleCoordsPrevData2 = floatArrayOf(     // in counterclockwise order:
    0.0f + offset, 0.622008459f + offset,
    -0.5f + offset, -0.311004243f + offset,
    0.5f + offset, -0.311004243f + offset
)
//
//val triangleCoords = Coords(triangleCoordsPrevData, CoordsPerVertex.VERTEX_3D)
//val triangleCoords2 = Coords(triangleCoordsPrevData2, CoordsPerVertex.VERTEX_2D)

class Triangle(
    private var program: Int,
    private val coords: FloatArray,
    private val color: Color = Color.WHITE,
) {
    private val vertexCount: Int = coords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    var vertexBuffer: FloatBuffer = //coords.asSortedFloatBuffer()
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(coords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(coords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }

    fun draw(vpMatrix: FloatArray) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program)
        // get handle to vertex shader's vPosition member
        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition").also {
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)
            // Prepare the triangle coordinate data

            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )
            // get handle to fragment shader's vColor member
            GLES20.glGetUniformLocation(program, "vColor").also { colorHandle ->
                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color.toFloatArray(), 0)
            }
            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }

        // pass in the calculated transformation matrix
        // get handle to shape's transformation matrix
        val vPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vpMatrix, 0)
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

}