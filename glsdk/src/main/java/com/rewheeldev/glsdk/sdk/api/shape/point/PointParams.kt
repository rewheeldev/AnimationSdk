package com.rewheeldev.glsdk.sdk.api.shape.point

import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color

class PointParams {
    var coords: Coords
        private set
    var color: Color = Color.WHITE
    var coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
        private set

    constructor(
        coords: Coords,
        color: Color = Color.WHITE,
        coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
    ) {
        this.coords = coords
        this.color = color
        this.coordsPerVertex = coordsPerVertex
    }

    constructor(
        x: Int,
        y: Int,
        color: Color = Color.WHITE,
        coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
    ) {
        val vertex = floatArrayOf(x.toFloat(), y.toFloat())
        this.coords = Coords(vertex, coordsPerVertex)
        this.color = color
    }
}