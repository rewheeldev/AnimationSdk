package com.rewheeldev.glsdk.sdk.api.shape.line

import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import utils.Color

class LineParams(
    coords: Coords,
    var color: Color = Color.WHITE,
    coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
) {
    var coords: Coords = coords
        private set
    var coordsPerVertex: CoordsPerVertex = coordsPerVertex
        private set

}