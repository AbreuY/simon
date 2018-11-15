package com.gokep.app.ui.main;

import com.gokep.app.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainView> extends MvpPresenter<V> {

    boolean isMatureHidden();
    boolean getIsMaintenance();
    boolean getIsTimeToForceUpdate();
}
