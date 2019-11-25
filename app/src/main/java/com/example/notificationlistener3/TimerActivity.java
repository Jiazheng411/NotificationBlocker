package com.example.notificationlistener3;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final FrameLayout root = findViewById(R.id.TimerActivity);
        setContentView(R.layout.activity_timer);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                root.setBackground(DynamicBackground.getBackground());
            }
        }, 10000);

        TimerView timerView = TimerView.getInstance(this);
        timerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        timerView.reset();
        root.addView(timerView);

        ImageButton settings = findViewById(R.id.settings);
        ImageButton toggle = findViewById(R.id.toggle);

        settings.bringToFront();
        toggle.bringToFront();

        // Add your code
    }
}
