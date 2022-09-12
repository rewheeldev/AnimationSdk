package com.rewheeldev.glsdk.sdk.internal.gl

import android.content.Context
import android.opengl.GLSurfaceView
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController

class MyGLSurfaceView(context: Context, shapeController: ShapeController, onReady: () -> Unit) :
    GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {
        setEGLContextClientVersion(3)
        renderer = MyGLRenderer(shapeController, onReady)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun bindCamera(camera: CameraView) {
        renderer.bindCamera(camera)
    }
}