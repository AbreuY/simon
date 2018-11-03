/*
 * Copyright (c) 2018. Winnerawan T - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential|
 * Written by Winnerawan T <winnerawan@gmail.com>, September 2018
 */

package net.simontok.app.ui.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import net.simontok.app.ui.base.BaseActivity;

import net.simontok.app.R;
import net.simontok.app.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    SplashMvpPresenter<SplashView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);

        setUp();
    }


    @Override
    protected void setUp() {
        showLogo();
        mPresenter.fetch();
        mPresenter.getIpAddress();
        mPresenter.checkIpDetail();
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }, 3000);
    }

    private void showLogo() {
        Object localObject = ObjectAnimator.ofFloat(findViewById(R.id.logo), "scaleX", 5.0F, 1.0F);
        ((ObjectAnimator) localObject).setInterpolator(new AccelerateDecelerateInterpolator());
        ((ObjectAnimator) localObject).setDuration(1500L);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.setStartDelay(1500L);
        localAnimatorSet.start();
        findViewById(R.id.logo).setAlpha(1.0F);
        localObject = AnimationUtils.loadAnimation(this, R.anim.fade);
        findViewById(R.id.logo).startAnimation((Animation) localObject);
    }
}
