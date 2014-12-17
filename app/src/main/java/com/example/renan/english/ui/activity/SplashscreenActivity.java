package com.example.renan.english.ui.activity;

import com.example.renan.english.ui.activity.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.renan.english.R;
import com.example.renan.english.util.NavigationUtil;

public class SplashscreenActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                go(MainActivity.class);
            }
        }, 2000);
    }

    private void go(Class clazz){
        NavigationUtil.navigation(SplashscreenActivity.this, clazz);
        finish();
    }

}
