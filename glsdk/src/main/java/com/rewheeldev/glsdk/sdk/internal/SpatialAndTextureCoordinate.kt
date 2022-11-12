package com.rewheeldev.glsdk.sdk.internal

import com.rewheeldev.glsdk.sdk.api.model.Coord
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

//Copying Memory from Java’s Memory Heap to the Native Memory Heap
//We have access
//to a special set of classes in Java that will allocate a block of native memory
//and copy our data to that memory. This native memory will be accessible to
//the native environment, and it will not be managed by the garbage collector.
fun FloatArray.asSortedFloatBuffer(): FloatBuffer {

    //We’ve added a constant, BYTES_PER_FLOAT, and a FloatBuffer. A float in Java has
    //32 bits of precision, while a byte has 8 bits of precision. This might seem like
    //an obvious point to make, but there are 4 bytes in every float. We’ll need to
    //refer to that in many places down the road. The FloatBuffer will be used to store
    //data in native memory.

    //1. we allocated a block of native memory, this memory will not be managed by the garbage collector.
    //2. The next line tells the byte buffer that it should organize its bytes in native
    //order. When it comes to values that span multiple bytes, such as 32-bit
    //integers, the bytes can be ordered either from most significant to least signif-
    //icant or from least to most. Think of this as similar to writing a number either
    //from left to right or right to left. It’s not important for us to know what that
    //order is, but it is important that we use the same order as the platform.
    //3.Finally, we’d rather not deal with individual bytes directly. We want to work
    //with floats, so we call asFloatBuffer() to get a FloatBuffer that reflects the underlying
    //bytes.
    //4. We then copy data from Dalvik’s memory to native memory

    //more info about Memory pool: https://en.wikipedia.org/wiki/Memory_pool

    //The memory will be freed when the process
    //gets destroyed
    return ByteBuffer.allocateDirect(this.size * 4) //1
        .order(ByteOrder.nativeOrder()) //2
        .asFloatBuffer() //3
        .put(this)//4
        .apply {
            //Before we tell OpenGL to read data from this buffer, we need to make sure
            //that it’ll read our data starting at the beginning and not at the middle or the
            //end.
            position(0)
        }
}
