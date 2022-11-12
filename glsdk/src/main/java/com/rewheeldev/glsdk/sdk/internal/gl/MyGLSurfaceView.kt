package com.rewheeldev.glsdk.sdk.internal.gl

import android.content.Context
import android.opengl.GLSurfaceView
import com.rewheeldev.glsdk.sdk.api.constants.OPEN_GL_VERSION_3
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import utils.Color

class MyGLSurfaceView(
    context: Context,
    shapeController: ShapeController,
    var glContextClientVersion: Int = OPEN_GL_VERSION_3,
    backgroundColor: Color,
    onReady: () -> Unit
) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer
    val backgroundColor: Color
        get() = renderer.backgroundColor

    init {
        setEGLContextClientVersion(glContextClientVersion)
        renderer = MyGLRenderer(shapeController, backgroundColor, onReady)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun bindCamera(camera: CameraView) {
        renderer.bindCamera(camera)
    }
}