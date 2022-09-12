package com.rewheeldev.glsdk.sdk.internal

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * взаимосвязь координат в пространстве с координатами текстуры
 * в [textureX],[textureY] рекомендуется указывать значения 0f , 1f. другие значения могут исказить изображение
 * не коректная связь их с пространственными координатами также может исказить изображение или перевернуть систему координат
 */
data class SpatialAndTextureCoordinate(
    val x: Float, val y: Float, val z: Float,
    val textureX: Float, val textureY: Float
)

const val START_OF_SIDE = 0f
const val END_OF_SIDE = 1f
const val DEFAULT_Z_VALUE = 1f

fun createDefaultRectangleVertices(x: Float, y: Float, width: Float, height: Float): FloatArray {
    val leftTopPoint = SpatialAndTextureCoordinate(
        x, height + y, DEFAULT_Z_VALUE,
        START_OF_SIDE, START_OF_SIDE
    )
    val leftBottomPoint = SpatialAndTextureCoordinate(
        x, y, DEFAULT_Z_VALUE,
        START_OF_SIDE, END_OF_SIDE
    )
    val rightTopPoint = SpatialAndTextureCoordinate(
        width + x, height + y, DEFAULT_Z_VALUE,
        END_OF_SIDE, START_OF_SIDE
    )
    val rightBottomPoint = SpatialAndTextureCoordinate(
        width + x, y, DEFAULT_Z_VALUE,
        END_OF_SIDE, END_OF_SIDE
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
        .asFloatBuffer().put(this).apply { position(0) }
}
