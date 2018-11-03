package net.simontok.app.ui.setting.lock;

import android.os.Bundle;
import butterknife.ButterKnife;
import net.simontok.app.R;
import net.simontok.app.ui.base.BaseActivity;

import javax.inject.Inject;

public class LockActivity extends BaseActivity implements LockView {

    @Inject
    LockMvpPresenter<LockView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

    }
}
