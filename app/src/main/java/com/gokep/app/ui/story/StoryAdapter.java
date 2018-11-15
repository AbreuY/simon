package com.gokep.app.ui.story;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.gokep.app.R;
import com.gokep.app.data.network.model.Story;
import com.gokep.app.ui.base.BaseViewHolder;
import com.gokep.app.ui.stream.SuggestionAdapter;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;


public class StoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_ADS = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_LOADING = 2;

    private int[] viewTypes;

    public String BASE_URL;
    private Callback mCallback;
    private OnLoadMoreListener onLoadMoreListener;
    private List<Story> categories;

    public StoryAdapter(List<Story> categoryList, int[] viewTypes) {
        this.categories = categoryList;
        this.viewTypes = viewTypes;
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
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType==VIEW_TYPE_ADS) {
            return new AdsViewHolder(inflater.inflate(R.layout.item_ad_story,parent,false));
        } else if (viewType==VIEW_TYPE_NORMAL) {
            return new ViewHolder(inflater.inflate(R.layout.item_story, parent, false));
        } else {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_live_empty_view, parent, false));
        }
//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new ViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false));
//
//            case VIEW_TYPE_ADS:
//                return new AdsViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ads, parent, false));
//            default:
//                return new EmptyViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_empty_view, parent, false));
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (categories != null && categories.size() > 0) {
//            return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_ADS;
//        }
        AppLogger.e("viewtype " + String.valueOf(viewTypes[position]));
        return viewTypes[position];
    }

    @Override
    public int getItemCount() {
        if (categories != null && categories.size() > 0) {
            return categories.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Story> stories) {
        categories.addAll(stories);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onStorySelected(String title);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }



    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.title)
        TextView txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);

            final Story category = categories.get(position);

            if (category.getTitle() != null) {
                txtTitle.setText(category.getTitle());
            }

            itemView.setOnClickListener(v -> {
                if (category.getId() != null) {

                    mCallback.onStorySelected(category.getTitle());
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

        @Override
        public void onBind(int position) {
            super.onBind(position);

        }
    }

    public class AdsViewHolder extends BaseViewHolder {

        @BindView(R.id.adsView)
        CardView mAds;

        public AdsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            AdView adView = new AdView(itemView.getContext());
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            mAds.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }

        //        @OnClick(R.id.btn_retry)
//        void onRetryClick() {
//            if (mCallback != null)
//                mCallback.onBlogEmptyViewRetryClick();
//        }
    }
}
