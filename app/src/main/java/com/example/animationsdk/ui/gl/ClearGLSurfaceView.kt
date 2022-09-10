package com.example.animationsdk.ui.gl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer


class ClearGLSurfaceView(context: Context, attrs: AttributeSet? = null) :
    GLSurfaceView(context, attrs) {
    //    val mRenderer = BitmapRenderer(context, context.resources)
    val mRenderer = OpenGLRenderer(context)

    init {
        setEGLContextClientVersion(3)
        setRenderer(mRenderer)

    }
    fun createViewMatrix(
        cameraPosition: OpenGLRenderer.Position3D,
        cameraDirectionPoint: OpenGLRenderer.Position3D,
        upVector: OpenGLRenderer.Position3D
    ){
        mRenderer.createViewMatrix(cameraPosition, cameraDirectionPoint, upVector)
    }
}