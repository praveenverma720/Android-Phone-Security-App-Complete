package com.redapps.phonepolice.view;


public interface IHomeView {
    void onAntiTouchBtnClick(String str);

    void onAntiTouchClick(Boolean bool);

    void onBatteryAlertBtnClick();

    void onBatteryAlertClick(Boolean bool);

    void onChargerBtnClick(String str);

    void onChargerClick(Boolean bool);

    void onIntruderBtnClick(String str);

    void onIntruderClick(Boolean bool);

    void onPocketAlarmBtnClick(String str);

    void onPocketAlarmClick(Boolean bool);

    void onWrongPassBtnClick(String str);

    void onWrongPassClick(Boolean bool);
}
