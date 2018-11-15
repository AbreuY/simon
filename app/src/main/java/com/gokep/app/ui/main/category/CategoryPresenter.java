package com.gokep.app.ui.main.category;

import com.androidnetworking.error.ANError;
import com.gokep.app.data.DataManager;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.data.DataManager;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.ui.base.BasePresenter;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class CategoryPresenter<V extends CategoryView> extends BasePresenter<V> implements CategoryMvpPresenter<V> {

    @Inject
    public CategoryPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchCategories() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categoryList -> {
                    if (categoryList!=null) {
                        getMvpView().showCategories(categoryList);
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
    public String getBannerId() {
        return getDataManager().getBannerId();
    }
}
