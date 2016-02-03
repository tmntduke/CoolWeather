package com.example.tmnt.coolweather.Model;

/**
 * Created by tmnt on 2016/2/1.
 */
public class County {
    private  String county_id;
    private String county_name;

    public County() {
    }

    public County(String county_id, String county_name) {
        this.county_id = county_id;
        this.county_name = county_name;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }
}
