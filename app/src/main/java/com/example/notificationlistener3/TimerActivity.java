package com.example.notificationlistener3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;

public class TimerActivity extends AppCompatActivity {
    String restTime;
    String studyTime;
    SharedPreferences msharedPreference;
    TimerView timerView;
    Long i = 50l;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_timer);
        final FrameLayout root = findViewById(R.id.TimerActivity);

        DynamicBackground.initialize();

        /*
        root.setBackground(DynamicBackground.getBackground());
        msharedPreference = getSharedPreferences("setting", MODE_PRIVATE);
        studyTime = msharedPreference.getString(Util_String.FOCUS_TIME, "45");
        restTime = msharedPreference.getString(Util_String.RESTING_TIME, "15");
        int studyPeriod = Integer.valueOf(studyTime) * 60000;
        int restPeriod = Integer.valueOf(restTime) * 60000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                root.setBackground(DynamicBackground.getBackground());
                handler.postDelayed(this, 10000);
            }
        }, 10000);
        */
        //Use this part if you want to speed up time

        root.setBackground(DynamicBackground.getBackground(0));
        msharedPreference = getSharedPreferences("setting", MODE_PRIVATE);
        studyTime = msharedPreference.getString(Util_String.FOCUS_TIME, "45");
        restTime = msharedPreference.getString(Util_String.RESTING_TIME, "15");
        int studyPeriod = Integer.valueOf(studyTime) * 60000;
        int restPeriod = Integer.valueOf(restTime) * 60000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                root.setBackground(DynamicBackground.getBackground(i));
                i += 120000;
                handler.postDelayed(this, 50);
            }
        }, 1000);

        timerView = TimerView.getInstance(this);
        timerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        timerView.reset(studyPeriod, restPeriod);
        Log.i("TimerActivity", ""+studyTime);
        Log.i("TimerActivity", ""+restTime);
        if(timerView.getParent() != null) {
            ((ViewGroup)timerView.getParent()).removeView(timerView); // <- fix
        }
        root.addView(timerView);

        Button settings = findViewById(R.id.settings);
        Button toggle = findViewById(R.id.toggle);

        settings.bringToFront();
        toggle.bringToFront();

        //
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TimeActibity","toggle button is clicked");
                timerView.toggleMode();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TimeActivity","onResume");
        Log.i("TimeActivity","TimeActivity"+ msharedPreference.getString(Util_String.CHANGEING_TIMING_SETTING, "false"));
        if (msharedPreference.getString(Util_String.CHANGEING_TIMING_SETTING, "false").equals("true") ){
            studyTime = msharedPreference.getString(Util_String.FOCUS_TIME, "45");
            restTime = msharedPreference.getString(Util_String.RESTING_TIME, "15");
            int studyPeriod = Integer.valueOf(studyTime) * 60000;
            int restPeriod = Integer.valueOf(restTime) * 60000;
            timerView.reset(studyPeriod, restPeriod);
            SharedPreferences.Editor editor = msharedPreference.edit();
            editor.putString(Util_String.CHANGEING_TIMING_SETTING, "false").apply();
        }
    }
}