package com.gokep.app.ui.main.movie;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface MovieView extends MvpView {
    void showLiveContent(List<MovieResponse> movies);
}
