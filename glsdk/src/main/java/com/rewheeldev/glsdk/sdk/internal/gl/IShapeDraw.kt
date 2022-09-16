package com.rewheeldev.glsdk.sdk.internal.gl

import com.rewheeldev.glsdk.sdk.internal.ViewScene

interface IShapeDraw {
    val id: Long
    var programId: Int

    fun draw(vpMatrix: FloatArray)
    fun draw(scene: ViewScene)
}