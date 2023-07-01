package com.rewheeldev.glsdk.sdk.internal.util

import com.rewheeldev.glsdk.sdk.api.model.Coord
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object Math3d {

    fun limitAngle(value: Float) = if (value < 0) 360 + value % 360 else value % 360
    fun limitVerticalAngle(value: Float) =
        if (value < 89f) 89f else if (value > 269f) 269f else value

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
        length: Float = 5f
    ): Coord {
        val radPitch = Math.toRadians(limitAngle(verticalAnglePitch).toDouble())
        val radYaw = Math.toRadians(limitAngle(horizontalAngleYaw).toDouble())
        val radRoll = 0.0

        return Coord().apply {
            val sx = sin(radPitch)
            val cx = cos(radPitch)
            val sy = sin(radYaw)
            val cy = cos(radYaw)
            val sz = sin(radRoll)
            val cz = cos(radRoll)

            x = (cy * cz).toFloat()
            y = (sx * sy * cz + cx * sz).toFloat()
            z = (-cx * sy * cz + sx * sz).toFloat()
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
