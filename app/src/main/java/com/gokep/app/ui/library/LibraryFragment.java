package com.gokep.app.ui.library;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gokep.app.BuildConfig;
import com.gokep.app.R;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.setting.watch_later.WatchLaterFragment;
import com.gokep.app.ui.story.StoryFragment;
import com.gokep.app.utils.CommonUtils;

import javax.inject.Inject;

public class LibraryFragment extends BaseFragment implements LibraryView {

    public static final String TAG = LibraryFragment.class.getSimpleName();
    @Inject
    LibraryMvpPresenter<LibraryView> presenter;
    @BindView(R.id.version)
    TextView txtVersion;

    public static LibraryFragment newInstance() {

        Bundle args = new Bundle();

        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_library, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.containerSetting)
    void setting() {
        Toast.makeText(getBaseActivity(), getString(R.string.available_next_version), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnStory)
    void story() {
        Toast.makeText(getBaseActivity(), getString(R.string.available_next_version), Toast.LENGTH_SHORT).show();
        //openFragment(StoryFragment.newInstance(), StoryFragment.TAG);
    }

    @OnClick(R.id.btnManga)
    void manga() {
        Toast.makeText(getBaseActivity(), getString(R.string.available_next_version), Toast.LENGTH_SHORT).show();
        //openFragment(StoryFragment.newInstance(), StoryFragment.TAG);
    }

    @OnClick(R.id.btnWL)
    void WL() {
        openFragment(WatchLaterFragment.newInstance(), WatchLaterFragment.TAG);
    }

    @OnClick(R.id.containerFeedback)
    void feedback() {
        Intent send = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:third1337@gmail.com"));
        send.putExtra(Intent.EXTRA_SUBJECT, "GO-KEP");
        startActivity(send);
    }

    @OnClick(R.id.containerUpdate)
    void update() {
        Intent www = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gokep.id.aptoide.com"));
        startActivity(www);
    }


    @Override
    protected void setUp(View view) {
        txtVersion.setText(getString(R.string.format_string, BuildConfig.VERSION_NAME));
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(com.gokep.app.R.id.container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
