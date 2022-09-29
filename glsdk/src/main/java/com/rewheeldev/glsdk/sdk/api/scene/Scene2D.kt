package com.rewheeldev.glsdk.sdk.api.scene

import com.rewheeldev.glsdk.sdk.api.IScene
import com.rewheeldev.glsdk.sdk.api.IViewScene
import com.rewheeldev.glsdk.sdk.api.model.Coord
import com.rewheeldev.glsdk.sdk.internal.CameraView
import com.rewheeldev.glsdk.sdk.internal.ViewScene

class Scene2D(
    var widthMeters: Int = 800,
    var heightMeters: Int = 600
) : IScene {
    private val camera = CameraView(
        cameraPosition = Coord(0.0f, 0.0f, 5.0f),
        cameraDirectionPoint = Coord(0.0f, 0.0f, 0.0f),
        cameraTiltPoint = Coord(0.0f, 5f, 0.0f),
        nearVision = 0.1f,
        farVision = 1000f
    )

    override val scene: IViewScene = ViewScene(camera)
    private val sceneInternal: ViewScene = when (scene) {
        is ViewScene -> {
            scene
        }
        else -> {
            throw RuntimeException("Scene")
        }
    }

    fun reInitScene(sceneWidth: Int, sceneHeight: Int) {
        sceneInternal.reInitScene(sceneWidth, sceneHeight)
    }
}