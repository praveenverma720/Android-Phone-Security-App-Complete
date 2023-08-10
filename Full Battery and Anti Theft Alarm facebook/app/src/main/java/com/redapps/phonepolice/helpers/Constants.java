package com.redapps.phonepolice.helpers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import com.redapps.phonepolice.BuildConfig;
import com.redapps.phonepolice.R;


public class Constants {
    public static final String Anti_Touch = "Anti_Touch";
    public static final String Apppurchase = "apppurchase";
    public static final String Full_Battery = "Full_Battery";
    public static final String Intruder_Alarm = "Intruder_Alarm";
    public static final String Intruder_Selfie = "Intruder_Selfie";
    public static final String No_of_attempts = "No_of_attempts";
    public static final String PRODUCT_ID = "remove_ads";
    public static final String SHARED_PREFS = "pref";
    public static final String Tone_Selected = "Tone_Selected";
    public static final String User_Value = "userpass";
    public static String Charger_Removal = "Charger_Removel";
    public static String Claps_Detect = "Claps_Detect";
    public static String Flash_Light = "Flash_Light";
    public static String Pocket_Alarm = "Pocket_Alram";
    public static String Vibration_Enabled = "Vibration_Enabled";
    public static String Wrong_Pass = "Wrong_Pass";
    public static int count = 1;
    public static MediaPlayer mp;
    public static Boolean Vibration_Status = false;
    public static Boolean Pocket_Status = false;
    public static Boolean Light_Status = false;
    public static Boolean isAppPurchased = false;

    public static void startMediaPlayer() {
        try {
            MediaPlayer mediaPlayer = mp;
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mp.start();
            }
        } catch (Exception unused) {
        }
    }

    public static void pauseMediaPlayer() {
        try {
            MediaPlayer mediaPlayer = mp;
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mp.pause();
            }
        } catch (Exception unused) {
        }
    }

    public static void setFlash(Context context, boolean z) {
        boolean hasSystemFeature = context.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String str = cameraManager.getCameraIdList()[0];
            if (hasSystemFeature && Build.VERSION.SDK_INT >= 23) {
                cameraManager.setTorchMode(str, z);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setAnalytics(Context context, String str) {

    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void shareApp(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", context.getString(R.string.heycheckoutthisappat) + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    public static void rateApp(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void showPrivacyPolicy(Context context) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://redapps.in/apps_legal/phone_police_privacy.html")));
    }
}
