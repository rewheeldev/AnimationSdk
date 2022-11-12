package com.rewheeldev.glsdk.sdk.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.rewheeldev.glsdk.sdk.api.IDraw
import com.rewheeldev.glsdk.sdk.api.IShapeController
import com.rewheeldev.glsdk.sdk.api.constants.OPEN_GL_VERSION_3
import com.rewheeldev.glsdk.sdk.api.factories.IShapeFactory
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.ShapeFactory
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import com.rewheeldev.glsdk.sdk.internal.draw.RwDraw
import com.rewheeldev.glsdk.sdk.internal.gl.MyGLSurfaceView
import utils.Color
import java.util.concurrent.atomic.AtomicBoolean


class MainLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private lateinit var mainView: MyGLSurfaceView
    private val draw = RwDraw()
    private val shapeController = ShapeController()
    private val shapeFactory = ShapeFactory()
    private val rendererSet = AtomicBoolean(false)

    val backgroundColor: Color
        get() {
            return checkRenderSet { mainView.backgroundColor }
        }

    /**
     * Gl context client version
     *
     * by default the system will set version 3
     * You can set your own version
     */
    var glContextClientVersion: Int
        get() {
            return checkRenderSet { mainView.glContextClientVersion }
        }
        set(value) {
            checkRenderSet { mainView.glContextClientVersion = value }
        }

    fun initialize(
        glContextClientVersion: Int = OPEN_GL_VERSION_3,
        backgroundColor: Color = Color(0.2f, 0.2f, 0.2f, 1.0f),
        onReady: () -> Unit
    ) {
        mainView = MyGLSurfaceView(
            context,
            shapeController,
            glContextClientVersion,
            backgroundColor,
            onReady
        )
        rendererSet.set(true)
        addView(mainView)
    }

    fun bindCamera(camera: CameraView) {
        checkRenderSet {
            mainView.bindCamera(camera)
        }
    }

    /**
     * The function `pause()` is called when the user presses the back button on their device
     */
    fun pause() {
        checkRenderSet {
            mainView.onPause()
        }
    }

    /**
     * Resumes the game
     */
    fun resume() {
        checkRenderSet {
            mainView.onResume()
        }
    }

    private fun <T> checkRenderSet(block: () -> T): T {
        return if (rendererSet.get())
            block()
        else
            throw java.lang.IllegalStateException("First you need to call initialize(...) method")
    }

    fun draw(): IDraw {
        return draw
    }

    fun getShapeController(): IShapeController {
        return shapeController
    }

    fun getShapeFactory(): IShapeFactory {
        return shapeFactory
    }
}