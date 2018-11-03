package net.simontok.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Interest {

    @SerializedName("movie_name")
    @Expose
    public String movie_name;
    @SerializedName("country_code")
    @Expose
    public String country_code;
    @SerializedName("country_name")
    @Expose
    public String country_name;
    @SerializedName("capital")
    @Expose
    public String capital;
    @SerializedName("language_name")
    @Expose
    public String language_name;

    public Interest() {
    }

    public Interest(String movie_name, String country_code, String country_name, String capital, String language_name) {
        this.movie_name = movie_name;
        this.country_code = country_code;
        this.country_name = country_name;
        this.capital = capital;
        this.language_name = language_name;
    }
}
