package com.redapps.phonepolice.ui.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.redapps.phonepolice.databinding.ActivitySplashScreenBinding;
import com.redapps.phonepolice.ui.loginScreen.LoginActivity;


public class SplashActivity extends AppCompatActivity {


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySplashScreenBinding inflate = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}
