package com.example.animationsdk.example

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.animationsdk.R
import java.util.*

class MainActivity : AppCompatActivity() {
    var MainConstraintLayout: ConstraintLayout? = null
    var t_views: MutableList<View>? = null
    var _background: c_backgroundType? = null
    var t_bgEngine: c_backgroundEngine? = null
    var t_bgEngine2: c_backgroundEngine? = null
    var _Drawable: Drawable? = null
    var _timer: Timer? = null
    var _msSysTime: Long = 0
    var t_Angel = 0f
    var t_byte = 0
    var t_isRotate = false
    var z_OnTouch: OnTouchListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(_TAG, "onCreate()Bundle called")
        requestWindowFeature(Window.FEATURE_NO_TITLE) //Убираем верхнию панель с именем приложения часть 2
        requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //бЛОКИРОВКА ИЗМЕНЕНИЕ  ОРИЕНТАЦИИИ ЭКРАНА
        //region MainConstraintLayout
        MainConstraintLayout = ConstraintLayout(this)
        MainConstraintLayout!!.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        super.onCreate(savedInstanceState)
        setContentView(MainConstraintLayout)
        MainConstraintLayout!!.setBackgroundColor(Color.DKGRAY)
        MainConstraintLayout!!.setPadding(0, 0, 0, 0)
        //endregion
        //region t_views
        t_views = ArrayList<View>()
        for (i in 0..1) {
            val view = View(this)
            view.x = 256f
            view.y = (384 * i + 50).toFloat()
            //         view.setGravity(Gravity.CENTER);
            view.layoutParams = ConstraintLayout.LayoutParams(384, 384)
            view.setPadding(0, 0, 0, 0)
            //         view.setText(i+"");
//         view.setTypeface(null, Typeface.BOLD);
//         view.setTextSize(15);
//         view.setTextColor(Color.RED);
            MainConstraintLayout!!.addView(view)
            t_views?.add(view)
        }
        //endregion
        //region _background
        val t_source = this.resources.getDrawable(R.drawable.orc_archer_0)
        //      Drawable t_source = this.getResources().getDrawable(R.mipmap.skeleton_0);
//      Drawable t_source = this.getResources().getDrawable(R.mipmap.antlion_0);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.fire_ant);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.ice_ant);
        // Drawable t_source = this.getResources().getDrawable(R.mipmap.orc_elite_0);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.orc_heavy_1);
        _background = c_backgroundType(t_source, c_unit.e_types.archer)
        _background!!.f_addState(c_unit.e_states.stend, 4.toByte(), 0.toByte(), 0.toByte())
        _background!!.f_addState(c_unit.e_states.run, 8.toByte(), 4.toByte(), 1.toByte())
        _background!!.f_addState(c_unit.e_states.magicAtack, 4.toByte(), 12.toByte(), 3.toByte())
        _background!!.f_addState(c_unit.e_states.dead, 8.toByte(), 16.toByte(), 11.toByte())
        _background!!.f_addState(c_unit.e_states.tackABlow, 4.toByte(), 24.toByte(), 10.toByte())
        _background!!.f_addState(c_unit.e_states.atack, 4.toByte(), 28.toByte(), 2.toByte())
        _background!!.f_allStatesAdded()
        //t_source = null;
        t_bgEngine = c_backgroundEngine(_background!!)
        t_bgEngine!!.f_addBgEvent(c_unit.e_states.stend, true)
        t_bgEngine2 = c_backgroundEngine(_background!!)
        t_bgEngine2!!.f_addBgEvent(c_unit.e_states.run, true)

        //endregion
        //region _timer
        _timer = Timer()
        _timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (t_isRotate) t_Angel += 3f
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        while (true) {
                            _Drawable = t_bgEngine!!.g_nextBG(t_Angel + 180)
                            if (_Drawable != null) break
                        }
                        t_views!!.get(0).background = _Drawable
                        while (true) {
                            _Drawable = t_bgEngine2!!.g_nextBG(t_Angel)
                            if (_Drawable != null) break
                        }
                        t_views!!.get(1).background = _Drawable
                    }
                }
            }
        }, 0, 150)
        //endregion
        //region z_OnTouch
        z_OnTouch = OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                //t_bgEngine.f_addBgEvent(c_unit.e_states.atack, false);
            } else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (motionEvent.y > 500) {
                    if (motionEvent.x > 300) {
                        t_bgEngine!!.f_addBgEvent(c_unit.e_states.run, false)
                        t_isRotate = !t_isRotate
                        Toast.makeText(
                            this@MainActivity,
                            "isRotate=$t_isRotate",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    t_bgEngine!!.f_addBgEvent(c_unit.e_states.atack, false)
                    t_bgEngine2!!.f_addBgEvent(c_unit.e_states.magicAtack, false)
                    Toast.makeText(this@MainActivity, "нажали2222", Toast.LENGTH_SHORT).show()
                }
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
            }
            true
        }
        MainConstraintLayout!!.setOnTouchListener(z_OnTouch)
        //endregion
    }

    public override fun onStart() {
        super.onStart()
        Log.d(_TAG, "onStart() called")
    }

    public override fun onResume() {
        super.onResume()
        Log.d(_TAG, "onResume() called")
    }

    public override fun onPause() {
        super.onPause()
        Log.d(_TAG, "onPause() called")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.d(_TAG, "onStart() called")
    }

    companion object {
        private const val _TAG = "MainActivity"
    }
}