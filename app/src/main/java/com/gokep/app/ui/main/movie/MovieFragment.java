package com.gokep.app.ui.main.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.helper.SpacesItemDecoration;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.helper.SpacesItemDecoration;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.main.home.MovAdapter;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.utils.AppLogger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieFragment extends BaseFragment implements MovieView, MoviesAdapter.Callback {

    public static final String TAG = MovieFragment.class.getSimpleName();

    @Inject
    MovieMvpPresenter<MovieView> presenter;

    @Inject
    AdRequest adRequest;

    @BindView(com.gokep.app.R.id.recycler_movie)
    RecyclerView mRecyclerView;

    List<MovieResponse> list;
    private MoviesAdapter adapter;
    private InterstitialAd mInterstitialAd;

    public static MovieFragment newInstance() {

        Bundle args = new Bundle();

        MovieFragment fragment = new MovieFragment();
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

        list = new ArrayList<>();

        adapter = new MoviesAdapter(getBaseActivity().getApplicationContext(), list);
        adapter.setLoadMoreListener(() -> mRecyclerView.post(() -> {
            for (int i=0; i<25; i++) {
                loadMore(i);
            }
        }));

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 2);
        int spacingInPixels = getResources().getDimensionPixelSize(com.gokep.app.R.dimen.padding_16dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        presenter.fetchMovies(0);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mInterstitialAd = new InterstitialAd(getBaseActivity().getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(com.gokep.app.R.string.interstitial));

        mInterstitialAd.loadAd(adRequest);
        adapter.setCallback(this);
    }

    @Override
    public void showLiveContent(List<MovieResponse> movies) {
        if(movies.size()>0){
            //add loaded data
            list.addAll(movies);
        }else{//result size 0 means there is no more data available at server
            adapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
        }
        adapter.notifyDataChanged();
    }

    private void loadMore(int index) {
        AppLogger.e("LOAD PAGE = "+index);
        presenter.fetchMovies(index);
    }


    @Override
    public void onMovieSelected(MovieResponse movie) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(adRequest);
                    Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }
}
