package com.gokep.app.ui.main.category.genre;

import com.androidnetworking.error.ANError;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class GenrePresenter<V extends GenreView> extends BasePresenter<V> implements GenreMvpPresenter<V> {

    @Inject
    public GenrePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchContents(String index, int p) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamSearch();
        getCompositeDisposable().add(getDataManager().postSearch(COMPLETE_URL, index, p)
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
}

