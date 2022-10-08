package com.rewheeldev.glsdk.sdk.api.shape.border

import com.rewheeldev.glsdk.sdk.api.shape.line.LinkLineTypes
import com.rewheeldev.glsdk.sdk.internal.F_NOT_INIT
import utils.Color

data class Border(
    val type: LinkLineTypes = LinkLineTypes.Loop,
    val color: Color = Color.WHITE,

    /**
     * if [width] is less than or equal to 0 no border will be drawn
     *
     * если [width] меньше или равен 0 border не будет нарисован
     */
    val width: Float = F_NOT_INIT
)
