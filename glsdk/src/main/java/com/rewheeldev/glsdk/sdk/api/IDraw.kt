package com.rewheeldev.glsdk.sdk.api

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.fonts.Font
import com.rewheeldev.glsdk.sdk.api.model.Color

interface IDraw {

    /**
     *
     */
    fun drawBitmap(
        bitmap: Bitmap,
        x: Float, y: Float,
        width: Float, height: Float
    )

    /**
     *
     */
    fun drawText(
        text: String,
        x: Float, y: Float, width: Float, height: Float,
        font: Font, fontSize: Float = 14f, fontColor: Color = Color.BLACK,
        backColor: Color = Color.TRANSPARENT
    )

    fun drawText(
        text: String,
        rect: RectF,
        font: Font, fontSize: Float = 14f, fontColor: Color = Color.BLACK,
        backColor: Color = Color.TRANSPARENT
    )

    fun drawText(
        text: String,
        x: Float, y: Float, width: Float, height: Float,
        paint: Paint,
        backColor: Color = Color.TRANSPARENT
    )

    fun drawText(
        text: String,
        rect: RectF,
        paint: Paint,
        backColor: Color = Color.TRANSPARENT
    )

    /**
     * формирует фигуру по предоставленным точкам и рисует ее
     */
    fun drawPoints(
        arrayPoints: Array<PointF>,
        fillColor: Color = Color.TRANSPARENT,
        borderColor: Color = Color.BLACK,
        borderFatness: Int = 1
    )

    /**
     * рисует прямоугольник
     */
    fun drawRect(
        rect: RectF,
        fillColor: Color = Color.TRANSPARENT,
        borderColor: Color = Color.BLACK,
        borderFatness: Int = 1
    )

    fun drawRect(
        x: Float, y: Float,
        width: Float, height: Float,
        fillColor: Color = Color.TRANSPARENT,
        borderColor: Color = Color.BLACK,
        borderFatness: Int = 1
    )

    /**
     * закрашивает предоставленое поле рисования
     */
    fun drawColor(color: Color = Color.BLACK)
}