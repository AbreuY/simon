package net.simontok.app.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "geoip")
public class GeoIp {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "continent_code")
    private String continent_code;

    @Property(nameInDb = "continent_name")
    private String continent_name;

    @Property(nameInDb = "country_code")
    private String country_code;

    @Property(nameInDb = "country_name")
    private String country_name;

    @Property(nameInDb = "region_code")
    private String region_code;

    @Property(nameInDb = "region_name")
    private String region_name;

    @Property(nameInDb = "city")
    private String city;

    @Property(nameInDb = "capital")
    private String capital;

    @Property(nameInDb = "latitude")
    private Double latitude;

    @Property(nameInDb = "longitude")
    private Double longitude;

    @Property(nameInDb = "lang_code")
    private String lang_code;

    @Property(nameInDb = "lang_name")
    private String lang_name;



    public GeoIp(String continent_code, String continent_name, String country_code, String country_name, String region_code, String region_name, String city, String capital, Double latitude, Double longitude, String lang_code, String lang_name) {
        this.continent_code = continent_code;
        this.continent_name = continent_name;
        this.country_code = country_code;
        this.country_name = country_name;
        this.region_code = region_code;
        this.region_name = region_name;
        this.city = city;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lang_code = lang_code;
        this.lang_name = lang_name;
    }

    @Generated(hash = 860683847)
    public GeoIp(Long id, String continent_code, String continent_name, String country_code, String country_name, String region_code, String region_name, String city, String capital, Double latitude, Double longitude, String lang_code,
            String lang_name) {
        this.id = id;
        this.continent_code = continent_code;
        this.continent_name = continent_name;
        this.country_code = country_code;
        this.country_name = country_name;
        this.region_code = region_code;
        this.region_name = region_name;
        this.city = city;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lang_code = lang_code;
        this.lang_name = lang_name;
    }

    @Generated(hash = 624022692)
    public GeoIp() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContinent_code() {
        return this.continent_code;
    }

    public void setContinent_code(String continent_code) {
        this.continent_code = continent_code;
    }

    public String getContinent_name() {
        return this.continent_name;
    }

    public void setContinent_name(String continent_name) {
        this.continent_name = continent_name;
    }

    public String getCountry_code() {
        return this.country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return this.country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getRegion_code() {
        return this.region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return this.region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLang_code() {
        return this.lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getLang_name() {
        return this.lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }
}
