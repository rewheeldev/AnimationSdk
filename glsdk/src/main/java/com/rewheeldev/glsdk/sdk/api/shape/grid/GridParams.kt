package com.rewheeldev.glsdk.sdk.api.shape.grid

import com.rewheeldev.glsdk.sdk.api.shape.border.Border

/**
 * @author Ivantsov Mykola
 * @since 09 Oct 2022
 * */
data class GridParams(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val columns: Int = 10,
    val rows: Int = 10,
    val stepSize: Float = 10f,
    val border: Border = Border()
)
