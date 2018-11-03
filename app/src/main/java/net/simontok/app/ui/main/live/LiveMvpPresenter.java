package net.simontok.app.ui.main.live;

import net.simontok.app.ui.base.MvpPresenter;

public interface LiveMvpPresenter<V extends LiveView> extends MvpPresenter<V> {

    void fetchLiveStream();
}
