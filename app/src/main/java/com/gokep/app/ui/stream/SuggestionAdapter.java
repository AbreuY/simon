package com.gokep.app.ui.stream;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;

import javax.inject.Inject;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 2;
    public final int TYPE_ADS = 1;
    static Context context;
    List<MovieResponse> movies;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    int[] viewTypes;
    private static Callback callback;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public SuggestionAdapter(Context context, List<MovieResponse> movies, int[] viewTypes) {
        this.context = context;
        this.movies = movies;
        this.viewTypes = viewTypes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_MOVIE){
            return new MovieHolder(inflater.inflate(com.gokep.app.R.layout.item_content,parent,false));
        } else if (viewType==TYPE_ADS) {
            return new AdsHolder(inflater.inflate(com.gokep.app.R.layout.item_ads, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(com.gokep.app.R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SharedPreferences pref = holder.itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
        String BANNER_ID = pref.getString("KEY_BANNER", "ca-app-pub-3940256099942544/1033173712");

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            ((MovieHolder)holder).bindData(movies.get(position));
        } else if (getItemViewType(position)==TYPE_ADS) {
//            AdsHolder adsHolder = (AdsHolder) holder;
//            AdRequest request = new AdRequest.Builder().build();
//            ((AdsHolder) holder).adView.loadAd(request);
            AdView adView = new AdView(holder.itemView.getContext());
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(BANNER_ID);
            AdRequest adRequest = new AdRequest.Builder().build();
            ((AdsHolder) holder).mAds.addView(adView);
            adView.loadAd(adRequest);
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
//        if(movies.get(position).getType().equals("movie")){
//            return TYPE_MOVIE;
//        }else{
//            return TYPE_LOAD;
//        }
        return viewTypes[position];
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* VIEW HOLDERS */

    public static class MovieHolder extends RecyclerView.ViewHolder{

        ImageView imgContent;
        ImageView imgThreeDot;
        TextView txtTitle;
        TextView txtTime;
        TextView txtViewer;
        RelativeLayout imgContainer;
        String BASE_URL = "";

        public MovieHolder(View itemView) {
            super(itemView);
            SharedPreferences pref = itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
            BASE_URL = pref.getString("KEY_BASE_URL", "");

            imgContent = (ImageView) itemView.findViewById(com.gokep.app.R.id.image);
            txtTitle = (TextView) itemView.findViewById(com.gokep.app.R.id.title);
            txtViewer = (TextView) itemView.findViewById(com.gokep.app.R.id.total_views);
            txtTime = (TextView) itemView.findViewById(com.gokep.app.R.id.rating);
            imgThreeDot = (ImageView) itemView.findViewById(com.gokep.app.R.id.add_watch_later);
            imgContainer = (RelativeLayout) itemView.findViewById(com.gokep.app.R.id.img_container);
        }

        void bindData(MovieResponse movie){
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

            imgThreeDot.setOnClickListener(v -> {
                callback.onThreeDotClicked(v, movie);
            });

            imgContainer.setOnClickListener(v -> {
                if (movie.getDatat() != null) {
                    try {
                        callback.onMovieClick(movie);
                    } catch (Exception e) {
                        AppLogger.d("id error");
                    }
                }
            });
        }
           }

    public static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public static class AdsHolder extends RecyclerView.ViewHolder{
        LinearLayout mAds;
        public AdsHolder(View itemView) {
            super(itemView);
            mAds = (LinearLayout) itemView.findViewById(R.id.adsView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface Callback {
        void onMovieClick(MovieResponse movie);
        void onThreeDotClicked(View view, MovieResponse movieResponse);
    }

    public void setCallback(Callback mCallback) {
        this.callback = mCallback;
    }
}