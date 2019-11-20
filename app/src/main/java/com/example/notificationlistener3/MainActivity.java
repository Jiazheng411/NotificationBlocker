// this is the main activity
package com.example.notificationlistener3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ImageView settingButton;
    Button startFocusModeButton;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isEnabled()) {
            showNormalDialog();
            }

        settingButton = findViewById( R.id.buttonSetting );
        startFocusModeButton = findViewById( R.id.buttonStartFocusMode );

        mSharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        boolean is_blocking = false;
        editor.putBoolean(Util_String.IS_BLOCKING, is_blocking).apply();
        Log.i("MainActivity", "edit shared preference is_blocking, not blocking notification");

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

    @Override
    protected void onStart() {
        super.onStart();
        mSharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        String appsNotBlocking = mSharedPreferences.getString(Util_String.APPS_RECEIVING_NOTIFICATION, "");
        HashSet<String> appsNotBlocked = new HashSet<>(Arrays.asList(appsNotBlocking.split(";")));
        appsNotBlocked.add("com.android.calendar");
        appsNotBlocked.add("come.google.android.calendar");
        String AppsNotBlocking = TextUtils.join(";", appsNotBlocked);
        editor.putString(Util_String.APPS_RECEIVING_NOTIFICATION, AppsNotBlocking).apply();

        String lampRBrightness = mSharedPreferences.getString(Util_String.LAMP_R_BRIGHTNESS, null);
        if (lampRBrightness == null) {
            lampRBrightness = "50";
            editor.putString(Util_String.LAMP_R_BRIGHTNESS,lampRBrightness).apply();
        }

        String lampGBrightness = mSharedPreferences.getString(Util_String.LAMP_G_BRIGHTNESS, null);
        if (lampGBrightness == null) {
            lampGBrightness = "50";
            editor.putString(Util_String.LAMP_G_BRIGHTNESS,lampRBrightness).apply();
        }

        String lampBBrightness = mSharedPreferences.getString(Util_String.LAMP_B_BRIGHTNESS, null);
        if (lampBBrightness == null) {
            lampBBrightness = "50";
            editor.putString(Util_String.LAMP_B_BRIGHTNESS,lampBBrightness).apply();
        }


        String restTime = mSharedPreferences.getString(Util_String.RESTING_TIME,null);
        if (restTime == null){
            restTime = "15";
            editor.putString(Util_String.RESTING_TIME,restTime).apply();
        }

        String focusTime = mSharedPreferences.getString(Util_String.FOCUS_TIME, null);
        if (focusTime == null){
            focusTime = "60";
            editor.putString(Util_String.FOCUS_TIME,focusTime).apply();
        }

        Log.i("MainActivity", "edit shared preference apps_receiving_notification, receive calender notification by default");

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


    private void showNormalDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("Notification Manage Permission");
        normalDialog.setMessage("Please let us manage your notification");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    }
                });
        normalDialog.show();
    }





}
