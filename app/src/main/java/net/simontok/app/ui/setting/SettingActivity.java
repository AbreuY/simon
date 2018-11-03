package net.simontok.app.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import net.simontok.app.R;
import net.simontok.app.di.component.ActivityComponent;
import net.simontok.app.ui.base.BaseActivity;
import net.simontok.app.ui.base.BaseFragment;
import net.simontok.app.utils.AppUtils;
import net.simontok.app.utils.CommonUtils;

import javax.inject.Inject;

public class SettingActivity extends BaseActivity implements SettingView {

    public static final String TAG = SettingActivity.class.getSimpleName();

    @Inject
    SettingMvpPresenter<SettingView> presenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @OnClick(R.id.containerRate)
    void rate() {
        AppUtils.openPlayStoreForApp(getApplicationContext());
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.setting);
        }
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
    }

    public void onBackPressed() {
        finishAction();
    }
}
