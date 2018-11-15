/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.ui.splash;

import com.gokep.app.ui.base.MvpView;

public interface SplashView extends MvpView {


    void showDummyContent();

    void gotoRealContent();
}
