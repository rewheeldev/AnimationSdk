package com.example.animationsdk.example;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.animationsdk.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout MainConstraintLayout;
    List<View> t_views;
    c_backgroundType _background;
    c_backgroundEngine t_bgEngine;
    c_backgroundEngine t_bgEngine2;
    Drawable _Drawable;
    Timer _timer;
    long _msSysTime = 0;
    float t_Angel = 0;
    int t_byte = 0;
    boolean t_isRotate = false;
    View.OnTouchListener z_OnTouch;
    private static final String _TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(_TAG, "onCreate()Bundle called");

        requestWindowFeature(Window.FEATURE_NO_TITLE); //Убираем верхнию панель с именем приложения часть 2
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //бЛОКИРОВКА ИЗМЕНЕНИЕ  ОРИЕНТАЦИИИ ЭКРАНА
        //region MainConstraintLayout
        MainConstraintLayout = new ConstraintLayout(this);
        MainConstraintLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        super.onCreate(savedInstanceState);
        setContentView(MainConstraintLayout);
        MainConstraintLayout.setBackgroundColor(Color.DKGRAY);
        MainConstraintLayout.setPadding(0, 0, 0, 0);
        //endregion
        //region t_views
        t_views = new ArrayList<View>();
        for (int i = 0; i < 2; i++) {
            View view = new View(this);
            view.setX(256);
            view.setY((384 * (i)) + 50);
//         view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new ConstraintLayout.LayoutParams(384, 384));


            view.setPadding(0, 0, 0, 0);
//         view.setText(i+"");
//         view.setTypeface(null, Typeface.BOLD);
//         view.setTextSize(15);
//         view.setTextColor(Color.RED);

            MainConstraintLayout.addView(view);
            t_views.add(view);
        }
        //endregion
        //region _background
        Drawable t_source = this.getResources().getDrawable(R.drawable.orc_archer_0);
//      Drawable t_source = this.getResources().getDrawable(R.mipmap.skeleton_0);
//      Drawable t_source = this.getResources().getDrawable(R.mipmap.antlion_0);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.fire_ant);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.ice_ant);
        // Drawable t_source = this.getResources().getDrawable(R.mipmap.orc_elite_0);
        //Drawable t_source = this.getResources().getDrawable(R.mipmap.orc_heavy_1);
        _background = new c_backgroundType(t_source, c_unit.e_types.archer);
        _background.f_addState(c_unit.e_states.stend, (byte) 4, (byte) 0, (byte) 0);
        _background.f_addState(c_unit.e_states.run, (byte) 8, (byte) 4, (byte) 1);
        _background.f_addState(c_unit.e_states.magicAtack, (byte) 4, (byte) 12, (byte) 3);
        _background.f_addState(c_unit.e_states.dead, (byte) 8, (byte) 16, (byte) 11);
        _background.f_addState(c_unit.e_states.tackABlow, (byte) 4, (byte) 24, (byte) 10);
        _background.f_addState(c_unit.e_states.atack, (byte) 4, (byte) 28, (byte) 2);
        _background.f_allStatesAdded();
        //t_source = null;

        t_bgEngine = new c_backgroundEngine(_background);
        t_bgEngine.f_addBgEvent(c_unit.e_states.stend, true);

        t_bgEngine2 = new c_backgroundEngine(_background);
        t_bgEngine2.f_addBgEvent(c_unit.e_states.run, true);

        //endregion
        //region _timer
        _timer = new Timer();
        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (t_isRotate) t_Angel += 3;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            while (true) {
                                _Drawable = t_bgEngine.g_nextBG(t_Angel + 180);
                                if (_Drawable != null) break;
                            }
                            t_views.get(0).setBackground(_Drawable);
                            while (true) {
                                _Drawable = t_bgEngine2.g_nextBG(t_Angel);
                                if (_Drawable != null) break;
                            }
                            t_views.get(1).setBackground(_Drawable);
                        }
                    }
                });
            }
        }, 0, 150);
        //endregion
        //region z_OnTouch
        z_OnTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    //t_bgEngine.f_addBgEvent(c_unit.e_states.atack, false);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (motionEvent.getY() > 500) {
                        if (motionEvent.getX() > 300) {
                            t_bgEngine.f_addBgEvent(c_unit.e_states.run, false);
                            t_isRotate = !t_isRotate;
                            Toast.makeText(MainActivity.this, "isRotate=" + t_isRotate, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        t_bgEngine.f_addBgEvent(c_unit.e_states.atack, false);
                        t_bgEngine2.f_addBgEvent(c_unit.e_states.magicAtack, false);

                        Toast.makeText(MainActivity.this, "нажали2222", Toast.LENGTH_SHORT).show();
                    }

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                }
                return true;
            }
        };

        MainConstraintLayout.setOnTouchListener(z_OnTouch);
        //endregion
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(_TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(_TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(_TAG, "onPause() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(_TAG, "onStart() called");
    }
}

