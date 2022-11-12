package com.rewheeldev.glsdk.sdk.internal.gl

import android.opengl.GLES20
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.internal.ViewScene
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_UMVPMATRIX
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VCOLOR
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VPOSITION
import utils.Color

//todo: this class must die
data class TriangleInternal(
    override val id: Long,
    override var programId: Int,
    private val coords: Coords,
    private val color: Color = Color.WHITE,
    override val figureType: FigureType
) : IShapeDraw {


    override fun draw(vpMatrix: FloatArray) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(programId)
        // get handle to vertex shader's vPosition member
        val positionHandle = GLES20.glGetAttribLocation(programId, SHADER_VARIABLE_VPOSITION).also {
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
            GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_VCOLOR).also { colorHandle ->
                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color.asFloatArray(), 0)
            }

            GLES20.glLineWidth(100f)
            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, coords.size)
            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }

        // pass in the calculated transformation matrix
        // get handle to shape's transformation matrix
        val vPMatrixHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_UMVPMATRIX)
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vpMatrix, 0)
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.size)
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    override fun draw(scene: ViewScene) {
        draw(scene.lastResultMatrix)
    }

}