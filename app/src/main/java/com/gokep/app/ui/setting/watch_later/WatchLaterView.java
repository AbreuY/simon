package com.gokep.app.ui.setting.watch_later;

import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.ui.base.MvpView;

import java.util.List;

public interface WatchLaterView extends MvpView {
    void showWatchLaterList(List<MovieDb> movieDbs);
}
