package com.rewheeldev.glsdk.sdk.internal

import com.rewheeldev.glsdk.sdk.api.IShape
import com.rewheeldev.glsdk.sdk.api.factories.IShapeFactory
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.grid.GridParams
import com.rewheeldev.glsdk.sdk.api.shape.point.PointParams
import com.rewheeldev.glsdk.sdk.api.shape.triangle.TriangleParams

/**
 * @author Ivantsov Mykola
 * @since 09 Oct 2022
 * */
class ShapeFactory : IShapeFactory {

    override fun createTriangle(params: TriangleParams): IShape {
        return Shape(coords = params.coords, colors = Colors(params.color), border = params.border)
    }

    override fun createGrid(params: GridParams): IShape {
        return Shape(
            with(params) {
                Shape.prepareCoordsForGrid(
                    x = x,
                    y = y,
                    z = z,
                    columns = columns,
                    rows = rows,
                    stepSize = stepSize,
                )
            },
            border = params.border
        )
    }

    fun createPoint(params: PointParams): IShape {
        return Shape(
            coords = Coords(params.coord.asFloatArray(), params.coordsPerVertex),
            colors = Colors(params.color)
        )
    }

}