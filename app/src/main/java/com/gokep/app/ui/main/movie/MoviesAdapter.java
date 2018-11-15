package com.gokep.app.ui.main.movie;

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
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<MovieResponse> movies;
    OnLoadMoreListener loadMoreListener;
    private static Callback callback;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public MoviesAdapter(Context context, List<MovieResponse> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(com.gokep.app.R.layout.item_movie_poster, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(com.gokep.app.R.layout.row_load, parent, false));
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
        if (movies.get(position).getType().equals("movie")) {
            return TYPE_MOVIE;
        } else {
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* VIEW HOLDERS */

    public static class MovieHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView txtTitle;
        String BASE_URL = "";

        public MovieHolder(View itemView) {
            super(itemView);
            SharedPreferences pref = itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
            BASE_URL = pref.getString("KEY_BASE_URL", "");
            imgPoster = (ImageView) itemView.findViewById(com.gokep.app.R.id.poster);
            txtTitle = (TextView) itemView.findViewById(com.gokep.app.R.id.txtTitle);
        }

        void bindData(MovieResponse movie) {
            if (movie.getImgs() != null) {
                Glide.with(itemView.getContext())
                        .load(BASE_URL + "/images/" + movie.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgPoster);
            }

            if (movie.getTitle() != null) {
                txtTitle.setText(movie.getTitle());
            }

            itemView.setOnClickListener(v -> {
                if (movie.getDatat() != null) {
                    try {
                        callback.onMovieSelected(movie);
                    } catch (Exception e) {
                        AppLogger.d("id error");
                    }
                }
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
        void onMovieSelected(MovieResponse movie);
    }

    public void setCallback(Callback mCallback) {
        this.callback = mCallback;
    }
}