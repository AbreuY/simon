package net.simontok.app.ui.main.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import net.simontok.app.ui.helper.SpacesItemDecoration;

import java.util.List;

import javax.inject.Inject;

public class MovieFragment extends BaseFragment implements MovieView {

    public static final String TAG = MovieFragment.class.getSimpleName();

    @Inject
    MovieMvpPresenter<MovieView> presenter;

    @Inject
    MovieAdapter adapter;

    @BindView(R.id.recycler_movie)
    RecyclerView mRecyclerView;

    public static MovieFragment newInstance() {

        Bundle args = new Bundle();

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        adapter.setOnLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.fetchMovies(2);
            }
        });

        return view;
    }

    @Override
    protected void setUp(View view) {
        presenter.fetchMovies(1);
    }

    @Override
    public void showLiveContent(List<MovieResponse> movies) {
        adapter.addItems(movies);
        mRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 3);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_8dp);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecyclerView.setLayoutManager(layoutManager);
    }


}
