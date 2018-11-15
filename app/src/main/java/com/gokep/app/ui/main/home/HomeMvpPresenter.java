package com.gokep.app.ui.main.home;

import com.gokep.app.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeView> extends MvpPresenter<V> {

    void fetchContents(int index);
    void fetchFirstContents(int index);
    String getIntersId();
}
