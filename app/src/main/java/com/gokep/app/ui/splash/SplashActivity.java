/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package com.gokep.app.ui.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import com.gokep.app.ui.base.BaseActivity;

import com.gokep.app.R;
import com.gokep.app.ui.main.MainActivity;
import com.gokep.app.ui.stealth.StealthActivity;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.AppUtils;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.StartAppAd;

public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    SplashMvpPresenter<SplashView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);
        StartAppAd.showSplash(this, savedInstanceState,
                new SplashConfig().setAppName("").setLogo(R.drawable.gokep)
                        .setOrientation(SplashConfig.Orientation.PORTRAIT));
        setUp();

    }


    @Override
    protected void setUp() {
        showLogo();



        new Handler().postDelayed(() -> {
//            Intent i = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(i);
//
//            finish();
            mPresenter.fetch();
        }, 3000);
    }

    private void showLogo() {
        Object localObject = ObjectAnimator.ofFloat(findViewById(com.gokep.app.R.id.logo), "scaleX", 5.0F, 1.0F);
        ((ObjectAnimator) localObject).setInterpolator(new AccelerateDecelerateInterpolator());
        ((ObjectAnimator) localObject).setDuration(1500L);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.setStartDelay(1500L);
        localAnimatorSet.start();
        findViewById(com.gokep.app.R.id.logo).setAlpha(1.0F);
        localObject = AnimationUtils.loadAnimation(this, com.gokep.app.R.anim.fade);
        findViewById(com.gokep.app.R.id.logo).startAnimation((Animation) localObject);
    }

    @Override
    public void showDummyContent() {
        Intent i = new Intent(SplashActivity.this, StealthActivity.class);
        startActivity(i);

        finish();
    }

    @Override
    public void gotoRealContent() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);

        finish();
    }
}
