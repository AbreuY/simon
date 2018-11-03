package net.simontok.app.ui.setting.lock;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.data.DataManager;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LockPresenter<V extends LockView> extends BasePresenter<V> implements LockMvpPresenter<V> {

    @Inject
    public LockPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
