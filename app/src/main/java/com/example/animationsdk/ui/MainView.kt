package com.example.animationsdk.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle), Runnable {

    private lateinit var canvas: Canvas
    private val ourHolder: SurfaceHolder = holder
    private var gameThread: Thread? = null
    private val paint: Paint = Paint()

    @Volatile
    var playing = true


    // This is used to help calculate the fps
    private var timeThisFrame: Long = 0

    // This variable tracks the game frame rate
    var fps: Long = 0

    override fun run() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            val startFrameTime = System.currentTimeMillis()

            update()
            draw()
            control()

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    fun update() {

    }

    fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()

            canvas.drawColor(Color.BLACK)

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0))

            // Make the text a bit bigger
            paint.setTextSize(45F)

            // Display the current fps on the screen
            canvas.drawText("FPS:$fps", 20F, 40F, paint)

            ourHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control() {
        try {
            Thread.sleep(20)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun pause() {
        playing = false
        try {
            gameThread!!.join()
        } catch (ex: InterruptedException) {
            ex.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

}