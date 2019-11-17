package com.example.notificationlistener3;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FocusModeActivity extends AppCompatActivity {
    ImageView settingButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("FocusModeActivity", "oncreate");
        setContentView(R.layout.focus_mode_activity);
        settingButton = findViewById(R.id.buttonSetting);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusModeActivity.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });

    }



}
