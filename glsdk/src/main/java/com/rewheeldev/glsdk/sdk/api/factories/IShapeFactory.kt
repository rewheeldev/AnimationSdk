package com.rewheeldev.glsdk.sdk.api.factories

import com.rewheeldev.glsdk.sdk.api.IShape
import com.rewheeldev.glsdk.sdk.api.model.GridParams
import com.rewheeldev.glsdk.sdk.api.model.TriangleParams

/**
 * @author Ivantsov Mykola
 * @since 09 Oct 2022
 * */
interface IShapeFactory {

    /**
     * Create a triangle with the given parameters.
     *
     * @param params TriangleParams
     */
    fun createTriangle(params: TriangleParams): IShape

    /**
     * Create a grid with the given parameters.
     *
     * @param params GridParams
     */
    fun createGrid(params: GridParams): IShape
}