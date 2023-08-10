package com.redapps.phonepolice.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IHomePresenter;
import com.redapps.phonepolice.serviceHandler.AntiTouchService;
import com.redapps.phonepolice.serviceHandler.BatteryService;
import com.redapps.phonepolice.ui.commonSettingsScreen.CommonSettingActivity;
import com.redapps.phonepolice.ui.fullChargerScreen.FullChargerActivity;
import com.redapps.phonepolice.ui.intruderAlert.IntruderAlertScreen;

import java.lang.ref.WeakReference;


public class HomeView implements IHomeView {
    final DbHelper dbHelper;
    final IHomePresenter presenter;
    private final WeakReference<Activity> activityWeakReference;

    public HomeView(IHomePresenter iHomePresenter, Activity activity) {
        this.presenter = iHomePresenter;
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        this.activityWeakReference = weakReference;
        this.dbHelper = new DbHelper(weakReference.get());
    }

    @Override
    public void onIntruderClick(Boolean bool) {
        Constants.setAnalytics(this.activityWeakReference.get(), "Intruder Alert");
        this.presenter.onIntruderAlertClick(bool);
    }


    @Override
    public void onAntiTouchClick(Boolean bool) {
        Constants.setAnalytics(this.activityWeakReference.get(), "Anti Touch Alert");
        if (!bool.booleanValue() || !isServiceRunning(AntiTouchService.class.getName())) {
            this.dbHelper.setBroadCast(Constants.Anti_Touch, false);
            this.activityWeakReference.get().stopService(new Intent(this.activityWeakReference.get(), AntiTouchService.class));
            return;
        }
        this.dbHelper.setBroadCast(Constants.Anti_Touch, true);
        Intent intent = new Intent(this.activityWeakReference.get(), AntiTouchService.class);
        intent.putExtra("inputExtra", "input");
        ContextCompat.startForegroundService(this.activityWeakReference.get(), intent);
    }

    @Override
    public void onPocketAlarmClick(Boolean bool) {
        this.presenter.onPocketAlarmClick(bool);
    }

    @Override
    public void onWrongPassClick(Boolean bool) {
        Constants.setAnalytics(this.activityWeakReference.get(), "Wrong Password Alert");
        this.presenter.onWrongPassClick(bool);
    }

    @Override
    public void onChargerClick(Boolean bool) {
        Constants.setAnalytics(this.activityWeakReference.get(), "Charger Removel Alert");
        this.presenter.onChargerAlarmClick(bool);
    }

    @Override
    public void onBatteryAlertClick(Boolean bool) {
        Constants.setAnalytics(this.activityWeakReference.get(), "Full Battery  Alert");
        if (!bool.booleanValue() || !isServiceRunning(BatteryService.class.getName())) {
            this.dbHelper.setBroadCast(Constants.Full_Battery, false);
            this.activityWeakReference.get().stopService(new Intent(this.activityWeakReference.get(), BatteryService.class));
            return;
        }
        this.dbHelper.setBroadCast(Constants.Full_Battery, true);
        Intent intent = new Intent(this.activityWeakReference.get(), BatteryService.class);
        intent.putExtra("inputExtra", "input");
        ContextCompat.startForegroundService(this.activityWeakReference.get(), intent);
    }

    @Override
    public void onIntruderBtnClick(String str) {
        Intent intent = new Intent(this.activityWeakReference.get(), IntruderAlertScreen.class);
        intent.putExtra("from", str);
        this.activityWeakReference.get().startActivity(intent);
    }


    @Override
    public void onAntiTouchBtnClick(String str) {
        Intent intent = new Intent(this.activityWeakReference.get(), CommonSettingActivity.class);
        intent.putExtra("from", str);
        this.activityWeakReference.get().startActivity(intent);
    }

    @Override
    public void onPocketAlarmBtnClick(String str) {
        Intent intent = new Intent(this.activityWeakReference.get(), CommonSettingActivity.class);
        intent.putExtra("from", str);
        this.activityWeakReference.get().startActivity(intent);
    }

    @Override
    public void onWrongPassBtnClick(String str) {
        Intent intent = new Intent(this.activityWeakReference.get(), IntruderAlertScreen.class);
        intent.putExtra("from", str);
        this.activityWeakReference.get().startActivity(intent);
    }

    @Override
    public void onChargerBtnClick(String str) {
        Intent intent = new Intent(this.activityWeakReference.get(), CommonSettingActivity.class);
        intent.putExtra("from", str);
        this.activityWeakReference.get().startActivity(intent);
    }

    @Override
    public void onBatteryAlertBtnClick() {
        this.activityWeakReference.get().startActivity(new Intent(this.activityWeakReference.get(), FullChargerActivity.class));
    }

    public boolean isServiceRunning(String str) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) this.activityWeakReference.get().getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (runningServiceInfo.service.getClassName().equals(str)) {
                return false;
            }
        }
        return true;
    }
}
