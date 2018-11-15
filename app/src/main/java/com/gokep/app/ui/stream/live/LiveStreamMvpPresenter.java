package com.gokep.app.ui.stream.live;

import com.gokep.app.ui.base.MvpPresenter;

public interface LiveStreamMvpPresenter<V extends LiveStreamView> extends MvpPresenter<V> {

    void fetchSuggestion(int index);

}
