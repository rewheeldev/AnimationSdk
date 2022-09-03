package com.example.animationsdk.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.animationsdk.R

class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle), Runnable {

    private lateinit var canvas: Canvas
    private val ourHolder: SurfaceHolder = holder
    private var gameThread: Thread? = null
    private val paint: Paint = Paint()

    init {
//        ourHolder.setFixedSize(1000,1000)
    }
    @Volatile
    var playing = true

    private val orcFrameWidth = 650
    private val orcFrameHeight = 650

    var orcXPosition = 500f
    val orcBitmaps = mutableListOf<Bitmap>()
    private val orcFrameCount = 256
    private val orcFrameCount2 = 256

    private var orcCurrentFrame = 0
    private var orcCurrentFrame2 = 0


    // What time was it when we last changed frames
    private var lastFrameChangeTime: Long = 0

    // How long should each frame last
    private val frameLengthInMilliseconds = 70

    // A rectangle to define an area of the
    // sprite sheet that represents 1 frame
    private val orcFrameToDraw = Rect(
        0,
        0,
        orcFrameWidth,
        orcFrameHeight
    )
    var whereToDrawOrc = RectF(
        orcXPosition, 100F,
        orcXPosition + orcFrameWidth,
        orcFrameHeight.toFloat()
    )
    private val orcFrameToDraw2 = Rect(
        0,
        0,
        orcFrameWidth,
        orcFrameHeight
    )
    var whereToDrawOrc2 = RectF(
        orcXPosition, 100F,
        orcXPosition + 500 + orcFrameWidth,
        orcFrameHeight.toFloat()
    )


    //region star

    val starBitmaps = mutableListOf<Bitmap>()
    private val starFrameWidth = 200
    private val starFrameHeight = 200


    var starXPosition = 1200F
    var starYPosition = 100f

    private val starFrameCount = 30
    private var starCurrentFrame = 0


    var whereToDrawStar = RectF(
        starXPosition, starYPosition,
        starXPosition + starFrameWidth,
        starFrameHeight.toFloat()
    )

    private val starFrameToDraw = Rect(
        0,
        0,
        starFrameWidth,
        starFrameHeight
    )

    //endregion

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

    fun getCurrentFrame() {
        val time = System.currentTimeMillis()
        if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
            lastFrameChangeTime = time
            orcCurrentFrame++
            if (orcCurrentFrame >= orcFrameCount) {
                orcCurrentFrame = 0
            }
            orcCurrentFrame2++
            if (orcCurrentFrame2 >= orcFrameCount2) {
                orcCurrentFrame2 = 0
            }
            //region star
            starCurrentFrame++
            if (starCurrentFrame >= starFrameCount) {
                starCurrentFrame = 0
            }
            //endregion
        }
    }

    fun update() {

    }

    fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()

            canvas.drawColor(Color.WHITE)

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0))

            // Make the text a bit bigger
            paint.setTextSize(45F)

            // Display the current fps on the screen
            canvas.drawText("FPS:$fps", 20F, 40F, paint)



            // New drawing code goes here
            //region First orc
            whereToDrawOrc.set(
                orcXPosition,
                200f,
                (orcXPosition + orcFrameWidth),
                orcFrameHeight.toFloat()
            )
            canvas.drawBitmap(
                orcBitmaps[orcCurrentFrame],
                orcFrameToDraw,
                whereToDrawOrc, paint
            )
            //endregion

            //region Second orc
            whereToDrawOrc2.set(
                orcXPosition + 500,
                200f,
                (orcXPosition + 500 + orcFrameWidth),
                orcFrameHeight.toFloat()
            )
            canvas.drawBitmap(
                orcBitmaps[orcCurrentFrame2],
                orcFrameToDraw2,
                whereToDrawOrc2, paint
            )
            //endregion

            //region Star
            whereToDrawStar.set(
                starXPosition,
                starYPosition,
                (starXPosition + starFrameWidth),
                (starFrameHeight.toFloat() + starYPosition)
            )

            canvas.drawBitmap(
                starBitmaps[starCurrentFrame],
                starFrameToDraw,
                whereToDrawStar, paint
            )
            //endregion
            getCurrentFrame()
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

    fun initBitmap(
        context: Context,
        orcBitmaps: MutableList<Bitmap>
    ) {
        val orcBitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.orc_archer_0)
        val partImgSizeX = orcBitmap.width / 32
        val partImgSizeY = orcBitmap.height / 8
        Log.d(
            "TAG_1",
            "orcBitmap.width:${orcBitmap.width}, partImgSizeX = $partImgSizeX, orcBitmap.height: ${orcBitmap.height}, partImgSizeY = $partImgSizeY"
        )

        for (row in 0 until 8) {
            Log.i("TAG_1", "row: $row * partImgSizeY: $partImgSizeY = ${row * partImgSizeY}")
            for (colm in 0 until 32) {
                Log.i("TAG_1", "colm: $colm * partImgSizeX: $partImgSizeX = ${colm * partImgSizeX}")
                var bitmap = Bitmap.createBitmap(
                    orcBitmap,
                    colm * partImgSizeX,
                    row * partImgSizeY,
                    partImgSizeX,
                    partImgSizeY
                )
//                bitmapExplosion = bitmap
                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    orcFrameWidth,
                    orcFrameHeight,
                    false
                )
                orcBitmaps.add(bitmap)
            }
        }
//star
        val starBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.star)
        val starPartImgSizeX = starBitmap.width / 6
        val starPartImgSizeY = starBitmap.height / 5
        Log.d(
            "TAG_1",
            "orcBitmap.width:${starBitmap.width}, partImgSizeX = $starPartImgSizeX, orcBitmap.height: ${starBitmap.height}, starPartImgSizeY = $starPartImgSizeY"
        )
        for (row in 0 until 5) {
            Log.i("TAG_1", "row: $row")
            for (colm in 0 until 6) {
                Log.i("TAG_1", "colm: $colm")
                var bitmap = Bitmap.createBitmap(
                    starBitmap,
                    colm * starPartImgSizeX,
                    row * starPartImgSizeY,
                    starPartImgSizeX,
                    starPartImgSizeY
                )
                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    starFrameWidth,
                    starFrameHeight,
                    false
                )
                starBitmaps.add(bitmap)
            }
        }

        Log.d("TAG_1", "orc bitmaps size: ${orcBitmaps.size}")
    }

    init {
        initBitmap(context, orcBitmaps)
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