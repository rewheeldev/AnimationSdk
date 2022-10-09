package com.rewheeldev.glsdk.sdk.api.shape.point

import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color

class PointParams(
    val coord: Coord,
    val color: Color = Color.WHITE,
    val coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
)