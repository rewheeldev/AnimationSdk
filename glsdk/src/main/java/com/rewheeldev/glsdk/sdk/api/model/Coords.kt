package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.internal.CoordsPerVertex
import com.rewheeldev.glsdk.sdk.internal.asSortedFloatBuffer
import java.nio.FloatBuffer

class Coords(
    val coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D,
    val stride: Int = coordsPerVertex.num
) {
    val array: ArrayList<Coord> = ArrayList()
    val size: Int
        get() = array.size

    fun getStrideAsByte(): Int = stride * 4

    //region constructors
    constructor(
        floatArray: FloatArray,
        coordsPerVertex: CoordsPerVertex,
        stride: Int = coordsPerVertex.num
    ) : this(coordsPerVertex = coordsPerVertex, stride = stride) {
        if (floatArray.size % stride != 0) throw(Exception("floatArray.size%stride != 0"))
        val countPoints = floatArray.size / stride
        var count = 0
        for (i in 0 until countPoints) {
            val pointArray = ArrayList<Float>()
            (0 until stride).forEach {
                pointArray.add(floatArray[count])
                count++
            }
            array.add(Coord(pointArray.toFloatArray(), coordsPerVertex))
        }
    }

    constructor(
        countPoints: Int,
        coordsPerVertex: CoordsPerVertex = CoordsPerVertex.VERTEX_3D
    ) : this(coordsPerVertex) {
        for (i in 0 until countPoints) {
            array.add(Coord(coordsPerVertex))
        }
    }

    constructor(type: FigureType) : this(type.countPoint)
    //endregion

    fun asSortedFloatBufferFromCoordsPerVertex(): FloatBuffer =
        asFloatArrayFromCoordsPerVertex().asSortedFloatBuffer()

    fun asSortedFloatBuffer(): FloatBuffer =
        asFloatArray().asSortedFloatBuffer()

    fun asFloatArray(): FloatArray {
        val pointArray = ArrayList<Float>()
        array.forEach {
            pointArray.addAll(it.asFloatArray().toList())
        }
        return pointArray.toFloatArray()
    }

    fun asFloatArrayFromCoordsPerVertex(): FloatArray {
        val pointArray = ArrayList<Float>()
        array.forEach {
            pointArray.addAll(it.asFloatArray(coordsPerVertex).toList())
        }
        return pointArray.toFloatArray()
    }
}

enum class FigureType(val countPoint: Int) {
    Other(0),
    Point(1),
    Line(2),
    Triangle(3),
    Square(4),
    Pentagon(5),
    Hexagon(6),
    ;

}