package net.simontok.app.ui.stream.live;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.data.DataManager;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LiveStreamPresenter<V extends LiveStreamView> extends BasePresenter<V> implements LiveStreamMvpPresenter<V> {

    @Inject
    public LiveStreamPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
