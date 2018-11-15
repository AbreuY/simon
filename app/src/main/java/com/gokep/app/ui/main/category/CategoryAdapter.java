package com.gokep.app.ui.main.category;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.Category;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.ui.base.BaseViewHolder;
import com.gokep.app.utils.AppConstants;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_LOADING = 2;

    public String BASE_URL;
    private Callback mCallback;
    private OnLoadMoreListener onLoadMoreListener;
    private List<Category> categories;

    public CategoryAdapter(List<Category> categoryList) {
        this.categories = categoryList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (categories != null && categories.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (categories != null && categories.size() > 0) {
            return categories.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Category> categoryList) {
        categories.addAll(categoryList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onCategorySelected(String title);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }



    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.img_content)
        ImageView imgContent;
        @BindView(R.id.txt_title)
        TextView txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            SharedPreferences pref = itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
            BASE_URL = pref.getString("KEY_BASE_URL", "");
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);

            final Category category = categories.get(position);

            if (category.getImgs() != null) {
                Glide.with(itemView.getContext())
                        .load(category.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgContent);
            }

            if (category.getTitle() != null) {
                txtTitle.setText(category.getTitle());
            }

            itemView.setOnClickListener(v -> {
                if (category.getId() != null) {

                    mCallback.onCategorySelected(category.getTitle());
//                    try {
//                        Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
//                        itemView.getContext().startActivity(intent);
//                    } catch (Exception e) {
//                        AppLogger.d("id error");
//                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {


        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

//        @OnClick(R.id.btn_retry)
//        void onRetryClick() {
//            if (mCallback != null)
//                mCallback.onBlogEmptyViewRetryClick();
//        }
    }
}
