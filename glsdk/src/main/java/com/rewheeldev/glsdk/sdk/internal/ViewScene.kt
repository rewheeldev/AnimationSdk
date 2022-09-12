package com.rewheeldev.glsdk.sdk.internal

import android.opengl.GLES20
import android.opengl.Matrix
import com.rewheeldev.glsdk.sdk.api.Coord

const val matrixSideSize: Int = 4

class ViewScene(var camera: CameraView = CameraView()) {
    val lastResultMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val projectionMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val viewMatrix = FloatArray(matrixSideSize * matrixSideSize)

    fun update(): FloatArray {
        // Set the camera position (View matrix)
        Matrix.setLookAtM(
            viewMatrix, 0,
            camera.cameraPosition.x,
            camera.cameraPosition.y,
            camera.cameraPosition.z,
            camera.cameraDirectionPoint.x,
            camera.cameraDirectionPoint.y,
            camera.cameraDirectionPoint.z,
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

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(
            projectionMatrix, 0,
            left, right, bottom, top,
            camera.nearVision, camera.farVision
        )
    }
}

data class CameraView(
    var cameraPosition: Coord = Coord(0.0f, 0.0f, 5.0f),
    var cameraDirectionPoint: Coord = Coord(0.0f, 0.0f, 0.0f),
    var upVector: Coord = Coord(0.0f, 5f, 0.0f),
    var nearVision: Float = 0.1f,
    var farVision: Float = 1000f
)