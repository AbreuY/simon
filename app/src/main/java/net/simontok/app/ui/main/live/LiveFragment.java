package net.simontok.app.ui.main.live;

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
import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.di.component.ActivityComponent;
import net.simontok.app.ui.base.BaseFragment;

import javax.inject.Inject;
import java.util.List;

public class LiveFragment extends BaseFragment implements LiveView {

    public static final String TAG = LiveFragment.class.getSimpleName();

    @Inject
    LiveMvpPresenter<LiveView> presenter;

    @Inject
    LiveAdapter adapter;

    @BindView(R.id.recycler_live)
    RecyclerView mRecyclerView;

    public static LiveFragment newInstance() {

        Bundle args = new Bundle();

        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

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
        presenter.fetchLiveStream();
    }

    @Override
    public void showLiveContent(List<LiveResponse> contents) {
        adapter.addItems(contents);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
