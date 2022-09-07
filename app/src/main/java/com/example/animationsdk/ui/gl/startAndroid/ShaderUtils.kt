package com.example.animationsdk.ui.gl.startAndroid

import android.opengl.GLES20

object ShaderUtils {
    fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        //done
        val programId = GLES20.glCreateProgram()
        if (programId == 0) {
            return 0
        }
        GLES20.glAttachShader(programId, vertexShaderId)
        GLES20.glAttachShader(programId, fragmentShaderId)
        GLES20.glLinkProgram(programId)
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    fun createShader(type: Int, shaderText: String): Int {
        //done
        val shaderId = GLES20.glCreateShader(type)
        if (shaderId == 0) {
            return 0
        }
        GLES20.glShaderSource(shaderId, shaderText)
        GLES20.glCompileShader(shaderId)
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }
}

val FRAGMENT_SHADER = """precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_Texture;

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_Texture);
}"""

val VERTEX_SHADER = """attribute vec4 a_Position;
uniform mat4 u_Matrix;
attribute vec2 a_Texture;
varying vec2 v_Texture;

void main()
{
    gl_Position = u_Matrix * a_Position;
    v_Texture = a_Texture;
}"""

var fragmentShader = "precision mediump float;       \n" +
        "varying vec2 v_Color;          \n" +
        "uniform sampler2D s_baseMap;   \n" +
        "void main()                    \n" +
        "{                              \n" +
        "  vec4 baseColor;              \n" +
        "  baseColor = texture2D( s_baseMap, v_Color );   \n" +
        "   gl_FragColor = baseColor;     \n" +
        "}"