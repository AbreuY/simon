package com.gokep.app.ui.main.live;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.DataManager;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LivePresenter<V extends LiveView> extends BasePresenter<V> implements LiveMvpPresenter<V> {

    @Inject
    public LivePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchLiveStream(int index) {

        getMvpView().showLoading();

        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamTv();
        getCompositeDisposable().add(getDataManager().getLiveStream(COMPLETE_URL, index)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(live -> {
                    if (live!=null) {
                        getMvpView().showLiveContent(live);
                    }

                    getMvpView().hideLoading();
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    AppLogger.e(throwable.getMessage());
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }
}
