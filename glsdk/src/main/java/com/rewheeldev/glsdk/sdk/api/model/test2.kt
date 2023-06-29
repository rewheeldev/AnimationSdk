package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.internal.util.Math3d
import kotlin.math.abs


fun main() {

    val verticalAnglePitch = 33f
    val horizontalAngleYaw = 55f

    val rayInitialPoint = Coord(37f, 10f, 20f)
    val point = Math3d.pointFromAngle(
        verticalAnglePitch = verticalAnglePitch,
        horizontalAngleYaw = horizontalAngleYaw,
        rayInitialPoint = rayInitialPoint
    )
    val angles = Math3d.anglesFromPoint(point, rayInitialPoint)

    val verticalAnglePitchNew = angles.first
    val horizontalAngleYawNew = angles.second

    val v = anglesToAxes(Vector3(verticalAnglePitch.toDouble(),
        horizontalAngleYaw.toDouble(),0.0))
    val anglesFromV = axesToAngles(v.first, v.second,v.third)
    val anglesFromV2 = axesToAngles2(v.first, v.second,v.third)

    print(
        "\nVertical Angle (Pitch)\n" +
                " | degrees =$verticalAnglePitch: " +
                " | new degrees = ${Math.toDegrees(verticalAnglePitchNew.toDouble())}" +
                " | radians = ${Math.toRadians(verticalAnglePitch.toDouble())}" +
                " | new = $verticalAnglePitchNew"
    )
    print(
        " | error = ${
            calculatePercentageError(
                Math.toRadians(verticalAnglePitch.toDouble()).toFloat(),
                verticalAnglePitchNew
            )
        }"
    )
    print("\n${verticalAnglePitch.toDouble().toRadians()} | ${anglesFromV.x} " +
            "| ${anglesFromV2.x} | " +
            "\n")
    print(
        "\nHorizontal Angle (Yaw)\n " +
                " | degrees = $horizontalAngleYaw: " +
                " | New degrees = ${Math.toDegrees(horizontalAngleYawNew.toDouble())}" +
                " | radians = ${Math.toRadians(horizontalAngleYaw.toDouble())}" +
                " | New = $horizontalAngleYawNew"
    )
    print(
        " | error = ${
            calculatePercentageError(
                Math.toRadians(horizontalAngleYaw.toDouble()).toFloat(),
                horizontalAngleYawNew
            )
        }"
    )
    println("\n${horizontalAngleYaw.toDouble().toRadians()} | ${anglesFromV.y}" +
            "| ${anglesFromV2.y} | " +
            "\n" )
}

fun calculatePercentageError(actualValue: Float, expectedValue: Float): Float {
    val error = abs(actualValue - expectedValue)
    val percentageError = (error / expectedValue) * 100.0f
    return percentageError
}
