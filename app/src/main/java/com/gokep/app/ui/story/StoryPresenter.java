package com.gokep.app.ui.story;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.network.model.response.StoryResponse;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;

public class StoryPresenter<V extends StoryView> extends BasePresenter<V> implements StoryMvpPresenter<V> {

    @Inject
    public StoryPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getStories() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getStories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(storyResponse -> {
                    if (storyResponse!=null) {
                        getMvpView().showStories(storyResponse.getStories());
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
