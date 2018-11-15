package com.gokep.app.ui.main.live;

import com.gokep.app.ui.base.MvpPresenter;

public interface LiveMvpPresenter<V extends LiveView> extends MvpPresenter<V> {

    void fetchLiveStream(int index);
}
