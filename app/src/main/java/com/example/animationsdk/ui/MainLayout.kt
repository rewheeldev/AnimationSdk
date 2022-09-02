package com.example.animationsdk.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class MainLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private lateinit var mainView: MainView

    fun initialize() {
        mainView = MainView(context)
        addView(mainView)
    }

    /**
     * The function `pause()` is called when the user presses the back button on their device
     */
    fun pause() {
        mainView.pause()
    }

    /**
     * Resumes the game
     */
    fun resume() {
        mainView.resume()
    }
}