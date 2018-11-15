/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.ui.splash;

import javax.inject.Inject;

import com.gokep.app.data.DataManager;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.NetworkUtils;
import com.gokep.app.utils.rx.SchedulerProvider;

public class SplashPresenter<V extends SplashView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetch() {
        getCompositeDisposable().add(getDataManager().getEndPoint()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(endPoint -> {
                    if (endPoint!=null) {
                        getDataManager().setMaintenance(endPoint.isMaintenance());
                        getDataManager().setForceUpdate(endPoint.isForceUpdate());
                        getDataManager().setBaseUrl(endPoint.getBaseUrl());
                        getDataManager().setParamTv(endPoint.getParamTv());
                        getDataManager().setParamMovies(endPoint.getParamMovies());
                        getDataManager().setParamPorn(endPoint.getParamPorn());
                        getDataManager().setParamIndo(endPoint.getParamIndo());
                        getDataManager().setParamBarat(endPoint.getParamBarat());
                        getDataManager().setParamHentai(endPoint.getParamHentai());
                        getDataManager().setParamKorea(endPoint.getParamKorea());
                        getDataManager().setParamAdult(endPoint.getParamAdult());
                        getDataManager().setParamSearch(endPoint.getParamSearch());
                        getDataManager().setMaintenance(endPoint.isMaintenance());
                        getDataManager().setBannerId(endPoint.getParamBanner());
                        getDataManager().setInterstitialId(endPoint.getParamInterstitial());
                        if (endPoint.isMaintenance()) {
                            getMvpView().showDummyContent();
                        } else {
                            getMvpView().gotoRealContent();
                        }
                    }
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
