/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.di.component;

import com.gokep.app.ui.library.LibraryFragment;
import com.gokep.app.ui.manga.MangaFragment;
import com.gokep.app.ui.stealth.StealthActivity;
import com.gokep.app.ui.story.StoryFragment;
import dagger.Component;
import com.gokep.app.di.PerActivity;
import com.gokep.app.di.module.ActivityModule;

import com.gokep.app.ui.main.MainActivity;
import com.gokep.app.ui.main.category.CategoryFragment;
import com.gokep.app.ui.main.category.genre.GenreFragment;
import com.gokep.app.ui.main.home.HomeFragment;
import com.gokep.app.ui.main.live.LiveFragment;
import com.gokep.app.ui.main.movie.MovieFragment;
import com.gokep.app.ui.search.SearchActivity;
import com.gokep.app.ui.setting.SettingFragment;
import com.gokep.app.ui.setting.lock.LockActivity;
import com.gokep.app.ui.setting.watch_later.WatchLaterFragment;
import com.gokep.app.ui.splash.SplashActivity;
import com.gokep.app.ui.stream.StreamActivity;
import com.gokep.app.ui.stream.live.LiveStreamActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);

    void inject(LiveFragment liveFragment);

    void inject(CategoryFragment categoryFragment);

    void inject(SettingFragment settingFragment);

    void inject(MovieFragment movieFragment);

    void inject(LockActivity lockActivity);

    void inject(SearchActivity searchActivity);

    void inject(LiveStreamActivity liveStreamActivity);

    void inject(StreamActivity streamActivity);

    void inject(WatchLaterFragment watchLaterFragment);

    void inject(GenreFragment genreFragment);

    void inject(StealthActivity stealthActivity);

    void inject(LibraryFragment libraryFragment);

    void inject(MangaFragment mangaFragment);

    void inject(StoryFragment storyFragment);
}
