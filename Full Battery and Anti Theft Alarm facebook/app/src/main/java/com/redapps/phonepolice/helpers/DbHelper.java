package com.redapps.phonepolice.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;


public class DbHelper {
    public static final String AD_PREFS = "adspref";
    final Context context;

    public DbHelper(Context context) {
        this.context = context;
    }

    public static void setRemoteValue(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AD_PREFS, 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static boolean getRemoteValue(Context context, String str) {
        return context.getSharedPreferences(AD_PREFS, 0).getBoolean(str, true);
    }

    public static void setRemoteCounter(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AD_PREFS, 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static long getRemoteCounter(Context context, String str) {
        return context.getSharedPreferences(AD_PREFS, 0).getLong(str, 5L);
    }

    public boolean userExists() {
        return new File("/data/data/" + this.context.getPackageName() + "/shared_prefs/" + Constants.SHARED_PREFS + ".xml").exists();
    }

    public void createUser(String str) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putString(Constants.User_Value, str);
        edit.apply();
    }

    public boolean chkPass(String str) {
        return str.equals(this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).getString(Constants.User_Value, "null"));
    }

    public boolean chkBroadCast(String str) {
        return this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).getBoolean(str, false);
    }

    public void setBroadCast(String str, boolean z) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public int getAttemptNo() {
        return Integer.parseInt(this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).getString(Constants.No_of_attempts, "1"));
    }

    public void setAttemptNo(String str) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putString(Constants.No_of_attempts, str);
        edit.apply();
    }

    public void setAlarmSetting(String str, Boolean bool) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putBoolean(str, bool.booleanValue());
        edit.apply();
    }

    public boolean getAlarmSetting(String str) {
        return this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).getBoolean(str, false);
    }

    public int getTone() {
        return Integer.parseInt(this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).getString(Constants.Tone_Selected, "1"));
    }

    public void setTone(String str) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putString(Constants.Tone_Selected, str);
        edit.apply();
    }

    public void setAppPurchase(boolean z, AppCompatActivity appCompatActivity) {
        SharedPreferences.Editor edit = appCompatActivity.getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
        edit.putBoolean(Constants.Apppurchase, z);
        edit.apply();
    }

    public boolean getAppPurchase(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFS, 0).getBoolean(Constants.Apppurchase, false);
    }
}
