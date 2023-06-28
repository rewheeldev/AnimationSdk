package com.rewheeldev.glsdk.sdk.api.model

import kotlin.math.*

data class Angles(val verticalAnglePitch: Float, val horizontalAngleYaw: Float)

fun pointFromAngle(rayInitialPoint: Coord = Coord(0f, 0f, 0f), verticalAnglePitch: Float = 45f, horizontalAngleYaw: Float = 90f): Coord {
    val radPitch = Math.toRadians(limiteAngle(verticalAnglePitch).toDouble())
    val radYaw = Math.toRadians(limiteAngle(horizontalAngleYaw).toDouble())
    return Coord().apply {
        x = (cos(radYaw) * cos(radPitch)).toFloat()
        y = sin(radPitch).toFloat()
//        z = (cos(radPitch) * sin(radYaw)).toFloat()
        z = sin(x = radYaw * cos(radPitch)).toFloat()
        normalize()
        join(rayInitialPoint)
    }
}

fun anglesFromPoint(point: Coord): Angles {
    val radVertical = atan2(point.y.toDouble(), sqrt(point.x.toDouble().pow(2) + point.z.toDouble().pow(2)))
    val radHorizontal = atan2(point.z.toDouble(), point.x.toDouble())
    val verticalAngle = Math.toDegrees(radVertical).toFloat()
    val horizontalAngle = Math.toDegrees(radHorizontal).toFloat()
    return Angles(verticalAnglePitch = verticalAngle, horizontalAngleYaw = horizontalAngle)
}
fun limiteAngle(value: Float) = if (value < 0) 360 + value % 360 else value % 360

fun main() {
    val initialPoint = Coord(0f, 0f, 0f)
//    val verticalAngle = 45f
//    val horizontalAngle = 90f
//    val verticalAngle = 112f
//    val horizontalAngle = 112f
    val verticalAnglePitch = 111f
    val horizontalAngleYaw = 43f

    val point = pointFromAngle(initialPoint, verticalAnglePitch, horizontalAngleYaw)
    val angles = anglesFromPoint(point)

    println("Input:")
    println("Initial Point: $initialPoint")
    println("verticalAnglePitch: $verticalAnglePitch degrees")
    println("Horizontal Angle: $horizontalAngleYaw degrees")

    println("\nOutput:")
    println("Point from Angles: $point")
    println("Angles from Point: $angles")

    println("====================")

    val sphericalCoords = SphericalCoordinates(r = 1.0,
        theta = Math.toRadians(verticalAnglePitch.toDouble()),
        phi = Math.toRadians(horizontalAngleYaw.toDouble())
    )
    val cartesianCoords = convertToCartesian(sphericalCoords)
    println(cartesianCoords)

    val angle = convertToSpherical(cartesianCoords)
    println("pitch = ${Math.toDegrees(angle.theta)}| yaw = ${Math.toDegrees(angle.phi)}")

    var p = 0.0
//    var yaw = 0.0
    repeat(360){

        val sphericalCoords = SphericalCoordinates(r = 1.0,
            theta = Math.toRadians(p++),
            phi = Math.toRadians(77.0)
        )
        val cartesianCoords = convertToCartesian(sphericalCoords)
        println(cartesianCoords)
        val angle = convertToSpherical(cartesianCoords)
        println("pitch = ${Math.toDegrees(angle.theta)}| yaw = ${Math.toDegrees(angle.phi)}")

    }

}

fun convertToSpherical(cartesianCoords: CartesianCoordinates): SphericalCoordinates {
    val r = sqrt(cartesianCoords.x * cartesianCoords.x + cartesianCoords.y * cartesianCoords.y + cartesianCoords.z * cartesianCoords.z)
    val theta = acos(cartesianCoords.z / r)
    val phi = atan2(cartesianCoords.y, cartesianCoords.x)
    return SphericalCoordinates(r, theta, phi)
}
data class SphericalCoordinates(val r: Double, val theta: Double, val phi: Double)

data class CartesianCoordinates(val x: Double, val y: Double, val z: Double)

fun convertToCartesian(sphericalCoords: SphericalCoordinates): CartesianCoordinates {
    val x = sphericalCoords.r * sin(sphericalCoords.theta) * cos(sphericalCoords.phi)
    val y = sphericalCoords.r * sin(sphericalCoords.theta) * sin(sphericalCoords.phi)
    val z = sphericalCoords.r * cos(sphericalCoords.theta)
    return CartesianCoordinates(x, y, z)
}