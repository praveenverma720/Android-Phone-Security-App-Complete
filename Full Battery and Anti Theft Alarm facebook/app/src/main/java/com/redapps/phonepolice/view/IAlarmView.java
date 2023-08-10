package com.redapps.phonepolice.view;


public interface IAlarmView {
    void onChangePinClick();

    void onChangeToneClick();

    void onFlashLightSwitch(Boolean bool);

    void onVibrateSwitch(Boolean bool);
}
