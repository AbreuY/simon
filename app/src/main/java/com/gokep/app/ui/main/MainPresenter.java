package com.gokep.app.ui.main;

import com.gokep.app.data.DataManager;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public boolean isMatureHidden() {
        return getDataManager().isMatureHiden();
    }

    @Override
    public boolean getIsMaintenance() {
        AppLogger.e("LOGS =>"+getDataManager().isMaintenance());
        return getDataManager().isMaintenance();
    }

    @Override
    public boolean getIsTimeToForceUpdate() {
        return getDataManager().isTimeToForceUpdate();
    }
}
