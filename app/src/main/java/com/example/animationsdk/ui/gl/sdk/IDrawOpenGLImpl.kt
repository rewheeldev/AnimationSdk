package com.example.animationsdk.ui.gl.sdk

import android.graphics.Bitmap
import android.graphics.RectF
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.animationsdk.ui.gl.startAndroid.OpenGLRenderer
import java.nio.FloatBuffer

class IDrawOpenGLImpl : IDrawOpenGL {
    //region IDrawOpenGL
    override fun initTexture(bitmap: Bitmap): Int {
        TODO("Not yet implemented")
    }

    override fun drawRectTexture(textureId: Int, x: Float, y: Float, width: Float, height: Float) {
        TODO("Not yet implemented")
    }

    override fun drawRectTexture(textureId: Int, rect: RectF) {
        TODO("Not yet implemented")
    }
    //endregion


    /**
     * метод связывает текстуру с координатами и рисует ее
     */
//    private fun drawTexture(texture: Int, x: Float, y: Float, width: Float, height: Float) {
//        val array = createDefaultRectangleVertices(x, y, width, height)
//        bindData(texture, array.asSortedFloatBuffer())
//        //0 это первый индекс вершины в масиве точек
//        //4 количество точек на основании которых создается прямоугольник
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
//    }
//
//    private fun bindData(texture: Int, vertexData: FloatBuffer) {
//        // region координаты вершин
//        vertexData.position(0)
//        GLES20.glVertexAttribPointer(
//            aPositionLocation, OpenGLRenderer.POSITION_COUNT, GLES20.GL_FLOAT,
//            false, OpenGLRenderer.STRIDE, vertexData
//        )
//        GLES20.glEnableVertexAttribArray(aPositionLocation)
//        //endregion
//
//        //region координаты текстур
//        vertexData.position(OpenGLRenderer.POSITION_COUNT)
//        GLES20.glVertexAttribPointer(
//            aTextureLocation, OpenGLRenderer.TEXTURE_COUNT, GLES20.GL_FLOAT,
//            false, OpenGLRenderer.STRIDE, vertexData
//        )
//        GLES20.glEnableVertexAttribArray(aTextureLocation)
//        //endregion
//
//        // помещаем текстуру в target 2D юнита 0
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)
//
//        // юнит текстуры
//        GLES20.glUniform1i(uTextureUnitLocation, 0)
//    }

}