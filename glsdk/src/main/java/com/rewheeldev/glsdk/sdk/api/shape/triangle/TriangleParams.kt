package com.rewheeldev.glsdk.sdk.api.shape.triangle

import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import utils.Color

/**
 * `TriangleParams` is a data class that contains a `Coords` and a `Color`.
 *
 * @property {Coords} coords - The coordinates of the triangle.
 * @property {Color} color - The color of the triangle.
 *
 * @author Ivantsov Mykola
 * @since 09 Oct 2022
 */
data class TriangleParams(
    val coords: Coords,
    val color: Color = Color(),
    val border: Border = Border()
)