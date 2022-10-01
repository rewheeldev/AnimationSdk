package com.rewheeldev.glsdk.sdk.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.rewheeldev.glsdk.sdk.api.IDraw
import com.rewheeldev.glsdk.sdk.api.IScene
import com.rewheeldev.glsdk.sdk.api.IShapeController
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import com.rewheeldev.glsdk.sdk.internal.draw.RwDraw
import com.rewheeldev.glsdk.sdk.internal.gl.MyGLSurfaceView
import utils.Color

class MainLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private lateinit var mainView: MyGLSurfaceView
    private val draw = RwDraw()
    private val shapeController = ShapeController()

    fun initialize(backgroundColor: Color = Color.BLACK, onReady: () -> Unit) {
        mainView = MyGLSurfaceView(context, shapeController, onReady)
        mainView.setBackgroundColor(backgroundColor)
        addView(mainView)
    }

    fun setScene(scene: IScene) {
        mainView.setScene(scene)
    }

    fun setBackgroundColor(color: Color) {
        mainView.setBackgroundColor(color)
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

    fun getShapeController(): IShapeController {
        return shapeController
    }
}