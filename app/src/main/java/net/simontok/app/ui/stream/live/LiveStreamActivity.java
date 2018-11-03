package net.simontok.app.ui.stream.live;

import android.os.Bundle;
import butterknife.ButterKnife;
import net.simontok.app.R;
import net.simontok.app.ui.base.BaseActivity;

import javax.inject.Inject;

public class LiveStreamActivity extends BaseActivity implements LiveStreamView {

    @Inject
    LiveStreamMvpPresenter<LiveStreamView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

    }
}
