/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.prefs;

public interface PreferencesHelper {

    boolean isFirstTimeLaunch();
    void setFirstTimeLaunch(boolean isFirstTime);

    void setIpAddress(String ip);
    String getIpAddress();
    void setRegion(String region);
    String getRegion();
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
