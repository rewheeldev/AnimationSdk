package com.example.animationsdk.ui.gl.tryThree

import android.graphics.PointF
import android.opengl.GLES10.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix.orthoM
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AsteroidsRenderer(  // A class to help manage our game objects
    // current state.
    private val gm: GameManager
) : GLSurfaceView.Renderer {
    // Are we debugging at the moment
    var debugging = true

    // For monitoring and controlling the frames per second
    var frameCounter: Long = 0
    var averageFPS: Long = 0
    private var fps: Long = 0

    // For converting each game world coordinate
    // into a GL space coordinate (-1,-1 to 1,1)
    // for drawing on the screen
    private val viewportMatrix = FloatArray(16)

    // For capturing various PointF details without
    // creating new objects in the speed critical areas
    var handyPointF: PointF
    var handyPointF2: PointF
    override fun onSurfaceCreated(glUnused: GL10?, config: EGLConfig?) {

        // The color that will be used to clear the
        // screen each frame in onDrawFrame()
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        // Get our GLManager to compile and link the shaders into an object
        GLManager.buildProgram()
        createObjects()
    }

    override fun onSurfaceChanged(glUnused: GL10?, width: Int, height: Int) {

        // Make full screen
        glViewport(0, 0, width, height)

        /*
            Initialize our viewport matrix by passing in the starting
            range of the game world that will be mapped, by OpenGL to
            the screen. We will dynamically amend this as the player
            moves around.

            The arguments to setup the viewport matrix:
            our array,
            starting index in array,
            min x, max x,
            min y, max y,
            min z, max z)
        */
//        orthoM(viewportMatrix, 0, 0f, gm.metresToShowX, 0f, gm.metresToShowY, 0f, 1f)
        orthoM(viewportMatrix, 0, 0f, 390F, 0f, 220F, 0f, 1f)
    }

    private fun createObjects() {
        // Create our game objects

        // First the ship in the center of the map
        gm.ship = SpaceShip(gm.mapWidth.toFloat() / 2, gm.mapHeight.toFloat() / 2)
    }

    override fun onDrawFrame(glUnused: GL10?) {
        val startFrameTime = System.currentTimeMillis()
        if (gm.isPlaying) {
            update(fps)
        }
        draw()

        // Calculate the fps this frame
        // We can then use the result to
        // time animations and more.
        val timeThisFrame = System.currentTimeMillis() - startFrameTime
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame
        }

        // Output the average frames per second to the console
        if (debugging) {
            frameCounter++
            averageFPS = averageFPS + fps
            if (frameCounter > 100) {
                averageFPS = averageFPS / frameCounter
                frameCounter = 0
                Log.e("averageFPS:", "" + averageFPS)
            }
        }
    }

    private fun update(fps: Long) {}
    var tmp = 0
    private fun draw() {
//        if (tmp % 15 == 0) {
//
//        }

//        tmp += 1

        // Where is the ship?
        handyPointF = gm.ship.worldLocation

        // Modify the viewport matrix orthographic projection
        // based on the ship location
        orthoM(
            viewportMatrix, 0,
            handyPointF.x - gm.metresToShowX / 2 + tmp,
            handyPointF.x + gm.metresToShowX / 2 + tmp,
            handyPointF.y - gm.metresToShowY / 2 + tmp,
            handyPointF.y + gm.metresToShowY / 2 + tmp,
            0f, 1f
        )

        // Clear the screen
        glClear(GL_COLOR_BUFFER_BIT)

        // Start drawing!

        // Draw the ship
        gm.ship.draw(viewportMatrix)
    }

    init {
        handyPointF = PointF()
        handyPointF2 = PointF()
    }
}