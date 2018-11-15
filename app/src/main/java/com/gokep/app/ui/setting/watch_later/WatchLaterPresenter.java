package com.gokep.app.ui.setting.watch_later;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.db.model.MovieDb;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import java.util.List;

public class WatchLaterPresenter<V extends WatchLaterView> extends BasePresenter<V> implements WatchLaterMvpPresenter<V> {

    @Inject
    public WatchLaterPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getWL() {
        getCompositeDisposable().add(getDataManager().getWL()
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(new Consumer<List<MovieDb>>() {
                    @Override
                    public void accept(List<MovieDb> movieDbs) throws Exception {
                        getMvpView().showWatchLaterList(movieDbs);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
                    }
                }));
    }
}
