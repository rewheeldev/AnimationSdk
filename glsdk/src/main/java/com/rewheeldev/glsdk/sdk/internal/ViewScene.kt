package com.rewheeldev.glsdk.sdk.internal

import android.opengl.GLES20
import android.opengl.Matrix
import com.rewheeldev.glsdk.sdk.api.model.Coord
import kotlin.math.cos
import kotlin.math.sin

const val matrixSideSize: Int = 4

class ViewScene(var camera: CameraView = CameraView()) {
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
            camera.cameraPosition.x + camera.cameraDirectionPoint.x,
            camera.cameraPosition.y + camera.cameraDirectionPoint.y,
            camera.cameraPosition.z + camera.cameraDirectionPoint.z,
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

data class CameraView(
    var cameraPosition: Coord = Coord(0.0f, 0.0f, 5.0f),
    var cameraDirectionPoint: Coord = Coord(0.0f, 0.0f, 0.0f),
    var upVector: Coord = Coord(0.0f, 5f, 0.0f),
    var nearVision: Float = 0.1f,
    var farVision: Float = 1000f,
    var fovY: Float = 100f
)

/**
 * возвращает точку на раастоянии [radius] от точки [position] в направлении угла [angle]
 */
fun directionPoint2d(
    positionX: Float = 0f,
    positionY: Float = 0f,
    angle: Float = 45f,
    radius: Float = 2f
): Pair<Float, Float> {
    val x = (positionX + radius * cos(Math.toRadians(angle.toDouble())))
    val y = (positionY + radius * sin(Math.toRadians(angle.toDouble())))
    return Pair(x.toFloat(), y.toFloat())
}

fun cameraDirectionFromAngel(
    cameraPosition: Coord = Coord(0.0f, 0.0f, 0.0f),
    horizontalAngel: Float,
    verticalAngel: Float,
): Coord {
    val horizontalPoint = directionPoint2d(
        cameraPosition.x, cameraPosition.y,
        horizontalAngel
    )
    val verticalPoint = directionPoint2d(
        cameraPosition.x, cameraPosition.z,
        verticalAngel,
//            radius = horizontalPoint.first
    )
    return Coord(
        x = horizontalPoint.first,
        y = horizontalPoint.second,
        z = verticalPoint.second
    )
}

fun directionPoint3d(
    cameraPosition: Coord = Coord(0f, 0f, 0f),
    angleY: Float = 0f,
    angleZ: Float = 0f,
    radius: Float = 1000f
): Coord {
    val angleYr = Math.toRadians(angleY.toDouble())
    val angleZr = Math.toRadians(angleZ.toDouble())

    val x = cameraPosition.x + radius * sin(angleZr) * cos(angleYr)
    val y = cameraPosition.y + radius * sin(angleZr) * sin(angleYr)
    val z = cameraPosition.z + radius * cos(angleZr)
    return Coord(x.toFloat(), y.toFloat(), z.toFloat())
}

fun directionPoint3d(
    pitch: Float,
    yaw: Float = 90f,
): Coord {
    //https://learnopengl.com/Getting-Started/Camera
    var pitchLocal: Float = pitch
    if (pitch > 89.0f) {
        pitchLocal = 89.0f
    }
    if (pitch < -89.0f) {
        pitchLocal = -89.0f
    }
    val direction = Coord()
    val radPitch = Math.toRadians(pitchLocal.toDouble())
    val radYaw = Math.toRadians(yaw.toDouble())
    direction.x = (cos(radYaw) * cos(radPitch)).toFloat()
    direction.y = sin(radPitch).toFloat()
    direction.z = sin(x = radYaw * cos(radPitch)).toFloat()

    return direction
}