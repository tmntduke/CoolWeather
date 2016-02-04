package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.MainActivity;
import com.example.tmnt.coolweather.Model.City;
import com.example.tmnt.coolweather.Model.Province;
import com.example.tmnt.coolweather.R;
import com.example.tmnt.coolweather.Utils.HttpUtils;
import com.example.tmnt.coolweather.Utils.SpiltUtils;
import com.example.tmnt.coolweather.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tmnt on 2016/2/3.
 */
public class CityActivity extends Activity {
    private ArrayList<String> list;
    private ListView cityList;
    private String cityId;
    private PlaceDAO dao;
    private String url;
    private ArrayList<String>county;
    public static final String CITY="city";
    public static final String CITYFLAG="cityflag";
    private Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 0) {
            county = new ArrayList<>();
            String returnString = (String) msg.obj;
            Map<String, String> map = SpiltUtils.returnProvince(returnString);
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                dao.insertCounty(entry.getValue(), entry.getKey(), cityId);
                county.add(entry.getKey());
            }
            Log.i("click Province",list.get(2));
            //String s = (String) msg.obj;
            Intent intent = new Intent(CityActivity.this, CountyActivity.class);
            intent.putStringArrayListExtra(CITY, county);
            intent.putExtra(CITYFLAG, 1);
            startActivity(intent);

        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_layout);
        cityList = (ListView) findViewById(R.id.city_list);
        dao = new PlaceDAO(getApplicationContext());
        Intent intent = getIntent();
        int flag = intent.getIntExtra(ProvinceActivity.PROVICEFLAG, 0);
        if (flag == 1) {
            list = intent.getStringArrayListExtra(ProvinceActivity.PROVINCE);
        } else if (flag == 2) {
            String id=intent.getStringExtra(ProvinceActivity.PROVINCE);
            List<City> cities = dao.queryCityAll(id);
            list = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                String name = cities.get(i).getCity_name();
                list.add(name);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        cityList.setAdapter(adapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityId = dao.queryCity(list.get(position));
                url = "http://www.weather.com.cn/data/list3/city" + cityId + ".xml";
                if (!dao.isHasCounty(cityId)) {

                    HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(byte[] result) {
                            String place = StringUtils.toStrings(result);

                            Message message = Message.obtain();
                            message.what = 0;
                            message.obj = place;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(CityActivity.this, "网络连接错误或选择错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Intent intent = new Intent(CityActivity.this, CountyActivity.class);
                    intent.putExtra(CITY, cityId);
                    intent.putExtra(CITYFLAG, 2);
                    startActivity(intent);
                }


            }
        });
    }
}
