package com.rewheeldev.glsdk.sdk.api.shape.rectangle

import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color

class RectangleParams {

    @Volatile
    var coords: Coords

    @Volatile
    var color: Color = Color()

    @Volatile
    var border: Border = Border()

    constructor(coords: Coords, color: Color = Color(), border: Border = Border()) {
        this.coords = coords
        this.color = color
        this.border = border
    }

    /* Creating a rectangle with the given parameters. */
    constructor(x: Int, y: Int, width: Int, height: Int) {

        val vertices = floatArrayOf(
            x.toFloat(), y.toFloat(),
            width.toFloat(), height.toFloat(),
            x.toFloat(), height.toFloat(),

            x.toFloat(), y.toFloat(),
            width.toFloat(), y.toFloat(),
            width.toFloat(), height.toFloat()
        )
        coords = Coords(vertices, CoordsPerVertex.VERTEX_2D)
    }
}