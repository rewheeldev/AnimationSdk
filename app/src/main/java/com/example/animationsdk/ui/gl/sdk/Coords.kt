package com.example.animationsdk.ui.gl.sdk

import com.example.animationsdk.ui.gl.sdk.internal.CoordsPerVertex
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
    other(0),
    point(1),
    line(2),
    triangle(3),
    square(4),
    pentagon(5),
    hexagon(6),
    ;

}