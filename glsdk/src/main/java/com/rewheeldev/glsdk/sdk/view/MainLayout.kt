package com.rewheeldev.glsdk.sdk.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.api.IDraw
import com.rewheeldev.glsdk.sdk.internal.draw.RwDraw
import com.rewheeldev.glsdk.sdk.internal.gl.MyGLSurfaceView


class MainLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private lateinit var mainView: MyGLSurfaceView
    private val draw = RwDraw()

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

    fun draw(): IDraw {
        return draw
    }
}