/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.network;

import com.gokep.app.BuildConfig;
import com.gokep.app.data.network.model.EndPoint;
import com.gokep.app.data.network.model.response.*;
import com.gokep.app.BuildConfig;
import com.gokep.app.data.network.model.EndPoint;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

import java.util.List;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<EndPoint> getEndPoint() {
        return Rx2AndroidNetworking.get(BuildConfig.BASE_URL + "/gokep.json")
                .doNotCacheResponse()
                .build()
                .getObjectSingle(EndPoint.class);
    }

    @Override
    public Single<List<LiveResponse>> getLiveStream(String COMPLETE_URL, int index) {
        return Rx2AndroidNetworking.get(COMPLETE_URL + index)
                .build()
                .getObjectListSingle(LiveResponse.class);
    }

    @Override
    public Single<List<MovieResponse>> getMovies(String COMPLETE_URL, int index) {
        return Rx2AndroidNetworking.get(COMPLETE_URL + index)
                .build()
                .getObjectListSingle(MovieResponse.class);
    }

    @Override
    public Single<List<MovieResponse>> getContents(String COMPLETE_URL, int index) {
        return Rx2AndroidNetworking.get(COMPLETE_URL + index)
                .build()
                .getObjectListSingle(MovieResponse.class);
    }

    @Override
    public Single<List<MovieResponse>> postSearch(String COMPLETE_URL, String index, int p) {
        return Rx2AndroidNetworking.post(COMPLETE_URL + index + "&p="+p)
                .build()
                .getObjectListSingle(MovieResponse.class);
    }

    @Override
    public Single<List<Category>> getCategories() {
        return Rx2AndroidNetworking.get(BuildConfig.BASE_URL + "/gocat.json")
                .build()
                .getObjectListSingle(Category.class);
    }

    @Override
    public Single<List<Dummy>> getDummyContents() {
        return Rx2AndroidNetworking.get(BuildConfig.BASE_URL + "/gostealth.json")
                .build()
                .getObjectListSingle(Dummy.class);
    }

    @Override
    public Single<StoryResponse> getStories() {
        return Rx2AndroidNetworking.get(BuildConfig.BASE_URL + "/stories.json")
                .build()
                .getObjectSingle(StoryResponse.class);
    }
}
