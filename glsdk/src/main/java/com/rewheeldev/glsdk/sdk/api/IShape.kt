package com.rewheeldev.glsdk.sdk.api

import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.api.shape.border.Border

interface IShape {
    val id: Long
    val coords: Coords
    val colors: Colors
    val border: Border
}