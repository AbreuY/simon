package com.gokep.app.ui.search;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface SearchView extends MvpView {
    void showContent(List<MovieResponse> contents);
}
