package com.example.animationsdk.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.animationsdk.ui.androidDev.MyGLSurfaceView
import com.example.animationsdk.ui.gl.sdk.CameraView


class MainLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private lateinit var mainView: MyGLSurfaceView

    fun initialize() {
        mainView = MyGLSurfaceView(context)
        addView(mainView)
    }

    fun bindCamera(camera: CameraView) {
        mainView.bindCamera(camera)
    }

    /**
     * The function `pause()` is called when the user presses the back button on their device
     */
    fun pause() {
        mainView.onPause()
    }

    /**
     * Resumes the game
     */
    fun resume() {
        mainView.onResume()
    }
}