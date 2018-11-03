package net.simontok.app.ui.stream.content;

import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.data.DataManager;
import net.simontok.app.ui.base.BasePresenter;
import net.simontok.app.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ContentStreamPresenter<V extends ContentStreamView> extends BasePresenter<V> implements ContentStreamMvpPresenter<V> {

    @Inject
    public ContentStreamPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
