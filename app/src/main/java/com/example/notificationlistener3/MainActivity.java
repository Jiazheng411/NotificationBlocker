// this is the main activity
package com.example.notificationlistener3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notificationlistener3.R;

public class MainActivity extends AppCompatActivity {

    Button settingButton;
    Button startFocusModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isEnabled()) {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "listening", Toast.LENGTH_SHORT);
            toast.show();
        }

        settingButton = findViewById( R.id.buttonSetting );
        startFocusModeButton = findViewById( R.id.buttonStartFocusMode );

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "setting button clicked");
                Intent intent = new Intent( MainActivity.this, SettingMainActivity.class);
                startActivity( intent );
            }
        });

        startFocusModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "startFocusMode button clicked");
                Intent intent = new Intent( MainActivity.this, FocusModeActivity.class );
                startActivity( intent );
            }
        });

    }


    // notification management permission checking
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
