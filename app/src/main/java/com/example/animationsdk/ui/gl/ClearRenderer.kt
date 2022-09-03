package com.example.animationsdk.ui.gl

import android.content.Context
import android.graphics.*
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.util.Log
import com.example.animationsdk.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ClearRenderer(private val context: Context) : GLSurfaceView.Renderer {
    //
    private var mRed = 0f
    private var mGreen = 0f
    private var mBlue = 0f
    private lateinit var canvas: Canvas
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

    init {
//        initBitmap(context.applicationContext, orcBitmaps)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // Do nothing special.
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.glViewport(0, 0, width, height)
    }
    private val paint: Paint = Paint()

    override fun onDrawFrame(gl: GL10?) {
        // Capture the current time in milliseconds in startFrameTime
        val startFrameTime = System.currentTimeMillis()
//        gl?.glClearColor(mRed, mGreen, mBlue, 1.0f)
        // Calculate the fps this frame
        // We can then use the result to
        // time animations and more.
        timeThisFrame = System.currentTimeMillis() - startFrameTime
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame
        }
        canvas = Canvas()
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 10)
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

        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
//        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, orcBitmaps.first(), 0)
    }

    fun draw() {

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

        Log.d("TAG_1", "orc bitmaps size: ${orcBitmaps.size}")
    }

    fun setColor(r: Float, g: Float, b: Float) {
        mRed = r
        mGreen = g
        mBlue = b
    }
}