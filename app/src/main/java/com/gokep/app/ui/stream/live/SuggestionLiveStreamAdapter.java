package com.gokep.app.ui.stream.live;

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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;
import com.gokep.app.R;
import com.gokep.app.data.network.model.response.LiveResponse;
import com.gokep.app.data.network.model.response.MovieResponse;
import com.gokep.app.utils.AppConstants;
import com.gokep.app.utils.AppLogger;

import java.util.List;

public class SuggestionLiveStreamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 2;
    public final int TYPE_ADS = 1;
    static Context context;
    List<LiveResponse> lives;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    int[] viewTypes;
    private static Callback callback;
    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public SuggestionLiveStreamAdapter(Context context, List<LiveResponse> liveResponses, int[] viewTypes) {
        this.context = context;
        this.lives = liveResponses;
        this.viewTypes = viewTypes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_MOVIE){
            return new MovieHolder(inflater.inflate(com.gokep.app.R.layout.item_content,parent,false));
        } else if (viewType==TYPE_ADS) {
            return new AdsHolder(inflater.inflate(com.gokep.app.R.layout.item_ad, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(com.gokep.app.R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            ((MovieHolder)holder).bindData(lives.get(position));
        } else if (getItemViewType(position)==TYPE_ADS) {
//            AdsHolder adsHolder = (AdsHolder) holder;
            AdRequest request = new AdRequest.Builder().build();
            ((AdsHolder) holder).adView.loadAd(request);
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
        return lives.size();
    }

    /* VIEW HOLDERS */

    public static class MovieHolder extends RecyclerView.ViewHolder{

        ImageView imgContent;
        TextView txtTitle;
        TextView txtTime;
        TextView txtViewer;
        String BASE_URL = "";
        
        public MovieHolder(View itemView) {
            super(itemView);
            SharedPreferences pref = itemView.getContext().getSharedPreferences(AppConstants.PREF_NAME, 0);
            BASE_URL = pref.getString("KEY_BASE_URL", "");
            imgContent = (ImageView) itemView.findViewById(com.gokep.app.R.id.image);
            txtTitle = (TextView) itemView.findViewById(com.gokep.app.R.id.title);
            txtViewer = (TextView) itemView.findViewById(com.gokep.app.R.id.total_views);
            txtTime = (TextView) itemView.findViewById(com.gokep.app.R.id.rating);
        }

        void bindData(LiveResponse live){
            if (live.getImgs() != null) {
                Glide.with(itemView.getContext())
                        .load(BASE_URL + "/images/" + live.getImgs())
                        .asBitmap()
                        .centerCrop()
                        .into(imgContent);
            }

            if (live.getTitle() != null) {
                txtTitle.setText(live.getTitle());
            }

            if (live.getDurasi() != null) {
                txtTime.setText(live.getDurasi());
            }

            itemView.setOnClickListener(v -> {
                if (live.getDatat() != null) {
                    try {
                        callback.onLiveClick(live);
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
        AdView adView;
        public AdsHolder(View itemView) {
            super(itemView);
            adView = (AdView) itemView.findViewById(com.gokep.app.R.id.adsView);
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
        void onLiveClick(LiveResponse live);
    }

    public void setCallback(Callback mCallback) {
        this.callback = mCallback;
    }
}