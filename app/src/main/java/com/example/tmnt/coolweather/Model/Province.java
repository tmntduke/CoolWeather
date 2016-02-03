package com.example.tmnt.coolweather.Model;

/**
 * Created by tmnt on 2016/2/1.
 */
public class Province {
    private String province_id;
    private String privince_name;

    public Province() {
    }

    public Province(String province_id, String privince_name) {
        this.province_id = province_id;
        this.privince_name = privince_name;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getPrivince_name() {
        return privince_name;
    }

    public void setPrivince_name(String privince_name) {
        this.privince_name = privince_name;
    }
}
