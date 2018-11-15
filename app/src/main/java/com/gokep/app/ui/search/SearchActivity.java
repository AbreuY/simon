package com.gokep.app.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.ui.stream.SuggestionAdapter;
import com.gokep.app.utils.AppLogger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements SearchView, SearchAdapter.Callback {

    @Inject
    SearchMvpPresenter<SearchView> presenter;

    @Inject
    AdRequest adRequest;

    private InterstitialAd mInterstitialAd;
    @BindView(com.gokep.app.R.id.txt_search)
    EditText txtSearch;
    @BindView(com.gokep.app.R.id.txt_result)
    TextView mResult;
    @BindView(com.gokep.app.R.id.txt_result_search)
    TextView mResultSearch;
    @BindView(com.gokep.app.R.id.recyclerview_search)
    RecyclerView mRecyclerView;

    private SearchAdapter adapter;
    private List<MovieResponse> movies =  new ArrayList<>();
    private int listSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_search);
        overridePendingTransition(com.gokep.app.R.anim.anim_pop_down, com.gokep.app.R.anim.anim_push_down);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
        adapter = new SearchAdapter(getApplicationContext(), movies);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.clear();
                if (txtSearch.getText().toString().length()>3) {
                    presenter.search(txtSearch.getText().toString(), 0);
                }
//                if (txtSearch.getText().toString().length()==0 || txtSearch.getText().toString().length()<3) {
//                    adapter.clear();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setUp();
    }

    @Override
    protected void setUp() {
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(com.gokep.app.R.string.interstitial));

        mInterstitialAd.loadAd(adRequest);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
    }

    private void finishAction() {
        finish();
        overridePendingTransition(com.gokep.app.R.anim.anim_pop_up, com.gokep.app.R.anim.anim_push_up);
    }

    public void onBackPressed() {
        finishAction();
    }

    @Override
    public void showContent(List<MovieResponse> contents) {
        if(contents.size()>0){
            //add loaded data
            movies.addAll(contents);

            listSize = movies.size();

        }else{//result size 0 means there is no more data available at server
            adapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
        }

        adapter.setCallback(this);
        adapter.setLoadMoreListener(() -> {
            int p = movies.size() - 4;
            AppLogger.e("P = "+p);
            presenter.search(txtSearch.getText().toString(), p);
        });
        //adapter = new SearchAdapter(getApplicationContext(), movies);



        adapter.notifyDataChanged();
    }

    @Override
    public void onMovieClick(MovieResponse movie) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(adRequest);
                    Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }

    @Override
    public void onThreeDotClicked(View view, MovieResponse movieResponse) {
        ImageView imgThreeDot = (ImageView) view.findViewById(com.gokep.app.R.id.add_watch_later);
        PopupMenu popupMenu = new PopupMenu(view.getContext(), imgThreeDot);
        getMenuInflater().inflate(com.gokep.app.R.menu.options_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case com.gokep.app.R.id.action_add_to_watch_later:
                        //handle menu1 click
                        presenter.addToWatchLater(movieResponse);
                        Toast.makeText(getApplicationContext(), getString(com.gokep.app.R.string.add_to_watch_later), Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
