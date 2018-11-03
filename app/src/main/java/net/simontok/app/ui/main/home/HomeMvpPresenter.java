package net.simontok.app.ui.main.home;

import net.simontok.app.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeView> extends MvpPresenter<V> {

    void fetchContents(int index);
}
