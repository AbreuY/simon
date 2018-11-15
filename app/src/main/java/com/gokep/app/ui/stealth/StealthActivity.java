package com.gokep.app.ui.stealth;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.Dummy;
import com.gokep.app.ui.base.BaseActivity;
import com.gokep.app.ui.helper.VerticalLineDecorator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StealthActivity extends BaseActivity implements StealthView {

    @Inject
    StealthMvpPresenter<StealthView> presenter;
    private DummyAdapter adapter;
    @BindView(com.gokep.app.R.id.recycler_content)
    RecyclerView mRecyclerView;

    private List<Dummy> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stealth);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        adapter = new DummyAdapter(getApplicationContext(), list);

        setUp();
    }

    @Override
    protected void setUp() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setAdapter(adapter);
        presenter.fetchDummyContent();
    }

    @Override
    public void showContents(List<Dummy> dummies) {
        list.addAll(dummies);
    }
}
