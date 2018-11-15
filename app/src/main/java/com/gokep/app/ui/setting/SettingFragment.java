package com.gokep.app.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.setting.lock.LockActivity;
import com.gokep.app.ui.setting.watch_later.WatchLaterFragment;
import com.gokep.app.utils.AppUtils;
import com.gokep.app.R;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.setting.lock.LockActivity;
import com.gokep.app.ui.setting.watch_later.WatchLaterFragment;
import com.gokep.app.utils.AppUtils;

import javax.inject.Inject;

public class SettingFragment extends BaseFragment implements SettingView {

    public static final String TAG = SettingFragment.class.getSimpleName();

    @Inject
    SettingMvpPresenter<SettingView> presenter;

    @Inject
    AdRequest adRequest;

    @BindView(R.id.switchMature)
    Switch switchMature;
    @BindView(R.id.switchLock)
    Switch switchLock;

    @BindView(R.id.adsView)
    LinearLayout mAds;

    @Inject
    AdView adView;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);

        }

        return view;
    }

    @OnClick(com.gokep.app.R.id.containerRate)
    void rate() {
        AppUtils.openPlayStoreForApp(getBaseActivity());
    }

    @OnClick(com.gokep.app.R.id.containerWL)
    void wl() {
        openFragment(WatchLaterFragment.newInstance(), WatchLaterFragment.TAG);
    }

    @OnClick(R.id.containerTos)
    void tos() {
        startActivity(new Intent(getBaseActivity(), TosActivity.class));
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(com.gokep.app.R.id.container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void setUp(View view) {
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(presenter.getBannerId());
        mAds.addView(adView);
        adView.loadAd(adRequest);

        if (presenter.getMatureHidden()) {
            switchMature.setChecked(true);
        }
        if (presenter.getAppLock()) {
            switchLock.setChecked(true);
        }

        switchLock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            startActivity(new Intent(getBaseActivity(), LockActivity.class));
            presenter.setAppLock(isChecked);
        });

        switchMature.setOnCheckedChangeListener((buttonView, isChecked) -> {
            presenter.setMatureHidden(isChecked);
        });
    }
}
