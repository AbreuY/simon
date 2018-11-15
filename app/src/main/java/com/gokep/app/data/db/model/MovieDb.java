package com.gokep.app.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "movies")
public class MovieDb implements Serializable  {

    private static final long serialVersionUID = 1L;
    
    @Id(autoincrement = true)
    private Long id;

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

    @Generated(hash = 1034511300)
    public MovieDb(Long id, String datat, String rating, String durasi,
            String title, String ur, String imgs, String views, String type) {
        this.id = id;
        this.datat = datat;
        this.rating = rating;
        this.durasi = durasi;
        this.title = title;
        this.ur = ur;
        this.imgs = imgs;
        this.views = views;
        this.type = type;
    }

    @Generated(hash = 1190811592)
    public MovieDb() {
    }

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
