package com.gokep.app.ui.main.home;

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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.helper.EndlessRecyclerViewScrollListener;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.utils.AppLogger;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements HomeView, MovAdapter.Callback {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomeMvpPresenter<HomeView> presenter;

    @Inject
    AdRequest adRequest;

    private MovAdapter adapter;
    private InterstitialAd mInterstitialAd;

    @BindView(com.gokep.app.R.id.recycler_content)
    RecyclerView mRecyclerView;
    List<MovieResponse> list;
    private StartAppAd startAppAd;
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_home, container, false);

        startAppAd = new StartAppAd(getBaseActivity());
        if (savedInstanceState!=null) {
            startAppAd.onSaveInstanceState(savedInstanceState);
        }
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }


        list = new ArrayList<>();

        adapter = new MovAdapter(getBaseActivity().getApplicationContext(), list);
        adapter.setLoadMoreListener(() -> mRecyclerView.post(() -> {
            int index = list.size() -1;
            loadMore(index);
        }));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
        presenter.fetchContents(0);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            startAppAd.onRestoreInstanceState(savedInstanceState);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    protected void setUp(View view) {
        mInterstitialAd = new InterstitialAd(getBaseActivity().getApplicationContext());
        mInterstitialAd.setAdUnitId(presenter.getIntersId());

        mInterstitialAd.loadAd(adRequest);
        adapter.setCallback(this);
    }

    @Override
    public void showFirstContent(List<MovieResponse> contents) {
//        list.addAll(contents);
    }

    @Override
    public void showContent(List<MovieResponse> contents) {
//        list.remove(list.size()-1);
        startAppAd.loadAd();
        if(contents.size()>0){
            //add loaded data
            list.addAll(contents);
        }else{//result size 0 means there is no more data available at server
            adapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
        }
        adapter.notifyDataChanged();
    }

    private void loadMore(int index) {
        presenter.fetchContents(index);
    }

    @Override
    public void onMovieSelected(MovieResponse movie) {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    mInterstitialAd.loadAd(adRequest);
//                    Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
//                    intent.putExtra("movie", movie);
//                    startActivity(intent);
//                }
//            });
//        } else {
//            Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
//            intent.putExtra("movie", movie);
//            startActivity(intent);
//        }
        if (startAppAd.isReady()) {
            Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
            startAppAd.showAd();
        } else {
            Intent intent = new Intent(getBaseActivity(), StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
            startAppAd.loadAd();
        }
    }
}
