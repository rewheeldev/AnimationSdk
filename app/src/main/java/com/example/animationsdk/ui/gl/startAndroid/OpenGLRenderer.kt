package com.example.animationsdk.ui.gl.startAndroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.example.animationsdk.R
import com.example.animationsdk.ui.gl.sdk.asSortedFloatBuffer
import com.example.animationsdk.ui.gl.sdk.createDefaultRectangleVertices
import com.example.animationsdk.ui.gl.sdk.internal.getOpenGlInfo
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private var aPositionLocation = 0
    private var aTextureLocation = 0
    private var uTextureUnitLocation = 0
    private var uMatrixLocation = 0
    private var programId = 0
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mMatrix = FloatArray(16)

    //    private var texture = 0
    private var textureArray = mutableListOf<Int>()
    override fun onSurfaceCreated(arg0: GL10, arg1: EGLConfig) {
        initBitmap(context, orcBitmaps, 650, 650)

        //region done
        GLES32.glClearColor(0.2f, 0.2f, 0.2f, 0.2f)
        GLES32.glEnable(GLES32.GL_DEPTH_TEST)
        createAndUseProgram()
        locations
        //endregion
        orcBitmaps.forEach {
            val texture = TextureUtils.loadTexture(it)
            textureArray.add(texture)
        }
        orcBitmaps.clear()
        createViewMatrix()
    }

    override fun onSurfaceChanged(arg0: GL10, width: Int, height: Int) {
        GLES32.glViewport(0, 0, width, height)
        createProjectionMatrix(width, height)
        bindMatrix()
    }

    private fun createAndUseProgram() {
        //done
        val vertexShaderId: Int = ShaderUtils.createShader(GLES32.GL_VERTEX_SHADER, VERTEX_SHADER)
        val fragmentShaderId: Int =
            ShaderUtils.createShader(GLES32.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        GLES32.glUseProgram(programId)
    }

    private val locations: Unit
        private get() {
            //done
            aPositionLocation = GLES32.glGetAttribLocation(programId, "a_Position")
            aTextureLocation = GLES32.glGetAttribLocation(programId, "a_Texture")
            uTextureUnitLocation = GLES32.glGetUniformLocation(programId, "u_TextureUnit")
            uMatrixLocation = GLES32.glGetUniformLocation(programId, "u_Matrix")
        }

    /**
     * метод связывает текстуру с координатами и рисует ее
     */
    private fun drawRectTexture(texture: Int, x: Float, y: Float, width: Float, height: Float) {
        val array = createDefaultRectangleVertices(x, y, width, height)
        bindData(texture, array.asSortedFloatBuffer())
        //0 это первый индекс вершины в масиве точек
        //4 количество точек на основании которых создается прямоугольник
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, 0, 4)
    }

    private fun bindData(texture: Int, vertexData: FloatBuffer) {
        // region координаты вершин
        vertexData.position(0)
        GLES32.glVertexAttribPointer(
            aPositionLocation, POSITION_COUNT, GLES32.GL_FLOAT,
            false, STRIDE, vertexData
        )
        GLES32.glEnableVertexAttribArray(aPositionLocation)
        //endregion

        //region координаты текстур
        vertexData.position(POSITION_COUNT)
        GLES32.glVertexAttribPointer(
            aTextureLocation, TEXTURE_COUNT, GLES32.GL_FLOAT,
            false, STRIDE, vertexData
        )
        GLES32.glEnableVertexAttribArray(aTextureLocation)
        //endregion

        // помещаем текстуру в target 2D юнита 0
        GLES32.glActiveTexture(GLES32.GL_TEXTURE0)
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, texture)

        // юнит текстуры
        GLES32.glUniform1i(uTextureUnitLocation, 0)
    }

    private fun createProjectionMatrix(width: Int, height: Int) {
        Log.i("TAG_1", "width: $width, height: $height")
        var ratio = 1f
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        val near = 2f
        val far = 1000f
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio
            right *= ratio

        } else {
            ratio = height.toFloat() / width
            bottom *= ratio
            top *= ratio
        }
        Log.i(
            "TAG_1", "ratio: $ratio, " +
                    "left: $left, right: $right, bottom: $bottom, top: $top, " +
                    "near: $near, far: $far"
        )
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
    }

    private fun createViewMatrix() {
        // точка положения камеры
        val eyeX = 0f
        val eyeY = 0f
        val eyeZ = 100f

        // точка направления камеры
        val centerX = 0f
        val centerY = 0f
        val centerZ = 0f

        // up-вектор
        val upX = 0f
        val upY = 1f
        val upZ = 0f

        Matrix.setLookAtM(
            mViewMatrix,
            0,
            eyeX,
            eyeY,
            eyeZ,
            centerX,
            centerY,
            centerZ,
            upX,
            upY,
            upZ
        )
    }

    private fun bindMatrix() {
        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        GLES32.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
    }

    private var currentFrame = 0
    private var orcCurrentFrame = 0


    override fun onDrawFrame(arg0: GL10) {
        measureFPS {
            GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT or GLES32.GL_DEPTH_BUFFER_BIT)
            //region Включаем прозрачность
            GLES32.glEnable(GLES32.GL_BLEND);
            GLES32.glBlendFunc(GLES32.GL_SRC_ALPHA, GLES32.GL_ONE_MINUS_SRC_ALPHA);
            //endregion
            drawRectTexture(
                textureArray[orcCurrentFrame % textureArray.size],
                (currentFrame % 99).toFloat() / 50,
                (currentFrame % 99).toFloat() / 50,
                2f, 2f
            )
            drawRectTexture(
                textureArray[(orcCurrentFrame + 4) % textureArray.size],
                -1f,
                -1f,
                6f,
                6f
            )

            if (currentFrame % 6 == 0) {
                orcCurrentFrame += 1
            }
            currentFrame += 1
        }
        getOpenGlInfo()
//        orcBitmaps[orcCurrentFrame % orcBitmaps.size].recycle()
    }


    val orcBitmaps = mutableListOf<Bitmap>()
    fun initBitmap(
        context: Context,
        orcBitmaps: MutableList<Bitmap>,
        orcFrameWidth: Int,
        orcFrameHeight: Int
    ) {
        val orcBitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.orc_archer_0)
        val tmpRowCount = 2//8
        val tmpColCount = 8//32
        val partImgSizeX = orcBitmap.width / 32
        val partImgSizeY = orcBitmap.height / 8
        Log.d(
            "TAG_1",
            "orcBitmap.width:${orcBitmap.width}, partImgSizeX = $partImgSizeX, orcBitmap.height: ${orcBitmap.height}, partImgSizeY = $partImgSizeY"
        )

        for (row in 0 until tmpRowCount) {
            Log.i("TAG_1", "row: $row * partImgSizeY: $partImgSizeY = ${row * partImgSizeY}")
            for (colm in 0 until tmpColCount) {
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

    fun measureFPS(run: () -> Unit): Long {
        val startTime = System.currentTimeMillis()
        run.invoke()
        val endTime = System.currentTimeMillis() - startTime
        if (endTime <= 0) return 0
        val fps = 1000 / endTime
//        Log.d("TAG_1", "FPS: $fps")
        return fps
    }

    companion object {
        internal const val POSITION_COUNT = 3
        internal const val TEXTURE_COUNT = 2
        internal const val STRIDE = (POSITION_COUNT + TEXTURE_COUNT) * 4
    }
}

