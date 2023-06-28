package com.rewheeldev.glsdk.sdk.internal.util

import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.api.model.normalize
import com.rewheeldev.glsdk.sdk.internal.util.Math.pointFromAngle
import java.lang.Math
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object Math {

    fun limiteAngle(value: Float) = if (value < 0) 360 + value % 360 else value % 360

    fun pointFromAngle(
        /**
         * точка из которой выходит луч
         */
        rayInitialPoint: Coord = Coord(0f, 0f, 0f),

        /**
         * угол откланения по вертикали (тангаж или наклонение)
         * принимает число в градусах (degree)
         * должен быть в пределах от 0 до 179
         */
        verticalAnglePitch: Float = 45f,
        /**
         * должен быть в пределах от 0 до 359
         */
        horizontalAngleYaw: Float = 90f
    ): Coord {
        //https://learnopengl.com/Getting-Started/Camera
        val radPitch = Math.toRadians(limiteAngle(verticalAnglePitch).toDouble())
        val radYaw = Math.toRadians(limiteAngle(horizontalAngleYaw).toDouble())
        return Coord().apply {


            x = (cos(radYaw) * cos(radPitch)).toFloat()
            y = sin(radPitch).toFloat()
            z = sin(x = radYaw * cos(radPitch)).toFloat()
//            z = (cos(radPitch) * sin(radYaw)).toFloat()

//            val r = 1f
//            x = (/*r **/ sin(radPitch) * cos(radYaw)).toFloat()
//            y = (/*r **/ sin(radPitch) * sin(radYaw)).toFloat()
//            z = (/*r **/ cos(radPitch)).toFloat()

            normalize()
            join(rayInitialPoint)
        }
    }


    data class SphericalCoordinates(val r: Double, val pitch: Double, val yaw: Double) {
        fun toCartesianCoord(): Coord = with(this) {
            val x = r * sin(pitch) * cos(yaw)
            val y = r * sin(pitch) * sin(yaw)
            val z = r * cos(pitch)
            Coord(x.toFloat(), y.toFloat(), z.toFloat())
        }
    }

    fun convertToSpherical(cartesianCoords: Coord): SphericalCoordinates {
        val r = sqrt(cartesianCoords.x * cartesianCoords.x + cartesianCoords.y * cartesianCoords.y + cartesianCoords.z * cartesianCoords.z)
        val theta = Math.acos(cartesianCoords.z.toDouble() / r)
        val phi = Math.atan2(cartesianCoords.y.toDouble(), cartesianCoords.x.toDouble())
        return SphericalCoordinates(r.toDouble(), theta, phi)
    }

    data class Angles(val verticalAnglePitch: Float, val horizontalAngleYaw: Float)

    fun anglesFromPoint(point: Coord): Angles {
        val r = sqrt(point.x.toDouble().pow(2) + point.y.toDouble().pow(2) + point.z.toDouble().pow(2))
        val verticalAnglePitch = asin(point.y.toDouble() / r).toFloat()
        val horizontalAngleYaw = atan2(point.z.toDouble(), point.x.toDouble()).toFloat()
        return Angles(Math.toDegrees(verticalAnglePitch.toDouble()).toFloat(), Math.toDegrees(horizontalAngleYaw.toDouble()).toFloat())
    }
}

fun main(){
    val point = pointFromAngle(verticalAnglePitch = 111f, horizontalAngleYaw = 88f)
    val r = sqrt(point.x.toDouble().pow(2) + point.y.toDouble().pow(2) + point.z.toDouble().pow(2))

    val verticalAnglePitch = asin(point.y.toDouble() / r).toFloat()
    val horizontalAngleYaw = atan2(point.z.toDouble(), point.x.toDouble()).toFloat()
    val pitch = Math.toDegrees(verticalAnglePitch.toDouble()).toFloat()
     val yaw =   Math.toDegrees(horizontalAngleYaw.toDouble()).toFloat()

    println("pitch = $pitch | yaw = $yaw")
}