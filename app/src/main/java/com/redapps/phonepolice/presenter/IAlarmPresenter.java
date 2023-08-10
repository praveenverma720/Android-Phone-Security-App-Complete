package com.redapps.phonepolice.presenter;


public interface IAlarmPresenter {
    void onChangePinClick();

    void onChangeToneClick();

    void onFlashLightSwitch(Boolean bool);

    void onVibrateSwitch(Boolean bool);
}
