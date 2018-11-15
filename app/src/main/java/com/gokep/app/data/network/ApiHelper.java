/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.network;

import com.gokep.app.data.network.model.EndPoint;
import com.gokep.app.data.network.model.response.*;
import com.gokep.app.data.network.model.EndPoint;

import io.reactivex.Single;

import java.util.List;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<EndPoint> getEndPoint();
    Single<List<LiveResponse>> getLiveStream(String COMPLETE_URL, int index);
    Single<List<MovieResponse>> getMovies(String COMPLETE_URL, int index);
    Single<List<MovieResponse>> getContents(String COMPLETE_URL, int index);
    Single<List<MovieResponse>> postSearch(String COMPLETE_URL, String index, int p);
    Single<List<Category>> getCategories();
    Single<StoryResponse> getStories();
    Single<List<Dummy>> getDummyContents();
}
