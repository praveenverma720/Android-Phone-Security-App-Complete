package com.redapps.phonepolice.ui.fullChargerScreen;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityFullChargerScreenBinding;
import com.redapps.phonepolice.helpers.Adshandler;
import com.redapps.phonepolice.helpers.Constants;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.IFullChargePresenter;
import com.redapps.phonepolice.view.FullChargeView;
import com.redapps.phonepolice.view.IFullChargeView;


public class FullChargerActivity extends AppCompatActivity implements IFullChargePresenter {
    Adshandler adsManager;
    ActivityFullChargerScreenBinding binding;
    DbHelper dbHelper;
    private IFullChargeView presenter;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityFullChargerScreenBinding inflate = ActivityFullChargerScreenBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.presenter = new FullChargeView(this, this);
        this.dbHelper = new DbHelper(this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(Color.parseColor("#FE7905"));
        }
        this.binding.backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FullChargerActivity.this.FullChargerActivity(view);
            }
        });
        this.binding.optionSwitch.setChecked(this.dbHelper.chkBroadCast(Constants.Full_Battery));
        this.binding.optionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FullChargerActivity.this.FullChargerActivity2(view);
            }
        });
        this.binding.alramSetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                FullChargerActivity.this.FullChargerActivity3(view);
            }
        });
        Adshandler adsManager = new Adshandler();
        this.adsManager = adsManager;
        this.adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
    }

    public void FullChargerActivity(View view) {
        onBackPressed();
    }

    public void FullChargerActivity2(View view) {
        this.presenter.onOptionSwitchClick(Boolean.valueOf(this.binding.optionSwitch.isChecked()));
    }

    public void FullChargerActivity3(View view) {
        this.presenter.onAlarmBtnClick();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void FullChargerActivity4() {
        finish();
    }

    @Override
    public void onBackPressed() {
        this.adsManager.showAd(this, new Adshandler.OnClose() {
            @Override

            public void onclick() {
                FullChargerActivity.this.FullChargerActivity4();
            }
        });
    }


    @Override

    public void onDestroy() {
        super.onDestroy();

    }
}
