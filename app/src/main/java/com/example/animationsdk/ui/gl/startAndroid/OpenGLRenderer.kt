package com.example.animationsdk.ui.gl.startAndroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.example.animationsdk.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private var vertexData: FloatBuffer? = null
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

        GLES20.glClearColor(0f, 0f, 0f, 1f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        createAndUseProgram()
        locations
        prepareData()
        orcBitmaps.forEach {
            val texture = TextureUtils.loadTexture(it)
            textureArray.add(texture)
        }
        orcBitmaps.clear()
        createViewMatrix()
    }

    override fun onSurfaceChanged(arg0: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        createProjectionMatrix(width, height)
        bindMatrix()
    }

    private fun prepareData() {
//        Смотрим метод prepareData. В массиве vertices мы задаем данные о 4 вершинах, чтобы
//        нарисовать квадрат. Для каждой вершины мы задаем 5 чисел.
//        Первые три – это координаты вершины,
//        а последние две – это координаты соответствующей точки текстуры.
        val vertices = floatArrayOf(
            -1f, 1f, 1f, 0f, 0f,
            -1f, -1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f, 0f,
            1f, -1f, 1f, 1f, 1f
        )
        vertexData = ByteBuffer
//Выделяет новый прямой байтовый буфер.
//Позиция нового буфера будет нулевой, его пределом будет его емкость, его метка будет неопределенной,
// и каждый из его элементов будет инициализирован нулем. Неизвестно, есть ли у него резервный массив.
//Параметры:
//емкость – Емкость нового буфера в байтах.
//Возвращает:
//Новый байтовый буфер
//Броски:
//IllegalArgumentException — если емкость является отрицательным целым числом.
            .allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData!!.put(vertices)
    }

    private fun createAndUseProgram() {
        val vertexShaderId: Int =
            ShaderUtils.createShader(context, GLES20.GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId: Int =
            ShaderUtils.createShader(context, GLES20.GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        GLES20.glUseProgram(programId)
    }

    private val locations: Unit
        private get() {
            aPositionLocation = GLES20.glGetAttribLocation(programId, "a_Position")
            aTextureLocation = GLES20.glGetAttribLocation(programId, "a_Texture")
            uTextureUnitLocation = GLES20.glGetUniformLocation(programId, "u_TextureUnit")
            uMatrixLocation = GLES20.glGetUniformLocation(programId, "u_Matrix")
        }

    private fun bindData(texture: Int) {
        // координаты вершин
        vertexData!!.position(0)
        GLES20.glVertexAttribPointer(
            aPositionLocation, POSITION_COUNT, GLES20.GL_FLOAT,
            false, STRIDE, vertexData
        )
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        // координаты текстур
        vertexData!!.position(POSITION_COUNT)
        GLES20.glVertexAttribPointer(
            aTextureLocation, TEXTURE_COUNT, GLES20.GL_FLOAT,
            false, STRIDE, vertexData
        )
        GLES20.glEnableVertexAttribArray(aTextureLocation)

        // помещаем текстуру в target 2D юнита 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)

        // юнит текстуры
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }

    private fun createProjectionMatrix(width: Int, height: Int) {
        Log.i("TAG_1", "width: $width, height: $height")
        var ratio = 1f
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        val near = 2f
        val far = 12f
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio
            right *= ratio

        } else {
            ratio = height.toFloat() / width
            bottom *= ratio
            top *= ratio
        }
        Log.i("TAG_1", "ratio: $ratio, left: $left, right: $right, bottom: $bottom, top: $top, near: $near, far: $far")
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
    }

    private fun createViewMatrix() {
        // точка положения камеры
        val eyeX = 0f
        val eyeY = 0f
        val eyeZ = 7f

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
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
    }

    private var orcCurrentFrame = 0

    override fun onDrawFrame(arg0: GL10) {
        measureFPS{
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

            bindData(textureArray[orcCurrentFrame % textureArray.size])

            orcCurrentFrame += 1
        }
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

    fun measureFPS(run: () -> Unit): Long {
        val startTime = System.currentTimeMillis()
        run.invoke()
        val endTime = System.currentTimeMillis() - startTime
        val fps = 1000 / endTime
//        Log.d("TAG_1", "FPS: $fps")
        return fps
    }

    companion object {
        private const val POSITION_COUNT = 3
        private const val TEXTURE_COUNT = 2
        private const val STRIDE = (POSITION_COUNT
                + TEXTURE_COUNT) * 4
    }
}
