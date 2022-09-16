package com.rewheeldev.glsdk.sdk.internal.gl

import android.opengl.GLES10
import android.opengl.GLES20
import com.rewheeldev.glsdk.sdk.api.model.Color
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.internal.F_NOT_INIT
import com.rewheeldev.glsdk.sdk.internal.ViewScene
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_UMVPMATRIX
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VCOLOR
import com.rewheeldev.glsdk.sdk.internal.util.FigureShader.SHADER_VARIABLE_VPOSITION

data class Figure(
    override val id: Long,
    override var programId: Int,
    private val coords: Coords,
    private val colors: Colors = Colors(),
    private val borderType: TypeLinkLines = TypeLinkLines.Loop,
    private val borderColor: Color = Color.WHITE,
    private val borderWidth: Float = F_NOT_INIT
) : IShapeDraw {
    override fun draw(scene: ViewScene) {
        draw(scene.lastResultMatrix)
    }

    override fun draw(vpMatrix: FloatArray) {
        GLES20.glUseProgram(programId)
        val positionHandle = GLES20.glGetAttribLocation(programId, SHADER_VARIABLE_VPOSITION).also {
            GLES20.glEnableVertexAttribArray(it)

            GLES20.glVertexAttribPointer(
                it,
                coords.coordsPerVertex.num,
                GLES20.GL_FLOAT,
                false,
                coords.getStrideAsByte(),
                coords.asSortedFloatBufferFromCoordsPerVertex()
            )
            val colorHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_VCOLOR)
            GLES20.glUniform4fv(colorHandle, 1/*TODO colors.size*/, colors.asFloatArray(), 0)
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.size)

            if (borderWidth > 0) {
                GLES20.glUniform4fv(colorHandle, 1, borderColor.asFloatArray(), 0)

                GLES20.glLineWidth(borderWidth)
                GLES20.glDrawArrays(borderType.code, 0, coords.size)
            }
            GLES20.glDisableVertexAttribArray(it)
        }

        val vPMatrixHandle = GLES20.glGetUniformLocation(programId, SHADER_VARIABLE_UMVPMATRIX)
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vpMatrix, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.size)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

}

enum class TypeLinkLines(val code: Int) {
    /**
     * соединяет все точки между собой: первую со второй, вторую с третей и так далее
     * Примечание: первая и последняя не будут соединены
     */
    Strip(GLES20.GL_LINE_STRIP),

    /**
     * соединяет пары точек между собой: первую со второй, третью с четветой
     * Примечание: если количество точек не четное последняя точка не учитывается
     */
    Lines(GLES20.GL_LINES),

    /**
     * соединяет все точки между собой: первую со второй, вторую с третей ... и последнюю с первой
     */
    Loop(GLES20.GL_LINE_LOOP),

    ;
}
