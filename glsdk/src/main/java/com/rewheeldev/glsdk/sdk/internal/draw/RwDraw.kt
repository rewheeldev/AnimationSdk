package com.rewheeldev.glsdk.sdk.internal.draw

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.fonts.Font
import com.rewheeldev.glsdk.sdk.api.Color
import com.rewheeldev.glsdk.sdk.api.IDraw

class RwDraw : IDraw {

    override fun drawBitmap(bitmap: Bitmap, x: Float, y: Float, width: Float, height: Float) {
        TODO("Not yet implemented")
    }

    override fun drawText(
        text: String,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        font: Font,
        fontSize: Float,
        fontColor: Color,
        backColor: Color
    ) {
        TODO("Not yet implemented")
    }

    override fun drawText(
        text: String,
        rect: RectF,
        font: Font,
        fontSize: Float,
        fontColor: Color,
        backColor: Color
    ) {
        TODO("Not yet implemented")
    }

    override fun drawText(
        text: String,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        paint: Paint,
        backColor: Color
    ) {
        TODO("Not yet implemented")
    }

    override fun drawText(text: String, rect: RectF, paint: Paint, backColor: Color) {
        TODO("Not yet implemented")
    }

    override fun drawPoints(
        arrayPoints: Array<PointF>,
        fillColor: Color,
        borderColor: Color,
        borderFatness: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun drawRect(rect: RectF, fillColor: Color, borderColor: Color, borderFatness: Int) {
        TODO("Not yet implemented")
    }

    override fun drawRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        fillColor: Color,
        borderColor: Color,
        borderFatness: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun drawColor(color: Color) {
        TODO("Not yet implemented")
    }
}