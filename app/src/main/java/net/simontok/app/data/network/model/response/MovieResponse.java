package net.simontok.app.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieResponse implements Serializable {

    @SerializedName("datat")
    @Expose
    private String datat;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("durasi")
    @Expose
    private String durasi;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ur")
    @Expose
    private String ur;
    @SerializedName("imgs")
    @Expose
    private String imgs;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDatat() {
        return datat;
    }

    public void setDatat(String datat) {
        this.datat = datat;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
