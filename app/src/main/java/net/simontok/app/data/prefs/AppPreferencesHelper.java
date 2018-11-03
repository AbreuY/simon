/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.PreferenceInfo;

import net.simontok.app.di.ApplicationContext;
import net.simontok.app.di.PreferenceInfo;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String KEY_IP = "KEY_IP";
    private static final String KEY_REGION = "KEY_REGION";
    private static final String KEY_LINK = "KEY_LINK";

    private static final String KEY_BASE_URL = "KEY_BASE_URL";
    private static final String KEY_PARAM_TV = "KEY_PARAM_TV";
    private static final String KEY_PARAM_PORN = "KEY_PARAM_PORN";
    private static final String KEY_PARAM_MOVIE = "KEY_PARAM_MOVIE";
    private static final String KEY_PARAM_INDO = "KEY_PARAM_INDO";
    private static final String KEY_PARAM_BARAT = "KEY_PARAM_BARAT";
    private static final String KEY_PARAM_HENTAI = "KEY_PARAM_HENTAI";
    private static final String KEY_PARAM_KOREA = "KEY_PARAM_KOREA";
    private static final String KEY_PARAM_ADULT = "KEY_PARAM_ADULT";
    private static final String KEY_PARAM_SEARCH = "KEY_PARAM_SEARCH";


    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isFirstTimeLaunch() {
        return mPrefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    @Override
    public void setFirstTimeLaunch(boolean isFirstTime) {
        mPrefs.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    @Override
    public void setIpAddress(String ip) {
        mPrefs.edit().putString(KEY_IP, ip).apply();
    }

    @Override
    public String getIpAddress() {
        return mPrefs.getString(KEY_IP, "72.229.28.185");
    }

    @Override
    public void setRegion(String region) {
        mPrefs.edit().putString(KEY_REGION, region).apply();
    }

    @Override
    public String getRegion() {
        return mPrefs.getString(KEY_REGION, "us");
    }

    @Override
    public String getBaseUrl() {
        return mPrefs.getString(KEY_BASE_URL, "BASE_URL");
    }

    @Override
    public String getParamTv() {
        return mPrefs.getString(KEY_PARAM_TV, "");
    }

    @Override
    public String getParamPorn() {
        return mPrefs.getString(KEY_PARAM_PORN, "");
    }

    @Override
    public String getParamMovies() {
        return mPrefs.getString(KEY_PARAM_MOVIE, "");
    }

    @Override
    public String getParamIndo() {
        return mPrefs.getString(KEY_PARAM_INDO, "");
    }

    @Override
    public String getParamBarat() {
        return mPrefs.getString(KEY_PARAM_BARAT, "");
    }

    @Override
    public String getParamHentai() {
        return mPrefs.getString(KEY_PARAM_HENTAI, "");
    }

    @Override
    public String getParamKorea() {
        return mPrefs.getString(KEY_PARAM_KOREA, "");
    }

    @Override
    public String getParamAdult() {
        return mPrefs.getString(KEY_PARAM_ADULT, "");
    }

    @Override
    public String getParamSearch() {
        return mPrefs.getString(KEY_PARAM_SEARCH, "");
    }

    @Override
    public void setBaseUrl(String BASE_URL) {
        mPrefs.edit().putString(KEY_BASE_URL, BASE_URL).apply();
    }

    @Override
    public void setParamTv(String tv) {
        mPrefs.edit().putString(KEY_PARAM_TV, tv).apply();
    }

    @Override
    public void setParamPorn(String porn) {
        mPrefs.edit().putString(KEY_PARAM_PORN, porn).apply();
    }

    @Override
    public void setParamMovies(String movies) {
        mPrefs.edit().putString(KEY_PARAM_MOVIE, movies).apply();
    }

    @Override
    public void setParamIndo(String indo) {
        mPrefs.edit().putString(KEY_PARAM_INDO, indo).apply();
    }

    @Override
    public void setParamBarat(String barat) {
        mPrefs.edit().putString(KEY_PARAM_BARAT, barat).apply();
    }

    @Override
    public void setParamHentai(String hentai) {
        mPrefs.edit().putString(KEY_PARAM_HENTAI, hentai).apply();
    }

    @Override
    public void setParamKorea(String korea) {
        mPrefs.edit().putString(KEY_PARAM_KOREA, korea).apply();
    }

    @Override
    public void setParamAdult(String adult) {
        mPrefs.edit().putString(KEY_PARAM_ADULT, adult).apply();
    }

    @Override
    public void setParamSearch(String search) {
        mPrefs.edit().putString(KEY_PARAM_SEARCH, search).apply();
    }
}
