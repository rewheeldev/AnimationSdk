package com.rewheeldev.glsdk.sdk.internal

import com.rewheeldev.glsdk.sdk.api.Coord
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val START_OF_SIDE = 0f
const val END_OF_SIDE = 1f

fun createDefaultRectangleVertices(x: Float, y: Float, width: Float, height: Float): FloatArray {
    val leftTopPoint = Coord(
        coordData = floatArrayOf(x, height + y, START_OF_SIDE, START_OF_SIDE),
        countCoordsPerVertex = CoordsPerVertex.VERTEX_2D
    )
    val leftBottomPoint = Coord(
        coordData = floatArrayOf(x, y, START_OF_SIDE, END_OF_SIDE),
        countCoordsPerVertex = CoordsPerVertex.VERTEX_2D
    )

    val rightTopPoint = Coord(
        coordData = floatArrayOf(width + x, height + y, END_OF_SIDE, START_OF_SIDE),
        countCoordsPerVertex = CoordsPerVertex.VERTEX_2D
    )

    val rightBottomPoint = Coord(
        coordData = floatArrayOf(width + x, y, END_OF_SIDE, END_OF_SIDE),
        countCoordsPerVertex = CoordsPerVertex.VERTEX_2D
    )

    return floatArrayOf(
        leftTopPoint.x, leftTopPoint.y, leftTopPoint.z,
        leftTopPoint.coordData[2], leftTopPoint.coordData[3],

        leftBottomPoint.x, leftBottomPoint.y, leftBottomPoint.z,
        leftBottomPoint.coordData[2], leftBottomPoint.coordData[3],

        rightTopPoint.x, rightTopPoint.y, rightTopPoint.z,
        rightTopPoint.coordData[2], rightTopPoint.coordData[3],

        rightBottomPoint.x, rightBottomPoint.y, rightBottomPoint.z,
        rightBottomPoint.coordData[2], rightBottomPoint.coordData[3]
    )
}

fun FloatArray.asSortedFloatBuffer(): FloatBuffer {
    return ByteBuffer.allocateDirect(this.size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer().put(this).apply { position(0) }
}
