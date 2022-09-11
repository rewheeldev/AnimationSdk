package com.example.animationsdk.ui.gl.tryThree

import android.opengl.GLES20


object GLManager {
    // Some constants to help count the number of bytes between
    // elements of our vertex data arrays
    const val COMPONENTS_PER_VERTEX = 3
    const val FLOAT_SIZE = 4
    const val STRIDE = (COMPONENTS_PER_VERTEX
            * FLOAT_SIZE)
    const val ELEMENTS_PER_VERTEX = 3 // x,y,z

    // Some constants to represent GLSL types in our shaders
    const val U_MATRIX = "u_Matrix"
    const val A_POSITION = "a_Position"
    const val U_COLOR = "u_Color"

    // Each of the above constants also has a matching int
    // which will represent its location in the open GL glProgram
    var uMatrixLocation = 0
    var aPositionLocation = 0
    var uColorLocation = 0

    // A very simple vertexShader
    // that we can define with a String
    private const val vertexShader = "uniform mat4 u_Matrix;" +
            "attribute vec4 a_Position;" +
            "void main()" +
            "{" +
            "gl_Position = u_Matrix * a_Position;" +
            "gl_PointSize = 3.0;" +
            "}"

    // A very simple fragment Shader
    // that we can define with a String
    private const val fragmentShader = "precision mediump float;" +
            "uniform vec4 u_Color;" +
            "void main()" +
            "{" +
            "gl_FragColor = u_Color;" +
            "}"

    // A handle to the GL glProgram
    var gLProgram = 0

    fun buildProgram(): Int {
        // Compile and link our shaders into a GL glProgram object
        return linkProgram(compileVertexShader(), compileFragmentShader())
    }

    private fun compileVertexShader(): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, vertexShader)
    }

    private fun compileFragmentShader(): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {

        // Create a shader object and store its ID
        val shader = GLES20.glCreateShader(type)

        // Pass in the code then compile the shader
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

    private fun linkProgram(vertexShader: Int, fragmentShader: Int): Int {

        // A handle to the GL glProgram -
        // the compiled and linked shaders
        gLProgram = GLES20.glCreateProgram()

        // Attach the vertex shader to the glProgram.
        GLES20.glAttachShader(gLProgram, vertexShader)

        // Attach the fragment shader to the glProgram.
        GLES20.glAttachShader(gLProgram, fragmentShader)

        // Link the two shaders together into a glProgram.
        GLES20.glLinkProgram(gLProgram)
        return gLProgram
    }
}