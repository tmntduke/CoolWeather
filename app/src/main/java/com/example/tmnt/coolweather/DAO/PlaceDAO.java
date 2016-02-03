package com.example.tmnt.coolweather.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tmnt.coolweather.Model.City;
import com.example.tmnt.coolweather.Model.County;
import com.example.tmnt.coolweather.Model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmnt on 2016/2/1.
 */
public class PlaceDAO {
    //private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public PlaceDAO(Context context) {

        dbHelper = new DBHelper(context);
    }

    public void insertProvince(String id, String name) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("provinceId", id);
        values.put("provinceName", name);
        db.insert("T_Province", "id", values);
    }

    public void insertCity(String id, String name) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cityId", id);
        values.put("cityName", name);
        db.insert("T_City", "id", values);
    }

    public void insertCounty(String id, String name) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("countyId", id);
        values.put("countyName", name);
        db.insert("T_County", "id", values);
    }

    public String queryProvince(String name) {
        String id = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("T_Province", new String[]{"provinceId"}, "provinceName=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            id = cursor.getString(1);
        }
        return id;
    }

    public String queryCity(String name) {
        String id = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("T_City", new String[]{"cityId"}, "cityName=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            id = cursor.getString(1);
        }
        return id;
    }

    public String queryCounty(String name) {
        String id = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("T_County", new String[]{"countyId"}, "countyName=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            id = cursor.getString(1);
        }
        return id;
    }

    public ArrayList<Province> queryQrovinceAll() {
        ArrayList<Province> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String id = null;
        String name = null;
        Cursor cursor = db.query("T_Province", new String[]{"provinceId", "provinceName"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(0);
            name = cursor.getString(1);
            Province province = new Province(id, name);
            list.add(province);
        }
        return list;
    }

    public ArrayList<City> queryCityAll() {
        ArrayList<City> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String id = null;
        String name = null;
        Cursor cursor = db.query("T_City", new String[]{"cityId", "cityName"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(1);
            name = cursor.getString(2);
            City city = new City(id, name);
            list.add(city);
        }
        return list;
    }

    public ArrayList<County> queryCountyAll() {
        ArrayList<County> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String id = null;
        String name = null;
        Cursor cursor = db.query("T_County", new String[]{"countyId", "countyName"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(1);
            name = cursor.getString(2);
            County county = new County(id, name);
            list.add(county);
        }
        return list;
    }


}