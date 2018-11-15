package com.gokep.app.ui.manga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;

import javax.inject.Inject;

public class MangaFragment extends BaseFragment implements MangaView {

    @Inject
    MangaMvpPresenter<MangaView> presenter;

    public static MangaFragment newInstance() {

        Bundle args = new Bundle();

        MangaFragment fragment = new MangaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_movie, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;

    }

    @Override
    protected void setUp(View view) {

    }
}
