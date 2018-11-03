package net.simontok.app.ui.main.movie;

import net.simontok.app.data.network.model.response.MovieResponse;
import net.simontok.app.ui.base.MvpView;

import java.util.List;

public interface MovieView extends MvpView {
    void showLiveContent(List<MovieResponse> movies);
}
