package com.example.cycletracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.cycletracker.R;
import com.example.cycletracker.ui.login.LoginActivity;

public class SplashActiivity extends AppCompatActivity {

    private static long SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActiivity.this, LoginActivity.class));
            }
        }, SPLASH_TIME_OUT);
    }
}
