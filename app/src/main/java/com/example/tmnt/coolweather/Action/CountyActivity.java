package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.tmnt.coolweather.Model.County;
import com.example.tmnt.coolweather.R;
import com.example.tmnt.coolweather.Utils.HttpUtils;
import com.example.tmnt.coolweather.Utils.SpiltUtils;
import com.example.tmnt.coolweather.Utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private ProgressDialog dialog;
    private boolean flag = false;
    private String name;
    private String countyId;
    private String weather;
    String place = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.county_list);
        countyList = (ListView) findViewById(R.id.coounty_list);
        dao = new PlaceDAO(getApplicationContext());
        Intent intent = getIntent();
        dialog = new ProgressDialog(CountyActivity.this);
        int flag = intent.getIntExtra(CityActivity.CITYFLAG, 0);
        if (flag == 1) {
            list = intent.getStringArrayListExtra(CityActivity.CITY);
        } else if (flag == 2) {
            String id = intent.getStringExtra(CityActivity.CITY);
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

                name = list.get(position);
                countyId = dao.queryCounty(name);
                dao.updateSelect(true, countyId);
                if (dao.querWeatherId(countyId) == null) {
                    new MyAsyncTask().execute("first");
                } else {
                    new MyAsyncTask().execute("second");
                }
            }
        });
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Intent intent = new Intent(CountyActivity.this, MainActivity.class);
            intent.putExtra("county", place);
            startActivity(intent);
            finish();

        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {

            if (params[0].equals("first")) {
                String url = "http://www.weather.com.cn/data/list3/city" + countyId + ".xml";
                HttpUtils.doGetAsyn(url, false, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(byte[] result) {
                        String place = StringUtils.toStrings(result);
                        Map<String, String> map = SpiltUtils.returnProvince(place);
                        Set<Map.Entry<String, String>> set = map.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            weather = entry.getKey();
                            dao.insertWeather(entry.getValue(), name, entry.getKey());
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(CountyActivity.this, "网络连接错误或选择错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            String weatherURL = "http://apis.baidu.com/apistore/weatherservice/cityid?cityid=" + dao.querWeatherId(countyId);
            HttpUtils.doGetAsyn(weatherURL, true, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(byte[] result) {
                    place = StringUtils.toStrings(result);
                }

                @Override
                public void onError(Exception e) {

                }
            });


            return place;
        }
    }


}