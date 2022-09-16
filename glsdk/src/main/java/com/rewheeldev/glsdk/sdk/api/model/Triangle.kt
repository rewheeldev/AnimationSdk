package com.rewheeldev.glsdk.sdk.api.model

import com.rewheeldev.glsdk.sdk.api.IShape

class Triangle(
    override val coords: Coords,
    override val color: Color = Color.WHITE,
) : IShape {
    override val id: Long = counterObject++

    companion object {
        var counterObject: Long = 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Triangle

        if (coords != other.coords) return false
        if (color != other.color) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coords.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "Triangle(coords=$coords, color=$color, id=$id)"
    }
}