/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.ui.splash;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.ui.base.BasePresenter;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import net.simontok.app.data.DataManager;
import net.simontok.app.data.db.model.GeoIp;
import net.simontok.app.data.network.model.response.IpDetailResponse;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.AppLogger;
import net.simontok.app.utils.NetworkUtils;
import net.simontok.app.utils.rx.SchedulerProvider;

public class SplashPresenter<V extends SplashView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getIpAddress() {
        getCompositeDisposable().add(getDataManager().getIp()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(ip -> {
                    if (ip!=null) {
                        getDataManager().setIpAddress(ip.getIp());
                    }
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    AppLogger.e("GET ip "+ throwable.getMessage());
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
    public void checkIpDetail() {
        getCompositeDisposable().add(getDataManager().getIpDetail(getDataManager().getIpAddress())
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(ipDetailResponse -> {
                    if (ipDetailResponse!=null) {
                        GeoIp geoIp = new GeoIp(1L, ipDetailResponse.getContinentCode(), ipDetailResponse.getContinentName(),
                                ipDetailResponse.getCountryCode(), ipDetailResponse.getCountryName(),
                                ipDetailResponse.getRegionCode(), ipDetailResponse.getRegionName(),
                                ipDetailResponse.getCity(), ipDetailResponse.getLocation().getCapital(),
                                ipDetailResponse.getLatitude(), ipDetailResponse.getLongitude(),
                                ipDetailResponse.getLocation().getLanguages().get(0).getCode(), ipDetailResponse.getLocation().getLanguages().get(0).getName());
                        AppLogger.e("GEOIP "+new Gson().toJson(geoIp));
                        getDataManager().saveGeoIp(geoIp);
                        getDataManager().setRegion(ipDetailResponse.getCountryCode());
                    }
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    AppLogger.e("CHECKING ip "+ throwable.getMessage());
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
    public void fetch() {
        getCompositeDisposable().add(getDataManager().getEndPoint()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(endPoint -> {
                    if (endPoint!=null) {
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
