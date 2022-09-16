package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.internal.asSortedFloatBuffer
import java.nio.FloatBuffer

class Colors() {
    val array: ArrayList<Color> = ArrayList()
    val size: Int
        get() = array.size

    constructor(
        vararg colors: Color
    ) : this() {
        colors.forEach { array.add(it) }
    }

    constructor(
        countColors: Int,
        defaultColor: Color = Color.WHITE,
    ) : this() {
        repeat(countColors) { array.add(defaultColor) }
    }

    fun asSortedFloatBuffer(): FloatBuffer = asFloatArray().asSortedFloatBuffer()

    fun asFloatArray(): FloatArray {
        val resultFloatArray = ArrayList<Float>()
        array.forEach { resultFloatArray.addAll(it.asFloatArray().toList()) }
        return resultFloatArray.toFloatArray()
    }
}