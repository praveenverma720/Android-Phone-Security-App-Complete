package com.redapps.phonepolice.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.ICommonSettingPresenter;
import com.redapps.phonepolice.serviceHandler.AntiTouchService;
import com.redapps.phonepolice.ui.alarmScreen.AlarmActivity;

import java.lang.ref.WeakReference;


public class CommonSettingView implements ICommonSettingView {
    final DbHelper dbHelper;
    final String from;
    final ICommonSettingPresenter presenter;
    private final WeakReference<Activity> activityWeakReference;

    public CommonSettingView(ICommonSettingPresenter iCommonSettingPresenter, Activity activity, String str) {
        this.presenter = iCommonSettingPresenter;
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        this.activityWeakReference = weakReference;
        this.from = str;
        this.dbHelper = new DbHelper(weakReference.get());
    }


    @Override
    public void onOptionSwitchClick(Boolean bool) {
        char c;
        String str = this.from;
        switch (str.hashCode()) {
            case -2100958947:
                if (str.equals("antitouch")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 94742811:
                if (str.equals("claps")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 739062846:
                if (str.equals("charger")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1316690498:
                if (str.equals("whislte")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2076963451:
                if (str.equals("pocketalarm")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.presenter.onChargerAlarmClick(bool);
        } else if (c != 1) {
            if (c == 2) {
                this.presenter.onPocketAlarmClick(bool);
            }
        } else if (!bool.booleanValue() || isServiceRunning(AntiTouchService.class.getName())) {
            this.dbHelper.setBroadCast(Constants.Anti_Touch, false);
            this.activityWeakReference.get().stopService(new Intent(this.activityWeakReference.get(), AntiTouchService.class));
        } else {
            this.dbHelper.setBroadCast(Constants.Anti_Touch, true);
            Intent intent = new Intent(this.activityWeakReference.get(), AntiTouchService.class);
            intent.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(this.activityWeakReference.get(), intent);
        }
    }

    @Override
    public void onAlarmSettingBtnClick() {
        Intent intent = new Intent(this.activityWeakReference.get(), AlarmActivity.class);
        intent.putExtra("from", this.from);
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
