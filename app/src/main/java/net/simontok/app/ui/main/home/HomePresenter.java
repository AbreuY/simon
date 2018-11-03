package net.simontok.app.ui.main.home;

import com.androidnetworking.error.ANError;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.data.DataManager;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.AppLogger;
import net.simontok.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class HomePresenter<V extends HomeView> extends BasePresenter<V> implements HomeMvpPresenter<V> {

    @Inject
    public HomePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void fetchContents(int index) {
        getMvpView().showLoading();
        String COMPLETE_URL = getDataManager().getBaseUrl() + getDataManager().getParamPorn();
        getCompositeDisposable().add(getDataManager().getContents(COMPLETE_URL, index)
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
