package com.kmy.yallana3raf.SplashClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kmy.yallana3raf.NewsClasses.NewsActivity.NewsHeadLinesActivity;
import com.kmy.yallana3raf.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startSplashScreen ();

    }

    /* this function used to show Splash Screen before loading app */
    public void startSplashScreen ()
    {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, NewsHeadLinesActivity.class));
            finish();
        }, 3000);
    }

}