package com.rewheeldev.glsdk.sdk.api

import com.rewheeldev.glsdk.sdk.api.model.Color
import com.rewheeldev.glsdk.sdk.api.model.Coords

interface IShape {
    val id: Long
    val coords: Coords
    val color: Color
}