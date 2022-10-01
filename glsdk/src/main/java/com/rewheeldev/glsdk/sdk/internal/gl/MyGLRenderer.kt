package com.rewheeldev.glsdk.sdk.internal.gl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.rewheeldev.glsdk.sdk.api.IViewScene
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.ViewScene
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.FRAGMENT_SHADER_CODE
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.VERTEX_SHADER_CODE
import com.rewheeldev.glsdk.sdk.internal.util.ShaderUtils.createShader
import utils.Color
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer(val shapeController: ShapeController, private val onReady: () -> Unit) :
    GLSurfaceView.Renderer {
    private lateinit var backgroundColor: Color
    private lateinit var sceneInternal: ViewScene
    var scene: IViewScene
        set(value) {
            sceneInternal = when (value) {
                is ViewScene -> {
                    value
                }
                else -> {
                    throw IllegalArgumentException("Unknown")
                }
            }
        }
        get() {
            return sceneInternal
        }


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
                    borderColor = shape.borderColor,
                    borderWidth = shape.borderWidth,
                    borderType = shape.borderType
                )
            )
        }

        shapeController.removeListener { shape ->
            val foundShape = shapeList.firstOrNull { it.id == shape.id }
            if (foundShape != null) shapeList.remove(foundShape)
        }
    }

    fun setBackgroundColor(color: Color) {
        backgroundColor = color
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color

//        GLES20.glClearColor(0.2f, 0.2f, 0.2f, 1.0f)
//        GLES20.glClearColor(
//            backgroundColor.r,
//            backgroundColor.g,
//            backgroundColor.b,
//            backgroundColor.a
//        )

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

        GLES20.glClearColor(
            backgroundColor.r,
            backgroundColor.g,
            backgroundColor.b,
            backgroundColor.a
        )
        Log.d("TAG_RenderFrame","backgroundColor: r: ${backgroundColor.r} g: ${backgroundColor.g} b: ${backgroundColor.b} a: ${backgroundColor.a} " )
        // Redraw background color
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Set the camera position (View matrix)
        val matrix = sceneInternal.update()
        shapeList.forEach { shape ->
            shape.draw(matrix)
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        sceneInternal.reInitScene(width, height)
    }

    fun bindCamera(camera: CameraView) {
        sceneInternal.camera = camera
    }
}