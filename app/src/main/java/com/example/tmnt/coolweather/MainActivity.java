package com.example.tmnt.coolweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmnt.coolweather.Action.ProvinceActivity;
import com.example.tmnt.coolweather.Action.WeatherFragment;
import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.Utils.HttpUtils;
import com.example.tmnt.coolweather.Utils.SpiltUtils;
import com.example.tmnt.coolweather.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends FragmentActivity {
    private boolean flag = false;
    private ViewPager mViewPager;
    private Button select;
    public static final String FLAGSELECT = "flagselect";
    public static final String SEND = "send";
    PlaceDAO dao;
    private String url = "http://www.weather.com.cn/data/list3/city.xml";
    private ArrayList<String> list;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                list = new ArrayList<>();
                String returnString = (String) msg.obj;
                Map<String, String> map = SpiltUtils.returnProvince(returnString);
                Set<Map.Entry<String, String>> set = map.entrySet();
                Iterator<Map.Entry<String, String>> iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    dao.insertProvince(entry.getValue(), entry.getKey());
                    list.add(entry.getKey());
                }
                Intent intent = new Intent(MainActivity.this, ProvinceActivity.class);
                intent.putExtra(FLAGSELECT, 2);
                intent.putStringArrayListExtra(SEND, list);
                startActivityForResult(intent, 0);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new PlaceDAO(getApplicationContext());

        if (dao.querySelect(true).size() != 0) {
            final List<String> list = dao.querySelect(true);
            mViewPager = new ViewPager(this);
            mViewPager.setId(R.id.ViewPager);
            setContentView(mViewPager);
            FragmentManager fragmentManager = getSupportFragmentManager();
            mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    return WeatherFragment.newInstance(list.get(position));
                }

                @Override
                public int getCount() {

                    return dao.querySelect(true).size();
                }
            });
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            // setContentView(R.layout.weather_layout);
        } else {
            setContentView(R.layout.activity_main);
            select = (Button) findViewById(R.id.select);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dao.queryQrovinceAll().size() == 0) {
                        HttpUtils.doGetAsyn(url, false, new HttpUtils.CallBack() {
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
                                Toast.makeText(MainActivity.this, "网络连接错误或选择错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Intent intent = new Intent(MainActivity.this, ProvinceActivity.class);
                        intent.putExtra(FLAGSELECT, 1);
                        startActivityForResult(intent, 0);
                        finish();
                    }

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add_city:
                Intent intent = new Intent(MainActivity.this, ProvinceActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.refush:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
