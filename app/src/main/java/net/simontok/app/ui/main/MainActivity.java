package net.simontok.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import net.simontok.app.R;
import net.simontok.app.ui.base.BaseActivity;
import net.simontok.app.ui.main.category.CategoryFragment;
import net.simontok.app.ui.main.home.HomeFragment;
import net.simontok.app.ui.main.live.LiveFragment;
import net.simontok.app.ui.main.movie.MovieFragment;
import net.simontok.app.ui.search.SearchActivity;
import net.simontok.app.ui.setting.SettingActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, BottomNavigationViewEx.OnNavigationItemSelectedListener {

    @Inject
    MainMvpPresenter<MainView> presenter;

    @BindView(R.id.tab_bottom)
    BottomNavigationViewEx mTabNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
        mTabNavigation.setOnNavigationItemSelectedListener(this);

        setUp();
    }

    @OnClick(R.id.fSearch)
    void search() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    protected void setUp() {

        mTabNavigation.enableAnimation(false);
        mTabNavigation.enableShiftingMode(false);
        mTabNavigation.enableItemShiftingMode(false);
        openFragment(HomeFragment.newInstance(), HomeFragment.TAG);
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, fragment, TAG);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                HomeFragment homeFragment = HomeFragment.newInstance();
                openFragment(homeFragment, HomeFragment.TAG);
                return true;
            case R.id.category:
                CategoryFragment categoryFragment = CategoryFragment.newInstance();
                openFragment(categoryFragment, CategoryFragment.TAG);
                return true;
            case R.id.movie:
                MovieFragment movieFragment = MovieFragment.newInstance();
                openFragment(movieFragment, MovieFragment.TAG);
                return true;
            case R.id.live:
                LiveFragment liveFragment = LiveFragment.newInstance();
                openFragment(liveFragment, LiveFragment.TAG);
                return true;
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }
        return false;
    }
}
