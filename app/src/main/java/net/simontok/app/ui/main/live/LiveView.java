package net.simontok.app.ui.main.live;

import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.ui.base.MvpView;

import java.util.List;

public interface LiveView extends MvpView {

    void showLiveContent(List<LiveResponse> contents);
}
