package com.rewheeldev.glsdk.sdk.api.model

import kotlin.math.*

data class Vector3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    fun toCoord() = Coord(x.toFloat(), y.toFloat(), z.toFloat())
}

fun Double.toRadians():Double{
    val DEG2RAD = acos(-1.0) / 180.0 // PI/180
    return this * DEG2RAD
}

fun radiansToDegrees(radians: Double): Double {
    val RAD2DEG = 180.0 / acos(-1.0) // 180/PI
    return radians * RAD2DEG
}

fun anglesToAxes(angles: Vector3): Triple<Vector3, Vector3, Vector3> {

    var sx: Double
    var sy: Double
    var sz: Double
    var cx: Double
    var cy: Double
    var cz: Double
    var theta: Double

    // rotation angle about X-axis (pitch)
    theta = angles.x.toRadians()
    sx = sin(theta)
    cx = cos(theta)

    // rotation angle about Y-axis (yaw)
    theta = angles.y.toRadians()
    sy = sin(theta)
    cy = cos(theta)

    // rotation angle about Z-axis (roll)
    theta = angles.z.toRadians()
    sz = sin(theta)
    cz = cos(theta)

    // determine left axis
    val left = Vector3(
        cy * cz,
        sx * sy * cz + cx * sz,
        -cx * sy * cz + sx * sz
    )

    // determine up axis
    val up = Vector3(
        -cy * sz,
        -sx * sy * sz + cx * cz,
        cx * sy * sz + sx * cz
    )

    // determine forward axis
    val forward = Vector3(
        sy,
        -sx * cy,
        cx * cy
    )

    return Triple(left, up, forward)
}

fun axesToAngles(left: Vector3, up: Vector3, forward: Vector3): Vector3 {
    val pitch = asin(forward.y)
    val yaw = atan2(forward.x, forward.z)

    val pitchDegrees = Math.toDegrees(pitch.toDouble())
    val yawDegrees = Math.toDegrees(yaw.toDouble())

    return Vector3(pitchDegrees, yawDegrees, 0.0)
}


fun axesToAngles2(left: Vector3, up: Vector3, forward: Vector3): Vector3 {
    val angles = Vector3()

    // Calculate pitch (X-axis rotation)
    angles.x = atan2(forward.y, sqrt(left.y * left.y + up.y * up.y)).toDouble()

    // Calculate yaw (Y-axis rotation)
    angles.y = atan2(-left.z, sqrt(left.x * left.x + left.y * left.y + up.z * up.z)).toDouble()

    // Calculate roll (Z-axis rotation)
    angles.z = atan2(left.y, up.y).toDouble()

    // Convert angles to degrees
//    angles.x = radiansToDegrees(angles.x)
//    angles.y = radiansToDegrees(angles.y)
//    angles.z = radiansToDegrees(angles.z)

    return angles
}