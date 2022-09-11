package com.example.animationsdk.ui.gl.startAndroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.opengl.GLU.gluLookAt
import android.opengl.Matrix
import android.util.Log
import com.example.animationsdk.R
import com.example.animationsdk.ui.gl.sdk.Position3D
import com.example.animationsdk.ui.gl.sdk.asSortedFloatBuffer
import com.example.animationsdk.ui.gl.sdk.createDefaultRectangleVertices
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

    var width = 0
    var height = 0

    var cameraPosition = Position3D(0.0f, 0.0f, 10.0f)
    var cameraDirectionPoint = Position3D(0.0f, 0.0f, 5.0f)
//    var upVector = Position3D(3.0f, 5f, 0.0f)
//    var upVector = Position3D(2.0f, 5f, 0.0f)
    var upVector = Position3D(1.0f, 5f, 0.0f)
//    var upVector = Position3D(-0.0f, 5f, 0.0f)
//    var upVector = Position3D(-1.0f, 5f, 0.0f)
//    var upVector = Position3D(-2.0f, 5f, 0.0f)
//    var upVector = Position3D(-3.0f, 5f, 0.0f)

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
        createViewMatrix(
            cameraPosition = cameraPosition,
            cameraDirectionPoint = cameraDirectionPoint,
            upVector = upVector
        )
    }

    override fun onSurfaceChanged(arg0: GL10, width: Int, height: Int) {
        //done
        this.width = width
        this.height = height
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
        bindTextureToVertex(texture, array.asSortedFloatBuffer())
        //0 это первый индекс вершины в масиве точек
        //4 количество точек на основании которых создается прямоугольник
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, 0, 4)
    }

    private fun bindTextureToVertex(texture: Int, vertexData: FloatBuffer) {
        // region координаты вершин
        vertexData.flip()
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

    fun createViewMatrix(
        cameraPosition: Position3D,
        cameraDirectionPoint: Position3D,
        upVector: Position3D
    ) {
        this.cameraPosition = cameraPosition
        this.cameraDirectionPoint = cameraDirectionPoint
        this.upVector = upVector
        GLES32.glViewport(0, 0, width, height)
        createProjectionMatrix(width, height)
        // точка положения камеры
        val (eyeX, eyeY, eyeZ) = cameraPosition

        // точка направления камеры
        val (centerX, centerY, centerZ) = cameraDirectionPoint

        // up-вектор
        val (upX, upY, upZ) = upVector
        Log.d("TAG_1", "cameraPosition: $cameraPosition, cameraDirectionPoint: $cameraDirectionPoint, upVector: $upVector")
        Log.d("TAG_1", "eyeX: $eyeX, eyeY: $eyeY, eyeZ: $eyeZ")
        Log.d("TAG_1", "centerX: $centerX, centerY: $centerY, centerZ: $centerZ")
        Log.d("TAG_1", "upX: $upX, upY: $upY, upZ: $upZ")
        //https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/gluLookAt.xml
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
        bindMatrix()

    }

    fun bindMatrix() {
        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        //https://registry.khronos.org/OpenGL-Refpages/gl4/html/glUniform.xhtml
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
//        getOpenGlInfo()
//        orcBitmaps[orcCurrentFrame % orcBitmaps.size].recycle()
        // точка положения камеры
        val (eyeX, eyeY, eyeZ) = cameraPosition

        // точка направления камеры
        val (centerX, centerY, centerZ) = cameraDirectionPoint

        // up-вектор
        val (upX, upY, upZ) = upVector
        gluLookAt(arg0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
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

