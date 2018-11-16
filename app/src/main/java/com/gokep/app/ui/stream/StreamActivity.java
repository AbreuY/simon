package com.gokep.app.ui.stream;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.CommonUtils;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.ui.main.home.MovAdapter;
import com.gokep.app.ui.splash.SplashActivity;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.CommonUtils;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.tonyodev.fetch2.*;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Extras;
import com.tonyodev.fetch2core.MutableExtras;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StreamActivity extends BaseActivity implements StreamView, SuggestionAdapter.Callback {

    @Inject
    StreamMvpPresenter<StreamView> presenter;

    @Inject
    AdRequest adRequest;

    InterstitialAd mInterstitialAd;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private Request request;
    private Fetch fetch;
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

    private SuggestionAdapter adapter;
    private Dialog mFullScreenDialog;

    @Inject
    StartAppAd startAppAd;

    private int mResumeWindow;
    private long mResumePosition;
    private MovieResponse movie;
    private List<String> streamUrl = new ArrayList<>();
    List<MovieResponse> list;
    int listSize = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_test);
        getActivityComponent().inject(this);

        StartAppSDK.init(getApplicationContext(), "210576097", true);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        fetch = Fetch.Impl.getDefaultInstance();
        fetch.addListener(fetchListener);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            movie = (MovieResponse) bundle.getSerializable("movie");
            if (movie != null) {
                mTitle.setText(movie.getTitle());
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
        startAppAd.onSaveInstanceState(outState);
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        startAppAd.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            //enqueueDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //enqueueDownload();
        } else {
            Toast.makeText(getApplicationContext(), R.string.permission_not_enabled, Snackbar.LENGTH_LONG).show();
        }
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
            movie = (MovieResponse) bundle.getSerializable("movie");
            if (movie != null) {
                mTitle.setText(movie.getTitle());
            }
        }
        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(com.gokep.app.R.id.player_view);
            initFullscreenDialog();
//            initFullscreenButton();

            streamUrl.add(movie.getUr());
            String userAgent = Util.getUserAgent(getApplicationContext(), getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), null, httpDataSourceFactory);
            Uri daUri = Uri.parse(streamUrl.get(0));
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            //mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory,  null, null);
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
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(presenter.getIntersId());

        mInterstitialAd.loadAd(adRequest);

        list = new ArrayList<>();

        int TYPE_MOVIE = 0;
        int TYPE_ADS = 1;

        int[] viewTypes = new int[listSize];

        for (int i = 0; i < listSize; i++) {
            //1 iklan tiap 5 film
            if (i > 1 && i % 5 == 0) {
                viewTypes[i] = TYPE_ADS;
            } else {
                viewTypes[i] = TYPE_MOVIE;
            }
        }
        adapter = new SuggestionAdapter(getApplicationContext(), list, viewTypes);
        adapter.setCallback(this);
        adapter.setLoadMoreListener(() -> {
            int index = list.size() -1;
            presenter.fetchSuggestion(index);
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);

        int random = CommonUtils.getRandomNumberInRange(1,50);
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
    public void showContent(List<MovieResponse> contents) {
        startAppAd.loadAd();
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
        Intent intent = new Intent(this, StreamActivity.class);
        intent.putExtra("movie", list.get(0));
        startActivity(intent);
        startAppAd.showAd();
        finish();
    }

    @Override
    public void onMovieClick(MovieResponse movie) {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    mInterstitialAd.loadAd(adRequest);
//                    killPlayer();
//                    Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
//                    intent.putExtra("movie", movie);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        } else {
//            killPlayer();
//            Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
//            intent.putExtra("movie", movie);
//            startActivity(intent);
//            finish();
//        }
        if (startAppAd.isReady()) {
            killPlayer();
            Intent intent = new Intent(this, StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
            startAppAd.showAd();
            finish();
        } else {
            killPlayer();
            Intent intent = new Intent(this, StreamActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
            startAppAd.loadAd();
            finish();
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
                    case R.id.action_download:
                        checkStoragePermission();

                        enqueueDownload(movieResponse.getUr(), movieResponse.getTitle());
                        //download(list.get(adapter.));
                        Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


    private static String getFilePath(final String url) {
        final Uri uri = Uri.parse(url);
        final String fileName = uri.getLastPathSegment();
        final String dir = getSaveDir();
        return (dir + "/" + fileName + ".mp4");
    }

    public static String getSaveDir() {
        return Environment.getExternalStorageDirectory() +
                File.separator + "GoKEP" +File.separator;
    }

    public void createDir() {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "GoKEP");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }
    }

    private void enqueueDownload(String url, String name) {
        createDir();
        final String filePath = getSaveDir() + name + ".mp4";
        request = new Request(url, filePath);
        request.setExtras(getExtrasForRequest(request));
        fetch.enqueue(request, updatedRequest -> {
            request = updatedRequest;
        }, error -> AppLogger.e("SingleDownloadActivity Error: %1$s", error.toString()));
    }

    private Extras getExtrasForRequest(Request request) {
        final MutableExtras extras = new MutableExtras();
        extras.putBoolean("testBoolean", true);
        extras.putString("testString", "test");
        extras.putFloat("testFloat", Float.MIN_VALUE);
        extras.putDouble("testDouble",Double.MIN_VALUE);
        extras.putInt("testInt", Integer.MAX_VALUE);
        extras.putLong("testLong", Long.MAX_VALUE);
        return extras;
    }

    private final FetchListener fetchListener = new FetchListener() {
        @Override
        public void onAdded(@NotNull Download download) {


        }

        @Override
        public void onQueued(@NotNull Download download, boolean b) {

        }

        @Override
        public void onWaitingNetwork(@NotNull Download download) {

        }

        @Override
        public void onCompleted(@NotNull Download download) {

        }

        @Override
        public void onError(@NotNull Download download, @NotNull com.tonyodev.fetch2.Error error, @org.jetbrains.annotations.Nullable Throwable throwable) {

        }

        @Override
        public void onDownloadBlockUpdated(@NotNull Download download, @NotNull DownloadBlock downloadBlock, int i) {

        }

        @Override
        public void onStarted(@NotNull Download download, @NotNull List<? extends DownloadBlock> list, int i) {

        }

        @Override
        public void onProgress(@NotNull Download download, long l, long l1) {

        }

        @Override
        public void onPaused(@NotNull Download download) {
            AppLogger.e("PAUSE DOWNLOAD");
            fetch.cancel(download.getId());
        }

        @Override
        public void onResumed(@NotNull Download download) {

        }

        @Override
        public void onCancelled(@NotNull Download download) {
            AppLogger.e("CANCEL DOWNLOAD");
            fetch.cancel(download.getId());
        }

        @Override
        public void onRemoved(@NotNull Download download) {

        }

        @Override
        public void onDeleted(@NotNull Download download) {

        }
    };


}
