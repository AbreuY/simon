/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.prefs;

public interface PreferencesHelper {

    boolean isFirstTimeLaunch();
    void setFirstTimeLaunch(boolean isFirstTime);


    boolean isMatureHiden();
    void setMatureHide(boolean hide);
    boolean isAppLock();
    void setAppLock(boolean lock);
    void setPattern(String pattern);
    String getPattern();

    void setInterstitialId(String interstitialId);
    String getInterstitialId();

    void setBannerId(String bannerId);
    String getBannerId();

    boolean isMaintenance();
    boolean isTimeToForceUpdate();
    String getBaseUrl();
    String getParamTv();
    String getParamPorn();
    String getParamMovies();
    String getParamIndo();
    String getParamBarat();
    String getParamHentai();
    String getParamKorea();
    String getParamAdult();
    String getParamSearch();


    void setForceUpdate(boolean forceUpdate);
    void setMaintenance(boolean ismaintenance);
    void setBaseUrl(String BASE_URL);
    void setParamTv(String tv);
    void setParamPorn(String porn);
    void setParamMovies(String movies);
    void setParamIndo(String indo);
    void setParamBarat(String barat);
    void setParamHentai(String hentai);
    void setParamKorea(String korea);
    void setParamAdult(String adult);
    void setParamSearch(String search);
}
