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
        setEGLContextClientVersion(2)
        setRenderer(mRenderer)
//       setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY)

    }
}