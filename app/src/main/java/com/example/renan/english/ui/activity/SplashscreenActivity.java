package com.example.renan.english.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

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
                go(DrawerLayoutMain.class);
            }
        }, 2000);
    }

    private void go(Class clazz){
        NavigationUtil.navigation(SplashscreenActivity.this, clazz);
        finish();
    }

}
