package com.gokep.app.ui.stealth;

import com.gokep.app.ui.base.MvpPresenter;

public interface StealthMvpPresenter<V extends StealthView> extends MvpPresenter<V> {

    void fetchDummyContent();
}
