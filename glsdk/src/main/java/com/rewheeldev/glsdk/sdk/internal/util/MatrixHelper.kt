package com.rewheeldev.glsdk.sdk.internal.util

object MatrixHelper {
    //todo: not sure that we need this
    fun perspectiveM(
        m: FloatArray, yFovInDegrees: Float, aspect: Float,
        n: Float, f: Float
    ) {
//        The first thing we’ll do is calculate the focal length, which will be based on
//        the field of vision across the y-axis.

        //We use Java’s Math class to calculate the tangent, and since it wants the angle
        //in radians, we convert the field of vision from degrees to radians. We then
        //calculate the focal length as described in the previous section.
//        val angleInRadians = (yFovInDegrees * Math.PI / 180.0).toFloat()
//        val a = (1.0 / Math.tan(angleInRadians / 2.0)).toFloat()

        //This writes out the matrix data to the floating-point array defined in the
        //argument m, which needs to have at least sixteen elements. OpenGL stores
        //matrix data in column-major order, which means that we write out data one
        //column at a time rather than one row at a time. The first four values refer to
        //the first column, the second four values to the second column, and so on.
//        m[0] = a / aspect
//        m[1] = 0f
//        m[2] = 0f
//        m[3] = 0f
//        m[4] = 0f
//        m[5] = a
//        m[6] = 0f
//        m[7] = 0f
//        m[8] = 0f
//        m[9] = 0f
//        m[10] = -((f + n) / (f - n))
//        m[11] = -1f
//        m[12] = 0f
//        m[13] = 0f
//        m[14] = -(2f * f * n / (f - n))
//        m[15] = 0f
    }
}