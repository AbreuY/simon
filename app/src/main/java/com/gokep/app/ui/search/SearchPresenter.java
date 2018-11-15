package com.gokep.app.ui.search;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class SearchPresenter<V extends SearchView> extends BasePresenter<V> implements SearchMvpPresenter<V> {

    @Inject
    public SearchPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void search(String s, int p) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamSearch();
        getCompositeDisposable().add(getDataManager().postSearch(COMPLETE_URL, s, p)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(contents -> {
                    if (contents!=null) {
                        getMvpView().showContent(contents);
                    }

                    getMvpView().hideLoading();
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    AppLogger.e(throwable.getMessage());
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void addToWatchLater(MovieResponse movie) {
        MovieDb movieDb = new MovieDb();
        movieDb.setDatat(movie.getDatat());
        movieDb.setDurasi(movie.getDurasi());
        movieDb.setImgs(movie.getImgs());
        movieDb.setTitle(movie.getTitle());
        movieDb.setType(movie.getType());
        movieDb.setUr(movie.getUr());
        movieDb.setViews(movie.getViews());

        getDataManager().addToWatchLater(movieDb);
    }
}
