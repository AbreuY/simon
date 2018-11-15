package com.gokep.app.ui.stream.live;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.CommonUtils;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LiveStreamActivity extends BaseActivity implements LiveStreamView, SuggestionLiveStreamAdapter.Callback {

    @Inject
    LiveStreamMvpPresenter<LiveStreamView> presenter;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private SimpleExoPlayer player;
    private boolean mExoPlayerFullscreen = false;
    @BindView(com.gokep.app.R.id.btn_iful)
    ImageButton mFullScreenButton;
//    @BindView(R.id.nex_play)
//    ImageButton btnNext;
    @BindView(com.gokep.app.R.id.progressBar)
    ProgressBar progressBar;
    @BindView(com.gokep.app.R.id.title)
    TextView mTitle;
    @BindView(com.gokep.app.R.id.viewer)
    TextView mViewer;
    @BindView(com.gokep.app.R.id.recycler_suggestion)
    RecyclerView mRecyclerView;

    private SuggestionLiveStreamAdapter adapter;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;
    private LiveResponse live;
    private List<String> streamUrl = new ArrayList<>();
    List<LiveResponse> list;
    int listSize = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_test);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            live = (LiveResponse) bundle.getSerializable("live");
            if (live != null) {
                mTitle.setText(live.getTitle());
            }
        }
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        setUp();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((RelativeLayout) findViewById(com.gokep.app.R.id.player_content)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

//        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenButton = controlView.findViewById(R.id.btn_iful);

    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getApplicationContext()), trackSelector);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

        player.prepare(mVideoSource);
        //mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);

        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });

        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                AppLogger.e(String.valueOf("onTimelineChanged").toUpperCase());

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                AppLogger.e(String.valueOf("onTracksChanged").toUpperCase());
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
//                if (isLoading) {
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                AppLogger.e(String.valueOf("onPlayerStateChanged").toUpperCase());
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                AppLogger.e(String.valueOf("onPlaybackParametersChanged").toUpperCase());
            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }


    @Override
    protected void onResume() {

        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            live = (LiveResponse) bundle.getSerializable("live");
            if (live != null) {
                mTitle.setText(live.getTitle());
            }
        }
        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(com.gokep.app.R.id.player_view);
            initFullscreenDialog();
//            initFullscreenButton();

            streamUrl.add(live.getUr());
            String userAgent = Util.getUserAgent(getApplicationContext(), getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), null, httpDataSourceFactory);
            Uri daUri = Uri.parse(streamUrl.get(0));
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
//            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory,  null, null);
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }
    }

    @Override
    protected void setUp() {
        list = new ArrayList<>();

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
        adapter = new SuggestionLiveStreamAdapter(getApplicationContext(), list, viewTypes);
        adapter.setCallback(this);
        adapter.setLoadMoreListener(() -> {
            int index = list.size() -1;
            presenter.fetchSuggestion(index);
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);

        int random = CommonUtils.getRandomNumberInRange(10,40);
        presenter.fetchSuggestion(random);
    }

    private void killPlayer(){
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        killPlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //change orientation here

        } else if (newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE) {
            //change orientation here
        }
    }

    @Override
    public void showContent(List<LiveResponse> contents) {

        if(contents.size()>0){
            //add loaded data
            list.addAll(contents);

            listSize = list.size();

        }else{//result size 0 means there is no more data available at server
            adapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
        }
        adapter.notifyDataChanged();
    }

    @OnClick(com.gokep.app.R.id.nex_play)
    void next() {
        killPlayer();
        Intent intent = new Intent(this, LiveStreamActivity.class);
        intent.putExtra("movie", list.get(0));
        startActivity(intent);
        finish();
    }

    @Override
    public void onLiveClick(LiveResponse live) {
        killPlayer();
        Intent intent = new Intent(this, LiveStreamActivity.class);
        intent.putExtra("live", live);
        startActivity(intent);
        finish();
    }
}
