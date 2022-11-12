package com.rewheeldev.glsdk.sdk.internal.gl

import android.opengl.GLES20
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import com.rewheeldev.glsdk.sdk.internal.ViewScene
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_UMVPMATRIX
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VCOLOR
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VPOSITION

data class Figure(
    override val id: Long,
    override var programId: Int,
    private val coords: Coords,
    override val figureType: FigureType,
    private val colors: Colors = Colors(),
    private val border: Border = Border(),
) : IShapeDraw {
    override fun draw(scene: ViewScene) {
        draw(scene.lastResultMatrix)
    }

    override fun draw(vpMatrix: FloatArray) {

        //We call glUseProgram() to tell OpenGL to use the program defined here when
        //drawing anything to the screen.
        GLES20.glUseProgram(programId)

        val attribLocationId = GLES20.glGetAttribLocation(programId, SHADER_VARIABLE_VPOSITION)

        withVertexAttribArray(attribLocationId) {

            GLES20.glVertexAttribPointer(
                attribLocationId,
                coords.coordsPerVertex.num,
                GLES20.GL_FLOAT,
                false,
                coords.getStrideAsByte(),
                coords.asSortedFloatBufferFromCoordsPerVertex()
            )

            val colorHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_VCOLOR)
            when (figureType) {
                FigureType.LINE -> {

                    withBlend {

                        colors.array.forEach { color ->
                            GLES20.glUniform4f(
                                colorHandle,
                                color.r,
                                color.g,
                                color.b,
                                color.a
                            )
                        }
                        //TODO:  GLES20.glDrawArrays(..., 0, coords.size) // coords.size cannot be used to tell how many points (vertices) should be taken
                        //for example, if we have to draw a line we need to draw 2 points (the size will be 4). We don't need to draw 4 points we need only 2 points
                        GLES20.glDrawArrays(GLES20.GL_LINES, 0, coords.size)

                    }
                }
                FigureType.TRIANGLE -> {


                    drawTriangle(colorHandle)

                    drawBorder(colorHandle)
                }
                FigureType.POINT -> {
                    val colorHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_VCOLOR)

                    GLES20.glVertexAttribPointer(
                        attribLocationId,
                        coords.coordsPerVertex.num,
                        GLES20.GL_FLOAT,
                        false,
                        coords.getStrideAsByte(),
                        coords.asSortedFloatBufferFromCoordsPerVertex()
                    )

                    withBlend {

                        colors.array.forEach { color ->
                            GLES20.glUniform4f(
                                colorHandle,
                                color.r,
                                color.g,
                                color.b,
                                color.a
                            )
                        }
                        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, coords.size)

                    }
                }
            }


        }
        val vPMatrixHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_UMVPMATRIX)
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vpMatrix, 0)
    }

    private fun drawTriangle(colorHandle: Int) {
        if (colors.array.isNotEmpty()) { //TODO: what do we need to do if we retrieve the empty array of colors
            withBlend {
                colors.array.forEach { color ->
                    GLES20.glUniform4f(
                        colorHandle,
                        color.r,
                        color.g,
                        color.b,
                        color.a
                    )
                }

                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.size)
            }
        }
    }

    private fun drawBorder(colorHandle: Int) {
        if (border.width > 0) {
            withBlend {
                GLES20.glUniform4f(
                    colorHandle,
                    border.color.r,
                    border.color.g,
                    border.color.b,
                    border.color.a
                )

                GLES20.glLineWidth(border.width)
                GLES20.glDrawArrays(border.type.code, 0, coords.size)
            }
        }
    }

    private fun withBlend(run: () -> Unit) {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        run()
        GLES20.glDisable(GLES20.GL_BLEND)
    }

    private fun withVertexAttribArray(id: Int, run: () -> Unit) {
        GLES20.glEnableVertexAttribArray(id)
        run()
        GLES20.glDisableVertexAttribArray(id)
    }

}

