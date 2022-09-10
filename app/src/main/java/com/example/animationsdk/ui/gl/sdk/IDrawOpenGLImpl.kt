package com.example.animationsdk.ui.gl.sdk

import android.opengl.GLES20.*
import android.opengl.GLES32
import android.opengl.Matrix
import android.util.Log
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

    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mMatrix = FloatArray(16)

    //        Задайте расстояния до ближней и дальней плоскостей отсечения глубины.
//        Оба расстояния должны быть положительными.
    //в случае присвоения новых значений переменным должен быть вызван метод Matrix.frustumM
    //для пересоздания матрици
    private var near: Float = 0.0f
    private var far: Float = 14.0f

    /**
     *
     *
     */
    fun initialize() {
        //создание компонентов GL (Shader & fragment)
        val vertexShaderId: Int = createShader(GLES32.GL_VERTEX_SHADER, VERTEX_SHADER)
        val fragmentShaderId: Int = createShader(GLES32.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)

        programId = createProgram(vertexShaderId, fragmentShaderId)
        if (programId == NOT_INIT) {
            printErrorLog("Error creating and linking with the program.")
            return
        }
        //if creating and linking with the fragments and shaders is successful
        // end than add the program to the OpenGl' conveyor
        GLES32.glUseProgram(programId)

        //region регистрация и привязка переменных которые определенны в шейдерах
        aPositionLocation = GLES32.glGetAttribLocation(programId, "a_Position")
        aTextureLocation = GLES32.glGetAttribLocation(programId, "a_Texture")
        uTextureUnitLocation = GLES32.glGetUniformLocation(programId, "u_TextureUnit")
        uMatrixLocation = GLES32.glGetUniformLocation(programId, "u_Matrix")
        //endregion

        GLES32.glClearColor(0.2f, 0.2f, 0.2f, 0.2f)
        GLES32.glEnable(GLES32.GL_DEPTH_TEST)
        createViewMatrix()
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

    /**
     * It creates a shader object, copies the shader code into it, compiles the shader and returns the
     * shader object id
     *
     * @param type The type of shader to create. This can be either GL_VERTEX_SHADER, GL_FRAGMENT_SHADER,
     * GL_GEOMETRY_SHADER, GL_TESS_EVALUATION_SHADER or GL_TESS_CONTROL_SHADER.
     * @param shaderText The shader code.
     * @return The shaderId is being returned.
     */
    private fun createShader(type: Int, shaderText: String): Int {

        val shaderId =
            GLES32.glCreateShader(type) //if we will get an error here then return 0 (zero)
        if (shaderId == GLES32.GL_FALSE) {
            printErrorLog("Error creating vertex shader.")
            return NOT_INIT
        }
        //copy shader' code into shader object
        GLES32.glShaderSource(
            shaderId,
            shaderText
        ) //can compile shader from different source files or just strings
        //compile shader
        GLES32.glCompileShader(shaderId)

        if (checkShaderCompilationStatus(shaderId)) return NOT_INIT
        return shaderId
    }

    fun printErrorLog(message: String) {
        Log.e(TAG, "message: $message")
    }

    private fun checkShaderCompilationStatus(shaderId: Int): Boolean {
        val compileStatus = IntArray(1)
        //get the compilation status of the shader
        GLES32.glGetShaderiv(shaderId, GLES32.GL_COMPILE_STATUS, compileStatus, 0)

        if (compileStatus.first() == GLES32.GL_FALSE) {
            printErrorLog("Vertex shader compilation failed!")
            val logLen = IntArray(1)
            //check length of error message
            glGetShaderiv(shaderId, GL_INFO_LOG_LENGTH, logLen, 0)
            if (logLen.first() > 0) {
                val log = glGetShaderInfoLog(shaderId)
                printErrorLog("Shader info log: $log")
            }
            //delete shader
            //Note: this method can be called multiple times for deleting shaders when they don't need anymore.
            //if the shader has already connected to the program object then deleting will be done
            //only after disconnecting from the program object
            GLES32.glDeleteShader(shaderId)
            return true
        }
        return false
    }

    fun onSurfaceChanged(width: Int, height: Int, x: Int = 0, y: Int = 0) {
        //Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
        //Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
        GLES32.glViewport(x, y, width, height)
        createProjectionMatrix(width, height)

        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        //https://registry.khronos.org/OpenGL-Refpages/gl4/html/glUniform.xhtml
        GLES32.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)

    }

    private fun createProjectionMatrix(width: Int, height: Int) {
        Log.i("TAG_1", "width: $width, height: $height")
        var ratio = 1f
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        //1500 / 800 = 1.875
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio //-1 * 1.875
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
        //Усеченная видимость — это просто визуальное представление перспективной проекции,
        // которое используется для преобразования 3D-точки в мировом координатном
        // пространстве в 2D-точку на экране.
        //https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/glFrustum.xml
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
    }

    //here we will create the program and make linking with shaders
    //we can create a different number of programs and we can change these programs
    //just call OpenGL' conveyor function glUseProgram(..)
    private fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = GLES32.glCreateProgram()

        if (programId == GLES32.GL_FALSE) {
            printErrorLog("Error creating program object.")
            return NOT_INIT
        }
        //connecting the program with shaders
        GLES32.glAttachShader(programId, vertexShaderId)
        GLES32.glAttachShader(programId, fragmentShaderId)

        //linking the program
        GLES32.glLinkProgram(programId)

        if (checkProgramLinkingStatus(programId)) return NOT_INIT

        return programId
    }

    private fun checkProgramLinkingStatus(programId: Int): Boolean {
        val linkStatus = IntArray(1)

        GLES32.glGetProgramiv(programId, GLES32.GL_LINK_STATUS, linkStatus, 0)

        if (linkStatus.first() == GLES32.GL_FALSE) {
            printErrorLog("Failed to link shader program!")
            val logLen = IntArray(1)
            //check length of error message
            glGetShaderiv(programId, GL_INFO_LOG_LENGTH, logLen, 0)
            if (logLen.first() > 0) {
                val log = glGetProgramInfoLog(programId)
                printErrorLog("Program log: $log")
            }
            //deleting shader' program. If the program is using in a moment of deleting
            // the program will be removed only after the program will be stopped using
            //shader' objects will be off from the program
            //this method is not removed shaders so you need to take care about removing unused shaders
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
        vertexData.flip()
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

    companion object {
        private const val TAG = "DrawOpenGL"
    }

}