package com.example.animationsdk.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.animationsdk.ui.androidDev.MyGLSurfaceView
import com.example.animationsdk.ui.gl.ClearGLSurfaceView
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer
//import com.example.animationsdk.ui.gl.ClearGLSurfaceView

class MainLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    //    private lateinit var mainView: MainView
//    private lateinit var mainView: ClearGLSurfaceView
    private lateinit var mainView: MyGLSurfaceView
//    private lateinit var mainView: AsteroidsView

    fun initialize() {
//        mainView = AsteroidsView(context, 390 * 2, 220 * 2)
//        mainView = ClearGLSurfaceView(context)
        mainView = MyGLSurfaceView(context)
        addView(mainView)
    }
    fun createViewMatrix(
        cameraPosition: OpenGLRenderer.Position3D,
        cameraDirectionPoint: OpenGLRenderer.Position3D,
        upVector: OpenGLRenderer.Position3D
    ){
        mainView.createViewMatrix(cameraPosition, cameraDirectionPoint, upVector)
    }
    /**
     * The function `pause()` is called when the user presses the back button on their device
     */
    fun pause() {
//        mainView.pause()
        mainView.onPause()
    }

    /**
     * Resumes the game
     */
    fun resume() {
//        mainView.resume()
        mainView.onResume()
    }
}