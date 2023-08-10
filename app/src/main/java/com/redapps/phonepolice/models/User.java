package com.redapps.phonepolice.models;

import android.text.TextUtils;


public class User implements IUser {
    private final String pass;
    private final String pass2;

    public User(String str, String str2) {
        this.pass = str;
        this.pass2 = str2;
    }

    @Override
    public String getPass1() {
        return this.pass;
    }

    @Override
    public String getPass2() {
        return this.pass2;
    }

    @Override
    public int isValidData() {
        if (TextUtils.isEmpty(getPass1()) || TextUtils.isEmpty(getPass2())) {
            return 0;
        }
        return getPass1().equals(getPass2()) ? 1 : 3;
    }
}
