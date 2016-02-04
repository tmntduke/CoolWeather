package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.Model.City;
import com.example.tmnt.coolweather.Model.County;
import com.example.tmnt.coolweather.R;
import com.example.tmnt.coolweather.Utils.SpiltUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tmnt on 2016/2/3.
 */
public class CountyActivity extends Activity {
    private ArrayList<String> list;
    private ListView countyList;
    private PlaceDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.county_list);
        countyList=(ListView)findViewById(R.id.coounty_list);
        dao=new PlaceDAO(getApplicationContext());
        Intent intent=getIntent();
        int flag=intent.getIntExtra(CityActivity.CITYFLAG,0);
        if (flag == 1) {
            list = intent.getStringArrayListExtra(CityActivity.CITY);
        } else if (flag == 2) {
            String id=intent.getStringExtra(CityActivity.CITY);
            List<County> coutries = dao.queryCountyAll(id);
            list = new ArrayList<>();
            for (int i = 0; i < coutries.size(); i++) {
                String name = coutries.get(i).getCounty_name();
                list.add(name);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        countyList.setAdapter(adapter);

        countyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
