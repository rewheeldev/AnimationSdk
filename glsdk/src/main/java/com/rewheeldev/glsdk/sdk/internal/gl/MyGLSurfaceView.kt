package com.rewheeldev.glsdk.sdk.internal.gl

import android.content.Context
import android.opengl.GLSurfaceView
import com.rewheeldev.glsdk.sdk.api.IScene
import com.rewheeldev.glsdk.sdk.api.scene.Scene2D
import com.rewheeldev.glsdk.sdk.internal.controllers.ShapeController
import utils.Color

class MyGLSurfaceView(context: Context, shapeController: ShapeController, onReady: () -> Unit) :
    GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {
        setEGLContextClientVersion(3)
        renderer = MyGLRenderer(shapeController, onReady)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun setScene(scene: IScene) {
        when (scene) {
            is Scene2D -> {
                renderer.scene = scene.scene
            }
            else -> {
                //todo
            }
        }
    }
    fun setBackgroundColor(color: Color){
        renderer.setBackgroundColor(color)
    }
}