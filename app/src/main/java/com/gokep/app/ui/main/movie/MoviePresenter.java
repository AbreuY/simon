package com.gokep.app.ui.main.movie;

import com.androidnetworking.error.ANError;

import com.gokep.app.data.DataManager;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MoviePresenter<V extends MovieView> extends BasePresenter<V> implements MovieMvpPresenter<V> {

    @Inject
    public MoviePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchMovies(int index) {
        getMvpView().showLoading();

        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamMovies();
        getCompositeDisposable().add(getDataManager().getMovies(COMPLETE_URL, index)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(movies -> {
                    if (movies!=null) {
                        getMvpView().showLiveContent(movies);
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
}
