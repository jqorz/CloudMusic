package com.asus.cloudmusic.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.asus.cloudmusic.R;
import com.asus.cloudmusic.Util.MusicData_Util;

public class Welcome extends AppCompatActivity {
    private MusicData_Util musicData_util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        start();
    }

    private void initPermisssion() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int store_permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (store_permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0x0010);
                }
                int net_permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET);
                if (net_permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUtil() {
        ContentResolver contentResolver = this.getContentResolver();
        musicData_util = new MusicData_Util(contentResolver, this);
        musicData_util.initSongs();
    }


    private void start() {
        new Thread() {
            @Override
            public void run() {
                initPermisssion();
                initUtil();
                musicData_util.destroyUtil();
                Intent i = new Intent(Welcome.this, Main.class);
                startActivity(i);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
