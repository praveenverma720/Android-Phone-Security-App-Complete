package com.redapps.phonepolice.view;

import com.redapps.phonepolice.presenter.IAlarmPresenter;


public class AlarmView implements IAlarmView {
    final IAlarmPresenter presenter;

    public AlarmView(IAlarmPresenter iAlarmPresenter) {
        this.presenter = iAlarmPresenter;
    }

    @Override
    public void onChangeToneClick() {
        this.presenter.onChangeToneClick();
    }

    @Override
    public void onChangePinClick() {
        this.presenter.onChangePinClick();
    }

    @Override
    public void onVibrateSwitch(Boolean bool) {
        this.presenter.onVibrateSwitch(bool);
    }

    @Override
    public void onFlashLightSwitch(Boolean bool) {
        this.presenter.onFlashLightSwitch(bool);
    }
}
