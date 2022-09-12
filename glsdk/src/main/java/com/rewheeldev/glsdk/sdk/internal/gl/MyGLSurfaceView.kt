package com.rewheeldev.glsdk.sdk.internal.gl

import android.content.Context
import android.opengl.GLSurfaceView
import com.rewheeldev.glsdk.sdk.internal.CameraView

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {
        setEGLContextClientVersion(3)
        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun bindCamera(camera: CameraView) {
        renderer.bindCamera(camera)
    }
}