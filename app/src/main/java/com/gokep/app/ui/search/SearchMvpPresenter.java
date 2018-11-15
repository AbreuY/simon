package com.gokep.app.ui.search;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpPresenter;

public interface SearchMvpPresenter<V extends SearchView> extends MvpPresenter<V> {
    void search(String s, int p);
    void addToWatchLater(MovieResponse movieResponse);
}
