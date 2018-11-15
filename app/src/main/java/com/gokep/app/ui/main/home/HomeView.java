package com.gokep.app.ui.main.home;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface HomeView extends MvpView {
    void showContent(List<MovieResponse> contents);

    void showFirstContent(List<MovieResponse> contents);
}
