package com.redapps.phonepolice.ui.intruderAlert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.exifinterface.media.ExifInterface;

import com.redapps.phonepolice.Admin;
import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityIntruderAlertScreenBinding;
import com.redapps.phonepolice.helpers.Adshandler;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IIntruderAlertPresenter;
import com.redapps.phonepolice.serviceHandler.CameraService;
import com.redapps.phonepolice.serviceHandler.PasswordService;
import com.redapps.phonepolice.ui.alarmScreen.AlarmActivity;
import com.redapps.phonepolice.ui.showIntruderScreen.ShowIntruderActivity;
import com.redapps.phonepolice.view.IIntruderAlertView;
import com.redapps.phonepolice.view.IntruderAlertView;

import java.util.Objects;


public class IntruderAlertScreen extends AppCompatActivity implements IIntruderAlertPresenter {
    Adshandler adsManager;
    ActivityIntruderAlertScreenBinding binding;
    DbHelper dbHelper;
    String from;
    IIntruderAlertView presenter;
    private int REQUEST_PERMISSION_SETTING = 0;
    private ComponentName mComponentName;
    private DevicePolicyManager mDevicePolicyManager;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityIntruderAlertScreenBinding inflate = ActivityIntruderAlertScreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.dbHelper = new DbHelper(this);
        this.presenter = new IntruderAlertView(this);
        Adshandler adsManager = new Adshandler();
        this.adsManager = adsManager;
        this.mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        this.mComponentName = new ComponentName(this, Admin.class);
        initViews();
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null && devicePolicyManager.isAdminActive(this.mComponentName)) {
            this.binding.activateIntruderSwitch.setChecked(true);
        }
        this.binding.alramSetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen(view);
            }
        });
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen1(view);
            }
        });
        this.binding.showIntruderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen2(view);
            }
        });
        this.binding.ringAlarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen3(view);
            }
        });
        this.binding.activateIntruderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                IntruderAlertScreen.this.IntruderAlertScreen4(compoundButton, z);
            }
        });
        this.binding.at1.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen5(view);
            }
        });
        this.binding.at2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen6(view);
            }
        });
        this.binding.at3.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                IntruderAlertScreen.this.IntruderAlertScreen7(view);
            }
        });
        this.binding.ringAlarmSwitch.setChecked(this.dbHelper.getAlarmSetting(Constants.Intruder_Alarm));
    }

    public void IntruderAlertScreen(View view) {
        this.presenter.onAlarmSettingBtn();
    }

    public void IntruderAlertScreen1(View view) {
        onBackPressed();
    }

    public void IntruderAlertScreen2(View view) {
        this.presenter.onShowIntruderAlertBtn();
    }

    public void IntruderAlertScreen3(View view) {
        this.presenter.onRingAlarmSwitchClick(Boolean.valueOf(this.binding.ringAlarmSwitch.isChecked()));
    }

    public void IntruderAlertScreen4(CompoundButton compoundButton, boolean z) {
        this.presenter.onActiveIntruderSwitchClick(Boolean.valueOf(z));
    }

    public void IntruderAlertScreen5(View view) {
        this.presenter.onAt1Click();
    }

    public void IntruderAlertScreen6(View view) {
        this.presenter.onAt2Click();
    }

    public void IntruderAlertScreen7(View view) {
        this.presenter.onAt3Click();
    }

    public void initViews() {
        String stringExtra = getIntent().getStringExtra("from");
        this.from = stringExtra;
        Objects.requireNonNull(stringExtra);
        if (stringExtra.equals("wrongpas")) {
            this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
            this.binding.activateIntruderSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Wrong_Pass));
            this.binding.showIntruderBtn.setVisibility(View.GONE);
            this.binding.alramSetingBtn.setBackgroundColor(Color.parseColor("#5F82E2"));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.addFlags(Integer.MIN_VALUE);
                window.clearFlags(67108864);
                window.setStatusBarColor(Color.parseColor("#5F82E2"));
            }
            int[][] iArr = {new int[]{-16842912}, new int[]{16842912}};
            int[] iArr2 = {Color.parseColor("#B2B2B2"), Color.parseColor("#5F82E2")};
            int[] iArr3 = {Color.parseColor("#EFEFEF"), Color.parseColor("#B4CAF7")};
            DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.activateIntruderSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
            DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.activateIntruderSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
            DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.ringAlarmSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
            DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.ringAlarmSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
            this.binding.at1.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.at2.setBackgroundColor(Color.parseColor("#5F82E2"));
            this.binding.at3.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.screendis.setText(getString(R.string.wrongpassdes));
            this.binding.screenlabl.setText(getString(R.string.wrongpasslabel));
            this.binding.intruderlabl.setText(getString(R.string.wrongpassswitchlabel));
        } else {
            this.binding.activateIntruderSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Intruder_Selfie));
            this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
            if (Build.VERSION.SDK_INT >= 21) {
                Window window2 = getWindow();
                window2.addFlags(Integer.MIN_VALUE);
                window2.clearFlags(67108864);
                window2.setStatusBarColor(Color.parseColor("#7e8ee7"));
            }
        }
        if (this.dbHelper.getAttemptNo() == 1) {
            this.presenter.onAt1Click();
        } else if (this.dbHelper.getAttemptNo() == 2) {
            this.presenter.onAt2Click();
        } else if (this.dbHelper.getAttemptNo() == 3) {
            this.presenter.onAt3Click();
        }
    }

    @Override
    public void onActiveIntruderSwitchClick(Boolean bool) {
        if (bool.booleanValue()) {
            final Dialog dialog = new Dialog(this);
            View inflate = getLayoutInflater().inflate(R.layout.activation_alert_dialog, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.alram_seting_btn);
            Button button2 = (Button) inflate.findViewById(R.id.show_intruder_btn);
            if (this.from.equals("wrongpas")) {
                button.setTextColor(Color.parseColor("#5F82E2"));
                button2.setBackgroundColor(Color.parseColor("#5F82E2"));
                dialog.setContentView(inflate);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = Gravity.CENTER;
                window.setAttributes(attributes);
                dialog.getWindow().setLayout(-2, -2);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        IntruderAlertScreen.this.IntruderAlertScreen8(dialog, view);
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public final void onClick(View view) {
                        IntruderAlertScreen.this.IntruderAlertScreen9(dialog, view);
                    }
                });
                return;
            }
            dialog.setContentView(inflate);
            Window window2 = dialog.getWindow();
            WindowManager.LayoutParams attributes2 = window2.getAttributes();
            attributes2.gravity = Gravity.CENTER;
            window2.setAttributes(attributes2);
            dialog.getWindow().setLayout(-2, -2);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    IntruderAlertScreen.this.IntruderAlertScreen10(dialog, view);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    IntruderAlertScreen.this.IntruderAlertScreen11(dialog, view);
                }
            });
            return;
        }
        if (Constants.isMyServiceRunning(this, CameraService.class) && this.from.equals("intruder")) {
            stopService(new Intent(this, CameraService.class));
        } else if (Constants.isMyServiceRunning(this, PasswordService.class) && this.from.equals("wrongpas")) {
            stopService(new Intent(this, PasswordService.class));
        }
        this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
        this.dbHelper.setBroadCast(Constants.Wrong_Pass, false);
        this.mDevicePolicyManager.removeActiveAdmin(this.mComponentName);
    }

    public void IntruderAlertScreen8(Dialog dialog, View view) {
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mComponentName);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "Administrator description");
        startActivityForResult(intent, 1);
        dialog.dismiss();
    }

    public void IntruderAlertScreen9(Dialog dialog, View view) {
        dialog.dismiss();
        this.mDevicePolicyManager.removeActiveAdmin(this.mComponentName);
        this.binding.activateIntruderSwitch.setChecked(false);
    }

    public void IntruderAlertScreen10(Dialog dialog, View view) {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
            if (devicePolicyManager == null || devicePolicyManager.isAdminActive(this.mComponentName)) {
                this.dbHelper.setBroadCast(Constants.Intruder_Selfie, true);
                Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
                intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mComponentName);
                intent.putExtra("android.app.extra.ADD_EXPLANATION", "Administrator description");
                startActivityForResult(intent, 3);
            }
        } else {
            requestCameraPermission();
        }
        dialog.dismiss();
    }

    public void IntruderAlertScreen11(Dialog dialog, View view) {
        dialog.dismiss();
        this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
        this.mDevicePolicyManager.removeActiveAdmin(this.mComponentName);
        this.binding.activateIntruderSwitch.setChecked(false);
    }

    @Override
    public void onRingAlarmSwitchClick(Boolean bool) {
        this.dbHelper.setAlarmSetting(Constants.Intruder_Alarm, bool);
    }

    @Override
    public void onAt1Click() {
        if (this.from.equals("wrongpas")) {
            this.binding.at1.setBackgroundColor(Color.parseColor("#5F82E2"));
            this.binding.at2.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.at3.setBackgroundColor(Color.parseColor("#B3C9F7"));
        } else {
            this.binding.at1.setBackgroundColor(Color.parseColor("#7e8ee7"));
            this.binding.at2.setBackgroundColor(Color.parseColor("#FFE1E1"));
            this.binding.at3.setBackgroundColor(Color.parseColor("#FFE1E1"));
        }
        this.binding.at1Txt.setTextColor(Color.parseColor("#FFFFFF"));
        this.binding.at2Txt.setTextColor(Color.parseColor("#374045"));
        this.binding.at3Txt.setTextColor(Color.parseColor("#374045"));
        this.dbHelper.setAttemptNo("1");
    }

    @Override
    public void onAt2Click() {
        this.dbHelper.setAttemptNo(ExifInterface.GPS_MEASUREMENT_2D);
        if (this.from.equals("wrongpas")) {
            this.binding.at2.setBackgroundColor(Color.parseColor("#5F82E2"));
            this.binding.at1.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.at3.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.at2Txt.setTextColor(Color.parseColor("#FFFFFF"));
            this.binding.at1Txt.setTextColor(Color.parseColor("#374045"));
            this.binding.at3Txt.setTextColor(Color.parseColor("#374045"));
            return;
        }
        this.binding.at2Txt.setTextColor(Color.parseColor("#FFFFFF"));
        this.binding.at1Txt.setTextColor(Color.parseColor("#374045"));
        this.binding.at3Txt.setTextColor(Color.parseColor("#374045"));
        this.binding.at2.setBackgroundColor(Color.parseColor("#7e8ee7"));
        this.binding.at1.setBackgroundColor(Color.parseColor("#FFE1E1"));
        this.binding.at3.setBackgroundColor(Color.parseColor("#FFE1E1"));
    }

    @Override
    public void onAt3Click() {
        this.dbHelper.setAttemptNo(ExifInterface.GPS_MEASUREMENT_3D);
        if (this.from.equals("wrongpas")) {
            this.binding.at3.setBackgroundColor(Color.parseColor("#5F82E2"));
            this.binding.at2.setBackgroundColor(Color.parseColor("#B3C9F7"));
            this.binding.at1.setBackgroundColor(Color.parseColor("#B3C9F7"));
        } else {
            this.binding.at3.setBackgroundColor(Color.parseColor("#7e8ee7"));
            this.binding.at2.setBackgroundColor(Color.parseColor("#FFE1E1"));
            this.binding.at1.setBackgroundColor(Color.parseColor("#FFE1E1"));
        }
        this.binding.at3Txt.setTextColor(Color.parseColor("#FFFFFF"));
        this.binding.at2Txt.setTextColor(Color.parseColor("#374045"));
        this.binding.at1Txt.setTextColor(Color.parseColor("#374045"));
    }

    @Override
    public void onAlarmSettingBtn() {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("from", this.from);
        startActivity(intent);
    }

    @Override
    public void onShowIntruderAlertBtn() {
        startActivity(new Intent(this, ShowIntruderActivity.class));
    }


    @Override

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            if (i2 == -1) {
                this.dbHelper.setBroadCast(Constants.Wrong_Pass, true);
            } else {
                this.dbHelper.setBroadCast(Constants.Wrong_Pass, false);
                this.binding.activateIntruderSwitch.setChecked(false);
            }
        }
        if (i != 3) {
            return;
        }
        if (i2 == -1) {
            this.dbHelper.setBroadCast(Constants.Intruder_Selfie, true);
            return;
        }
        this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
        this.binding.activateIntruderSwitch.setChecked(false);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            new AlertDialog.Builder(this).setTitle(getString(R.string.permission_needed)).setMessage(getString(R.string.camera_permission)).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    IntruderAlertScreen.this.IntruderAlertScreen12(dialogInterface, i);
                }
            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    IntruderAlertScreen.this.IntruderAlertScreen13(dialogInterface, i);
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
        }
    }

    public void IntruderAlertScreen12(DialogInterface dialogInterface, int i) {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 2);
    }

    public void IntruderAlertScreen13(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.dbHelper.setBroadCast(Constants.Intruder_Selfie, false);
        this.binding.activateIntruderSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Intruder_Selfie));
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
            this.binding.activateIntruderSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Intruder_Selfie));
        } else {
            Toast.makeText(this, getString(R.string.set_Permission_From_Settings), Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", getPackageName(), null));
            startActivityForResult(intent2, this.REQUEST_PERMISSION_SETTING);
            this.REQUEST_PERMISSION_SETTING = 1;
        }
    }


    @Override

    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onResume() {
        super.onResume();
        String str = this.from;
        Objects.requireNonNull(str);
        if (!str.equals("wrongpas")) {
            this.binding.activateIntruderSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Intruder_Selfie));
        }
    }

    public void IntruderAlertScreen14() {
        finish();
    }

    @Override
    public void onBackPressed() {
        this.adsManager.showAd(this, new Adshandler.OnClose() {
            @Override

            public void onclick() {
                IntruderAlertScreen.this.IntruderAlertScreen14();
            }
        });
    }
}
