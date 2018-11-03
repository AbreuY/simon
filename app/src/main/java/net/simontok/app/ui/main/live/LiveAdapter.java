package net.simontok.app.ui.main.live;

import java.util.List;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import net.simontok.app.R;
import net.simontok.app.data.network.model.response.LiveResponse;
import net.simontok.app.ui.base.BaseViewHolder;
import net.simontok.app.utils.AppConstants;
import net.simontok.app.utils.AppLogger;


public class LiveAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public String BASE_URL;
    private Callback mCallback;
    private List<LiveResponse> mMovieResponseList;

    public LiveAdapter(List<LiveResponse> liveResponseList) {
        mMovieResponseList = liveResponseList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMovieResponseList != null && mMovieResponseList.size() > 0) {
            AppLogger.e("NORMAL");
            return VIEW_TYPE_NORMAL;
        } else {
            AppLogger.e("EMPTY");

            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieResponseList != null && mMovieResponseList.size() > 0) {
            return mMovieResponseList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<LiveResponse> blogList) {
        mMovieResponseList.addAll(blogList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onBlogEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.backdrop)
        ImageView imgPoster;
        @BindView(R.id.txtTitle)
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

            final LiveResponse tv = mMovieResponseList.get(position);

            if (tv.getImgs() != null) {
                Glide.with(itemView.getContext())
                        .load(BASE_URL + "/images/" + tv.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgPoster);
            }

            if (tv.getTitle() != null) {
                txtTitle.setText(tv.getTitle());
            }

            itemView.setOnClickListener(v -> {
                if (tv.getDatat() != null) {
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
