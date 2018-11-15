package com.gokep.app.ui.main.home;

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

public class HomePresenter<V extends HomeView> extends BasePresenter<V> implements HomeMvpPresenter<V> {

    @Inject
    public HomePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchContents(int index) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamPorn();
        getCompositeDisposable().add(getDataManager().getContents(COMPLETE_URL, index)
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

    @Override
    public void fetchFirstContents(int index) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamPorn();
        getCompositeDisposable().add(getDataManager().getContents(COMPLETE_URL, index)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(contents -> {
                    if (contents!=null) {
                        getMvpView().showFirstContent(contents);
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

    @Override
    public String getIntersId() {
        return getDataManager().getInterstitialId();
    }
}
