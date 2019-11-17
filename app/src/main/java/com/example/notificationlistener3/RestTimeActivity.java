package com.example.notificationlistener3;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RestTimeActivity extends AppCompatActivity {
    ImageView buttonSetting;
    Button buttonStartFocusMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_time_activity);
        Log.i("RestTimeActivity", "oncreate");
        buttonSetting = findViewById(R.id.buttonSetting);
        buttonStartFocusMode = findViewById(R.id.buttonStartFocusModeRestPage);

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestTimeActivity.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });

        buttonStartFocusMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RestTimeActivity.this, FocusModeActivity.class );
                startActivity( intent );
            }
        });
    }
}
