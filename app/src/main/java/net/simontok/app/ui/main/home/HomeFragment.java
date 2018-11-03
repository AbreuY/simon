package net.simontok.app.ui.main.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import net.simontok.app.R;
import net.simontok.app.data.network.model.response.MovieResponse;
import net.simontok.app.di.component.ActivityComponent;
import net.simontok.app.ui.base.BaseFragment;
import net.simontok.app.ui.helper.EndlessRecyclerViewScrollListener;
import net.simontok.app.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements HomeView {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomeMvpPresenter<HomeView> presenter;

    @Inject
    ContentAdapter adapter;

    @BindView(R.id.recycler_content)
    RecyclerView mRecyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    List<MovieResponse> list;
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        presenter.fetchContents(0);

        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                AppLogger.e("PAGE = "+page);
                int currentSize = adapter.getItemCount();
                presenter.fetchContents(page);
                adapter.notifyItemRangeInserted(currentSize, adapter.getItemCount() - 5);

            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        return view;
    }


    @Override
    protected void setUp(View view) {

    }

    @Override
    public void showContent(List<MovieResponse> contents) {
        adapter.addItems(contents);

    }

    private void loadNextData(int index) {
        presenter.fetchContents(index);
    }
}
