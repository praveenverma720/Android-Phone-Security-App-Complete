package com.redapps.phonepolice.ui.alarmScreen;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.exifinterface.media.ExifInterface;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityAlramScreeBinding;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IAlarmPresenter;
import com.redapps.phonepolice.view.AlarmView;
import com.redapps.phonepolice.view.IAlarmView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class AlarmActivity extends AppCompatActivity implements IAlarmPresenter {
    public MediaPlayer player;
    ActivityAlramScreeBinding binding;
    DbHelper dbHelper;
    String from;
    IAlarmView presenter;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityAlramScreeBinding inflate = ActivityAlramScreeBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.from = getIntent().getStringExtra("from");
        this.dbHelper = new DbHelper(this);
        this.presenter = new AlarmView(this);
        findViewById(R.id.fl_adplaceholder).setVisibility(View.GONE);
        this.binding.changetone.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity3(view);
            }
        });
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity4(view);
            }
        });
        this.binding.chagnepincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity5(view);
            }
        });
        this.binding.vibrateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity6(view);
            }
        });
        this.binding.flashlightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity7(view);
            }
        });
        this.binding.flashlightSwitch.setChecked(this.dbHelper.getAlarmSetting(Constants.Flash_Light));
        this.binding.vibrateSwitch.setChecked(this.dbHelper.getAlarmSetting(Constants.Vibration_Enabled));
    }

    public void AlarmActivity3(View view) {
        this.presenter.onChangeToneClick();
    }

    public void AlarmActivity4(View view) {
        onBackPressed();
    }

    public void AlarmActivity5(View view) {
        this.presenter.onChangePinClick();
    }

    public void AlarmActivity6(View view) {
        this.presenter.onVibrateSwitch(Boolean.valueOf(this.binding.vibrateSwitch.isChecked()));
    }

    public void AlarmActivity7(View view) {
        this.presenter.onFlashLightSwitch(Boolean.valueOf(this.binding.flashlightSwitch.isChecked()));
    }


    @Override
    public void onChangeToneClick() {
        char c;
        final Dialog dialog = new Dialog(this);
        View inflate = getLayoutInflater().inflate(R.layout.select_tone_dialog, (ViewGroup) null);
        Button button = (Button) inflate.findViewById(R.id.cancel_button);
        Button button2 = (Button) inflate.findViewById(R.id.select_btn);
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.radioGroup);
        RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.radio_one);
        RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.radio_two);
        RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.radio_three);
        RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.radio_four);
        RadioButton radioButton5 = (RadioButton) inflate.findViewById(R.id.radio_five);
        RadioButton radioButton6 = (RadioButton) inflate.findViewById(R.id.radio_six);
        final String[] strArr = {String.valueOf(this.dbHelper.getTone())};
        switch (this.dbHelper.getTone()) {
            case 1:
                radioButton.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone1);
                break;
            case 2:
                radioButton2.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone2);
                break;
            case 3:
                radioButton3.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone3);
                break;
            case 4:
                radioButton4.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone4);
                break;
            case 5:
                radioButton5.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone5);
                break;
            case 6:
                radioButton6.setChecked(true);
                this.player = MediaPlayer.create(this, (int) R.raw.tone6);
                break;
        }
        String str = this.from;
        str.hashCode();
        switch (str.hashCode()) {
            case -2100958947:
                if (str.equals("antitouch")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1680160811:
                if (str.equals("wrongpas")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -378415994:
                if (str.equals("fullbatery")) {
                    c = 2;
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
            case 582496351:
                if (str.equals("intruder")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 739062846:
                if (str.equals("charger")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1316690498:
                if (str.equals("whislte")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2076963451:
                if (str.equals("pocketalarm")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                button.setTextColor(Color.parseColor("#FFC401"));
                button2.setBackgroundColor(Color.parseColor("#FFC401"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                break;
            case 1:
                button.setTextColor(Color.parseColor("#5F82E2"));
                button2.setBackgroundColor(Color.parseColor("#5F82E2"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                break;
            case 2:
                button.setTextColor(Color.parseColor("#FE7905"));
                button2.setBackgroundColor(Color.parseColor("#FE7905"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                break;
            case 3:
                button.setTextColor(Color.parseColor("#59D075"));
                button2.setBackgroundColor(Color.parseColor("#59D075"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#59D075")));
                break;
            case 4:
                button.setTextColor(Color.parseColor("#7e8ee7"));
                button2.setBackgroundColor(Color.parseColor("#7e8ee7"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                break;
            case 5:
                button.setTextColor(Color.parseColor("#E363D7"));
                button2.setBackgroundColor(Color.parseColor("#E363D7"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                break;
            case 6:
                button.setTextColor(Color.parseColor("#71D2FE"));
                button2.setBackgroundColor(Color.parseColor("#71D2FE"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                break;
            case 7:
                button.setTextColor(Color.parseColor("#C7E362"));
                button2.setBackgroundColor(Color.parseColor("#C7E362"));
                radioButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                radioButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                radioButton3.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                radioButton4.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                radioButton5.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                radioButton6.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                break;
        }
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(-2, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                AlarmActivity.this.AlarmActivity(strArr, radioGroup2, i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity3(strArr, dialog, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity2(dialog, view);
            }
        });
    }

    public void AlarmActivity(String[] strArr, RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_five:
                strArr[0] = "5";
                this.player.stop();
                this.player.release();
                MediaPlayer create = MediaPlayer.create(this, (int) R.raw.tone5);
                this.player = create;
                create.start();
                return;
            case R.id.radio_four:
                strArr[0] = "4";
                this.player.stop();
                this.player.release();
                MediaPlayer create2 = MediaPlayer.create(this, (int) R.raw.tone4);
                this.player = create2;
                create2.start();
                return;
            case R.id.radio_one:
                strArr[0] = "1";
                this.player.stop();
                this.player.release();
                MediaPlayer create3 = MediaPlayer.create(this, (int) R.raw.tone1);
                this.player = create3;
                create3.start();
                return;
            case R.id.radio_six:
                strArr[0] = "6";
                this.player.stop();
                this.player.release();
                MediaPlayer create4 = MediaPlayer.create(this, (int) R.raw.tone6);
                this.player = create4;
                create4.start();
                return;
            case R.id.radio_three:
                strArr[0] = ExifInterface.GPS_MEASUREMENT_3D;
                this.player.stop();
                this.player.release();
                MediaPlayer create5 = MediaPlayer.create(this, (int) R.raw.tone3);
                this.player = create5;
                create5.start();
                return;
            case R.id.radio_two:
                strArr[0] = ExifInterface.GPS_MEASUREMENT_2D;
                this.player.stop();
                this.player.release();
                MediaPlayer create6 = MediaPlayer.create(this, (int) R.raw.tone2);
                this.player = create6;
                create6.start();
                return;
            default:
                return;
        }
    }

    public void AlarmActivity3(String[] strArr, Dialog dialog, View view) {
        this.dbHelper.setTone(strArr[0]);
        switch (this.dbHelper.getTone()) {
            case 1:
                this.binding.tonidtxt.setText(getString(R.string.one));
                break;
            case 2:
                this.binding.tonidtxt.setText(getString(R.string.two));
                break;
            case 3:
                this.binding.tonidtxt.setText(getString(R.string.three));
                break;
            case 4:
                this.binding.tonidtxt.setText(getString(R.string.four));
                break;
            case 5:
                this.binding.tonidtxt.setText(getString(R.string.five));
                break;
            case 6:
                this.binding.tonidtxt.setText(getString(R.string.six));
                break;
        }
        this.player.stop();
        this.player.release();
        dialog.dismiss();
    }

    public void AlarmActivity2(Dialog dialog, View view) {
        this.player.stop();
        this.player.release();
        dialog.dismiss();
    }

    @Override
    public void onChangePinClick() {
        final Dialog dialog = new Dialog(this);
        View inflate = getLayoutInflater().inflate(R.layout.changepin_dialog, (ViewGroup) null);
        Button button = (Button) inflate.findViewById(R.id.cancel_button);
        Button button2 = (Button) inflate.findViewById(R.id.select_btn);
        final TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.txtinoutlayout);
        final TextInputEditText textInputEditText = (TextInputEditText) inflate.findViewById(R.id.editTextTextPassword);
        String str = this.from;
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2100958947:
                if (str.equals("antitouch")) {
                    c = 0;
                    break;
                }
                break;
            case -1680160811:
                if (str.equals("wrongpas")) {
                    c = 1;
                    break;
                }
                break;
            case -378415994:
                if (str.equals("fullbatery")) {
                    c = 2;
                    break;
                }
                break;
            case 94742811:
                if (str.equals("claps")) {
                    c = 3;
                    break;
                }
                break;
            case 582496351:
                if (str.equals("intruder")) {
                    c = 4;
                    break;
                }
                break;
            case 739062846:
                if (str.equals("charger")) {
                    c = 5;
                    break;
                }
                break;
            case 1316690498:
                if (str.equals("whislte")) {
                    c = 6;
                    break;
                }
                break;
            case 2076963451:
                if (str.equals("pocketalarm")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                button.setTextColor(Color.parseColor("#FFC401"));
                button2.setBackgroundColor(Color.parseColor("#FFC401"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#FFC401"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FFC401")));
                break;
            case 1:
                button.setTextColor(Color.parseColor("#5F82E2"));
                button2.setBackgroundColor(Color.parseColor("#5F82E2"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#5F82E2"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#5F82E2")));
                break;
            case 2:
                button.setTextColor(Color.parseColor("#FE7905"));
                button2.setBackgroundColor(Color.parseColor("#FE7905"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#FE7905"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FE7905")));
                break;
            case 3:
                button.setTextColor(Color.parseColor("#59D075"));
                button2.setBackgroundColor(Color.parseColor("#59D075"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#59D075"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#59D075")));
                break;
            case 4:
                button.setTextColor(Color.parseColor("#7e8ee7"));
                button2.setBackgroundColor(Color.parseColor("#7e8ee7"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#7e8ee7"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#7e8ee7")));
                break;
            case 5:
                button.setTextColor(Color.parseColor("#E363D7"));
                button2.setBackgroundColor(Color.parseColor("#E363D7"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#E363D7"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#E363D7")));
                break;
            case 6:
                button.setTextColor(Color.parseColor("#71D2FE"));
                button2.setBackgroundColor(Color.parseColor("#71D2FE"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#71D2FE"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#71D2FE")));
                break;
            case 7:
                button.setTextColor(Color.parseColor("#C7E362"));
                button2.setBackgroundColor(Color.parseColor("#C7E362"));
                textInputLayout.setBoxStrokeColor(Color.parseColor("#C7E362"));
                textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#C7E362")));
                break;
        }
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(-2, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AlarmActivity.this.AlarmActivity(textInputEditText, textInputLayout, dialog, view);
            }
        });
    }

    public void AlarmActivity(TextInputEditText textInputEditText, TextInputLayout textInputLayout, Dialog dialog, View view) {
        Editable text = textInputEditText.getText();
        Objects.requireNonNull(text);
        if (text.toString().equals("")) {
            textInputLayout.setError(getString(R.string.please_Write_Something));
            return;
        }
        this.dbHelper.createUser(textInputEditText.getText().toString());
        Toast.makeText(this, "Password Changed!", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onVibrateSwitch(Boolean bool) {
        this.dbHelper.setAlarmSetting(Constants.Vibration_Enabled, bool);
        Constants.Vibration_Status = Boolean.valueOf(this.dbHelper.getAlarmSetting(Constants.Vibration_Enabled));
    }

    @Override
    public void onFlashLightSwitch(Boolean bool) {
        this.dbHelper.setAlarmSetting(Constants.Flash_Light, bool);
        Constants.Light_Status = Boolean.valueOf(this.dbHelper.getAlarmSetting(Constants.Flash_Light));
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
            case -1680160811:
                if (str2.equals("wrongpas")) {
                    c = 1;
                    break;
                }
                break;
            case -378415994:
                if (str2.equals("fullbatery")) {
                    c = 2;
                    break;
                }
                break;
            case 94742811:
                if (str2.equals("claps")) {
                    c = 3;
                    break;
                }
                break;
            case 582496351:
                if (str2.equals("intruder")) {
                    c = 4;
                    break;
                }
                break;
            case 739062846:
                if (str2.equals("charger")) {
                    c = 5;
                    break;
                }
                break;
            case 1316690498:
                if (str2.equals("whislte")) {
                    c = 6;
                    break;
                }
                break;
            case 2076963451:
                if (str2.equals("pocketalarm")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(Integer.MIN_VALUE);
                    window.clearFlags(67108864);
                    window.setStatusBarColor(Color.parseColor("#FFC401"));
                }
                int[][] iArr = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr2 = {Color.parseColor("#B2B2B2"), Color.parseColor("#FFC401")};
                int[] iArr3 = {Color.parseColor("#D6D5D5"), Color.parseColor("#FBF5AB")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr, iArr2));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr, iArr3));
                break;
            case 1:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window2 = getWindow();
                    window2.addFlags(Integer.MIN_VALUE);
                    window2.clearFlags(67108864);
                    window2.setStatusBarColor(Color.parseColor("#5F82E2"));
                }
                int[][] iArr4 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr5 = {Color.parseColor("#B2B2B2"), Color.parseColor("#5F82E2")};
                int[] iArr6 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B4CAF7")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr4, iArr5));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr4, iArr6));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr4, iArr5));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr4, iArr6));
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window3 = getWindow();
                    window3.addFlags(Integer.MIN_VALUE);
                    window3.clearFlags(67108864);
                    window3.setStatusBarColor(Color.parseColor("#FE7905"));
                }
                int[][] iArr7 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr8 = {Color.parseColor("#D6D5D5"), Color.parseColor("#FE7905")};
                int[] iArr9 = {Color.parseColor("#A5A4A4"), Color.parseColor("#F8C8B6")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr7, iArr8));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr7, iArr9));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr7, iArr8));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr7, iArr9));
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window4 = getWindow();
                    window4.addFlags(Integer.MIN_VALUE);
                    window4.clearFlags(67108864);
                    window4.setStatusBarColor(Color.parseColor("#59D075"));
                }
                int[][] iArr10 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr11 = {Color.parseColor("#B2B2B2"), Color.parseColor("#59D075")};
                int[] iArr12 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B1F5D4")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr10, iArr11));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr10, iArr12));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr10, iArr11));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr10, iArr12));
                break;
            case 4:
                this.binding.flashlayout.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window5 = getWindow();
                    window5.addFlags(Integer.MIN_VALUE);
                    window5.clearFlags(67108864);
                    window5.setStatusBarColor(Color.parseColor("#7e8ee7"));
                }
                int[][] iArr13 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr14 = {Color.parseColor("#B2B2B2"), Color.parseColor("#7e8ee7")};
                int[] iArr15 = {Color.parseColor("#D6D5D5"), Color.parseColor("#FFE1E1")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr13, iArr14));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr13, iArr15));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr13, iArr14));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr13, iArr15));
                break;
            case 5:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window6 = getWindow();
                    window6.addFlags(Integer.MIN_VALUE);
                    window6.clearFlags(67108864);
                    window6.setStatusBarColor(Color.parseColor("#E363D7"));
                }
                int[][] iArr16 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr17 = {Color.parseColor("#B2B2B2"), Color.parseColor("#E363D7")};
                int[] iArr18 = {Color.parseColor("#D6D5D5"), Color.parseColor("#F2B8F8")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr16, iArr17));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr16, iArr18));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr16, iArr17));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr16, iArr18));
                break;
            case 6:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window7 = getWindow();
                    window7.addFlags(Integer.MIN_VALUE);
                    window7.clearFlags(67108864);
                    window7.setStatusBarColor(Color.parseColor("#71D2FE"));
                }
                int[][] iArr19 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr20 = {Color.parseColor("#B2B2B2"), Color.parseColor("#71D2FE")};
                int[] iArr21 = {Color.parseColor("#D6D5D5"), Color.parseColor("#B4E9FF")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr19, iArr20));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr19, iArr21));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr19, iArr20));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr19, iArr21));
                break;
            case 7:
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window8 = getWindow();
                    window8.addFlags(Integer.MIN_VALUE);
                    window8.clearFlags(67108864);
                    window8.setStatusBarColor(Color.parseColor("#C8E25E"));
                }
                int[][] iArr22 = {new int[]{-16842912}, new int[]{16842912}};
                int[] iArr23 = {Color.parseColor("#B2B2B2"), Color.parseColor("#C8E25E")};
                int[] iArr24 = {Color.parseColor("#D6D5D5"), Color.parseColor("#E1F591")};
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getThumbDrawable()), new ColorStateList(iArr22, iArr23));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.vibrateSwitch.getTrackDrawable()), new ColorStateList(iArr22, iArr24));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getThumbDrawable()), new ColorStateList(iArr22, iArr23));
                DrawableCompat.setTintList(DrawableCompat.wrap(this.binding.flashlightSwitch.getTrackDrawable()), new ColorStateList(iArr22, iArr24));
                break;
        }
        switch (this.dbHelper.getTone()) {
            case 1:
                this.binding.tonidtxt.setText(getString(R.string.one));
                return;
            case 2:
                this.binding.tonidtxt.setText(getString(R.string.two));
                return;
            case 3:
                this.binding.tonidtxt.setText(getString(R.string.three));
                return;
            case 4:
                this.binding.tonidtxt.setText(getString(R.string.four));
                return;
            case 5:
                this.binding.tonidtxt.setText(getString(R.string.five));
                return;
            case 6:
                this.binding.tonidtxt.setText(getString(R.string.six));
                return;
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
    }
}
