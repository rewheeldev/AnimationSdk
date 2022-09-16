package com.rewheeldev.glsdk.sdk.api.model

class Color(
    var alpha: Int = 255,
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0
) {
    constructor() : this(255, 0, 0, 0)
    constructor (alpha: Float = 1f, red: Float = 0f, green: Float = 0f, blue: Float = 0f) : this(
        alpha.toUByteColor(),
        red.toUByteColor(),
        green.toUByteColor(),
        blue.toUByteColor()
    )

    companion object {
        val BLACK by lazy { Color() }
        val WHITE by lazy { Color(255, 255, 255, 255) }
        val RED by lazy { Color(255, 255, 0, 0) }
        val GREEN by lazy { Color(255, 0, 255, 0) }
        val BLUE by lazy { Color(255, 0, 0, 255) }
        val TRANSPARENT by lazy { Color(alpha = 0f) }

        fun createFrom(argbHex: String) = Color(//TODO требует проверки
            alpha = argbHex.substring(0, 2).toInt(16),
            red = argbHex.substring(2, 4).toInt(16),
            green = argbHex.substring(4, 6).toInt(16),
            blue = argbHex.substring(6, 8).toInt(16)
        )
    }

    fun setARGB(argbHex: String) {
        //#00FF00
        alpha = argbHex.substring(0, 2).toInt(16)
        red = argbHex.substring(2, 4).toInt(16)
        green = argbHex.substring(4, 6).toInt(16)
        blue = argbHex.substring(6, 8).toInt(16)
    }

    fun asFloatArray(type: ColorSchemeType = ColorSchemeType.RGBA): FloatArray {
        return when (type) {
            ColorSchemeType.RGBA -> floatArrayOf(
                red.toPercentFloat(),
                green.toPercentFloat(),
                blue.toPercentFloat(),
                alpha.toPercentFloat()
            )
            ColorSchemeType.ARGB -> floatArrayOf(
                alpha.toPercentFloat(),
                red.toPercentFloat(),
                green.toPercentFloat(),
                blue.toPercentFloat()
            )
            ColorSchemeType.RGB -> floatArrayOf(
                red.toPercentFloat(),
                green.toPercentFloat(),
                blue.toPercentFloat()
            )
        }
    }
}

enum class ColorSchemeType {
    ARGB,
    RGBA,
    RGB
}

//region TODO требует проверки
private fun Int.toPercentFloat() = (this.toFloat() / 255.toFloat())

private fun Float.toUByteColor() = (255.toFloat() * this).toInt()

//endregion