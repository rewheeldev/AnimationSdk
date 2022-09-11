package com.example.animationsdk.ui.gl.tryThree

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet


class AsteroidsView @JvmOverloads constructor(
    context: Context,
    screenX: Int,
    screenY: Int,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
    var gm: GameManager

    init {
        gm = GameManager(screenX, screenY)

        // Which version of OpenGl we are using
        setEGLContextClientVersion(2)

        // Attach our renderer to the GLSurfaceView
        setRenderer(AsteroidsRenderer(gm))
    }
}