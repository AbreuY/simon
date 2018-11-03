package net.simontok.app.ui.main.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.simontok.app.R;
import net.simontok.app.data.network.model.response.MovieResponse;
import net.simontok.app.ui.base.BaseViewHolder;
import net.simontok.app.ui.stream.content.ContentStreamActivity;
import net.simontok.app.utils.AppConstants;
import net.simontok.app.utils.AppLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public String BASE_URL;
    private Callback mCallback;
    private List<MovieResponse> mMovieResponseList;

    public ContentAdapter(List<MovieResponse> contentResponseList) {
        mMovieResponseList = contentResponseList;
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_backdrop, parent, false));
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

    public void addItems(List<MovieResponse> contents) {
        mMovieResponseList.addAll(contents);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onBlogEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.img_content)
        ImageView imgContent;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;

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

            final MovieResponse movie = mMovieResponseList.get(position);

            if (movie.getImgs() != null) {
                Glide.with(itemView.getContext())
                        .load(BASE_URL + "/images/" + movie.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgContent);
            }

            if (movie.getTitle() != null) {
                txtTitle.setText(movie.getTitle());
            }

            if (movie.getDurasi() != null) {
                txtTime.setText(movie.getDurasi());
            }

            itemView.setOnClickListener(v -> {
                if (movie.getDatat() != null) {
                    try {
                        Intent intent = new Intent(itemView.getContext(), ContentStreamActivity.class);
                        intent.putExtra("movie", movie);
                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        AppLogger.d("id error");
                    }
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
