/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.di.component;

import dagger.Component;
import net.simontok.app.di.PerActivity;
import net.simontok.app.di.module.ActivityModule;

import net.simontok.app.ui.main.MainActivity;
import net.simontok.app.ui.main.category.CategoryFragment;
import net.simontok.app.ui.main.home.HomeFragment;
import net.simontok.app.ui.main.live.LiveFragment;
import net.simontok.app.ui.main.movie.MovieFragment;
import net.simontok.app.ui.search.SearchActivity;
import net.simontok.app.ui.setting.SettingActivity;
import net.simontok.app.ui.setting.lock.LockActivity;
import net.simontok.app.ui.splash.SplashActivity;
import net.simontok.app.ui.stream.content.ContentStreamActivity;
import net.simontok.app.ui.stream.live.LiveStreamActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);

    void inject(LiveFragment liveFragment);

    void inject(CategoryFragment categoryFragment);

    void inject(SettingActivity settingActivity);

    void inject(MovieFragment movieFragment);

    void inject(LockActivity lockActivity);

    void inject(SearchActivity searchActivity);

    void inject(LiveStreamActivity liveStreamActivity);

    void inject(ContentStreamActivity contentStreamActivity);
}
