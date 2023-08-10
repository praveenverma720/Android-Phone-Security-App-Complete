package com.redapps.phonepolice.serviceHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.helpers.CamManager;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.ui.homeScreen.HomeActivity;


public class CameraService extends Service {
    private static final String NOTIFY_CHANNEL_ID = "IntruderBackgroundService";
    DbHelper dbHelper;
    long mLastClickTime = 0;
    private CamManager mgr;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbHelper = new DbHelper(this);
        this.mgr = new CamManager(this);
        setRingtone();
        startForeground();
    }

    private void startForeground() {
        createNotificationChannel();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), (int) R.layout.notification_collapsed);
        boolean z = false;
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), PendingIntent.FLAG_MUTABLE);
        remoteViews.setTextViewText(R.id.text_view_collapsed_2, "Intruder Alert is Active");
        int i = getResources().getConfiguration().uiMode & 48;
        if (!(i == 0 || i == 16 || i != 32)) {
            z = true;
        }
        if (z) {
            remoteViews.setTextColor(R.id.text_view_collapsed_2, Color.parseColor("#FFFFFF"));
            remoteViews.setTextColor(R.id.text_view_collapsed_1, Color.parseColor("#FFFFFF"));
        }
        if (Build.VERSION.SDK_INT >= 30) {
            startForeground(5, new NotificationCompat.Builder(this, NOTIFY_CHANNEL_ID).setOngoing(true).setColor(-1).setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(remoteViews).setContentIntent(activity).build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_CAMERA);
        } else {
            startForeground(5, new NotificationCompat.Builder(this, NOTIFY_CHANNEL_ID).setOngoing(true).setColor(-1).setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(remoteViews).setContentIntent(activity).build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(NOTIFY_CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_HIGH));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        if (this.dbHelper.getAlarmSetting(Constants.Intruder_Alarm)) {
            Constants.startMediaPlayer();
        }
        if (Constants.Vibration_Status.booleanValue()) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(3500L);
        }
        try {
            if (SystemClock.elapsedRealtime() - this.mLastClickTime < 300) {
                return Service.START_STICKY;
            }
            this.mLastClickTime = SystemClock.elapsedRealtime();
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                return Service.START_STICKY;
            }
            this.mgr.takePhoto();
            return Service.START_STICKY;
        } catch (Exception e) {
            e.printStackTrace();
            return Service.START_STICKY;
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
