package com.example.animationsdk.ui.gl

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer
import java.nio.IntBuffer


class ClearGLSurfaceView(context: Context, attrs: AttributeSet? = null) :
    GLSurfaceView(context, attrs) {
    //    val mRenderer = BitmapRenderer(context, context.resources)
    val mRenderer = OpenGLRenderer(context)

    init {
        setEGLContextClientVersion(3)
        setRenderer(mRenderer)
        val render = glGetString(GLES30.GL_RENDERER)
        val vendor = glGetString(GLES30.GL_VENDOR)
        val version = glGetString(GLES30.GL_VERSION)
        val glslVersion = glGetString(GLES30.GL_SHADING_LANGUAGE_VERSION)
        val major: IntBuffer = IntBuffer.allocate(100)
        val minor: IntBuffer = IntBuffer.allocate(100)
        glGetIntegerv(GLES30.GL_MAJOR_VERSION, major)
        glGetIntegerv(GLES30.GL_MINOR_VERSION, minor)

        Log.d("TAG_7", "render: $render, vendor: $vendor, version: $version, , major: ${major}, minor: $minor, glslVersion: $glslVersion")

    }
}