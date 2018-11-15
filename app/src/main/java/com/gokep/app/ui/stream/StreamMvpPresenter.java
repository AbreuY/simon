package com.gokep.app.ui.stream;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpPresenter;

public interface StreamMvpPresenter<V extends StreamView> extends MvpPresenter<V> {

    void fetchSuggestion(int index);

    void addToWatchLater(MovieResponse movie);

    String getIntersId();
}
