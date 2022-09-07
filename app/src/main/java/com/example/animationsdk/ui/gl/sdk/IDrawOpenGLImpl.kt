package com.example.animationsdk.ui.gl.sdk

import android.opengl.GLES32
import com.example.animationsdk.ui.gl.sdk.internal.NOT_INIT
import com.example.animationsdk.ui.gl.startAndroid.FRAGMENT_SHADER
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer
import com.example.animationsdk.ui.gl.startAndroid.VERTEX_SHADER
import java.nio.FloatBuffer

class IDrawOpenGLImpl {

    private var aPositionLocation = 0
    private var aTextureLocation = 0
    private var uTextureUnitLocation = 0
    private var uMatrixLocation = 0
    private var programId = 0

    /**
     *
     *
     */
    fun initialize() {
        //создание компонентов GL (Shader & fragment)
        val vertexShaderId: Int = createShader(GLES32.GL_VERTEX_SHADER, VERTEX_SHADER)
        val fragmentShaderId: Int = createShader(GLES32.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)

        programId = createProgram(vertexShaderId, fragmentShaderId)

        GLES32.glUseProgram(programId)

        //region регистрация и привязка переменных которые определенны в шейдерах
        aPositionLocation = GLES32.glGetAttribLocation(programId, "a_Position")
        aTextureLocation = GLES32.glGetAttribLocation(programId, "a_Texture")
        uTextureUnitLocation = GLES32.glGetUniformLocation(programId, "u_TextureUnit")
        uMatrixLocation = GLES32.glGetUniformLocation(programId, "u_Matrix")
        //endregion

        GLES32.glClearColor(0.2f, 0.2f, 0.2f, 0.2f)
        GLES32.glEnable(GLES32.GL_DEPTH_TEST)
    }

    private fun createShader(type: Int, shaderText: String): Int {

        val shaderId = GLES32.glCreateShader(type)
        if (shaderId == 0) {
            return NOT_INIT
        }

        GLES32.glShaderSource(shaderId, shaderText)
        GLES32.glCompileShader(shaderId)

        if (checkShaderStatusCreation(shaderId)) return NOT_INIT
        return shaderId
    }

    private fun checkShaderStatusCreation(shaderId: Int): Boolean {
        val compileStatus = IntArray(1)
        GLES32.glGetShaderiv(shaderId, GLES32.GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus.first() == 0) {
            GLES32.glDeleteShader(shaderId)
            return true
        }
        return false
    }

    fun onSurfaceChanged(width: Int, height: Int, x: Int = 0, y: Int = 0) {
        //Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
        //Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
        GLES32.glViewport(x, y, width, height)
        //todo continue here
//        createProjectionMatrix(width, height)
//        bindMatrix()
    }

    private fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = GLES32.glCreateProgram()

        if (programId == 0) {
            return NOT_INIT
        }

        GLES32.glAttachShader(programId, vertexShaderId)
        GLES32.glAttachShader(programId, fragmentShaderId)
        GLES32.glLinkProgram(programId)

        if (checkProgramStatusCreation(programId)) return NOT_INIT

        return programId
    }

    private fun checkProgramStatusCreation(programId: Int): Boolean {
        val linkStatus = IntArray(1)
        GLES32.glGetProgramiv(programId, GLES32.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus.first() == 0) {
            GLES32.glDeleteProgram(programId)
            return true
        }
        return false
    }

    /**
     * метод связывает текстуру с координатами и рисует ее
     */
    private fun drawTexture(texture: Int, x: Float, y: Float, width: Float, height: Float) {
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
            aPositionLocation, OpenGLRenderer.POSITION_COUNT, GLES32.GL_FLOAT,
            false, OpenGLRenderer.STRIDE, vertexData
        )
        GLES32.glEnableVertexAttribArray(aPositionLocation)
        //endregion

        //region координаты текстур
        vertexData.position(OpenGLRenderer.POSITION_COUNT)
        GLES32.glVertexAttribPointer(
            aTextureLocation, OpenGLRenderer.TEXTURE_COUNT, GLES32.GL_FLOAT,
            false, OpenGLRenderer.STRIDE, vertexData
        )
        GLES32.glEnableVertexAttribArray(aTextureLocation)
        //endregion

        // помещаем текстуру в target 2D юнита 0
        GLES32.glActiveTexture(GLES32.GL_TEXTURE0)
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, texture)

        // юнит текстуры
        GLES32.glUniform1i(uTextureUnitLocation, 0)
    }


}