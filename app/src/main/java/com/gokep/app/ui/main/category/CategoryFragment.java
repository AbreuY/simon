package com.gokep.app.ui.main.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.main.category.genre.GenreFragment;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.main.category.genre.GenreFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import javax.inject.Inject;
import java.util.List;

public class CategoryFragment extends BaseFragment implements CategoryView, CategoryAdapter.Callback {

    public static final String TAG = CategoryFragment.class.getSimpleName();

    @Inject
    CategoryMvpPresenter<CategoryView> presenter;

    @Inject
    CategoryAdapter adapter;

    @BindView(com.gokep.app.R.id.recycler_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.adsView)
    LinearLayout mAds;

    @Inject
    AdRequest adRequest;

    @Inject
    AdView adView;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_category, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(presenter.getBannerId());
        mAds.addView(adView);
        adView.loadAd(adRequest);

        adapter.setCallback(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);


        presenter.fetchCategories();
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        adapter.addItems(categoryList);
    }

    @Override
    public void onCategorySelected(String title) {
        GenreFragment genreFragment = GenreFragment.newInstance();
        genreFragment.setTitle(title);
        openFragment(genreFragment, GenreFragment.TAG);
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(com.gokep.app.R.id.container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
