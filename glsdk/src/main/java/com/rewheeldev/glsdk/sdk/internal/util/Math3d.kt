package com.rewheeldev.glsdk.sdk.internal.util

import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.api.model.Vector3
import com.rewheeldev.glsdk.sdk.api.model.anglesToAxes
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object Math3d {

    fun limiteAngle(value: Float) = if (value < 0) 360 + value % 360 else value % 360

    /**
     * Функция вычисляет координаты точки в трехмерном пространстве, которая
     * располагается на луче, выходящем из точки [rayInitialPoint] в направлении,
     * определяемом углами [verticalAnglePitch] и [horizontalAngleYaw], на
     * расстоянии [length] с использованием принципа Углов Эйлера.
     */
    fun pointFromAngle(
        /**
         * угол откланения по вертикали (тангаж или наклонение)
         * принимает число в градусах (degree)
         */
        verticalAnglePitch: Float,
        /**
         * угол откланения по горизонтали (рысканье или поворот)
         * принимает число в градусах (degree)
         */
        horizontalAngleYaw: Float,

        /**
         * точка из которой выходит луч
         */
        rayInitialPoint: Coord = Coord(0f, 0f, 0f),

        /**
         *
         */
        length: Float = 1f
    ): Coord {
        val radPitch = Math.toRadians(limiteAngle(verticalAnglePitch).toDouble())
        val radYaw = Math.toRadians(limiteAngle(horizontalAngleYaw).toDouble())
        return Coord().apply {
//            x = (cos(radYaw) * cos(radPitch)).toFloat()
//            y = sin(radPitch).toFloat()
//            z = sin(x = radYaw * cos(radPitch)).toFloat()
            val result = anglesToAxes(Vector3(limiteAngle(verticalAnglePitch).toDouble(),
                limiteAngle(horizontalAngleYaw).toDouble(),0.0)).first
            x = Math.toDegrees(result.x).toFloat()
            y = Math.toDegrees(result.y).toFloat()
            z = Math.toDegrees(result.z).toFloat()
            normalize()
            multiply(length)
//            join(rayInitialPoint)
        }
    }

    /**
     * Функция вычисляет углы направления точки в трехмерном пространстве,
     * которая находится на луче, выходящем из точки [rayInitialPoint] в направлении,
     * определяемом углами [verticalAnglePitch] и [horizontalAngleYaw], на
     * расстоянии [length] с использованием принципа Углов Эйлера.
     */
    fun anglesFromPoint(
        /**
         * Координаты точки в трехмерном пространстве
         */
        point: Coord,
        /**
         * Точка, из которой выходит луч
         */
        rayInitialPoint: Coord = Coord(0f, 0f, 0f)
    ): Pair<Float, Float> {
        val directionVector = point.minus(rayInitialPoint)
        val length = directionVector.calculateVectorLength()

        val verticalAnglePitch = asin(directionVector.y / length)
        val horizontalAngleYaw = atan2(directionVector.z, directionVector.x)

        return Pair(verticalAnglePitch, horizontalAngleYaw)
    }
}
