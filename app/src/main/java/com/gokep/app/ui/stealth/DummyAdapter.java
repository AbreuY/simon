package com.gokep.app.ui.stealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.Dummy;
import com.gokep.app.data.network.model.response.Dummy;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;

import java.util.List;

public class DummyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<Dummy> movies;
    OnLoadMoreListener loadMoreListener;
    private static Callback callback;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public DummyAdapter(Context context, List<Dummy> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.item_movie_backdrop, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_MOVIE) {
            ((MovieHolder) holder).bindData(movies.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_MOVIE;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* VIEW HOLDERS */

    public static class MovieHolder extends RecyclerView.ViewHolder {

        ImageView imgContent;
        TextView txtTitle;
        TextView txtTime;
        String BASE_URL = "";

        public MovieHolder(View itemView) {
            super(itemView);
            SharedPreferences pref = itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
            BASE_URL = pref.getString("KEY_BASE_URL", "");
            imgContent = (ImageView) itemView.findViewById(R.id.img_content);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
        }

        void bindData(Dummy movie) {
            if (movie.getImgs() != null) {

                Glide.with(itemView.getContext())
                        .load(movie.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgContent);

            }

            if (movie.getTitle() != null) {
                txtTitle.setText(movie.getTitle());
                txtTime.setVisibility(View.INVISIBLE);
            }


            itemView.setOnClickListener(v -> {

            });
        }


    }

    public static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface Callback {
        void onMovieSelected(Dummy movie);
    }

    public void setCallback(Callback mCallback) {
        this.callback = mCallback;
    }
}