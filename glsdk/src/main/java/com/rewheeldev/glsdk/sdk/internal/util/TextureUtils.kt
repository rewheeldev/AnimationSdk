package com.rewheeldev.glsdk.sdk.internal.util

import android.graphics.Bitmap
import android.opengl.GLES10
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log

object TextureUtils {
    fun loadTexture(bitmap: Bitmap): Int {
        // создание объекта текстуры
        val numberTextures = 1
        val startIndex = 0
        val textureIds = IntArray(numberTextures)
        GLES20.glGenTextures(numberTextures, textureIds, startIndex)
        if (textureIds[0] == 0) return 0

        // настройка объекта текстуры
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0])
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0,
//            GLES20.GL_RGBA,
            bitmap,
            0
        )

        val checkImageWidth = 500
        val checkImageHeight = 500
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, checkImageWidth,
//            checkImageHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
//            bitmap)
//        GLES10.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, checkImageWidth,
//            checkImageHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
//            bitmap)
        Log.d("TAG_1",GLUtils.getInternalFormat(bitmap).toString())


        bitmap.recycle()

        // сброс target
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textureIds[0]
    }
}