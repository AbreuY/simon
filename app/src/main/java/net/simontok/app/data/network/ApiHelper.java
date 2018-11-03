/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.network;

import net.simontok.app.data.network.model.EndPoint;
import net.simontok.app.data.network.model.response.IpDetailResponse;
import net.simontok.app.data.network.model.response.IpResponse;

import io.reactivex.Single;
import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.data.network.model.response.MovieResponse;

import java.util.List;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<IpDetailResponse> getIpDetail(String ip);
    Single<IpResponse> getIp();
    Single<EndPoint> getEndPoint();
    Single<List<LiveResponse>> getLiveStream(String COMPLETE_URL, int index);
    Single<List<MovieResponse>> getMovies(String COMPLETE_URL, int index);
    Single<List<MovieResponse>> getContents(String COMPLETE_URL, int index);

}
