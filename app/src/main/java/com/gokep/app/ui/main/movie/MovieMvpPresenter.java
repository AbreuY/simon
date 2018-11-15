package com.gokep.app.ui.main.movie;

import com.gokep.app.ui.base.MvpPresenter;

public interface MovieMvpPresenter<V extends MovieView> extends MvpPresenter<V> {

    void fetchMovies(int index);
}
