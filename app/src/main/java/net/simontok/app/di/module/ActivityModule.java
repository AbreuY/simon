/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import net.simontok.app.di.ActivityContext;

import net.simontok.app.di.PerActivity;

import net.simontok.app.ui.main.MainMvpPresenter;
import net.simontok.app.ui.main.MainPresenter;
import net.simontok.app.ui.main.MainView;
import net.simontok.app.ui.main.category.CategoryMvpPresenter;
import net.simontok.app.ui.main.category.CategoryPresenter;
import net.simontok.app.ui.main.category.CategoryView;
import net.simontok.app.ui.main.home.ContentAdapter;
import net.simontok.app.ui.main.home.HomeMvpPresenter;
import net.simontok.app.ui.main.home.HomePresenter;
import net.simontok.app.ui.main.home.HomeView;
import net.simontok.app.ui.main.live.LiveAdapter;
import net.simontok.app.ui.main.live.LiveMvpPresenter;
import net.simontok.app.ui.main.live.LivePresenter;
import net.simontok.app.ui.main.live.LiveView;
import net.simontok.app.ui.main.movie.MovieAdapter;
import net.simontok.app.ui.main.movie.MovieMvpPresenter;
import net.simontok.app.ui.main.movie.MoviePresenter;
import net.simontok.app.ui.main.movie.MovieView;
import net.simontok.app.ui.search.SearchMvpPresenter;
import net.simontok.app.ui.search.SearchPresenter;
import net.simontok.app.ui.search.SearchView;
import net.simontok.app.ui.setting.SettingMvpPresenter;
import net.simontok.app.ui.setting.SettingPresenter;
import net.simontok.app.ui.setting.SettingView;
import net.simontok.app.ui.setting.lock.LockMvpPresenter;
import net.simontok.app.ui.setting.lock.LockPresenter;
import net.simontok.app.ui.setting.lock.LockView;
import net.simontok.app.ui.splash.SplashMvpPresenter;
import net.simontok.app.ui.splash.SplashPresenter;
import net.simontok.app.ui.splash.SplashView;
import net.simontok.app.ui.stream.content.ContentStreamMvpPresenter;
import net.simontok.app.ui.stream.content.ContentStreamPresenter;
import net.simontok.app.ui.stream.content.ContentStreamView;
import net.simontok.app.ui.stream.live.LiveStreamMvpPresenter;
import net.simontok.app.ui.stream.live.LiveStreamPresenter;
import net.simontok.app.ui.stream.live.LiveStreamView;
import net.simontok.app.utils.rx.AppSchedulerProvider;
import net.simontok.app.utils.rx.SchedulerProvider;

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
    @PerActivity
    SettingMvpPresenter<SettingView> provideSettingPresenter(
            SettingPresenter<SettingView> presenter) {
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
    ContentStreamMvpPresenter<ContentStreamView> provideContentStreamPresenter(
            ContentStreamPresenter<ContentStreamView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LiveStreamMvpPresenter<LiveStreamView> provideLiveStreamPresenter(
            LiveStreamPresenter<LiveStreamView> presenter) {
        return presenter;
    }


    @Provides
    MovieMvpPresenter<MovieView> provideMoviePresenter(
            MoviePresenter<MovieView> presenter) {
        return presenter;
    }

    @Provides
    LiveAdapter provideLiveAdapter() {
        return new LiveAdapter(new ArrayList<>());
    }

    @Provides
    MovieAdapter provideMovieAdapter() {
        return new MovieAdapter(new ArrayList<>());
    }

    @Provides
    ContentAdapter provideContentAdapter() {
        return new ContentAdapter(new ArrayList<>());
    }
}
