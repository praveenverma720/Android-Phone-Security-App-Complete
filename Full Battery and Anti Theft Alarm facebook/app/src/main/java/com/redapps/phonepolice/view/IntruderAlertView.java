package com.redapps.phonepolice.view;

import com.redapps.phonepolice.presenter.IIntruderAlertPresenter;


public class IntruderAlertView implements IIntruderAlertView {
    final IIntruderAlertPresenter presenter;

    public IntruderAlertView(IIntruderAlertPresenter iIntruderAlertPresenter) {
        this.presenter = iIntruderAlertPresenter;
    }

    @Override
    public void onActiveIntruderSwitchClick(Boolean bool) {
        this.presenter.onActiveIntruderSwitchClick(bool);
    }

    @Override
    public void onRingAlarmSwitchClick(Boolean bool) {
        this.presenter.onRingAlarmSwitchClick(bool);
    }

    @Override
    public void onAt1Click() {
        this.presenter.onAt1Click();
    }

    @Override
    public void onAt2Click() {
        this.presenter.onAt2Click();
    }

    @Override
    public void onAt3Click() {
        this.presenter.onAt3Click();
    }

    @Override
    public void onAlarmSettingBtn() {
        this.presenter.onAlarmSettingBtn();
    }

    @Override
    public void onShowIntruderAlertBtn() {
        this.presenter.onShowIntruderAlertBtn();
    }
}
