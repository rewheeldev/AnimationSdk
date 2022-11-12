package com.rewheeldev.glsdk.sdk.internal.controllers

import com.rewheeldev.glsdk.sdk.api.IShape
import com.rewheeldev.glsdk.sdk.api.IShapeController

class ShapeController : IShapeController {

    val shapeList = ArrayList<IShape>()
    val addListenerList = ArrayList<IAddShapeListener>()
    val removeListenerList = ArrayList<IRemoveShapeListener>()

    override fun add(shape: IShape): Boolean {
        //todo: this is a really weird solution need to find time to make it in a different way
        addListenerList.forEach {
            it.addListener(shape)
        }
        return shapeList.add(shape)
    }

    override fun remove(shape: IShape): Boolean {
        //todo: this is a really weird solution need to find time to make it in a different way
        removeListenerList.forEach {
            it.addListener(shape)
        }
        return shapeList.remove(shape)
    }

    fun addListener(listener: IAddShapeListener) {
        if (addListenerList.contains(listener)) return
        addListenerList.add(listener)
    }

    fun removeListener(listener: IRemoveShapeListener) {
        if (removeListenerList.contains(listener)) return
        removeListenerList.add(listener)
    }
}


fun interface IAddShapeListener {
    fun addListener(shape: IShape)
}

fun interface IRemoveShapeListener {
    fun addListener(shape: IShape)
}
