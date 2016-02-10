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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tmnt on 2016/2/6.
 */
public class WeatherFragment extends Fragment {
    public static final String RETURNFRAGMENTID = "returnFragmentId";
    private String countyId;
    private TextView countyName, countyCount, weatherNum, weatherShow, weatherPraShow, wind, day1, day2, day3, day4;
    private ImageView weatherImage1;
    private com.example.tmnt.coolweather.Dview.TextViewPlus tp1,tp2,tp3,tp4;
    private PlaceDAO dao;
    public static final String POSITION = "position";
    private int postition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        countyId = (String) getArguments().getSerializable(RETURNFRAGMENTID);

        dao = new PlaceDAO(getActivity().getApplicationContext());
        postition = (int) getArguments().getSerializable(POSITION);
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
        weatherImage1=(ImageView)view.findViewById(R.id.weatherICO);
        tp1=(com.example.tmnt.coolweather.Dview.TextViewPlus)view.findViewById(R.id.day1ICO);
        tp2=(com.example.tmnt.coolweather.Dview.TextViewPlus)view.findViewById(R.id.day2ICO);
        tp3=(com.example.tmnt.coolweather.Dview.TextViewPlus)view.findViewById(R.id.day3ICO);
        tp4=(com.example.tmnt.coolweather.Dview.TextViewPlus)view.findViewById(R.id.day4ICO);
        wind = (TextView) view.findViewById(R.id.wind);
        day1 = (TextView) view.findViewById(R.id.day1);
        day2 = (TextView) view.findViewById(R.id.day2);
        day3 = (TextView) view.findViewById(R.id.day3);
        day4 = (TextView) view.findViewById(R.id.day4);
        countyName.setText(dao.queryCountyName(countyId));
        countyCount.setText((postition + 1) + "/" + dao.querySelect(true).size());
        day1.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 0));
        day2.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 1));
        day3.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 2));
        day4.setText(getWeekOfDate(new Date(System.currentTimeMillis()), 3));

        return view;
    }

    public static WeatherFragment newInstance(String countyId, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RETURNFRAGMENTID, countyId);
        bundle.putSerializable(POSITION, position);
        WeatherFragment fragmentCrime = new WeatherFragment();
        fragmentCrime.setArguments(bundle);
        return fragmentCrime;
    }

    public static String getWeekOfDate(Date date, int count) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, (-count));
        Date date1 = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String s = simpleDateFormat.format(date1);
        String ss=null;
        if (s.equals("Monday")) {
            ss = weekDaysName[1];
        } else if (s.equals("Sunday")) {
            ss = weekDaysName[0];
        } else if (s.equals("Saturday")) {
            ss = weekDaysName[6];
        } else if (s.equals("Friday")) {
            ss = weekDaysName[5];
        } else if (s.equals("Thursday")) {
            ss = weekDaysName[4];
        } else if (s.equals("Wednesday")) {
            ss = weekDaysName[3];
        } else if (s.equals("Tuesday")) {
            ss = weekDaysName[2];
        }

        return ss;
    }

}
