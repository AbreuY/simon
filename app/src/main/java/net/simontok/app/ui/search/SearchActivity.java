package net.simontok.app.ui.search;

import android.os.Bundle;
import butterknife.ButterKnife;
import net.simontok.app.R;
import net.simontok.app.ui.base.BaseActivity;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity implements SearchView {

    @Inject
    SearchMvpPresenter<SearchView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
    }

    public void onBackPressed() {
        finishAction();
    }
}
