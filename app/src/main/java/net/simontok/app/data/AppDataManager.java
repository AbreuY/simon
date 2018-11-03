/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.simontok.app.data.db.DbHelper;
import net.simontok.app.data.db.model.GeoIp;
import net.simontok.app.data.network.ApiHeader;
import net.simontok.app.data.network.ApiHelper;
import net.simontok.app.data.network.model.EndPoint;
import net.simontok.app.data.network.model.response.IpDetailResponse;
import net.simontok.app.data.network.model.response.IpResponse;
import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.data.network.model.response.MovieResponse;
import net.simontok.app.data.prefs.PreferencesHelper;
import net.simontok.app.di.ApplicationContext;

import io.reactivex.Single;

import java.util.List;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = AppDataManager.class.getSimpleName();

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public boolean isFirstTimeLaunch() {
        return mPreferencesHelper.isFirstTimeLaunch();
    }

    @Override
    public void setFirstTimeLaunch(boolean isFirstTime) {
        mPreferencesHelper.setFirstTimeLaunch(isFirstTime);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }


    @Override
    public void setIpAddress(String ip) {
        mPreferencesHelper.setIpAddress(ip);
    }

    @Override
    public String getIpAddress() {
        return mPreferencesHelper.getIpAddress();
    }

    @Override
    public Single<IpDetailResponse> getIpDetail(String ip) {
        return mApiHelper.getIpDetail(ip);
    }

    @Override
    public void saveGeoIp(GeoIp geoIp) {
        mDbHelper.saveGeoIp(geoIp);
    }

    @Override
    public void setRegion(String region) {
        mPreferencesHelper.setRegion(region);
    }

    @Override
    public String getRegion() {
        return mPreferencesHelper.getRegion();
    }

    @Override
    public Single<IpResponse> getIp() {
        return mApiHelper.getIp();
    }

    @Override
    public GeoIp getGeoIp() {
        return mDbHelper.getGeoIp();
    }

    @Override
    public Single<EndPoint> getEndPoint() {
        return mApiHelper.getEndPoint();
    }

    @Override
    public String getBaseUrl() {
        return mPreferencesHelper.getBaseUrl();
    }

    @Override
    public String getParamTv() {
        return mPreferencesHelper.getParamTv();
    }

    @Override
    public String getParamPorn() {
        return mPreferencesHelper.getParamPorn();
    }

    @Override
    public String getParamMovies() {
        return mPreferencesHelper.getParamMovies();
    }

    @Override
    public String getParamIndo() {
        return mPreferencesHelper.getParamIndo();
    }

    @Override
    public String getParamBarat() {
        return mPreferencesHelper.getParamBarat();
    }

    @Override
    public String getParamHentai() {
        return mPreferencesHelper.getParamHentai();
    }

    @Override
    public String getParamKorea() {
        return mPreferencesHelper.getParamKorea();
    }

    @Override
    public String getParamAdult() {
        return mPreferencesHelper.getParamAdult();
    }

    @Override
    public String getParamSearch() {
        return mPreferencesHelper.getParamSearch();
    }

    @Override
    public void setBaseUrl(String BASE_URL) {
        mPreferencesHelper.setBaseUrl(BASE_URL);
    }

    @Override
    public void setParamTv(String tv) {
        mPreferencesHelper.setParamTv(tv);
    }

    @Override
    public void setParamPorn(String porn) {
        mPreferencesHelper.setParamPorn(porn);
    }

    @Override
    public void setParamMovies(String movies) {
        mPreferencesHelper.setParamMovies(movies);
    }

    @Override
    public void setParamIndo(String indo) {
        mPreferencesHelper.setParamIndo(indo);
    }

    @Override
    public void setParamBarat(String barat) {
        mPreferencesHelper.setParamBarat(barat);
    }

    @Override
    public void setParamHentai(String hentai) {
        mPreferencesHelper.setParamHentai(hentai);
    }

    @Override
    public void setParamKorea(String korea) {
        mPreferencesHelper.setParamKorea(korea);
    }

    @Override
    public void setParamAdult(String adult) {
        mPreferencesHelper.setParamAdult(adult);
    }

    @Override
    public void setParamSearch(String search) {
        mPreferencesHelper.setParamSearch(search);
    }

    @Override
    public Single<List<LiveResponse>> getLiveStream(String COMPLETE_URL, int index) {
        return mApiHelper.getLiveStream(COMPLETE_URL, index);
    }

    @Override
    public Single<List<MovieResponse>> getMovies(String COMPLETE_URL, int index) {
        return mApiHelper.getMovies(COMPLETE_URL, index);
    }

    @Override
    public Single<List<MovieResponse>> getContents(String COMPLETE_URL, int index) {
        return mApiHelper.getContents(COMPLETE_URL, index);
    }
}
