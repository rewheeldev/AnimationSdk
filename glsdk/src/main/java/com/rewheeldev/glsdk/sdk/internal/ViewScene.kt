package com.rewheeldev.glsdk.sdk.internal

import android.opengl.GLES20
import android.opengl.Matrix
import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.internal.util.Math.pointFromAngle
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

const val matrixSideSize: Int = 4

class ViewScene(var camera: CameraProperties = CameraProperties()) {
    val lastResultMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val projectionMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val viewMatrix = FloatArray(matrixSideSize * matrixSideSize)

    fun update(): FloatArray {
        //https://www.scratchapixel.com/lessons/3d-basic-rendering/perspective-and-orthographic-projection-matrix
        Matrix.perspectiveM(
            projectionMatrix, 0, camera.fovY, lastWidth.toFloat()
                    / lastHeight.toFloat(), camera.farVision, camera.nearVision
        )

        //to show movement
        Matrix.setLookAtM(
            viewMatrix, 0,
            camera.cameraPosition.x,
            camera.cameraPosition.y,
            camera.cameraPosition.z,
            /*camera.cameraPosition.x +*/ camera.cameraDirectionPoint.x,
            /*camera.cameraPosition.y +*/ camera.cameraDirectionPoint.y,
            /*camera.cameraPosition.z + */camera.cameraDirectionPoint.z,
            camera.upVector.x,
            camera.upVector.y,
            camera.upVector.z
        )

        // Calculate the projection and view transformation
        //https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/gluLookAt.xml
        Matrix.multiplyMM(
            lastResultMatrix, 0,
            projectionMatrix, 0,
            viewMatrix, 0
        )
        return lastResultMatrix
    }

    var lastWidth: Int = 0
    var lastHeight: Int = 0

    fun reInitScene(sceneWidth: Int, sceneHeight: Int) {
        lastWidth = sceneWidth
        lastHeight = sceneHeight
        GLES20.glViewport(0, 0, sceneWidth, sceneHeight)
    }
}

class CameraProperties(
    var cameraPosition: Coord = Coord(0.0f, 0.0f, 5.0f),
    rollAngle: Float = 0f,
    verticalAnglePitch: Float = 45f,
    horizontalAngleYaw: Float = 90f,
    var nearVision: Float = 0.1f,
    var farVision: Float = 1000f,
    var fovY: Float = 100f
) {
    var verticalAnglePitch: Float = verticalAnglePitch
        get() = if (isDirectionChangeAngleUpdated) field else recalcPitchAngle()
        set(value) {
            field = value
            isDirectionChangeAngleUpdated = true
            setDirectionAsAngel()
        }

    var horizontalAngleYaw: Float = horizontalAngleYaw
        get() = if (isDirectionChangeAngleUpdated) field else recalcYawAngle()
        set(value) {
            field = value
            isDirectionChangeAngleUpdated = true
            setDirectionAsAngel()
        }

    var rollAngle: Float = rollAngle
        get() = if (isUpVectorChangeAngleUpdated) field else recalcRollAngle()
        set(value) {
            field = value
            isUpVectorChangeAngleUpdated = true
//            setDirectionAsAngel()
        }

    var cameraDirectionPoint: Coord = Coord(0.0f, 0.0f, 0.0f)
        set(value) {
            field = value
            isDirectionChangeAngleUpdated = false
        }
    var upVector: Coord = Coord(0.0f, 5f, 0.0f)
        set(value) {
            field = value
            isUpVectorChangeAngleUpdated = false
        }
    private var isDirectionChangeAngleUpdated: Boolean = false
    private var isUpVectorChangeAngleUpdated: Boolean = false

    fun setDirectionAsAngel(
        verticalAnglePitch: Float = this.verticalAnglePitch,
        horizontalAngleYaw: Float = this.horizontalAngleYaw
    ) {
        cameraDirectionPoint = pointFromAngle(
            rayInitialPoint = cameraPosition,
            verticalAnglePitch = verticalAnglePitch,
            horizontalAngleYaw = horizontalAngleYaw
        )
    }

    private fun setUpVectorAsAngel() {
        //TODO set cameraDirectionPoint
        TODO()
    }

    private fun recalcPitchAngle(): Float {
        TODO()
//        val angleDegrees = Math.atan2(y, x) * (180 / Math.PI)

        val c = cameraDirectionPoint - cameraPosition

        val length = sqrt(c.x * c.x + c.y * c.y + c.z * c.z)
        val pitch = Math.PI - atan(sqrt(c.x * c.x + c.y * c.y) / c.z)
        val yaw = atan(c.y / c.x)
    }

    private fun recalcYawAngle(): Float {
        TODO()
    }

    private fun recalcRollAngle(): Float {
        TODO()
    }


    fun offsetDirectionAngle(offsetHorizontalAngle: Float, offsetVerticalAngle: Float) {
        horizontalAngleYaw += offsetHorizontalAngle
        verticalAnglePitch += offsetVerticalAngle
    }
}

fun convertToCartesian(sphericalCoords: SphericalCoordinates): Coord {
    val x = sphericalCoords.r * sin(sphericalCoords.theta) * cos(sphericalCoords.phi)
    val y = sphericalCoords.r * sin(sphericalCoords.theta) * sin(sphericalCoords.phi)
    val z = sphericalCoords.r * cos(sphericalCoords.theta)
    return Coord(x.toFloat(), y.toFloat(), z.toFloat())
}

data class SphericalCoordinates(val r: Double, val theta: Double, val phi: Double)
fun convertToSpherical(cartesianCoords: Coord): SphericalCoordinates {
    val r = sqrt(cartesianCoords.x * cartesianCoords.x + cartesianCoords.y * cartesianCoords.y + cartesianCoords.z * cartesianCoords.z)
    val theta = Math.acos(cartesianCoords.z.toDouble() / r)
    val phi = Math.atan2(cartesianCoords.y.toDouble(), cartesianCoords.x.toDouble())
    return SphericalCoordinates(r.toDouble(), theta, phi)
}

//fun directionPoint3d(
//    pitch: Float,
//    yaw: Float = 90f,
//): Coord {
//    //https://learnopengl.com/Getting-Started/Camera
//    var pitchLocal: Float = pitch
//    if (pitch > 89.0f) {
//        pitchLocal = 89.0f
//    }
//    if (pitch < -89.0f) {
//        pitchLocal = -89.0f
//    }
//    val direction = Coord()
//    val radPitch = Math.toRadians(pitchLocal.toDouble())
//    val radYaw = Math.toRadians(yaw.toDouble())
//    direction.x = (cos(radYaw) * cos(radPitch)).toFloat()
//    direction.y = sin(radPitch).toFloat()
//    direction.z = sin(x = radYaw * cos(radPitch)).toFloat()
//
//    return direction
//}