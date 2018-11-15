package com.gokep.app.ui.main.live;

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
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.helper.VerticalLineDecorator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends BaseFragment implements LiveView {

    public static final String TAG = LiveFragment.class.getSimpleName();

    @Inject
    LiveMvpPresenter<LiveView> presenter;

    LiveAdapter adapter;

    @BindView(com.gokep.app.R.id.recycler_live)
    RecyclerView mRecyclerView;
    List<LiveResponse> list;

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
        View view = inflater.inflate(com.gokep.app.R.layout.fragment_live, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        list = new ArrayList<>();

        adapter = new LiveAdapter(getBaseActivity().getApplicationContext(), list);
        adapter.setLoadMoreListener(() -> mRecyclerView.post(() -> {
            int index = list.size() -1;
            loadMore(index);
        }));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
        presenter.fetchLiveStream(0);

        return view;
    }

    @Override
    protected void setUp(View view) {


    }

    @Override
    public void showLiveContent(List<LiveResponse> contents) {
        if(contents.size()>0){
            //add loaded data
            list.addAll(contents);
        }else{//result size 0 means there is no more data available at server
            adapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
        }
        adapter.notifyDataChanged();
    }

    private void loadMore(int index) {
        presenter.fetchLiveStream(index);
    }

}
