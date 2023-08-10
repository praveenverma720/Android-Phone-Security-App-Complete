package com.redapps.phonepolice.serviceHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.ui.homeScreen.HomeActivity;


public class ChargerService extends Service {
    private static final String NOTIFY_CHANNEL_ID = "AppNameBackgroundService";
    DbHelper dbHelper;
    BroadcastReceiver receiver;
    private String mCameraId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbHelper = new DbHelper(this);
        setRingtone();
        startForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        createChargerBroadcast();
        return Service.START_STICKY;
    }

    private void startForeground() {
        createNotificationChannel();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), (int) R.layout.notification_collapsed);
        boolean z = false;
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), PendingIntent.FLAG_MUTABLE);
        remoteViews.setTextViewText(R.id.text_view_collapsed_2, getString(R.string.chargerRemoverAlertisActive));
        int i = getResources().getConfiguration().uiMode & 48;
        if (!(i == 0 || i == 16 || i != 32)) {
            z = true;
        }
        if (z) {
            remoteViews.setTextColor(R.id.text_view_collapsed_2, Color.parseColor("#FFFFFF"));
            remoteViews.setTextColor(R.id.text_view_collapsed_1, Color.parseColor("#FFFFFF"));
        }
        startForeground(1, new NotificationCompat.Builder(this, NOTIFY_CHANNEL_ID).setOngoing(true).setColor(-1).setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(remoteViews).setContentIntent(activity).build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.pauseMediaPlayer();
        if (Constants.Light_Status.booleanValue()) {
            setFlash(false);
        }
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(NOTIFY_CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_HIGH));
        }
    }

    public void createChargerBroadcast() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra("plugged", -1);
                Vibrator vibrator = (Vibrator) ChargerService.this.getSystemService(VIBRATOR_SERVICE);
                if (intExtra == 1) {
                    Constants.pauseMediaPlayer();
                    if (Constants.Light_Status.booleanValue()) {
                        ChargerService.this.setFlash(false);
                    }
                    vibrator.cancel();
                } else if (intExtra == 2) {
                    Constants.pauseMediaPlayer();
                    vibrator.cancel();
                    if (Constants.Light_Status.booleanValue()) {
                        ChargerService.this.setFlash(false);
                    }
                } else if (intExtra == 0) {
                    Constants.startMediaPlayer();
                    if (Constants.Light_Status.booleanValue()) {
                        ChargerService.this.setFlash(true);
                    }
                    if (Constants.Vibration_Status.booleanValue()) {
                        vibrator.vibrate(5000L);
                    }
                }
            }
        };
        this.receiver = broadcastReceiver;
        registerReceiver(broadcastReceiver, intentFilter);
    }


    public void setFlash(boolean z) {
        boolean hasSystemFeature = getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            this.mCameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (hasSystemFeature) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    cameraManager.setTorchMode(this.mCameraId, z);
                }
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setRingtone() {
        int tone = this.dbHelper.getTone();
        if (tone == 1) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone1);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        } else if (tone == 2) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone2);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        } else if (tone == 3) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone3);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        } else if (tone == 4) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone4);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        } else if (tone == 5) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone5);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        } else if (tone == 6) {
            Constants.mp = MediaPlayer.create(this, (int) R.raw.tone6);
            Constants.mp.setLooping(true);
            Constants.mp.setVolume(100.0f, 100.0f);
        }
    }
}
