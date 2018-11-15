/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.ui.splash;

import com.gokep.app.di.PerActivity;

import com.gokep.app.di.PerActivity;
import com.gokep.app.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashView> extends MvpPresenter<V> {
    void fetch();

}
