package com.example.animationsdk.example

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.util.ArrayList

class c_backgroundType(private var bigDrawable: Drawable?, private val _unitType: Byte) {
    //endregion
    //region переменные
    var _states: MutableList<c_bgState> = ArrayList()  //нужно попробовать реализовать background основаный на трехмерном масиве

    //   private int _sizeOneFrame = 128;
    //endregion
    //region функции
    fun f_addState(StateByEnum: Byte, countFrame: Byte, firstPositionFrame: Byte, Priority: Byte) {
        _states.add(c_bgState(StateByEnum, countFrame, firstPositionFrame))
        _states[_states.size - 1]._priority = Priority
    }

    fun f_allStatesAdded() {
        bigDrawable = null
    }

    fun g_Bg(State: Byte, indexFrame: Byte, angelState: Byte): Drawable? {
        val t_stateIndex = indexOf(State)
        val t_state = _states[t_stateIndex.toInt()]
        if (indexFrame < 0 || indexFrame >= t_state._stateFrames.size) return null
        val t_rotate = t_state._stateFrames[indexFrame.toInt()]
        return t_rotate.g_Bg(angelState)
    }

    fun g_State(unitState: Byte): c_bgState? {
        val index = indexOf(unitState)
        return if (index == c_enum.e_Exception) null else _states[index.toInt()]
    }

    //endregion
    fun indexOf(unitState: Byte): Byte {
        if (unitState == c_enum.e_Exception) return c_enum.e_Exception
        var index: Byte
        index = 0
        while (index < _states.size) {
            if (_states[index.toInt()]._state == unitState) return index
            index++
        }
        return c_enum.e_Exception
    }

    //endregion
    inner class c_bgState(
        var _state: Byte,
        private val _countFrames: Byte,
        firstPositionFrame: Byte
    ) {
        var _priority: Byte = 0
        var _stateFrames: MutableList<c_bgStateRotate>
        var _sizeOneFrame = bigDrawable!!.intrinsicWidth / 32
        fun g_countFrames(): Byte {
            return _countFrames
        }

        inner class c_bgStateRotate(left: Int) {
            private val _array = arrayOfNulls<Drawable>(8)
            fun g_BgFromAngel(angel: Float): Drawable? {
                return _array[g_angelStateFromAngel(angel).toInt()]
            }

            fun g_Bg(i: Byte): Drawable? {
                return _array[i.toInt()]
            }

            init {
                for (i in 0..7) {
//               _array[i] = c_background.getScaleBitmap(bigBitmap, left, i * _sizeOneFrame, _sizeOneFrame, _sizeOneFrame);
                    _array[i] = g_partImage(
                        bigDrawable,
                        left,
                        i * _sizeOneFrame,
                        _sizeOneFrame,
                        _sizeOneFrame
                    )
                }
            }
        }

        init {
            _stateFrames = ArrayList()
            for (i in 0 until _countFrames) {
                _stateFrames.add(c_bgStateRotate((firstPositionFrame + i) * _sizeOneFrame))
            }
        }
    }

    companion object {
        //region Static
        fun drawableToBitmap(drawable: Drawable?): Bitmap? {
            var bitmap: Bitmap? = null
            if (drawable is BitmapDrawable) {
                val bitmapDrawable = drawable
                if (bitmapDrawable.bitmap != null) {
                    return bitmapDrawable.bitmap
                }
            }
            bitmap = if (drawable!!.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                Bitmap.createBitmap(
                    100,
                    100,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        fun bitmapToDrawable(bitmap: Bitmap?): Drawable {
            return BitmapDrawable(bitmap)
        }

        fun g_partImage(
            _Drawable: Drawable?,
            left: Int,
            top: Int,
            width: Int,
            height: Int
        ): Drawable {
            return g_partImage(drawableToBitmap(_Drawable), left, top, width, height)
        }

        fun g_partImage(bitmap: Bitmap?, left: Int, top: Int, width: Int, height: Int): Drawable {
            return bitmapToDrawable(Bitmap.createBitmap(bitmap!!, left, top, width, height))
        }

        fun g_angelStateFromAngel(_angel: Float): Byte {
            var angel = _angel
            angel += 90f
            if (angel <= 0) angel = 1f
            if (angel > 360) angel = angel % 360
            val result = (angel / 45).toInt().toByte()
            return (result % 8).toByte()
        }
    }

}