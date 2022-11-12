package com.rewheeldev.glsdk.sdk.api.util

import android.app.ActivityManager
import android.content.Context
import android.os.Build

class OpenGLConfigurationInfoManager {

    var activityManager: ActivityManager? = null

    fun initActivityManager(context: Context) {
        activityManager =
            context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    }

    /**
     * The GLES version used by an application.
     * The upper order 16 bits represent the major version and the lower order 16 bits the minor version.
     */
    val openGLVersion: Int
        get() {
            val activityManager =
                activityManager
                    ?: throw IllegalStateException("Please provide Activity Manager first.")
            return activityManager.deviceConfigurationInfo.reqGlEsVersion
        }

    fun checkSupportEs2(): Boolean {
        val activityManager =
            activityManager ?: throw IllegalStateException("Please provide Activity Manager first.")
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"))
    }

}