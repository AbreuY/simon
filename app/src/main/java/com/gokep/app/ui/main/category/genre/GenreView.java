package com.gokep.app.ui.main.category.genre;

import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface GenreView extends MvpView {
    void showContent(List<MovieResponse> contents);
}
