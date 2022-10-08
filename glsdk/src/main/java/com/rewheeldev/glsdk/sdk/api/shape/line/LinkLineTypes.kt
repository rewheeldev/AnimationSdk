package com.rewheeldev.glsdk.sdk.api.shape.line

import android.opengl.GLES20

enum class LinkLineTypes(val code: Int) {
    /**
     * connects all the points together: the first with the second, the second with the third, and so on
     * Note: the first and last will not be connected
     *
     * соединяет все точки между собой: первую со второй, вторую с третей и так далее
     * Примечание: первая и последняя не будут соединены
     */
    Strip(GLES20.GL_LINE_STRIP),

    /**
     * connects pairs of points to each other: the first with the second, the third with the fourth
     * Note: if the number of points is not even, the last point is not taken into account
     *
     * соединяет пары точек между собой: первую со второй, третью с четветой
     * Примечание: если количество точек не четное последняя точка не учитывается
     */
    Lines(GLES20.GL_LINES),

    /**
     * connects all the points together: the first with the second, the second with the third ... and the last with the first
     *
     * соединяет все точки между собой: первую со второй, вторую с третей ... и последнюю с первой
     */
    Loop(GLES20.GL_LINE_LOOP),

    ;
}