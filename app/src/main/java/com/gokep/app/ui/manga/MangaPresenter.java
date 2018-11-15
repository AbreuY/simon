package com.gokep.app.ui.manga;

import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;

public class MangaPresenter<V extends MangaView> extends BasePresenter<V> implements MangaMvpPresenter<V>{

    @Inject
    public MangaPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
