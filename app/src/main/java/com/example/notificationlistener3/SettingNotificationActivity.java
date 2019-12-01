package com.example.notificationlistener3;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class SettingNotificationActivity extends AppCompatActivity implements onCheckboxClicked, SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PackageManager manager;

    private SearchView searchView;

    private SharedPreferences mPreferences;

    private List<ApplicationInfo> applications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notification_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        manager = getPackageManager();
        applications = manager.getInstalledApplications(0);

        recyclerView = findViewById(R.id.appRecyclerView);
        searchView = findViewById(R.id.notificationSearch);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        applications = manager.getInstalledApplications(0);

        Collections.sort(applications, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo app1, ApplicationInfo app2) {
                String label1 = app1.loadLabel(manager).toString();
                String label2 = app2.loadLabel(manager).toString();

                return label1.compareToIgnoreCase(label2);
            }
        });

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(SettingNotificationActivity.this, applications, this);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void OnCheckboxClicked(int pos, boolean isChecked){
        String appname = mAdapter.getItem(pos).packageName;
        HashSet<String> appsNotBlocking = new HashSet<>(Arrays.asList(mPreferences.getString(Util_String.APPS_RECEIVING_NOTIFICATION, "").split(";")));

        if (isChecked) {
            appsNotBlocking.add(appname);
        } else {
            appsNotBlocking.remove(appname);
        }

        mPreferences.edit().putString(Util_String.APPS_RECEIVING_NOTIFICATION, TextUtils.join(";", appsNotBlocking)).apply();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mAdapter.setApps(applications);

        } else {
            String query = newText.toLowerCase();
            ArrayList<ApplicationInfo> filtered = new ArrayList<>();
            for (ApplicationInfo app : applications) {
                String label = app.loadLabel(manager).toString().toLowerCase();
                if (label.contains(query)) {
                    filtered.add(app);
                }
            }
            mAdapter.setApps(filtered);
        }
        return true;
    }
}
