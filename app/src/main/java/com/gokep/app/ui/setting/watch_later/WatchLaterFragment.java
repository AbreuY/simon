package com.gokep.app.ui.setting.watch_later;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.R;
import com.gokep.app.data.db.model.MovieDb;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.ui.stream.SuggestionAdapter;
import com.gokep.app.utils.AppLogger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class WatchLaterFragment extends BaseFragment implements WatchLaterView, WatchLaterAdapter.Callback {

    public static final String TAG = WatchLaterFragment.class.getSimpleName();

    @Inject
    WatchLaterMvpPresenter<WatchLaterView> presenter;

    @BindView(com.gokep.app.R.id.recycler_watch_later)
    RecyclerView mRecyclerView;
    @BindView(com.gokep.app.R.id.adView)
    AdView adView;

    private WatchLaterAdapter adapter;

    private List<MovieDb> list = new ArrayList<>();
    private int listSize = 0;

    public static WatchLaterFragment newInstance() {

        Bundle args = new Bundle();

        WatchLaterFragment fragment = new WatchLaterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_watch_later, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
        return view;
    }

    @Override
    protected void setUp(View view) {

        int TYPE_MOVIE = 0;
        int TYPE_ADS = 1;

        int[] viewTypes = new int[listSize];

        for (int i = 0; i < listSize; i++) {
            //movies.add(new Movie());
            //insert native ads once in five items
            if (i > 1 && i % 5 == 0) {
                viewTypes[i] = TYPE_ADS;
            } else {
                viewTypes[i] = TYPE_MOVIE;
            }
        }
        adapter = new WatchLaterAdapter(getBaseActivity(), list, viewTypes);
        adapter.setCallback(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
        AppLogger.e("WATCH LATER COUNT "+adapter.getItemCount());

        presenter.getWL();
    }

    @Override
    public void showWatchLaterList(List<MovieDb> movieDbs) {
        //list.addAll(movieDbs);
        adapter.addItems(movieDbs);
        listSize = list.size();
        AppLogger.e("WATCH LATER "+new Gson().toJson(movieDbs));
    }

    @Override
    public void onMovieClick(MovieResponse movieResponse) {
        Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
        intent.putExtra("movie", movieResponse);
        startActivity(intent);
    }
}
