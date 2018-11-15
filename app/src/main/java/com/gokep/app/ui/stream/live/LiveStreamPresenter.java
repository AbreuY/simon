package com.gokep.app.ui.stream.live;

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

public class LiveStreamPresenter<V extends LiveStreamView> extends BasePresenter<V> implements LiveStreamMvpPresenter<V> {

    @Inject
    public LiveStreamPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchSuggestion(int index) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamTv();
        getCompositeDisposable().add(getDataManager().getLiveStream(COMPLETE_URL, index)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(contents -> {
                    if (contents!=null) {
                        getMvpView().showContent(contents);
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
