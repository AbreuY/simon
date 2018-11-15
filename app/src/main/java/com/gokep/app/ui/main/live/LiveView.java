package com.gokep.app.ui.main.live;

import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface LiveView extends MvpView {

    void showLiveContent(List<LiveResponse> contents);
}
