/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.data.network;

import net.simontok.app.BuildConfig;
import net.simontok.app.data.network.model.EndPoint;
import net.simontok.app.data.network.model.response.IpDetailResponse;
import net.simontok.app.data.network.model.response.IpResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.data.network.model.response.MovieResponse;

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
    public Single<IpDetailResponse> getIpDetail(String ip) {
        return Rx2AndroidNetworking.get("http://api.ipstack.com/"+ip)
                .addQueryParameter("access_key", "ec65cd82129e340fec85b986e4849143")
                .addQueryParameter("format", String.valueOf(1))
                .build()
                .getObjectSingle(IpDetailResponse.class);
    }

    @Override
    public Single<IpResponse> getIp() {
        return Rx2AndroidNetworking.get("https://api.ipify.org?format=json")
                .build()
                .getObjectSingle(IpResponse.class);
    }

    @Override
    public Single<EndPoint> getEndPoint() {
        return Rx2AndroidNetworking.get(BuildConfig.BASE_URL + "/simontok.json")
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
}
