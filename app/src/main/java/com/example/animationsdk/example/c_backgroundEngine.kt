package com.example.animationsdk.example

import android.graphics.drawable.Drawable

class c_backgroundEngine(var _linkOnUnitBackground: c_backgroundType) {
    //region переменные
    var _lifeEvents: MutableList<c_bgEvent> = ArrayList()
    private var _counter: Long = 0

    //endregion
    //region функции
    fun f_addBgEvent(UnitState: Byte, isRepeat: Boolean) {
        if (UnitState == c_enum.e_Exception) return
        if (isHaveState(UnitState)) return  /*если тип состояния имеется обновляем счетчик и завершаем функцию*/
        _lifeEvents.add(c_bgEvent(UnitState))
        _lifeEvents[_lifeEvents.size - 1]._isRepeat = isRepeat
    }

    private fun isHaveState(UnitState: Byte): Boolean /*идет по всем актуальным событиям  и если его
      тип состояния совпадает с типом состояния добовляемого события просто обновляем стартовый счетчик у имеющегося  */ {
        for (i in _lifeEvents.indices) {
            if (_lifeEvents[i]._unitState == UnitState) {
                _lifeEvents[i]._counterStart = _counter
                return true
            }
        }
        return false
    }

    fun g_nextBG(angel: Float): Drawable? {
        f_cleanEvents()
        val t_ActualEvent: c_bgEvent
        val t_angelState = c_backgroundType.g_angelStateFromAngel(angel)
        while (true) {
            if (g_ActualEvent().g_currentIndex() == c_enum.e_Exception) {
                _lifeEvents.remove(g_ActualEvent())
            } else {
                break
            }
        }
        t_ActualEvent = g_ActualEvent()
        _counter++
        return _linkOnUnitBackground.g_Bg(
            t_ActualEvent._unitState,
            t_ActualEvent.g_currentIndex(),
            t_angelState
        )
    }

    private fun f_cleanEvents() {
        for (i in 0 until _lifeEvents.size - 1) {
            if (_lifeEvents[i]._isDeath) {
                _lifeEvents.removeAt(i)
                f_cleanEvents()
                return
            }
        }
    }

    private fun g_ActualEvent(): c_bgEvent {
        return _lifeEvents[g_indexMaxPriorityEvent().toInt()]
    }

    private fun g_indexMaxPriorityEvent(): Byte {
        var indexMax: Byte = 0
        for (i in _lifeEvents.indices) {
            val iPriority = _linkOnUnitBackground.g_State(_lifeEvents[i]._unitState)!!._priority
            val maxPriority =
                _linkOnUnitBackground.g_State(_lifeEvents[indexMax.toInt()]._unitState)!!._priority
            if (iPriority > maxPriority) indexMax = i.toByte()
        }
        return indexMax
    }

    private fun f_indexOfInEvents(UnitState: Byte): Byte {
        for (i in _lifeEvents.indices) {
            if (_lifeEvents[i]._unitState == UnitState) return i.toByte()
        }
        return c_enum.e_Exception
    }

    private fun g_event(UnitState: Byte): c_bgEvent? {
        val i = f_indexOfInEvents(UnitState)
        return if (i == c_enum.e_Exception) null else _lifeEvents[i.toInt()]
    }

    //endregion
    inner class c_bgEvent(  //endregion
        //region переменные
        var _unitState: Byte
    ) {
        var _counterStart: Long
        var _countFrames: Int
        var _isRepeat = false
        var _isDeath = false

        //endregion
        //region функции
        private fun g_framesPassed(): Int /*возвращает .......*/ {
            val tickPassed = _counter - _counterStart
            if (!_isRepeat) {
                if (tickPassed >= _countFrames) {
                    return c_enum.e_Exception.toInt()
                } else if (tickPassed >= _countFrames - 1) {
                    _isDeath = true
                }
            }
            return tickPassed.toInt()
        }

        fun g_currentIndex(): Byte {
            val t_framesPassed = g_framesPassed()
            if (t_framesPassed == c_enum.e_Exception.toInt()) return c_enum.e_Exception
            //if (t_framesPassed == _countFrames) return c_enum.e_Exception;
            return if (t_framesPassed < _countFrames) {
                t_framesPassed.toByte()
            } else (t_framesPassed % _countFrames).toByte()
        } //endregion

        //region Конструкторы
        init {
            _counterStart = _counter
            _countFrames = _linkOnUnitBackground.g_State(_unitState)!!.g_countFrames().toInt()
        }
    }
}

