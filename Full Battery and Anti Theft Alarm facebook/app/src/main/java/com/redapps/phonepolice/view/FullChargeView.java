package com.redapps.phonepolice.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IFullChargePresenter;
import com.redapps.phonepolice.serviceHandler.BatteryService;
import com.redapps.phonepolice.ui.alarmScreen.AlarmActivity;

import java.lang.ref.WeakReference;


public class FullChargeView implements IFullChargeView {
    final IFullChargePresenter presenter;
    private final WeakReference<Activity> activityWeakReference;
    DbHelper dbHelper;

    public FullChargeView(IFullChargePresenter iFullChargePresenter, Activity activity) {
        this.presenter = iFullChargePresenter;
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        this.activityWeakReference = weakReference;
        this.dbHelper = new DbHelper(weakReference.get());
    }

    @Override
    public void onOptionSwitchClick(Boolean bool) {
        if (!bool.booleanValue() || isServiceRunning(BatteryService.class.getName())) {
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
    public void onAlarmBtnClick() {
        Intent intent = new Intent(this.activityWeakReference.get(), AlarmActivity.class);
        intent.putExtra("from", "fullbatery");
        this.activityWeakReference.get().startActivity(intent);
    }

    public boolean isServiceRunning(String str) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) this.activityWeakReference.get().getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (runningServiceInfo.service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
