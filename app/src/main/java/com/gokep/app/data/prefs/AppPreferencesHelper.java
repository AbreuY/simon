/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gokep.app.di.ApplicationContext;
import com.gokep.app.di.PreferenceInfo;

import com.gokep.app.di.ApplicationContext;
import com.gokep.app.di.PreferenceInfo;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
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
    private static final String KEY_MATURE = "KEY_MATURE";
    private static final String KEY_LOCK = "KEY_LOCK";
    private static final String KEY_PATTERN = "KEY_PATTERN";
    private static final String KEY_MAINTENANCE = "KEY_MAINTENANCE";
    private static final String KEY_FORCE_UPDATE = "KEY_FORCE_UPDATE";

    private static final String KEY_INTERSTITIAL = "KEY_INTERSTITIAL";
    private static final String KEY_BANNER = "KEY_BANNER";


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
    public boolean isAppLock() {
        return mPrefs.getBoolean(KEY_LOCK, false);
    }

    @Override
    public void setAppLock(boolean lock) {
        mPrefs.edit().putBoolean(KEY_LOCK, lock).apply();
    }

    @Override
    public boolean isMatureHiden() {
        return mPrefs.getBoolean(KEY_MATURE, false);
    }

    @Override
    public void setMatureHide(boolean hide) {
        mPrefs.edit().putBoolean(KEY_MATURE, hide).apply();
    }

    @Override
    public void setPattern(String pattern) {
        mPrefs.edit().putString(KEY_PATTERN, pattern).apply();
    }

    @Override
    public String getPattern() {
        return mPrefs.getString(KEY_PATTERN, "");
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

    @Override
    public boolean isMaintenance() {
        return mPrefs.getBoolean(KEY_MAINTENANCE, false);
    }

    @Override
    public void setMaintenance(boolean ismaintenance) {
        mPrefs.edit().putBoolean(KEY_MAINTENANCE, ismaintenance).apply();
    }

    @Override
    public boolean isTimeToForceUpdate() {
        return mPrefs.getBoolean(KEY_FORCE_UPDATE, false);
    }

    @Override
    public void setForceUpdate(boolean forceUpdate) {
        mPrefs.edit().putBoolean(KEY_FORCE_UPDATE, forceUpdate).apply();
    }

    @Override
    public void setInterstitialId(String interstitialId) {
        mPrefs.edit().putString(KEY_INTERSTITIAL, interstitialId).apply();
    }

    @Override
    public String getInterstitialId() {
        return mPrefs.getString(KEY_INTERSTITIAL, "ca-app-pub-3940256099942544/1033173712");
    }

    @Override
    public void setBannerId(String bannerId) {
        mPrefs.edit().putString(KEY_BANNER, bannerId).apply();
    }

    @Override
    public String getBannerId() {
        return mPrefs.getString(KEY_BANNER, "ca-app-pub-3940256099942544/6300978111");
    }
}
