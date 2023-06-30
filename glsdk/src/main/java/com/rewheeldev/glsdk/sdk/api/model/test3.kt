package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.api.IShapeController
import com.rewheeldev.glsdk.sdk.api.factories.IShapeFactory
import com.rewheeldev.glsdk.sdk.api.shape.point.PointParams
import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import com.rewheeldev.glsdk.sdk.internal.util.Math3d.pointFromAngle
import utils.Color

fun draw360Points(
    factory: IShapeFactory,
    controller: IShapeController,
    centerPoint: Coord = Coord(40f, 50f, 70f),
    radius: Float = 50f
) {
    repeat(360) {
        val newAnglePoint = pointFromAngle(it.toFloat() * 1f, 23f, centerPoint, radius)
        val pointParams = PointParams(
            Coords(
                floatArrayOf(newAnglePoint.x, newAnglePoint.y, newAnglePoint.z),
                CoordsPerVertex.VERTEX_3D
            ),
            coordsPerVertex = CoordsPerVertex.VERTEX_3D,
            color = Color.GOLD
        )
        val point = factory.createPoint(pointParams)
        controller.add(point)
    }
}

fun draw360PointsYaw(
    factory: IShapeFactory,
    controller: IShapeController,
    centerPoint: Coord = Coord(40f, 50f, 70f),
    radius: Float = 50f
) {
    repeat(360) {
        val newAnglePoint = pointFromAngle(43f, it.toFloat() * 1f, centerPoint, radius)
        val pointParams = PointParams(
            Coords(
                floatArrayOf(newAnglePoint.x, newAnglePoint.y, newAnglePoint.z),
                CoordsPerVertex.VERTEX_3D
            ),
            coordsPerVertex = CoordsPerVertex.VERTEX_3D,
            color = Color.BROWN
        )
        val point = factory.createPoint(pointParams)
        controller.add(point)
    }
}