package com.example.animationsdk.ui.gl.sdk.internal

import android.opengl.GLES32
import android.util.Log
import java.nio.IntBuffer

fun getOpenGlInfo(): String {
    val render = GLES32.glGetString(GLES32.GL_RENDERER)
    val vendor = GLES32.glGetString(GLES32.GL_VENDOR)
    val version = GLES32.glGetString(GLES32.GL_VERSION)
    val glslVersion = GLES32.glGetString(GLES32.GL_SHADING_LANGUAGE_VERSION)
    val major = IntBuffer.allocate(1).array()
    val minor = IntBuffer.allocate(1).array()

    //glGetIntegerv - can call only with OpenGL version 3.0
    GLES32.glGetIntegerv(GLES32.GL_MAJOR_VERSION, major, 0)
    GLES32.glGetIntegerv(GLES32.GL_MINOR_VERSION, minor, 0)
    val error = GLES32.glGetError()
    val result =
        "error: ${if (error == GLES32.GL_NO_ERROR) "no error" else "error code: $error"}, render: $render, vendor: $vendor, version: $version, , major: ${major.contentToString()}, minor: ${minor.contentToString()}, glslVersion: $glslVersion"
    Log.d("TAG_7", result)
    val nExtensions = IntBuffer.allocate(1000).array()
    GLES32.glGetIntegerv(GLES32.GL_NUM_EXTENSIONS, nExtensions, 0)

    for (i in nExtensions.indices) {
        val result = GLES32.glGetStringi(GLES32.GL_EXTENSIONS, i) ?: continue
        Log.i("TAG_7", "i = $i: $result")
    }
    return result
}