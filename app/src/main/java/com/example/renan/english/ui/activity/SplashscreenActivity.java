package com.example.renan.english.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.renan.english.R;
import com.example.renan.english.util.NavigationUtil;
import com.parse.ParseAnalytics;

public class SplashscreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        new Handler().postDelayed(new Runnable() {
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
