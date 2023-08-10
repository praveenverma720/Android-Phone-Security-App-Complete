package com.redapps.phonepolice.ui.homeScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.redapps.phonepolice.Admin;
import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityHomeBinding;
import com.redapps.phonepolice.helpers.Adshandler;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IHomePresenter;
import com.redapps.phonepolice.serviceHandler.CameraService;
import com.redapps.phonepolice.serviceHandler.ChargerService;
import com.redapps.phonepolice.serviceHandler.PasswordService;
import com.redapps.phonepolice.serviceHandler.PocketService;
import com.redapps.phonepolice.view.HomeView;
import com.redapps.phonepolice.view.IHomeView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements IHomePresenter, SensorEventListener {
    private static final int ADMIN_INTENT = 1;
    private static final int INTRUDER_INTENT = 3;
    private final int CAMERA_PERMISSION_CODE = 2;
    Sensor accelerometer;
    Adshandler adsManager;
    AlertDialog alertDialogExit;
    DbHelper dbHelper;
    ActivityHomeBinding homeBinding;
    ComponentName mComponentName;
    DevicePolicyManager mDevicePolicyManager;
    private int REQUEST_PERMISSION_SETTING = 0;
    private IHomeView presenter;
    private SensorManager sensorMan;

    public static boolean isCharging(Context context) {
        int intExtra = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        return intExtra == 2 || intExtra == 5;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onClapClick(Boolean bool) {
    }

    @Override
    public void onWhistleClick(Boolean bool) {
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityHomeBinding inflate = ActivityHomeBinding.inflate(getLayoutInflater());
        this.homeBinding = inflate;
        setContentView(inflate.getRoot());
        this.dbHelper = new DbHelper(this);
        this.presenter = new HomeView(this, this);
        setupNav();
        Adshandler adsManager = new Adshandler();
        this.adsManager = adsManager;
        adsManager.Banner(this, (LinearLayout) findViewById(R.id.fl_adplaceholder));
        this.mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        this.mComponentName = new ComponentName(this, Admin.class);
        this.homeBinding.navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity(view);
            }
        });
        this.homeBinding.intuderMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity2(view);
            }
        });
        this.homeBinding.imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity3(view);
            }
        });
        this.homeBinding.wrongpasswordMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity4(view);
            }
        });
        this.homeBinding.imgbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity5(view);
            }
        });
        this.homeBinding.antitouchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity8(view);
            }
        });
        this.homeBinding.imgbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity9(view);
            }
        });
        this.homeBinding.chargingremovelMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity10(view);
            }
        });
        this.homeBinding.imgbtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity11(view);
            }
        });
        this.homeBinding.batteryalertMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity12(view);
            }
        });
        this.homeBinding.imgbtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity13(view);
            }
        });
        this.homeBinding.chargerRemoveSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity14(view);
            }
        });
        this.homeBinding.wrongPassSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity15(view);
            }
        });
        this.homeBinding.bateryalertSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity16(view);
            }
        });
        this.homeBinding.antiTouchSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity17(view);
            }
        });
        this.homeBinding.intruderSwithch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity18(view);
            }
        });
        this.homeBinding.pocketalramSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity19(view);
            }
        });
        this.homeBinding.pocketalramMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity20(view);
            }
        });
        this.homeBinding.imgbtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity21(view);
            }
        });
    }

    public void HomeActivity(View view) {
        this.homeBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void HomeActivity2(View view) {
        this.presenter.onIntruderBtnClick("intruder");
    }

    public void HomeActivity3(View view) {
        this.presenter.onIntruderBtnClick("intruder");
    }

    public void HomeActivity4(View view) {
        this.presenter.onWrongPassBtnClick("wrongpas");
    }

    public void HomeActivity5(View view) {
        this.presenter.onWrongPassBtnClick("wrongpas");
    }

    public void HomeActivity8(View view) {
        this.presenter.onAntiTouchBtnClick("antitouch");
    }

    public void HomeActivity9(View view) {
        this.presenter.onAntiTouchBtnClick("antitouch");
    }

    public void HomeActivity10(View view) {
        this.presenter.onChargerBtnClick("charger");
    }

    public void HomeActivity11(View view) {
        this.presenter.onChargerBtnClick("charger");
    }

    public void HomeActivity12(View view) {
        this.presenter.onBatteryAlertBtnClick();
    }

    public void HomeActivity13(View view) {
        this.presenter.onBatteryAlertBtnClick();
    }

    public void HomeActivity14(View view) {
        this.presenter.onChargerClick(Boolean.valueOf(this.homeBinding.chargerRemoveSwitch.isChecked()));
    }

    public void HomeActivity15(View view) {
        this.presenter.onWrongPassClick(Boolean.valueOf(this.homeBinding.wrongPassSwitch.isChecked()));
    }

    public void HomeActivity16(View view) {
        this.presenter.onBatteryAlertClick(Boolean.valueOf(this.homeBinding.bateryalertSwitch.isChecked()));
    }

    public void HomeActivity17(View view) {
        this.presenter.onAntiTouchClick(Boolean.valueOf(this.homeBinding.antiTouchSwitch.isChecked()));
    }

    public void HomeActivity18(View view) {
        this.presenter.onIntruderClick(Boolean.valueOf(this.homeBinding.intruderSwithch.isChecked()));
    }

    public void HomeActivity19(View view) {
        this.presenter.onPocketAlarmClick(Boolean.valueOf(this.homeBinding.pocketalramSwitch.isChecked()));
    }

    public void HomeActivity20(View view) {
        this.presenter.onPocketAlarmBtnClick("pocketalarm");
    }

    public void HomeActivity21(View view) {
        this.presenter.onPocketAlarmBtnClick("pocketalarm");
    }

    public void setupNav() {
        this.homeBinding.navView.setItemIconTintList(null);
        this.homeBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return HomeActivity.this.HomeActivity22(menuItem);
            }
        });
    }

    public boolean HomeActivity22(MenuItem menuItem) {
        this.homeBinding.drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_privcy:
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public final void run() {
                        HomeActivity.this.HomeActivity24();
                    }
                }, 300L);
                return true;
            case R.id.nav_rate:
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public final void run() {
                        HomeActivity.this.HomeActivity23();
                    }
                }, 300L);
                return true;
            case R.id.nav_share:
                Constants.shareApp(this);
                return true;
            default:
                this.homeBinding.drawerLayout.closeDrawers();
                return true;
        }
    }

    public void HomeActivity23() {
        Constants.rateApp(this);
    }

    public void HomeActivity24() {
        Constants.showPrivacyPolicy(this);
    }


    @Override

    public void onStart() {
        super.onStart();
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
        SensorManager sensorManager = this.sensorMan;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        this.homeBinding.chargerRemoveSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Charger_Removal));
        this.homeBinding.bateryalertSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Full_Battery));
        this.homeBinding.antiTouchSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Anti_Touch));
        this.homeBinding.wrongPassSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Wrong_Pass));
        this.homeBinding.intruderSwithch.setChecked(this.dbHelper.chkBroadCast(Constants.Intruder_Selfie));
        this.homeBinding.pocketalramSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Pocket_Alarm));
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            devicePolicyManager.isAdminActive(this.mComponentName);
        }
        Constants.Light_Status = Boolean.valueOf(this.dbHelper.getAlarmSetting(Constants.Flash_Light));
        Constants.Vibration_Status = Boolean.valueOf(this.dbHelper.getAlarmSetting(Constants.Vibration_Enabled));
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onIntruderAlertClick(Boolean bool) {
        if (!bool.booleanValue()) {
            this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
            if (Constants.isMyServiceRunning(this, CameraService.class)) {
                stopService(new Intent(this, CameraService.class));
            }
            this.mDevicePolicyManager.removeActiveAdmin(this.mComponentName);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
            if (devicePolicyManager == null || devicePolicyManager.isAdminActive(this.mComponentName)) {
                this.dbHelper.setBroadCast(Constants.Intruder_Selfie, true);
                return;
            }
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mComponentName);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "Administrator description");
            startActivityForResult(intent, 3);
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onWrongPassClick(Boolean bool) {
        if (bool.booleanValue()) {
            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
            if (devicePolicyManager == null || devicePolicyManager.isAdminActive(this.mComponentName)) {
                this.dbHelper.setBroadCast(Constants.Wrong_Pass, true);
                return;
            }
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mComponentName);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "Administrator description");
            startActivityForResult(intent, 1);
            return;
        }
        if (Constants.isMyServiceRunning(this, PasswordService.class)) {
            stopService(new Intent(this, PasswordService.class));
        }
        this.dbHelper.setBroadCast(Constants.Wrong_Pass, false);
        this.mDevicePolicyManager.removeActiveAdmin(this.mComponentName);
    }

    @Override
    public void onPocketAlarmClick(Boolean bool) {
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
                    HomeActivity.this.HomeActivity25(dialog, view);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    HomeActivity.this.HomeActivity26(dialog, view);
                }
            });
            return;
        }
        this.homeBinding.pocketalramSwitch.setChecked(false);
        this.dbHelper.setBroadCast(Constants.Pocket_Alarm, false);
        stopService(new Intent(this, PocketService.class));
    }

    public void HomeActivity25(Dialog dialog, View view) {
        this.homeBinding.pocketalramSwitch.setChecked(false);
        this.dbHelper.setBroadCast(Constants.Pocket_Alarm, false);
        stopService(new Intent(this, PocketService.class));
        dialog.dismiss();
    }

    public void HomeActivity26(Dialog dialog, View view) {
        dialog.dismiss();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sensorMan = sensorManager;
        Sensor defaultSensor = sensorManager.getDefaultSensor(8);
        this.accelerometer = defaultSensor;
        this.sensorMan.registerListener(this, defaultSensor, 3);
        Constants.setAnalytics(this, "Pocket Alarm Alert");
        if (!Constants.isMyServiceRunning(this, PocketService.class)) {
            this.dbHelper.setBroadCast(Constants.Pocket_Alarm, true);
            this.homeBinding.pocketalramSwitch.setChecked(true);
            Intent intent = new Intent(this, PocketService.class);
            intent.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(this, intent);
        }
    }

    @Override
    public void onChargerAlarmClick(Boolean bool) {
        if (!bool.booleanValue()) {
            this.dbHelper.setBroadCast(Constants.Charger_Removal, false);
            this.homeBinding.chargerRemoveSwitch.setChecked(false);
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
                    HomeActivity.this.HomeActivity27(dialog, view);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    HomeActivity.this.HomeActivity28(dialog, view);
                }
            });
        } else if (!Constants.isMyServiceRunning(getApplicationContext(), ChargerService.class)) {
            this.dbHelper.setBroadCast(Constants.Charger_Removal, true);
            this.homeBinding.chargerRemoveSwitch.setChecked(true);
            Intent intent = new Intent(this, ChargerService.class);
            intent.putExtra("inputExtra", "input");
            ContextCompat.startForegroundService(this, intent);
        }
    }

    public void HomeActivity27(Dialog dialog, View view) {
        this.homeBinding.chargerRemoveSwitch.setChecked(false);
        this.dbHelper.setBroadCast(Constants.Charger_Removal, false);
        if (Constants.isMyServiceRunning(this, ChargerService.class)) {
            stopService(new Intent(this, ChargerService.class));
        }
        dialog.dismiss();
    }

    public void HomeActivity28(Dialog dialog, View view) {
        if (isCharging(this)) {
            if (!Constants.isMyServiceRunning(this, ChargerService.class)) {
                this.dbHelper.setBroadCast(Constants.Charger_Removal, true);
                this.homeBinding.chargerRemoveSwitch.setChecked(true);
                Intent intent = new Intent(this, ChargerService.class);
                intent.putExtra("inputExtra", "input");
                ContextCompat.startForegroundService(this, intent);
            }
            dialog.dismiss();
            return;
        }
        Toast.makeText(this, getString(R.string.pleaseConnectChargerToActiveThisFeature) + "", Toast.LENGTH_SHORT).show();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            new AlertDialog.Builder(this).setTitle(getString(R.string.permission_needed)).setMessage(getString(R.string.camera_permission)).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    HomeActivity.this.HomeActivity29(dialogInterface, i);
                }
            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    HomeActivity.this.HomeActivity30(dialogInterface, i);
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
        }
    }

    public void HomeActivity29(DialogInterface dialogInterface, int i) {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
    }

    public void HomeActivity30(DialogInterface dialogInterface, int i) {
        this.homeBinding.intruderSwithch.setChecked(false);
        dialogInterface.dismiss();
    }


    @Override

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            if (i2 == -1) {
                this.dbHelper.setBroadCast(Constants.Wrong_Pass, true);
            } else {
                this.homeBinding.wrongPassSwitch.setChecked(false);
            }
        } else if (i != 3) {
        } else {
            if (i2 == -1) {
                this.dbHelper.setBroadCast(Constants.Intruder_Selfie, true);
            } else {
                this.homeBinding.intruderSwithch.setChecked(false);
            }
        }
    }

    @Override

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 2) {
            return;
        }
        if (iArr.length > 0 && iArr[0] == 0) {
            this.dbHelper.setBroadCast(Constants.Intruder_Selfie, true);
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mComponentName);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "Administrator description");
            startActivityForResult(intent, 3);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            Toast.makeText(this, getString(R.string.permissionDENIED), Toast.LENGTH_LONG).show();
            this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
            this.homeBinding.intruderSwithch.setChecked(false);
        } else {
            Toast.makeText(this, getString(R.string.set_Permission_From_Settings), Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", getPackageName(), null));
            startActivityForResult(intent2, this.REQUEST_PERMISSION_SETTING);
            this.REQUEST_PERMISSION_SETTING = 1;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < this.accelerometer.getMaximumRange()) {
            Constants.Pocket_Status = true;
        }
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    public void exitDialog() {
        final Adshandler adsManager = new Adshandler();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.exit_panel, (ViewGroup) null);
        final RatingBar ratingBar = (RatingBar) inflate.findViewById(R.id.ratingbar);
        ((Button) inflate.findViewById(R.id.exit_btn_yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity31(view);
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public final void onRatingChanged(RatingBar ratingBar2, float f, boolean z) {
                HomeActivity.this.HomeActivity32(ratingBar2, f, z);
            }
        });
        ((Button) inflate.findViewById(R.id.exit__btn_no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                HomeActivity.this.HomeActivity33(adsManager, ratingBar, view);
            }
        });
        builder.setView(inflate);
        AlertDialog create = builder.create();
        this.alertDialogExit = create;
        create.setCancelable(true);
        if (this.alertDialogExit.getWindow() != null) {
            this.alertDialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        this.alertDialogExit.setCanceledOnTouchOutside(false);
        this.alertDialogExit.show();
    }

    public void HomeActivity31(View view) {
        AlertDialog alertDialog = this.alertDialogExit;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.alertDialogExit.dismiss();
            finish();
        }
    }

    public void HomeActivity32(RatingBar ratingBar, float f, boolean z) {
        if (f >= 3.0d) {
            RateApp();
        } else {
            Toast.makeText(this, "Thank For Your FeedBack", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void HomeActivity33(Adshandler adsManager, final RatingBar ratingBar, View view) {
        adsManager.showAd(this, new Adshandler.OnClose() {
            @Override

            public void onclick() {
                HomeActivity.this.HomeActivity34(ratingBar);
            }
        });
    }

    public void HomeActivity34(RatingBar ratingBar) {
        ratingBar.setRating(0.0f);
        this.alertDialogExit.dismiss();
    }

    public void RateApp() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } catch (Exception unused) {
        }
    }
}
