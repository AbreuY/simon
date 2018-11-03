package net.simontok.app.ui.main.movie;

import net.simontok.app.ui.base.MvpPresenter;

public interface MovieMvpPresenter<V extends MovieView> extends MvpPresenter<V> {

    void fetchMovies(int index);
}
