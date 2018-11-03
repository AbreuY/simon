package net.simontok.app.ui.main.home;

import net.simontok.app.data.network.model.response.MovieResponse;
import net.simontok.app.ui.base.MvpView;

import java.util.List;

public interface HomeView extends MvpView {
    void showContent(List<MovieResponse> contents);
}
