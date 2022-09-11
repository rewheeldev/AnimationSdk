package com.example.animationsdk.ui.gl.tryThree

class GameManager(var screenWidth: Int, var screenHeight: Int) {
    var mapWidth = 600
    var mapHeight = 600
    var isPlaying = false
        private set

    // Our first game object
    lateinit var ship: SpaceShip

    // How many metres of our virtual world
    // we will show on screen at any time.
    var metresToShowX = 390
    var metresToShowY = 220
    fun switchPlayingStatus() {
        isPlaying = !isPlaying
    }

}