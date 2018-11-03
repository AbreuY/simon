/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.ui.splash;

import net.simontok.app.di.PerActivity;
import net.simontok.app.ui.base.MvpPresenter;

import net.simontok.app.di.PerActivity;
import net.simontok.app.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashView> extends MvpPresenter<V> {

    void getIpAddress();
    void checkIpDetail();
    void fetch();
}
