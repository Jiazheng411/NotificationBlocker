package com.example.notificationlistener3;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_timer);
        final FrameLayout root = findViewById(R.id.TimerActivity);

        DynamicBackground.initialize();

        root.setBackground(DynamicBackground.getBackground());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                root.setBackground(DynamicBackground.getBackground());
            }
        }, 10000);

        TimerView timerView = TimerView.getInstance(this);
        timerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        timerView.reset();
        root.addView(timerView);

        Button settings = findViewById(R.id.settings);
        Button toggle = findViewById(R.id.toggle);

        settings.bringToFront();
        toggle.bringToFront();

        // Add your code
    }
}
