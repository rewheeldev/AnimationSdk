package com.example.animationsdk.ui.gl.sdk

import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.fonts.FontFamily

interface IDrawOpenGL {
    /**
     * создает текстуру на основании предоставленого [bitmap] и возвращает ее идентификатор
     */
    fun initTexture(bitmap: Bitmap): Int

    /**
     * Рисует ранее заготовленную текстуру по [textureId]
     *
     * [x], [y] - положение текстуры в относительных еденицах измерения, размещается на
     * трехмерной матрице проекции
     * [width], [height] - размеры в относительных еденицах измерения, фактический
     * размер на экране зависит от размеров проэкции и положения камеры в трехмерном пространстве
     */
    fun drawRectTexture(textureId: Int, x: Float, y: Float, width: Float, height: Float)
    fun drawRectTexture(textureId: Int, rect: RectF)
}

