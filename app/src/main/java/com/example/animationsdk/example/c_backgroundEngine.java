package com.example.animationsdk.example;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class c_backgroundEngine {
    //region Конструкторы
    public c_backgroundEngine(c_backgroundType linkOnUnitBackground) {
        _linkOnUnitBackground = linkOnUnitBackground;
    }

    //endregion
    //region переменные
    List<c_bgEvent> _lifeEvents = new ArrayList<c_bgEvent>();
    public c_backgroundType _linkOnUnitBackground;
    private long _counter = 0;

    //endregion
    //region функции
    public void f_addBgEvent(byte UnitState, boolean isRepeat) {
        if (UnitState == c_enum.e_Exception) return;
        if (isHaveState(UnitState))
            return; /*если тип состояния имеется обновляем счетчик и завершаем функцию*/

        _lifeEvents.add(new c_bgEvent(UnitState));
        _lifeEvents.get(_lifeEvents.size() - 1)._isRepeat = isRepeat;
    }

    private boolean isHaveState(byte UnitState)/*идет по всем актуальным событиям  и если его
      тип состояния совпадает с типом состояния добовляемого события просто обновляем стартовый счетчик у имеющегося  */ {
        for (int i = 0; i < _lifeEvents.size(); i++) {
            if (_lifeEvents.get(i)._unitState == UnitState) {
                _lifeEvents.get(i)._counterStart = _counter;
                return true;
            }
        }
        return false;
    }

    public Drawable g_nextBG(float angel) {
        f_cleanEvents();
        c_bgEvent t_ActualEvent;
        byte t_angelState = c_backgroundType.g_angelStateFromAngel(angel);
        while (true) {
            if (g_ActualEvent().g_currentIndex() == c_enum.e_Exception) {
                _lifeEvents.remove(g_ActualEvent());
            } else {
                break;
            }
        }
        t_ActualEvent = g_ActualEvent();
        _counter++;
        return _linkOnUnitBackground.g_Bg(t_ActualEvent._unitState, t_ActualEvent.g_currentIndex(), t_angelState);
    }

    private void f_cleanEvents() {
        for (byte i = 0; i < _lifeEvents.size() - 1; i++) {
            if (_lifeEvents.get(i)._isDeath) {
                _lifeEvents.remove(i);
                f_cleanEvents();
                return;
            }
        }
    }

    private c_bgEvent g_ActualEvent() {
        return _lifeEvents.get(g_indexMaxPriorityEvent());
    }

    private byte g_indexMaxPriorityEvent() {
        byte indexMax = 0;
        for (byte i = 0; i < _lifeEvents.size(); i++) {
            byte iPriority = _linkOnUnitBackground.g_State(_lifeEvents.get(i)._unitState)._priority;
            byte maxPriority = _linkOnUnitBackground.g_State(_lifeEvents.get(indexMax)._unitState)._priority;
            if (iPriority > maxPriority) indexMax = i;
        }
        return indexMax;

    }

    private byte f_indexOfInEvents(byte UnitState) {
        for (byte i = 0; i < _lifeEvents.size(); i++) {
            if (_lifeEvents.get(i)._unitState == UnitState) return i;
        }
        return c_enum.e_Exception;
    }

    private c_bgEvent g_event(byte UnitState) {
        byte i = f_indexOfInEvents(UnitState);
        if (i == c_enum.e_Exception) return null;
        return _lifeEvents.get(i);
    }
//endregion

    class c_bgEvent {
        //region Конструкторы
        public c_bgEvent(byte unitState) {
            _unitState = unitState;
            _counterStart = _counter;
            _countFrames = _linkOnUnitBackground.g_State(_unitState).g_countFrames();
        }

        //endregion
        //region переменные
        byte _unitState;
        long _counterStart;
        int _countFrames;
        boolean _isRepeat = false;
        boolean _isDeath = false;

        //endregion
        //region функции
        private int g_framesPassed()/*возвращает .......*/ {
            long tickPassed = _counter - _counterStart;
            if (!_isRepeat) {
                if (tickPassed >= _countFrames) {
                    return c_enum.e_Exception;
                } else if (tickPassed >= _countFrames - 1) {
                    _isDeath = true;
                }
            }
            return (int) tickPassed;

        }

        public byte g_currentIndex() {
            int t_framesPassed = g_framesPassed();

            if (t_framesPassed == c_enum.e_Exception) return c_enum.e_Exception;
            //if (t_framesPassed == _countFrames) return c_enum.e_Exception;
            if (t_framesPassed < _countFrames) {
                return (byte) t_framesPassed;
            }

            return (byte) (t_framesPassed % _countFrames);
        }
        //endregion

    }
}

class c_backgroundType {//нужно попробовать реализовать background основаный на трехмерном масиве

    //region Конструкторы
    public c_backgroundType(Drawable bigDrawable, byte unitType) {
        this.bigDrawable = bigDrawable;
        _unitType = unitType;
        _states = new ArrayList<c_bgState>();
    }

    //endregion
    //region переменные
    List<c_bgState> _states;
    private Drawable bigDrawable;
    private byte _unitType;

    //   private int _sizeOneFrame = 128;
    //endregion
    //region функции
    public void f_addState(byte StateByEnum, byte countFrame, byte firstPositionFrame, byte Priority) {
        _states.add(new c_bgState(StateByEnum, countFrame, firstPositionFrame));
        _states.get(_states.size() - 1)._priority = Priority;

    }

    public void f_allStatesAdded() {
        this.bigDrawable = null;
    }

    public Drawable g_Bg(byte State, byte indexFrame, byte angelState) {
        byte t_stateIndex = indexOf(State);
        c_bgState t_state = _states.get(t_stateIndex);
        if (indexFrame < 0 || indexFrame >= t_state._stateFrames.size()) return null;
        c_bgState.c_bgStateRotate t_rotate = t_state._stateFrames.get(indexFrame);
        return t_rotate.g_Bg(angelState);
    }

    public c_bgState g_State(byte unitState) {
        byte index = indexOf(unitState);
        if (index == c_enum.e_Exception) return null;
        return _states.get(index);
    }

    //region Static
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    public static Drawable g_partImage(Drawable _Drawable, int left, int top, int width, int height) {
        return g_partImage(drawableToBitmap(_Drawable), left, top, width, height);
    }

    public static Drawable g_partImage(Bitmap bitmap, int left, int top, int width, int height) {
        return bitmapToDrawable(Bitmap.createBitmap(bitmap, left, top, width, height));
    }

    public static byte g_angelStateFromAngel(float angel) {
        angel += 90;
        if (angel <= 0) angel = 1;
        if (angel > 360) angel = angel % 360;
        byte result = ((byte) (angel / 45));
        return (byte) (result % 8);
    }

    //endregion
    public byte indexOf(byte unitState) {
        if (unitState == c_enum.e_Exception) return c_enum.e_Exception;
        byte index;
        for (index = 0; index < _states.size(); index++) {
            if (_states.get(index)._state == unitState) return index;
        }
        return c_enum.e_Exception;
    }

    //endregion

    class c_bgState {
        public byte _priority = 0;
        byte _state;
        private byte _countFrames;
        public List<c_bgStateRotate> _stateFrames;
        int _sizeOneFrame = bigDrawable.getIntrinsicWidth() / 32;

        public c_bgState(byte StateByEnum, byte countFrame, byte firstPositionFrame) {
            _state = StateByEnum;
            _countFrames = countFrame;
            _stateFrames = new ArrayList<c_bgStateRotate>();
            for (byte i = 0; i < countFrame; i++) {
                _stateFrames.add(new c_bgStateRotate((firstPositionFrame + i) * _sizeOneFrame));
            }
        }

        public byte g_countFrames() {
            return _countFrames;
        }

        public class c_bgStateRotate {
            private Drawable[] _array = new Drawable[8];

            public c_bgStateRotate(int left) {
                for (byte i = 0; i < 8; i++) {
//               _array[i] = c_background.getScaleBitmap(bigBitmap, left, i * _sizeOneFrame, _sizeOneFrame, _sizeOneFrame);
                    _array[i] = c_backgroundType.g_partImage(bigDrawable, left, i * _sizeOneFrame, _sizeOneFrame, _sizeOneFrame);
                }
            }

            public Drawable g_BgFromAngel(float angel) {
                return _array[g_angelStateFromAngel(angel)];
            }

            public Drawable g_Bg(byte i) {
                return _array[i];
            }
        }
    }
}