package net.simontok.app.ui.main;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.data.DataManager;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
