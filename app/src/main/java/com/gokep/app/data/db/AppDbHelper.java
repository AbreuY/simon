/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gokep.app.data.db.model.DaoMaster;
import com.gokep.app.data.db.model.DaoSession;
import com.gokep.app.data.db.model.MovieDb;
import io.reactivex.Observable;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppLogger;

import com.gokep.app.data.db.model.DaoMaster;
import com.gokep.app.data.db.model.DaoSession;

import java.util.List;
import java.util.concurrent.Callable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public void addToWatchLater(MovieDb movie) {
        mDaoSession.getMovieDbDao().insert(movie);
    }

    @Override
    public List<MovieDb> getWatchLaterList() {
        return mDaoSession.getMovieDbDao().loadAll();
    }

    @Override
    public Observable<List<MovieDb>> getWL() {
        return Observable.fromCallable(new Callable<List<MovieDb>>() {
            @Override
            public List<MovieDb> call() throws Exception {
                return mDaoSession.getMovieDbDao().loadAll();
            }
        });
    }
}
