package com.example.tmnt.coolweather.Action;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tmnt on 2016/2/6.
 */
public class WeatherFragment extends Fragment {
    public static final String RETURNFRAGMENTID = "returnFragmentId";
    private String countyId;
    private TextView countyName, countyCount, weatherNum, weatherShow, weatherPraShow, wind, day1, day2, day3, day4;
    private PlaceDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        countyId = (String) getArguments().getSerializable(RETURNFRAGMENTID);
        dao = new PlaceDAO(getActivity().getApplicationContext());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_layout, null, false);
        countyName = (TextView) view.findViewById(R.id.countyName);
        countyCount = (TextView) view.findViewById(R.id.countyCount);
        weatherNum = (TextView) view.findViewById(R.id.weatherNum);
        weatherShow = (TextView) view.findViewById(R.id.weatherShow);
        weatherPraShow = (TextView) view.findViewById(R.id.weatherPraShow);
        wind = (TextView) view.findViewById(R.id.wind);
        day1 = (TextView) view.findViewById(R.id.day1);
        day2 = (TextView) view.findViewById(R.id.day2);
        day3 = (TextView) view.findViewById(R.id.day3);
        day4 = (TextView) view.findViewById(R.id.day4);
        countyName.setText(dao.queryCountyName(countyId));
        day1.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 0));
        day2.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 1));
        day3.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 2));
        day4.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 3));
        return view;
    }

    public static WeatherFragment newInstance(String countyId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RETURNFRAGMENTID, countyId);
        WeatherFragment fragmentCrime = new WeatherFragment();
        fragmentCrime.setArguments(bundle);
        return fragmentCrime;
    }

    public static String getWeekOfDate(Date date, int count) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = (calendar.get(Calendar.DAY_OF_WEEK) - 1) - count;
        return weekDaysName[intWeek];
    }

}
