package com.gokep.app.ui.stream;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface StreamView extends MvpView {
    void showContent(List<MovieResponse> contents);
}
