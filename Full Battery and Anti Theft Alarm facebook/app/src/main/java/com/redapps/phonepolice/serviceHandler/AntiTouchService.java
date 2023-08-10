package com.redapps.phonepolice.serviceHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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


public class AntiTouchService extends Service implements SensorEventListener {
    private static final String NOTIFY_CHANNEL_ID = "AntiTouchBackgroundService";
    DbHelper dbHelper;
    private float mAcceleration;
    private float mAccelerationCurrent;
    private float mAccelerationLast;
    private SensorManager sensorMan;

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

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
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sensorMan = sensorManager;
        Sensor defaultSensor = sensorManager.getDefaultSensor(1);
        this.mAcceleration = 0.0f;
        this.mAccelerationCurrent = 9.80665f;
        this.mAccelerationLast = 9.80665f;
        this.sensorMan.registerListener(this, defaultSensor, 0);
        return Service.START_STICKY;
    }

    private void startForeground() {
        createNotificationChannel();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), (int) R.layout.notification_collapsed);
        boolean z = false;
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setTextViewText(R.id.text_view_collapsed_2, getString(R.string.antiTouchAlertisActive));
        int i = getResources().getConfiguration().uiMode & 48;
        if (!(i == 0 || i == 16 || i != 32)) {
            z = true;
        }
        if (z) {
            remoteViews.setTextColor(R.id.text_view_collapsed_2, Color.parseColor("#FFFFFF"));
            remoteViews.setTextColor(R.id.text_view_collapsed_1, Color.parseColor("#FFFFFF"));
        }
        startForeground(3, new NotificationCompat.Builder(this, NOTIFY_CHANNEL_ID).setOngoing(true).setColor(-1).setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(remoteViews).setContentIntent(activity).build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.pauseMediaPlayer();
        if (Constants.Light_Status.booleanValue()) {
            Constants.setFlash(this, false);
        }
        SensorManager sensorManager = this.sensorMan;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(NOTIFY_CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_HIGH));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            float[] fArr = (float[]) sensorEvent.values.clone();
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            this.mAccelerationLast = this.mAccelerationCurrent;
            float sqrt = (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
            this.mAccelerationCurrent = sqrt;
            float f4 = (this.mAcceleration * 0.5f) + (sqrt - this.mAccelerationLast);
            this.mAcceleration = f4;
            if (f4 > 1.0f) {
                if (Constants.Light_Status.booleanValue()) {
                    Constants.setFlash(this, true);
                }
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Constants.Vibration_Status.booleanValue()) {
                    vibrator.vibrate(5000L);
                }
                Constants.startMediaPlayer();
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
