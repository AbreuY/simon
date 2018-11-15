/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gokep.app.data.db.DbHelper;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.ApiHeader;
import com.gokep.app.data.network.ApiHelper;
import com.gokep.app.data.network.model.EndPoint;
import com.gokep.app.data.network.model.response.*;
import com.gokep.app.data.prefs.PreferencesHelper;
import com.gokep.app.di.ApplicationContext;
import io.reactivex.Observable;
import com.gokep.app.data.db.DbHelper;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.ApiHeader;
import com.gokep.app.data.network.ApiHelper;
import com.gokep.app.data.network.model.EndPoint;
import com.gokep.app.data.prefs.PreferencesHelper;
import com.gokep.app.di.ApplicationContext;

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
    public void addToWatchLater(MovieDb movie) {
        mDbHelper.addToWatchLater(movie);
    }

    @Override
    public Observable<List<MovieDb>> getWL() {
        return mDbHelper.getWL();
    }

    @Override
    public List<MovieDb> getWatchLaterList() {
        return mDbHelper.getWatchLaterList();
    }

    @Override
    public boolean isMatureHiden() {
        return mPreferencesHelper.isMatureHiden();
    }

    @Override
    public void setMatureHide(boolean hide) {
        mPreferencesHelper.setMatureHide(hide);
    }

    @Override
    public boolean isAppLock() {
        return mPreferencesHelper.isAppLock();
    }

    @Override
    public void setAppLock(boolean lock) {
        mPreferencesHelper.setAppLock(lock);
    }

    @Override
    public void setPattern(String pattern) {
        mPreferencesHelper.setPattern(pattern);
    }

    @Override
    public String getPattern() {
        return mPreferencesHelper.getPattern();
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

    @Override
    public Single<List<MovieResponse>> postSearch(String COMPLETE_URL, String index, int p) {
        return mApiHelper.postSearch(COMPLETE_URL, index, p);
    }

    @Override
    public Single<List<Category>> getCategories() {
        return mApiHelper.getCategories();
    }

    @Override
    public boolean isMaintenance() {
        return mPreferencesHelper.isMaintenance();
    }

    @Override
    public void setMaintenance(boolean ismaintenance) {
        mPreferencesHelper.setMaintenance(ismaintenance);
    }

    @Override
    public boolean isTimeToForceUpdate() {
        return mPreferencesHelper.isTimeToForceUpdate();
    }

    @Override
    public void setForceUpdate(boolean forceUpdate) {
        mPreferencesHelper.setForceUpdate(forceUpdate);
    }

    @Override
    public Single<List<Dummy>> getDummyContents() {
        return mApiHelper.getDummyContents();
    }

    @Override
    public void setInterstitialId(String interstitialId) {
        mPreferencesHelper.setInterstitialId(interstitialId);
    }

    @Override
    public String getInterstitialId() {
        return mPreferencesHelper.getInterstitialId();
    }

    @Override
    public void setBannerId(String bannerId) {
        mPreferencesHelper.setBannerId(bannerId);
    }

    @Override
    public String getBannerId() {
        return mPreferencesHelper.getBannerId();
    }

    @Override
    public Single<StoryResponse> getStories() {
        return mApiHelper.getStories();
    }
}
