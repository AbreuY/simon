package com.gokep.app.ui.setting.lock;

import com.gokep.app.data.DataManager;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LockPresenter<V extends LockView> extends BasePresenter<V> implements LockMvpPresenter<V> {

    @Inject
    public LockPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
