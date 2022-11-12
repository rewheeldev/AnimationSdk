package com.rewheeldev.glsdk.sdk.internal

import com.rewheeldev.glsdk.sdk.api.IShape
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border
import com.rewheeldev.glsdk.sdk.api.shape.line.LinkLineTypes
import com.rewheeldev.glsdk.sdk.internal.gl.FigureType

internal class Shape(
    override val coords: Coords,
    override val colors: Colors = Colors(),
    override val border: Border = Border(),
    override val figureType: FigureType = FigureType.TRIANGLE
) : IShape {
    override val id: Long = counterObject++

    companion object {
        var counterObject: Long = 0

        /**
         * для правильного отображения кординаты сетки должны
         * быть созданы с [borderType] = [LinkLineTypes.Strip]
         */
        fun prepareCoordsForGrid(
            x: Float = 0f, y: Float = 0f, z: Float = 0f,
            columns: Int = 10, rows: Int = 10,
            stepSize: Float = 10f
        ): Coords {
            val squareExample = Coords(
                floatArrayOf(
                    x, y,
                    x, y + stepSize,
                    x + stepSize, y + stepSize,
                    x + stepSize, y,
                    x, y,
                ),
                CoordsPerVertex.VERTEX_2D
            )
            val resultCoords = Coords(coordsPerVertex = CoordsPerVertex.VERTEX_3D)

            (0 until rows).forEach { yi ->
                val offsetY = yi.toFloat() * stepSize
                (0 until columns).forEach { xi ->
                    val offsetX = xi.toFloat() * stepSize
                    squareExample.array.forEach {
                        val coord = Coord(offsetX + it.x, offsetY + it.y, z)
                        resultCoords.array.add(coord)
                    }
                }
                //добавление последней дополнительной точки для переноса соединение на линию вверх
                resultCoords.array.add(resultCoords.array[resultCoords.array.lastIndex - 3])
            }
            return resultCoords
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shape

        if (coords != other.coords) return false
        if (colors != other.colors) return false
        if (border.type != other.border.type) return false
        if (border.color != other.border.color) return false
        if (border.width != other.border.width) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coords.hashCode()
        result = 31 * result + colors.hashCode()
        result = 31 * result + border.type.hashCode()
        result = 31 * result + border.color.hashCode()
        result = 31 * result + border.width.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "Shape(" +
                "coords=$coords" +
                ", colors=$colors" +
                ", border=$border" +
                ", id=$id)"
    }

}