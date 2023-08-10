package com.redapps.phonepolice.ui.commonSettingsScreen;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityWhistleTouchClapScreenBinding;
import com.redapps.phonepolice.helpers.Adshandler;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.ICommonSettingPresenter;
import com.redapps.phonepolice.serviceHandler.AntiTouchService;
import com.redapps.phonepolice.serviceHandler.ChargerService;
import com.redapps.phonepolice.serviceHandler.PocketService;
import com.redapps.phonepolice.view.CommonSettingView;
import com.redapps.phonepolice.view.ICommonSettingView;

import java.util.Objects;


public class CommonSettingActivity extends AppCompatActivity implements ICommonSettingPresenter, SensorEventListener {
    Sensor accelerometer;
    Adshandler adsManager;
    ActivityWhistleTouchClapScreenBinding binding;
    String from;
    ICommonSettingView presenter;
    private SensorManager sensorMan;

    public static boolean isCharging(Context context) {
        int intExtra = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        return intExtra == 2 || intExtra == 5;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityWhistleTouchClapScreenBinding inflate = ActivityWhistleTouchClapScreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.from = getIntent().getStringExtra("from");
        this.adsManager = new Adshandler();
        initViews();
        this.presenter = new CommonSettingView(this, this, this.from);
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                CommonSettingActivity(view);
            }
        });
        this.binding.alramSetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                CommonSettingActivity1(view);
            }
        });
        this.binding.optionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                CommonSettingActivity2(view);
            }
        });
        TextView textView = this.binding.seekbarlabel;
        textView.setText(getString(R.string.sensitivity) + " : " + (this.binding.seekBar.getProgress() / 10));
        this.binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextView textView2 = binding.seekbarlabel;
                textView2.setText(getString(R.string.sensitivity) + " : " + (seekBar.getProgress() / 10));
            }
        });
    }

    public void CommonSettingActivity(View view) {
        onBackPressed();
    }

    public void CommonSettingActivity1(View view) {
        this.presenter.onAlarmSettingBtnClick();
    }

    public void CommonSettingActivity2(View view) {
        this.presenter.onOptionSwitchClick(Boolean.valueOf(this.binding.optionSwitch.isChecked()));
    }

    public void initViews() {
        try {
            String str = this.from;
            Objects.requireNonNull(str);
            String str2 = str;
            char c = 65535;
            switch (str2.hashCode()) {
                case -2100958947:
                    if (str2.equals("antitouch")) {
                        c = 2;
                        break;
                    }
                    break;
                case 94742811:
                    if (str2.equals("claps")) {
                        c = 1;
                        break;
                    }
                    break;
                case 739062846:
                    if (str2.equals("charger")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1316690498:
                    if (str2.equals("whislte")) {
                        c = 0;
                        break;
                    }
                    break;
                case 2076963451:
                    if (str2.equals("pocketalarm")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                this.binding.screenlabel.setText(getString(R.string.whistle_screenlabel));
                this.binding.screenDes.setText(getString(R.string.whistle_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.whistle_switchoptionlabel));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(Integer.MIN_VALUE);
                    window.clearFlags(67108864);
                    window.setStatusBarColor(Color.parseColor("#71D2FE"));
                }
                int[][] iArr = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr2 = {Color.parseColor("#B2B2B2"), Color.parseColor("#71D2FE")};
                int[] iArr3 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B4E9FF")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
                this.binding.seekBar.getProgressDrawable().setTint(Color.parseColor("#B4E9FF"));
                this.binding.seekBar.getThumb().setTint(Color.parseColor("#71D2FE"));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#71D2FE"));
            } else if (c == 1) {
                this.binding.screenlabel.setText(getString(R.string.claps_screenlabel));
                this.binding.screenDes.setText(getString(R.string.claps_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.claps_switchoptionlabel));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window2 = getWindow();
                    window2.addFlags(Integer.MIN_VALUE);
                    window2.clearFlags(67108864);
                    window2.setStatusBarColor(Color.parseColor("#59D075"));
                }
                int[][] iArr4 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr5 = {Color.parseColor("#B2B2B2"), Color.parseColor("#59D075")};
                int[] iArr6 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B1F5D4")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr4, iArr5));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr4, iArr6));
                this.binding.seekBar.getProgressDrawable().setTint(Color.parseColor("#B1F5D4"));
                this.binding.seekBar.getThumb().setTint(Color.parseColor("#59D075"));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#59D075"));
            } else if (c == 2) {
                this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
                this.binding.screenlabel.setText(getString(R.string.anti_touch_screenlabel));
                this.binding.screenDes.setText(getString(R.string.anti_touch_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.anti_touch_switchoptionlabel));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window3 = getWindow();
                    window3.addFlags(Integer.MIN_VALUE);
                    window3.clearFlags(67108864);
                    window3.setStatusBarColor(Color.parseColor("#FFC401"));
                }
                int[][] iArr7 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr8 = {Color.parseColor("#B2B2B2"), Color.parseColor("#FFC401")};
                int[] iArr9 = {Color.parseColor("#D6D5D5"), Color.parseColor("#FBF5AB")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr7, iArr8));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr7, iArr9));
                this.binding.seekBar.getProgressDrawable().setTint(Color.parseColor("#FBF5AB"));
                this.binding.seekBar.getThumb().setTint(Color.parseColor("#FFC401"));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#FFC401"));
            } else if (c == 3) {
                this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
                this.binding.screenlabel.setText(getString(R.string.charger_screenlabel));
                this.binding.screenDes.setText(getString(R.string.charger_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.charger_switchoptionlabel));
                this.binding.seekBar.setVisibility(View.GONE);
                this.binding.seekbarlabel.setVisibility(View.GONE);
                this.binding.seekbarinfo.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window4 = getWindow();
                    window4.addFlags(Integer.MIN_VALUE);
                    window4.clearFlags(67108864);
                    window4.setStatusBarColor(Color.parseColor("#E363D7"));
                }
                int[][] iArr10 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr11 = {Color.parseColor("#B2B2B2"), Color.parseColor("#E363D7")};
                int[] iArr12 = {Color.parseColor("#D6D5D5"), Color.parseColor("#F2B8F8")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr10, iArr11));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr10, iArr12));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#E363D7"));
            } else if (c == 4) {
                this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
                this.binding.screenlabel.setText(getString(R.string.pocketAlarm));
                this.binding.screenDes.setText(getString(R.string.isomeontriestogetridofthphonefrompocketpurseanlarmwillexplodealertingyou));
                this.binding.switchoptionlabel.setText(R.string.activatePocketAlarm);
                this.binding.seekBar.setVisibility(View.GONE);
                this.binding.seekbarlabel.setVisibility(View.GONE);
                this.binding.seekbarinfo.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window5 = getWindow();
                    window5.addFlags(Integer.MIN_VALUE);
                    window5.clearFlags(67108864);
                    window5.setStatusBarColor(Color.parseColor("#C8E25E"));
                }
                int[][] iArr13 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr14 = {Color.parseColor("#B2B2B2"), Color.parseColor("#C8E25E")};
                int[] iArr15 = {Color.parseColor("#D6D5D5"), Color.parseColor("#E1F591")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr13, iArr14));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr13, iArr15));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#C7E362"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        String str = this.from;
        Objects.requireNonNull(str);
        String str2 = str;
        str2.hashCode();
        char c = 65535;
        switch (str2.hashCode()) {
            case -2100958947:
                if (str2.equals("antitouch")) {
                    c = 0;
                    break;
                }
                break;
            case 94742811:
                if (str2.equals("claps")) {
                    c = 1;
                    break;
                }
                break;
            case 739062846:
                if (str2.equals("charger")) {
                    c = 2;
                    break;
                }
                break;
            case 1316690498:
                if (str2.equals("whislte")) {
                    c = 3;
                    break;
                }
                break;
            case 2076963451:
                if (str2.equals("pocketalarm")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (isServiceRunning(AntiTouchService.class.getName())) {
                    this.binding.optionSwitch.setChecked(true);
                    return;
                }
                return;
            case 1:
                this.binding.screenlabel.setText(getString(R.string.claps_screenlabel));
                this.binding.screenDes.setText(getString(R.string.claps_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.claps_switchoptionlabel));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(Integer.MIN_VALUE);
                    window.clearFlags(67108864);
                    window.setStatusBarColor(Color.parseColor("#59D075"));
                }
                int[][] iArr = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr2 = {Color.parseColor("#B2B2B2"), Color.parseColor("#59D075")};
                int[] iArr3 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B1F5D4")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
                this.binding.seekBar.getProgressDrawable().setTint(Color.parseColor("#B1F5D4"));
                this.binding.seekBar.getThumb().setTint(Color.parseColor("#59D075"));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#59D075"));
                return;
            case 2:
                if (isServiceRunning(ChargerService.class.getName())) {
                    this.binding.optionSwitch.setChecked(true);
                    return;
                }
                return;
            case 3:
                this.binding.screenlabel.setText(getString(R.string.whistle_screenlabel));
                this.binding.screenDes.setText(getString(R.string.whistle_screen_des));
                this.binding.switchoptionlabel.setText(getString(R.string.whistle_switchoptionlabel));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window2 = getWindow();
                    window2.addFlags(Integer.MIN_VALUE);
                    window2.clearFlags(67108864);
                    window2.setStatusBarColor(Color.parseColor("#71D2FE"));
                }
                int[][] iArr4 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr5 = {Color.parseColor("#B2B2B2"), Color.parseColor("#71D2FE")};
                int[] iArr6 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B4E9FF")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getThumbDrawable()), new ColorStateList(iArr4, iArr5));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.optionSwitch.getTrackDrawable()), new ColorStateList(iArr4, iArr6));
                this.binding.seekBar.getProgressDrawable().setTint(Color.parseColor("#B4E9FF"));
                this.binding.seekBar.getThumb().setTint(Color.parseColor("#71D2FE"));
                this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#71D2FE"));
                return;
            case 4:
                if (isServiceRunning(PocketService.class.getName())) {
                    this.binding.optionSwitch.setChecked(true);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean isServiceRunning(String str) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (runningServiceInfo.service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }


    @Override

    public void onDestroy() {
        super.onDestroy();

        SensorManager sensorManager = this.sensorMan;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    public void CommonSettingActivity3() {
        finish();
    }

    @Override
    public void onBackPressed() {
        this.adsManager.showAd(this, new Adshandler.OnClose() {
            @Override

            public void onclick() {
                CommonSettingActivity3();
            }
        });
    }

    @Override
    public void onPocketAlarmClick(Boolean bool) {
        final DbHelper dbHelper = new DbHelper(this);
        if (bool.booleanValue()) {
            final Dialog dialog = new Dialog(this);
            View inflate = getLayoutInflater().inflate(R.layout.confrom_service_dialog, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.cnfrm_del_btn);
            Button button2 = (Button) inflate.findViewById(R.id.cancl_btn);
            button2.setTextColor(Color.parseColor("#C7E362"));
            button.setBackgroundColor(Color.parseColor("#C7E362"));
            ((TextView) inflate.findViewById(R.id.textView11)).setText(R.string.notificationAlert);
            ((TextView) inflate.findViewById(R.id.textView12)).setText(R.string.putyourPhoninPocketorunPocketAlarmAlert);
            dialog.setContentView(inflate);
            Window window = dialog.getWindow();
            window.setAttributes(window.getAttributes());
            dialog.getWindow().setLayout(-2, -2);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    CommonSettingActivity4(dbHelper, dialog, view);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    CommonSettingActivity5(dialog, dbHelper, view);
                }
            });
            return;
        }
        this.binding.optionSwitch.setChecked(false);
        dbHelper.setBroadCast(Constants.Pocket_Alarm, false);
        stopService(new Intent(this, PocketService.class));
    }

    public void CommonSettingActivity4(DbHelper dbHelper, Dialog dialog, View view) {
        this.binding.optionSwitch.setChecked(false);
        dbHelper.setBroadCast(Constants.Pocket_Alarm, false);
        stopService(new Intent(this, PocketService.class));
        dialog.dismiss();
    }

    public void CommonSettingActivity5(Dialog dialog, DbHelper dbHelper, View view) {
        dialog.dismiss();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sensorMan = sensorManager;
        Sensor defaultSensor = sensorManager.getDefaultSensor(8);
        this.accelerometer = defaultSensor;
        this.sensorMan.registerListener(this, defaultSensor, 3);
        Constants.setAnalytics(this, "Pocket Alarm Alert");
        if (!Constants.isMyServiceRunning(this, PocketService.class)) {
            dbHelper.setBroadCast(Constants.Pocket_Alarm, true);
            this.binding.optionSwitch.setChecked(true);
            Intent intent = new Intent(this, PocketService.class);
            intent.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(this, intent);
        }
    }

    @Override
    public void onChargerAlarmClick(Boolean bool) {
        final DbHelper dbHelper = new DbHelper(this);
        if (!bool.booleanValue()) {
            dbHelper.setBroadCast(Constants.Charger_Removal, false);
            this.binding.optionSwitch.setChecked(false);
            stopService(new Intent(this, ChargerService.class));
        } else if (!isCharging(this)) {
            final Dialog dialog = new Dialog(this);
            View inflate = getLayoutInflater().inflate(R.layout.confrom_service_dialog, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.cnfrm_del_btn);
            Button button2 = (Button) inflate.findViewById(R.id.cancl_btn);
            button2.setTextColor(Color.parseColor("#E363D7"));
            button.setBackgroundColor(Color.parseColor("#E363D7"));
            ((TextView) inflate.findViewById(R.id.textView11)).setText(R.string.notificationAlert);
            ((TextView) inflate.findViewById(R.id.textView12)).setText(R.string.pleaseConnectChargerToActiveThisFeature);
            dialog.setContentView(inflate);
            Window window = dialog.getWindow();
            window.setAttributes(window.getAttributes());
            dialog.getWindow().setLayout(-2, -2);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    CommonSettingActivity6(dbHelper, dialog, view);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    CommonSettingActivity7(dbHelper, dialog, view);
                }
            });
        } else if (!Constants.isMyServiceRunning(getApplicationContext(), ChargerService.class)) {
            dbHelper.setBroadCast(Constants.Charger_Removal, true);
            this.binding.optionSwitch.setChecked(true);
            Intent intent = new Intent(this, ChargerService.class);
            intent.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(this, intent);
        }
    }

    public void CommonSettingActivity6(DbHelper dbHelper, Dialog dialog, View view) {
        this.binding.optionSwitch.setChecked(false);
        dbHelper.setBroadCast(Constants.Charger_Removal, false);
        if (Constants.isMyServiceRunning(this, ChargerService.class)) {
            stopService(new Intent(this, ChargerService.class));
        }
        dialog.dismiss();
    }

    public void CommonSettingActivity7(DbHelper dbHelper, Dialog dialog, View view) {
        if (isCharging(this)) {
            if (!Constants.isMyServiceRunning(this, ChargerService.class)) {
                dbHelper.setBroadCast(Constants.Charger_Removal, true);
                this.binding.optionSwitch.setChecked(true);
                Intent intent = new Intent(this, ChargerService.class);
                intent.putExtra("inputExtra", "input");
                ContextCompat.startForegroundService(this, intent);
            }
            dialog.dismiss();
            return;
        }
        Toast.makeText(this, getString(R.string.pleaseConnectChargerToActiveThisFeature) + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < this.accelerometer.getMaximumRange()) {
            Constants.Pocket_Status = true;
        }
    }
}
