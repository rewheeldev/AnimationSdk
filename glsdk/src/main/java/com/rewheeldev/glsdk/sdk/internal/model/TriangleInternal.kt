package com.rewheeldev.glsdk.sdk.internal.model

import android.opengl.GLES20
import com.rewheeldev.glsdk.sdk.api.Color
import com.rewheeldev.glsdk.sdk.api.Coords

data class TriangleInternal(
    val triangleId: Long,
    private var programId: Int,
    private val coords: Coords,
    private val color: Color = Color.WHITE
) {

    fun draw(vpMatrix: FloatArray) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(programId)
        // get handle to vertex shader's vPosition member
        val positionHandle = GLES20.glGetAttribLocation(programId, "vPosition").also {
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)
            // Prepare the triangle coordinate data

            GLES20.glVertexAttribPointer(
                it,
                coords.coordsPerVertex.num,
                GLES20.GL_FLOAT,
                false,
                coords.getStrideAsByte(),
                coords.asSortedFloatBufferFromCoordsPerVertex()
            )
            // get handle to fragment shader's vColor member
            GLES20.glGetUniformLocation(programId, "vColor").also { colorHandle ->
                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color.toFloatArray(), 0)
            }

            GLES20.glLineWidth(100f)
            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, coords.size)
            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }

        // pass in the calculated transformation matrix
        // get handle to shape's transformation matrix
        val vPMatrixHandle = GLES20.glGetUniformLocation(programId, "uMVPMatrix")
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vpMatrix, 0)
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.size)
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

}