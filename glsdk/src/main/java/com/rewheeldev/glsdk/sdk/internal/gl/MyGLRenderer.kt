package com.rewheeldev.glsdk.sdk.internal.gl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.rewheeldev.glsdk.sdk.internal.CameraProperties
import com.rewheeldev.glsdk.sdk.internal.ViewScene
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.FRAGMENT_SHADER_CODE
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.VERTEX_SHADER_CODE
import com.rewheeldev.glsdk.sdk.internal.util.ShaderUtils.createShader
import utils.Color
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer(
    val shapeController: ShapeController,
    val backgroundColor: Color,
    private val onReady: () -> Unit
) :
    GLSurfaceView.Renderer {
    private var scene = ViewScene()

    var triangleProgram: Int = 0
    var vertexShader: Int = 0
    var fragmentShader: Int = 0
    val shapeList = ArrayList<IShapeDraw>()


    init {
        shapeController.addListener { shape ->
            shapeList.add(
                Figure(
                    id = shape.id,
                    programId = triangleProgram,
                    coords = shape.coords,
                    colors = shape.colors,
                    border = shape.border,
                    figureType = shape.figureType
                )
            )
        }

        shapeController.removeListener { shape ->
            val foundShape = shapeList.firstOrNull { it.id == shape.id }
            if (foundShape != null) shapeList.remove(foundShape)
        }
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        //GLSurfaceView calls this when the surface is created. This happens the first
        //time our application is run, and it may also be called when the device
        //wakes up or when the user switches back to our activity. In practice, this
        //means that this method may be called multiple times while our application
        //is running.

        // Set the background frame color
        GLES20.glClearColor(
            backgroundColor.r,
            backgroundColor.g,
            backgroundColor.b,
            backgroundColor.a
        )

        // initialize a triangle
        vertexShader = createShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE)
        fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE)
        triangleProgram = GLES20.glCreateProgram().also {
            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)
            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)
            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }

        onReady()
    }

    // add(TriangleInternal(programId = triangleProgram, shape.coords, shape.color))
    override fun onDrawFrame(unused: GL10) {
        // Set the background frame color
        GLES20.glClearColor(
            backgroundColor.r,
            backgroundColor.g,
            backgroundColor.b,
            backgroundColor.a
        )

        //GLSurfaceView calls this when it’s time to draw a frame. We must draw
        //something, even if it’s only to clear the screen. The rendering buffer will
        //be swapped and displayed on the screen after this method returns, so if
        //we don’t draw anything, we’ll probably get a bad flickering effect.

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        // Set the camera position (View matrix)
        val matrix = scene.update()
        shapeList.forEach { shape ->
            shape.draw(matrix)
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {

        //GLSurfaceView calls this after the surface is created and whenever the size
        //has changed. A size change can occur when switching from portrait to
        //landscape and vice versa.

        scene.reInitScene(width, height)
    }

    fun bindCamera(camera: CameraProperties) {
        scene.camera = camera
    }
}