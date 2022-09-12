package com.rewheeldev.glsdk.sdk.internal.model

data class DrawableUnit(
    val id: Int,
    var textureId: Int = 0,
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 0f,
    var height: Float = 0f
)