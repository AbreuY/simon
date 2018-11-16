/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.gokep.app.data.prefs.AppPreferencesHelper;
import com.gokep.app.data.prefs.PreferencesHelper;
import com.gokep.app.di.ApplicationContext;
import com.gokep.app.ui.library.LibraryMvpPresenter;
import com.gokep.app.ui.library.LibraryPresenter;
import com.gokep.app.ui.library.LibraryView;
import com.gokep.app.ui.manga.MangaMvpPresenter;
import com.gokep.app.ui.manga.MangaPresenter;
import com.gokep.app.ui.manga.MangaView;
import com.gokep.app.ui.stealth.StealthMvpPresenter;
import com.gokep.app.ui.stealth.StealthPresenter;
import com.gokep.app.ui.stealth.StealthView;
import com.gokep.app.ui.story.StoryAdapter;
import com.gokep.app.ui.story.StoryMvpPresenter;
import com.gokep.app.ui.story.StoryPresenter;
import com.gokep.app.ui.story.StoryView;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.CommonUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import com.gokep.app.R;
import com.gokep.app.di.ActivityContext;

import com.gokep.app.di.PerActivity;

import com.gokep.app.ui.main.MainMvpPresenter;
import com.gokep.app.ui.main.MainPresenter;
import com.gokep.app.ui.main.MainView;
import com.gokep.app.ui.main.category.CategoryAdapter;
import com.gokep.app.ui.main.category.CategoryMvpPresenter;
import com.gokep.app.ui.main.category.CategoryPresenter;
import com.gokep.app.ui.main.category.CategoryView;
import com.gokep.app.ui.main.category.genre.GenreMvpPresenter;
import com.gokep.app.ui.main.category.genre.GenrePresenter;
import com.gokep.app.ui.main.category.genre.GenreView;
import com.gokep.app.ui.main.home.HomeMvpPresenter;
import com.gokep.app.ui.main.home.HomePresenter;
import com.gokep.app.ui.main.home.HomeView;
import com.gokep.app.ui.main.live.LiveMvpPresenter;
import com.gokep.app.ui.main.live.LivePresenter;
import com.gokep.app.ui.main.live.LiveView;
import com.gokep.app.ui.main.movie.MovieMvpPresenter;
import com.gokep.app.ui.main.movie.MoviePresenter;
import com.gokep.app.ui.main.movie.MovieView;
import com.gokep.app.ui.search.SearchMvpPresenter;
import com.gokep.app.ui.search.SearchPresenter;
import com.gokep.app.ui.search.SearchView;
import com.gokep.app.ui.setting.SettingMvpPresenter;
import com.gokep.app.ui.setting.SettingPresenter;
import com.gokep.app.ui.setting.SettingView;
import com.gokep.app.ui.setting.lock.LockMvpPresenter;
import com.gokep.app.ui.setting.lock.LockPresenter;
import com.gokep.app.ui.setting.lock.LockView;
import com.gokep.app.ui.setting.watch_later.WatchLaterMvpPresenter;
import com.gokep.app.ui.setting.watch_later.WatchLaterPresenter;
import com.gokep.app.ui.setting.watch_later.WatchLaterView;
import com.gokep.app.ui.splash.SplashMvpPresenter;
import com.gokep.app.ui.splash.SplashPresenter;
import com.gokep.app.ui.splash.SplashView;
import com.gokep.app.ui.stream.StreamMvpPresenter;
import com.gokep.app.ui.stream.StreamPresenter;
import com.gokep.app.ui.stream.StreamView;
import com.gokep.app.ui.stream.live.LiveStreamMvpPresenter;
import com.gokep.app.ui.stream.live.LiveStreamPresenter;
import com.gokep.app.ui.stream.live.LiveStreamView;
import com.gokep.app.utils.rx.AppSchedulerProvider;
import com.gokep.app.utils.rx.SchedulerProvider;

import java.util.ArrayList;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashView> provideSplashPresenter(
            SplashPresenter<SplashView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainView> provideMainPresenter(
            MainPresenter<MainView> presenter) {
        return presenter;
    }

    @Provides
    CategoryMvpPresenter<CategoryView> provideCategoryPresenter(
            CategoryPresenter<CategoryView> presenter) {
        return presenter;
    }

    @Provides
    LiveMvpPresenter<LiveView> provideLivePresenter(
            LivePresenter<LiveView> presenter) {
        return presenter;
    }


    @Provides
    HomeMvpPresenter<HomeView> provideHomePresenter(
            HomePresenter<HomeView> presenter) {
        return presenter;
    }

    @Provides
    SettingMvpPresenter<SettingView> provideSettingPresenter(
            SettingPresenter<SettingView> presenter) {
        return presenter;
    }

    @Provides
    WatchLaterMvpPresenter<WatchLaterView> provideWatchLaterPresenter(
            WatchLaterPresenter<WatchLaterView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LockMvpPresenter<LockView> provideLockPresenter(
            LockPresenter<LockView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchMvpPresenter<SearchView> provideSearchPresenter(
            SearchPresenter<SearchView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    LiveStreamMvpPresenter<LiveStreamView> provideLiveStreamPresenter(
            LiveStreamPresenter<LiveStreamView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StreamMvpPresenter<StreamView> provideStreamPresenter(
            StreamPresenter<StreamView> presenter) {
        return presenter;
    }

    @Provides
    MovieMvpPresenter<MovieView> provideMoviePresenter(
            MoviePresenter<MovieView> presenter) {
        return presenter;
    }

    @Provides
    CategoryAdapter provideCategoryAdapter() {
        return new CategoryAdapter(new ArrayList<>());
    }

    @Provides
    GenreMvpPresenter<GenreView> provideGenrePresenter(
            GenrePresenter<GenreView> presenter) {
        return presenter;
    }

    @Provides
    Context provideContexts() {
        return mActivity.getApplicationContext();
    }

    @Provides
    PreferencesHelper providePreferenceHelper(@ApplicationContext Context context) {
        return new AppPreferencesHelper(context, AppConstants.PREF_NAME);
    }

    @Provides
    @PerActivity
    StealthMvpPresenter<StealthView> provideStealthPresenter(
            StealthPresenter<StealthView> presenter) {
        return presenter;
    }

    @Provides
    AdRequest provideAdRequest() {
        return new AdRequest.Builder().build();
    }


    @Provides
    AdView provideAdFragment(Context context) {
        return new AdView(context);
    }

    @Provides
    LibraryMvpPresenter<LibraryView> provideLibraryPresenter(
            LibraryPresenter<LibraryView> presenter) {
        return presenter;
    }

    @Provides
    MangaMvpPresenter<MangaView> provideMangaPresenter(
            MangaPresenter<MangaView> presenter) {
        return presenter;
    }

    @Provides
    StoryMvpPresenter<StoryView> provideStoryPresenter(
            StoryPresenter<StoryView> presenter) {
        return presenter;
    }

    @Provides
    StartAppAd provideStartAppAd(Context context) {
        return new StartAppAd(context);
    }

//    @Provides
//    StoryAdapter provideStoryAdapter(int[] vietype) {
//        return new StoryAdapter(new ArrayList<>(), vietype);
//    }

}
