package com.redapps.phonepolice.ui.loginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.databinding.ActivityLoginScreenBinding;
import com.redapps.phonepolice.helpers.Adshandler;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.presenter.ILoginPresenter;
import com.redapps.phonepolice.ui.homeScreen.HomeActivity;
import com.redapps.phonepolice.view.ILoginView;
import com.redapps.phonepolice.view.LoginView;


public class LoginActivity extends AppCompatActivity implements ILoginPresenter {
    Adshandler adsManager;
    ILoginView loginPresenter;
    ActivityLoginScreenBinding loginScreenBinding;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityLoginScreenBinding inflate = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        this.loginScreenBinding = inflate;
        setContentView(inflate.getRoot());
        this.loginPresenter = new LoginView(this);
        final DbHelper dbHelper = new DbHelper(this);
        Adshandler adsManager = new Adshandler();
        this.adsManager = adsManager;
        adsManager.refreshAd((com.facebook.ads.NativeAdLayout) findViewById(R.id.fl_adplaceholder), this);
        if (dbHelper.userExists()) {
            this.loginScreenBinding.pass2.setVisibility(View.GONE);
            this.loginScreenBinding.savebtn.setText(getString(R.string.login));
            this.loginScreenBinding.textView2.setText(getString(R.string.welcom));
        } else {
            this.loginScreenBinding.pass2.setVisibility(View.VISIBLE);
            this.loginScreenBinding.savebtn.setText(getString(R.string.signup));
            this.loginScreenBinding.textView2.setText(getString(R.string.set_your_password_first));
        }
        this.loginScreenBinding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                LoginActivity.this.LoginActivity(dbHelper, view);
            }
        });
    }

    public void LoginActivity(DbHelper dbHelper, View view) {
        if (dbHelper.userExists()) {
            if (this.loginScreenBinding.pass1.getText().toString().length() < 4) {
                this.loginScreenBinding.pass1.setError(getString(R.string.minimumasswordLengthShouldbe));
            } else {
                this.loginPresenter.onLogin(this.loginScreenBinding.pass1.getText().toString(), this);
            }
        } else if (this.loginScreenBinding.pass1.getText().toString().length() < 4) {
            this.loginScreenBinding.pass1.setError(getString(R.string.minimumasswordLengthShouldbe));
        } else if (this.loginScreenBinding.pass2.getText().toString().length() < 4) {
            this.loginScreenBinding.pass2.setError(getString(R.string.minimumasswordLengthShouldbe));
        } else {
            this.loginPresenter.onSignUp(this.loginScreenBinding.pass1.getText().toString(), this.loginScreenBinding.pass2.getText().toString(), this);
        }
    }

    @Override
    public void onLoginResult(String str) {
        Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onLoginError(String str) {
        Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
    }


    @Override

    public void onDestroy() {
        super.onDestroy();

    }
}
