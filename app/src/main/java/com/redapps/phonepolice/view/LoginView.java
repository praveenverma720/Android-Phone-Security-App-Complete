package com.redapps.phonepolice.view;

import android.content.Context;

import com.redapps.phonepolice.R;
import com.redapps.phonepolice.helpers.DbHelper;
import com.redapps.phonepolice.models.User;
import com.redapps.phonepolice.presenter.ILoginPresenter;


public class LoginView implements ILoginView {
    final ILoginPresenter loginView;

    public LoginView(ILoginPresenter iLoginPresenter) {
        this.loginView = iLoginPresenter;
    }

    @Override
    public void onLogin(String str, Context context) {
        if (new DbHelper(context).chkPass(str)) {
            this.loginView.onLoginResult(context.getString(R.string.welcom));
        } else {
            this.loginView.onLoginError("Please Enter Correct Password");
        }
    }

    @Override
    public void onSignUp(String str, String str2, Context context) {
        int isValidData = new User(str, str2).isValidData();
        if (isValidData == 1) {
            new DbHelper(context).createUser(str);
            this.loginView.onLoginResult("SignUp SuccessFull");
        } else if (isValidData == 0) {
            this.loginView.onLoginError("Please Fill Both Fields");
        } else {
            this.loginView.onLoginError("Password Donot Match");
        }
    }
}
