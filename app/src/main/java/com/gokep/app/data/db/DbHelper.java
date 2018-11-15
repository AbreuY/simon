/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.data.db;

import com.gokep.app.data.db.model.MovieDb;
import io.reactivex.Observable;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.model.response.MovieResponse;

import java.util.List;

public interface DbHelper {

    void addToWatchLater(MovieDb movie);

    List<MovieDb> getWatchLaterList();

    Observable<List<MovieDb>> getWL();
}
