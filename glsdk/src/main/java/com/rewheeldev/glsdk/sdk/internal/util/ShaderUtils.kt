package com.rewheeldev.glsdk.sdk.internal.util

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

    fun createShader(type: Int, shaderCode: String): Int {
        //done
        val shaderId = GLES20.glCreateShader(type)
        if (shaderId == 0) {
            return 0
        }
        GLES20.glShaderSource(shaderId, shaderCode)
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

const val vertex_basic = """
    #version 300 es
    
    layout (location=0) in vec3 VertexPosition;
    layout (location=1) in vec3 VertexColor;

    out vec3 Color;

    void main() 
    {
        Color = VertexColor;
        gl_Position = vec4(VertexPosition, 1.0);
    }
    """

const val frag_basic = """
    #version 300 es
    
in vec3 Color;
out  vec4  FragColor;

void main(){
    FragColor = vec4(Color, 1.0);
}
    """

object FigureShader {

    const val SHADER_VARIABLE_VPOSITION = "vPosition"
    const val SHADER_VARIABLE_VCOLOR = "vColor"

    //    const val SHADER_VARIABLE_VSIZE = "vSize"
    const val SHADER_VARIABLE_UMVPMATRIX = "uMVPMatrix"

    const val VERTEX_SHADER_CODE =
    // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute float vSize;" +
                "void main() {" +
                // the matrix must be included as a modifier of gl_Position
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  gl_Position = uMVPMatrix * vPosition;" +
                "  gl_PointSize = 10.0;" +
                "}"


    const val FRAGMENT_SHADER_CODE =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"


    const val SHADER_VARIABLE_APOSITION = "a_Position"
    const val SHADER_VARIABLE_ACOLOR = "a_Color"

    const val GRADIENT_VERTEX_SHADER_CODE = """
        attribute vec4 a_Position;
        attribute vec4 a_Color;

        varying vec4 v_Color;

        void main()
        {
            v_Color = a_Color;
    
            gl_Position =  a_Position;
            gl_PointSize = 10.0;
        }
    """
    const val GRADIENT_FRAGMENT_SHADER_CODE = """
        precision mediump float;
        varying vec4 v_Color;

        void main()
        {
            gl_FragColor = v_Color;
        }
    """
}
