package com.redapps.phonepolice;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;


public class AppClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        try {
            AudienceNetworkAds.initialize(this);
        } catch (Exception unused) {
        }
        try {
            MobileAds.initialize(this);
        } catch (Exception unused2) {
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(getString(R.string.app_name), "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
