package net.simontok.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndPoint {

    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("param_tv")
    @Expose
    private String paramTv;
    @SerializedName("param_porn")
    @Expose
    private String paramPorn;
    @SerializedName("param_movies")
    @Expose
    private String paramMovies;
    @SerializedName("param_indo")
    @Expose
    private String paramIndo;
    @SerializedName("param_barat")
    @Expose
    private String paramBarat;
    @SerializedName("param_hentai")
    @Expose
    private String paramHentai;
    @SerializedName("param_korea")
    @Expose
    private String paramKorea;
    @SerializedName("param_adult")
    @Expose
    private String paramAdult;
    @SerializedName("param_search")
    @Expose
    private String paramSearch;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getParamTv() {
        return paramTv;
    }

    public void setParamTv(String paramTv) {
        this.paramTv = paramTv;
    }

    public String getParamPorn() {
        return paramPorn;
    }

    public void setParamPorn(String paramPorn) {
        this.paramPorn = paramPorn;
    }

    public String getParamMovies() {
        return paramMovies;
    }

    public void setParamMovies(String paramMovies) {
        this.paramMovies = paramMovies;
    }

    public String getParamIndo() {
        return paramIndo;
    }

    public void setParamIndo(String paramIndo) {
        this.paramIndo = paramIndo;
    }

    public String getParamBarat() {
        return paramBarat;
    }

    public void setParamBarat(String paramBarat) {
        this.paramBarat = paramBarat;
    }

    public String getParamHentai() {
        return paramHentai;
    }

    public void setParamHentai(String paramHentai) {
        this.paramHentai = paramHentai;
    }

    public String getParamKorea() {
        return paramKorea;
    }

    public void setParamKorea(String paramKorea) {
        this.paramKorea = paramKorea;
    }

    public String getParamAdult() {
        return paramAdult;
    }

    public void setParamAdult(String paramAdult) {
        this.paramAdult = paramAdult;
    }

    public String getParamSearch() {
        return paramSearch;
    }

    public void setParamSearch(String paramSearch) {
        this.paramSearch = paramSearch;
    }
}
