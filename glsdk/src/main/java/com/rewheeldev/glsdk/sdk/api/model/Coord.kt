package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import com.rewheeldev.glsdk.sdk.internal.asSortedFloatBuffer
import java.nio.FloatBuffer

class Coord(
    val coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D,
    stride: Int = coordsPerVertex.num
) {
    val coordData: FloatArray

    var x: Float
        get() {
            return coordData[0]
        }
        set(value) {
            coordData[0] = value
        }

    var y: Float
        get() {
            return coordData[1]
        }
        set(value) {
            coordData[1] = value
        }

    var z: Float
        get() {
            return if (coordData.size < 3) 0f
            else coordData[2]
        }
        set(value) {
            coordData[2] = value
        }

    init {
        coordData = FloatArray(stride)
    }

    constructor(x: Float, y: Float) : this(CoordsPerVertex.VERTEX_2D, stride = 2) {
        coordData[0] = x
        coordData[1] = y
    }

    constructor(x: Float, y: Float, z: Float) : this(CoordsPerVertex.VERTEX_3D) {
        coordData[0] = x
        coordData[1] = y
        coordData[2] = z
    }

    constructor(
        coordData: FloatArray,
        countCoordsPerVertex: CoordsPerVertex
    ) : this(countCoordsPerVertex, stride = coordData.size) {
        coordData.forEachIndexed { i, value ->
            this.coordData[i] = value
        }
    }

    fun getStride(): Int = coordData.size
    fun getStrideAsByte(): Int = coordData.size * 4

    fun asSortedFloatBuffer(): FloatBuffer = coordData.asSortedFloatBuffer()
    fun asSortedFloatBuffer(type: CoordsPerVertex): FloatBuffer =
        asFloatArray(type).asSortedFloatBuffer()

    fun asFloatArray(): FloatArray = coordData
    fun asFloatArray(coordsPerVertex: CoordsPerVertex): FloatArray {
        return if (coordsPerVertex == CoordsPerVertex.VERTEX_2D) floatArrayOf(
            coordData[0],
            coordData[1]
        )
        else floatArrayOf(coordData[0], coordData[1], coordData[2])
    }

}