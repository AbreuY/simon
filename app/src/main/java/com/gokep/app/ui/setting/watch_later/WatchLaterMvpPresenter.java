package com.gokep.app.ui.setting.watch_later;

import com.gokep.app.ui.base.MvpPresenter;

public interface WatchLaterMvpPresenter<V extends WatchLaterView> extends MvpPresenter<V> {

    void getWL();
}
