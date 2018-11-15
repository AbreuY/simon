package com.gokep.app.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gokep.app.ui.library.LibraryFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.gokep.app.ui.main.category.CategoryFragment;
import com.gokep.app.ui.main.home.HomeFragment;
import com.gokep.app.ui.main.live.LiveFragment;
import com.gokep.app.ui.main.movie.MovieFragment;
import com.gokep.app.ui.search.SearchActivity;
import com.gokep.app.ui.setting.SettingFragment;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.AppUtils;
import com.gokep.app.R;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.main.category.CategoryFragment;
import com.gokep.app.ui.main.home.HomeFragment;
import com.gokep.app.ui.main.live.LiveFragment;
import com.gokep.app.ui.main.movie.MovieFragment;
import com.gokep.app.ui.search.SearchActivity;
import com.gokep.app.ui.setting.SettingFragment;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.utils.AppUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, BottomNavigationViewEx.OnNavigationItemSelectedListener {

    @Inject
    MainMvpPresenter<MainView> presenter;

    @BindView(com.gokep.app.R.id.tab_bottom)
    BottomNavigationViewEx mTabNavigation;

//    @BindView(com.gokep.app.R.id.guidelineHorizontal)
//    Guideline guidelineHorizontal;
//
//    @BindView(com.gokep.app.R.id.guidelineBottom)
//    Guideline guidelineBottom;
//
//    @BindView(com.gokep.app.R.id.guidelineMarginEnd)
//    Guideline guidelineMarginEnd;
//
//    @BindView(com.gokep.app.R.id.guidelineVertical)
//    Guideline guidelineVertical;
//
//    @BindView(com.gokep.app.R.id.frmVideoContainer)
//    FrameLayout frmVideoContainer;
//
//    @BindView(com.gokep.app.R.id.frmDetailsContainer)
//    FrameLayout frmDetailsContainer;
//
    private boolean isMatureHidden;
//
//    private ConstraintLayout.LayoutParams paramsGlHorizontal;
//    private ConstraintLayout.LayoutParams paramsGlVertical;
//    private ConstraintLayout.LayoutParams paramsGlBottom;
//    private ConstraintLayout.LayoutParams paramsGlMarginEnd;
//
//    private ConstraintSet constraintSet = new ConstraintSet();
//
//    private VideoTouchHandler animationTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gokep.app.R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
        isMatureHidden = presenter.isMatureHidden();

        mTabNavigation.setOnNavigationItemSelectedListener(this);

        setUp();
    }

    @OnClick(com.gokep.app.R.id.fSearch)
    void search() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    protected void setUp() {
//        if (presenter.getIsMaintenance()) {
////            startActivity(new Intent(this, MaintenanceActivity.class));
//            finish();
//            return;
//        }
//        if (presenter.getIsTimeToForceUpdate()) {
//            AppUtils.openPlayStoreForApp(getApplicationContext());
//            finish();
//            return;
//        }

        mTabNavigation.enableAnimation(false);
        mTabNavigation.enableShiftingMode(false);
        mTabNavigation.enableItemShiftingMode(false);

//        paramsGlHorizontal = (ConstraintLayout.LayoutParams) guidelineHorizontal.getLayoutParams();
//        paramsGlVertical = (ConstraintLayout.LayoutParams) guidelineVertical.getLayoutParams();
//        paramsGlBottom = (ConstraintLayout.LayoutParams) guidelineBottom.getLayoutParams();
//        paramsGlMarginEnd = (ConstraintLayout.LayoutParams) guidelineMarginEnd.getLayoutParams();

        if (isMatureHidden) {
            mTabNavigation.getMenu().getItem(0).setEnabled(false);
            mTabNavigation.getMenu().getItem(1).setEnabled(false);
            mTabNavigation.getMenu().getItem(3).setEnabled(false);
            openFragment(MovieFragment.newInstance(), MovieFragment.TAG);
        } else {
            openFragment(HomeFragment.newInstance(), HomeFragment.TAG);
        }

//        animationTouchListener = new VideoTouchHandler(this, this);
//        frmVideoContainer.setOnTouchListener(animationTouchListener);
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(com.gokep.app.R.id.container, fragment, TAG);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case com.gokep.app.R.id.home:
                HomeFragment homeFragment = HomeFragment.newInstance();
                openFragment(homeFragment, HomeFragment.TAG);
                return true;
            case com.gokep.app.R.id.category:
                CategoryFragment categoryFragment = CategoryFragment.newInstance();
                openFragment(categoryFragment, CategoryFragment.TAG);
                return true;
            case com.gokep.app.R.id.movie:
                MovieFragment movieFragment = MovieFragment.newInstance();
                openFragment(movieFragment, MovieFragment.TAG);
                return true;
            case com.gokep.app.R.id.live:
                LiveFragment liveFragment = LiveFragment.newInstance();
                openFragment(liveFragment, LiveFragment.TAG);
                return true;
            case R.id.library:
                LibraryFragment libraryFragment = LibraryFragment.newInstance();
                openFragment(libraryFragment, LibraryFragment.TAG);
                return true;
        }
        return false;
    }

//    private void scaleVideo(Float percentScrollUp) {
//        //Prevent guidelines to go out of screen bound
//        Float percentVerticalMoved = Math.max(0F, Math.min(VideoTouchHandler.MIN_VERTICAL_LIMIT, percentScrollUp));
//        Float movedPercent = percentVerticalMoved / VideoTouchHandler.MIN_VERTICAL_LIMIT;
//        Float percentHorizontalMoved = VideoTouchHandler.MIN_HORIZONTAL_LIMIT * movedPercent;
//        Float percentBottomMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_BOTTOM_LIMIT);
//        AppLogger.e("Bottom : $percentBottomMoved");
//        Float percentMarginMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_MARGIN_END_LIMIT);
//
//        paramsGlHorizontal.guidePercent = percentVerticalMoved;
//        paramsGlVertical.guidePercent = percentHorizontalMoved;
//        paramsGlBottom.guidePercent = percentBottomMoved;
//        paramsGlMarginEnd.guidePercent = percentMarginMoved;
//
//        guidelineHorizontal.setLayoutParams(paramsGlHorizontal);
//        guidelineVertical.setLayoutParams(paramsGlVertical);
//        guidelineBottom.setLayoutParams(paramsGlBottom);
//        guidelineMarginEnd.setLayoutParams(paramsGlMarginEnd);
//
//        frmDetailsContainer.setAlpha(1.0F - movedPercent);
//    }
//
//    private void swipeVideo(Float percentScrollSwipe) {
//        //Prevent guidelines to go out of screen bound
//        Float percentHorizontalMoved = Math.max(-0.25F, Math.min(VideoTouchHandler.MIN_HORIZONTAL_LIMIT, percentScrollSwipe));
//        Float percentMarginMoved = percentHorizontalMoved + (VideoTouchHandler.MIN_MARGIN_END_LIMIT - VideoTouchHandler.MIN_HORIZONTAL_LIMIT);
//
//        paramsGlVertical.guidePercent = percentHorizontalMoved;
//        paramsGlMarginEnd.guidePercent = percentMarginMoved;
//
//        guidelineVertical.setLayoutParams(paramsGlVertical);
//        guidelineMarginEnd.setLayoutParams(paramsGlMarginEnd);
//    }
//
//    private void hide() {
//
//    }
//
//    @Override
//    public void onClick(@NotNull View view) {
//        if (!animationTouchListener.isExpanded()) {
//            animationTouchListener.setExpanded(true);
//        } else {
//
//        }
//    }
//
//    @Override
//    public void onDismiss(@NotNull View view) {
//
//    }
//
//    @Override
//    public void onScale(float percentage) {
//        scaleVideo(percentage);
//    }
//
//    @Override
//    public void onSwipe(float percentage) {
//        swipeVideo(percentage);
//    }
//
//    @Override
//    public void onExpand(boolean isExpanded) {
//
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        if (isPortrait()) {
//            animationTouchListener.isEnabled = true
//            enableFullScreen(false)
//        } else {
//            animationTouchListener.isEnabled = false
//            if (!animationTouchListener.isExpanded) {
//                animationTouchListener.isExpanded = true
//            }
//            enableFullScreen(true)
//        }
//        rootContainer.updateParams(constraintSet) {
//            constrainHeight(frmVideoContainer.id, if (isPortrait()) 0 else getDeviceHeight())
//            constrainWidth(frmVideoContainer.id, if (isPortrait()) 0 else getDeviceWidth())
//        }
    }
}
