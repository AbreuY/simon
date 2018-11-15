package com.gokep.app.ui.story;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gokep.app.R;
import com.gokep.app.data.network.model.Story;
import com.gokep.app.di.component.ActivityComponent;
import com.gokep.app.ui.base.BaseFragment;
import com.gokep.app.ui.helper.VerticalLineDecorator;
import com.gokep.app.utils.CommonUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends BaseFragment implements StoryView {

    public static final String TAG = StoryFragment.class.getSimpleName();

    @Inject
    StoryMvpPresenter<StoryView> presenter;

    @BindView(R.id.recycler_story)
    RecyclerView mRecyclerView;
    private List<Story> list;

    StoryAdapter adapter;

    private int listSize;
    public static StoryFragment newInstance() {

        Bundle args = new Bundle();

        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

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
        presenter.getStories();



    }

    @Override
    public void showStories(List<Story> stories) {
        //list.addAll(stories);
        //adapter.addItems(stories);
        int VIEW_TYPE_ADS = 0;
        int VIEW_TYPE_NORMAL = 1;

        int[] viewTypes = new int[stories.size()];

        for (int i = 0; i < stories.size(); i++) {
            //1 iklan tiap 5 film
            if (i > 1 && i % 5 == 0) {
                viewTypes[i] = VIEW_TYPE_ADS;
            } else {
                viewTypes[i] = VIEW_TYPE_NORMAL;
            }
        }

        adapter = new StoryAdapter(stories, viewTypes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
//        if(stories.size()>0){
//            //add loaded data
//            list.addAll(stories);
//
//            listSize = list.size();
//
//        }else{//result size 0 means there is no more data available at server
//            adapter.setMoreDataAvailable(false);
//            //telling adapter to stop calling load more as no more server data available
//        }
    }

    private void openFragment(Fragment fragment, final String TAG) {
        FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(com.gokep.app.R.id.container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
