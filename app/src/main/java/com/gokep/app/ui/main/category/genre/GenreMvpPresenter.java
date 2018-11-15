package com.gokep.app.ui.main.category.genre;

import com.gokep.app.ui.base.MvpPresenter;

public interface GenreMvpPresenter<V extends GenreView> extends MvpPresenter<V> {

    void fetchContents(String index, int p);

}
