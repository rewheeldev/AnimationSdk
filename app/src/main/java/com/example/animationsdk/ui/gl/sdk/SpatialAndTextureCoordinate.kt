package com.example.animationsdk.ui.gl.sdk

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


data class Position3D(var x: Float, var y: Float, var z: Float)

/**
 * взаимосвязь координат в пространстве с координатами текстуры
 * в [textureX],[textureY] рекомендуется указывать значения 0f , 1f. другие значения могут исказить изображение
 * не коректная связь их с пространственными координатами также может исказить изображение или перевернуть систему координат
 */
data class SpatialAndTextureCoordinate(
    val x: Float, val y: Float, val z: Float,
    val textureX: Float, val textureY: Float
)

const val startOfSide = 0f
const val endOfSide = 1f
const val defaultZValue = 1f

fun createDefaultRectangleVertices(x: Float, y: Float, width: Float, height: Float): FloatArray {
    val leftTopPoint = SpatialAndTextureCoordinate(
        x, height + y, defaultZValue,
        startOfSide, startOfSide
    )
    val leftBottomPoint = SpatialAndTextureCoordinate(
        x, y, defaultZValue,
        startOfSide, endOfSide
    )
    val rightTopPoint = SpatialAndTextureCoordinate(
        width + x, height + y, defaultZValue,
        endOfSide, startOfSide
    )
    val rightBottomPoint = SpatialAndTextureCoordinate(
        width + x, y, defaultZValue,
        endOfSide, endOfSide
    )

    return floatArrayOf(
        leftTopPoint.x, leftTopPoint.y, leftTopPoint.z,
        leftTopPoint.textureX, leftTopPoint.textureY,

        leftBottomPoint.x, leftBottomPoint.y, leftBottomPoint.z,
        leftBottomPoint.textureX, leftBottomPoint.textureY,

        rightTopPoint.x, rightTopPoint.y, rightTopPoint.z,
        rightTopPoint.textureX, rightTopPoint.textureY,

        rightBottomPoint.x, rightBottomPoint.y, rightBottomPoint.z,
        rightBottomPoint.textureX, rightBottomPoint.textureY
    )
}

fun FloatArray.asSortedFloatBuffer(): FloatBuffer {
    return ByteBuffer.allocateDirect(this.size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer().put(this)
}
