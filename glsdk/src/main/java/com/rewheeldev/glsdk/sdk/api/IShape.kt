package com.rewheeldev.glsdk.sdk.api

import com.rewheeldev.glsdk.sdk.api.model.Color
import com.rewheeldev.glsdk.sdk.api.model.Colors
import com.rewheeldev.glsdk.sdk.api.model.Coords
import com.rewheeldev.glsdk.sdk.internal.gl.TypeLinkLines

interface IShape {
    val id: Long
    val coords: Coords
    val colors: Colors
    val borderType: TypeLinkLines
    val borderColor: Color
    val borderWidth: Float
}