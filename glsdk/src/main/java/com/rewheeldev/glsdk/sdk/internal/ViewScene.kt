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
        // Set the camera position (View matrix)
        //do not remove this part of code, it needs like an example
//        Matrix.setLookAtM(
//            viewMatrix, 0,
//            camera.cameraPosition.x,
//            camera.cameraPosition.y,
//            camera.cameraPosition.z,
//            camera.cameraDirectionPoint.x,
//            camera.cameraDirectionPoint.y,
//            camera.cameraDirectionPoint.z,
//            camera.upVector.x,
//            camera.upVector.y,
//            camera.upVector.z
//        )

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

    fun reInitScene(sceneWidth: Int, sceneHeight: Int) {
        GLES20.glViewport(0, 0, sceneWidth, sceneHeight)
        //region
        //todo: think about this part, it should be removed
        var ratio = 1f
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        if (sceneWidth > sceneHeight) {
            ratio = sceneWidth.toFloat() / sceneHeight
            left *= ratio
            right *= ratio

        } else {
            ratio = sceneHeight.toFloat() / sceneWidth
            bottom *= ratio
            top *= ratio
        }
        //endregion

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
//        Matrix.frustumM(
//            projectionMatrix, 0,
//            left, right, bottom, top,
//            camera.nearVision, camera.farVision
//        )
        //Unfortunately,
        //frustumM() has a bug that affects some types of projections, and perspectiveM()
        //was only introduced in Android Ice Cream Sandwich and is not available on
        //earlier versions of Android.

        //https://www.scratchapixel.com/lessons/3d-basic-rendering/perspective-and-orthographic-projection-matrix
        Matrix.perspectiveM(
            projectionMatrix, 0, camera.fovY, sceneWidth.toFloat()
                    / sceneHeight.toFloat(), camera.farVision, camera.nearVision
        )
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