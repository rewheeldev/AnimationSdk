package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import com.rewheeldev.glsdk.sdk.internal.asSortedFloatBuffer
import com.rewheeldev.glsdk.sdk.internal.util.Math3d
import java.nio.FloatBuffer
import kotlin.math.sqrt

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
            if (coordData.size < 3) return
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

    operator fun plus(coord: Coord): Coord {
        return Coord(x + coord.x, y + coord.y, z + coord.z)
    }

    fun join(coord: Coord) {
        x += coord.x
        y += coord.y
        z += coord.z
    }

    operator fun minus(coord: Coord): Coord {
        return Coord(x - coord.x, y - coord.y, z - coord.z)
    }

    operator fun times(times: Float): Coord {
        return Coord(x * times, y * times, z * times)
    }

    override fun toString(): String {
        return "Coord(x=$x, y=$y, z=$z)"
    }

    fun calculateVectorLength(): Float {
        return sqrt(x * x + y * y + z * z)
    }

    /**
     * нормализирует длину вектора направления на 1
     */
    fun normalize() {
        val length = calculateVectorLength()//.absoluteValue
        x = (x / length)
        y = (y / length)
        z = (z / length)
    }

    fun multiply(multiply: Float) {
        x = (x * multiply)
        y = (y * multiply)
        z = (z * multiply)
    }

    fun fromAngle(
        rayInitialPoint: Coord,
        verticalAnglePitch: Float = 45f,
        horizontalAngleYaw: Float = 90f,
        length: Float = 1f
    ) {
        val resultPoint = Math3d.pointFromAngle(
            verticalAnglePitch,
            horizontalAngleYaw,
            rayInitialPoint,
            length
        )
        x = resultPoint.x
        y = resultPoint.y
        z = resultPoint.z
    }
}

operator fun Float.times(coord: Coord): Coord {
    return Coord(this * coord.x, this * coord.y, this * coord.z)
}

fun Coord.cross(second: Coord): Coord {
    //https://registry.khronos.org/OpenGL-Refpages/gl4/html/cross.xhtml

    val x = this.y * second.z - second.y * this.z
    val y = this.z * second.x - second.z * this.x
    val z = this.x * second.y - second.x * this.y
    return Coord(x = x, y = y, z = z)
}

fun cross(x: FloatArray, y: FloatArray): FloatArray {
    //https://registry.khronos.org/OpenGL-Refpages/gl4/html/cross.xhtml

    val resultX = x[1] * y[2] - y[1] * x[2]
    val resultY = x[2] * y[0] - y[2] * x[0]
    val resultZ = x[0] * y[1] - y[0] * x[1]
    return floatArrayOf(resultX, resultY, resultZ)
}