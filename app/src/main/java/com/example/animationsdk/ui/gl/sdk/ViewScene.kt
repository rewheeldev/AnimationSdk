package com.example.animationsdk.ui.gl.sdk

import android.opengl.Matrix

const val matrixSideSize: Int = 4

class ViewScene(var camera: CameraView = CameraView()) {
    private val viewProjectionMultiplyMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val projectionMatrix = FloatArray(matrixSideSize * matrixSideSize)
    private val viewMatrix = FloatArray(matrixSideSize * matrixSideSize)

    fun update() {
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
            viewProjectionMultiplyMatrix, 0,
            projectionMatrix, 0,
            viewMatrix, 0
        )
    }
}

class CameraView {
    var cameraPosition = Position3D(0.0f, 0.0f, 5.0f)
    var cameraDirectionPoint = Position3D(0.0f, 0.0f, 0.0f)
    var upVector = Position3D(0.0f, 5f, 0.0f)
}