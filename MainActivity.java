// MainActivity.java
package com.example.filemanager;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private TextView pathView;
    private Button permissionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathView = findViewById(R.id.pathView);
        permissionButton = findViewById(R.id.permissionButton);

        if (!FileUtils.hasManageAllFilesPermission(this)) {
            permissionButton.setVisibility(View.VISIBLE);
        } else {
            permissionButton.setVisibility(View.GONE);
            loadFragments();
        }

        permissionButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FileUtils.hasManageAllFilesPermission(this)) {
            permissionButton.setVisibility(View.GONE);
            loadFragments();
        }
    }

    private void loadFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.leftPane, FilePaneFragment.newInstance("/storage/emulated/0/"));
        ft.replace(R.id.rightPane, FilePaneFragment.newInstance("/storage/emulated/0/"));
        ft.commit();
    }
}
