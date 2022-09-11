package com.example.animationsdk.ui.gl.tryThree

class SpaceShip(worldLocationX: Float, worldLocationY: Float) :
    GameObject() {
    init {

        // Make sure we know this object is a ship
        // So the draw() method knows what type
        // of primitive to construct from the vertices
        type = Type.SHIP
        setWorldLocation(worldLocationX, worldLocationY)
        val width = 15f
        val length = 20f
        setSize(width, length)

        // It will be useful to have a copy of the
        // length and width/2 so we don't have to keep dividing by 2
        val halfW = width / 2
        val halfL = length / 2

        // Define the space ship shape
        // as a triangle from point to point
        // in anti clockwise order
        val shipVertices = floatArrayOf(
            -halfW, -halfL, 0f,
            halfW, -halfL, 0f, 0f, 0 + halfL, 0f
        )
        setVertices(shipVertices)
    }
}
