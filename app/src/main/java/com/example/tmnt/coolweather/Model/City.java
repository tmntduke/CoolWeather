package com.example.tmnt.coolweather.Model;

/**
 * Created by tmnt on 2016/2/1.
 */
public class City {
    private String city_id;
    private String city_name;

    public City() {

    }

    public City(String city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
