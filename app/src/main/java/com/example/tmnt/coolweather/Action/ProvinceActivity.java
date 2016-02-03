package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.MainActivity;
import com.example.tmnt.coolweather.Model.Province;
import com.example.tmnt.coolweather.R;
import com.example.tmnt.coolweather.Utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmnt on 2016/2/3.
 */
public class ProvinceActivity extends Activity {
    private ListView provinceList;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_layout);
        provinceList = (ListView) findViewById(R.id.province_list);
        PlaceDAO dao = new PlaceDAO(getApplicationContext());
        Intent intent = getIntent();
        int flag = intent.getIntExtra(MainActivity.FLAGSELECT, 0);
        if (flag == 2) {
            list = intent.getStringArrayListExtra(MainActivity.SEND);
        } else if (flag == 1) {
            List<Province> provinces = dao.queryQrovinceAll();
            list=new ArrayList<>();
            Log.i("database", provinces.get(2).getPrivince_name());
            for (int i = 0; i < provinces.size(); i++) {
                String name = provinces.get(i).getPrivince_name();
                list.add(name);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        provinceList.setAdapter(adapter);
    }
}
