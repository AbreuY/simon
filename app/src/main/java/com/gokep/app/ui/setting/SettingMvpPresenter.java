package com.gokep.app.ui.setting;

import com.gokep.app.ui.base.MvpPresenter;

public interface SettingMvpPresenter<V extends SettingView> extends MvpPresenter<V> {
    void setMatureHidden(boolean b);
    boolean getMatureHidden();

    void setAppLock(boolean isChecked);
    boolean getAppLock();

    String  getBannerId();

}
