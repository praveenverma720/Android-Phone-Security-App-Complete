package com.redapps.phonepolice;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.serviceHandler.CameraService;
import com.redapps.phonepolice.serviceHandler.PasswordService;


public class Admin extends DeviceAdminReceiver {
    DbHelper dbHelper;

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        this.dbHelper = new DbHelper(context);
        if (Constants.count < this.dbHelper.getAttemptNo()) {
            Constants.count++;
        } else if (this.dbHelper.chkBroadCast(Constants.Intruder_Selfie)) {
            if (ContextCompat.checkSelfPermission(context, "android.permission.CAMERA") != 0) {
                this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
                if (Constants.isMyServiceRunning(context, CameraService.class)) {
                    context.stopService(new Intent(context, CameraService.class));
                }
            } else if (!Constants.isMyServiceRunning(context, CameraService.class)) {
                ContextCompat.startForegroundService(context, new Intent(context, CameraService.class));
            }
        } else if (!Constants.isMyServiceRunning(context, PasswordService.class)) {
            Intent intent2 = new Intent(context, PasswordService.class);
            intent2.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(context, intent2);
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        Constants.pauseMediaPlayer();
        Constants.count = 1;
        if (Constants.isMyServiceRunning(context, PasswordService.class) && Constants.Light_Status.booleanValue()) {
            Constants.setFlash(context, false);
        }
        if (Constants.isMyServiceRunning(context, CameraService.class)) {
            context.stopService(new Intent(context, CameraService.class));
        }
        if (Constants.isMyServiceRunning(context, PasswordService.class)) {
            context.stopService(new Intent(context, PasswordService.class));
        }
    }
}
