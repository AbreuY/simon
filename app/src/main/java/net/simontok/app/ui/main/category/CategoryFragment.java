package net.simontok.app.ui.main.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import net.simontok.app.R;
import net.simontok.app.di.component.ActivityComponent;
import net.simontok.app.ui.base.BaseFragment;

import javax.inject.Inject;

public class CategoryFragment extends BaseFragment implements CategoryView {

    public static final String TAG = CategoryFragment.class.getSimpleName();

    @Inject
    CategoryMvpPresenter<CategoryView> presenter;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

    }
}
