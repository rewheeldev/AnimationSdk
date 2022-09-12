package com.rewheeldev.glsdk.sdk.api

interface IShapeController {
    fun add(shape: IShape): Boolean
    fun remove(shape: IShape): Boolean
}