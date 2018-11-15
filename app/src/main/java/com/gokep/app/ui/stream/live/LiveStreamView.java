package com.gokep.app.ui.stream.live;

import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface LiveStreamView extends MvpView {
    void showContent(List<LiveResponse> contents);
}
