package com.example.animationsdk.ui.androidDev

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
    fun createViewMatrix(
        cameraPosition: OpenGLRenderer.Position3D,
        cameraDirectionPoint: OpenGLRenderer.Position3D,
        upVector: OpenGLRenderer.Position3D
    ){
        renderer.createViewMatrix(cameraPosition, cameraDirectionPoint, upVector)
    }
}