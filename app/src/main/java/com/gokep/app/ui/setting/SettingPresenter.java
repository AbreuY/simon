package com.gokep.app.ui.setting;

import com.gokep.app.data.DataManager;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class SettingPresenter<V extends SettingView> extends BasePresenter<V> implements SettingMvpPresenter<V> {

    @Inject
    public SettingPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void setMatureHidden(boolean b) {
        getDataManager().setMatureHide(b);
    }

    @Override
    public boolean getMatureHidden() {
        return getDataManager().isMatureHiden();
    }

    @Override
    public void setAppLock(boolean isChecked) {
        getDataManager().setAppLock(isChecked);
    }

    @Override
    public boolean getAppLock() {
        return getDataManager().isAppLock();
    }

    @Override
    public String getBannerId() {
        return getDataManager().getBannerId();
    }
}
