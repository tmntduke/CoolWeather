package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.content.Intent;
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
public class ProvinceActivity extends Activity {
    private ListView provinceList;
    private ArrayList<String> list, city;
    private PlaceDAO dao;
    public static final String PROVINCE = "province";
    private String provinceId;
    private String url;
    public static final String PROVICEFLAG = "provinceflag";
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
                city = new ArrayList<>();
                String returnString = (String) msg.obj;
                Map<String, String> map = SpiltUtils.returnProvince(returnString);
                Set<Map.Entry<String, String>> set = map.entrySet();
                Iterator<Map.Entry<String, String>> iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    dao.insertCity(entry.getValue(), entry.getKey(), provinceId);
                    city.add(entry.getKey());
                }
                Log.i("click Province",list.get(2));
                //String s = (String) msg.obj;
                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                intent.putStringArrayListExtra(PROVINCE, city);
                intent.putExtra(PROVICEFLAG, 1);
                startActivity(intent);
                finish();
            }
        }
    };

    /**
     * Called as part of the activity lifecycle when an activity is going into
     * the background, but has not (yet) been killed.  The counterpart to
     * {@link #onResume}.
     * <p/>
     * <p>When activity B is launched in front of activity A, this callback will
     * be invoked on A.  B will not be created until A's {@link #onPause} returns,
     * so be sure to not do anything lengthy here.
     * <p/>
     * <p>This callback is mostly used for saving any persistent state the
     * activity is editing, to present a "edit in place" model to the user and
     * making sure nothing is lost if there are not enough resources to start
     * the new activity without first killing this one.  This is also a good
     * place to do things like stop animations and other things that consume a
     * noticeable amount of CPU in order to make the switch to the next activity
     * as fast as possible, or to close resources that are exclusive access
     * such as the camera.
     * <p/>
     * <p>In situations where the system needs more memory it may kill paused
     * processes to reclaim resources.  Because of this, you should be sure
     * that all of your state is saved by the time you return from
     * this function.  In general {@link #onSaveInstanceState} is used to save
     * per-instance state in the activity and this method is used to store
     * global persistent data (in content providers, files, etc.)
     * <p/>
     * <p>After receiving this call you will usually receive a following call
     * to {@link #onStop} (after the next activity has been resumed and
     * displayed), however in some cases there will be a direct call back to
     * {@link #onResume} without going through the stopped state.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onStop
     */
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_layout);
        provinceList = (ListView) findViewById(R.id.province_list);
        dao = new PlaceDAO(getApplicationContext());
        Intent intent = getIntent();
        int flag = intent.getIntExtra(MainActivity.FLAGSELECT, 0);
        if (flag == 2) {
            list = intent.getStringArrayListExtra(MainActivity.SEND);
        } else  {
            List<Province> provinces = dao.queryQrovinceAll();
            list = new ArrayList<>();
            Log.i("database", provinces.get(2).getPrivince_name());
            for (int i = 0; i < provinces.size(); i++) {
                String name = provinces.get(i).getPrivince_name();
                list.add(name);
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        provinceList.setAdapter(adapter);
        provinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceId = dao.queryProvince(list.get(position));
                MainActivity.finishActivity();
                url = "http://www.weather.com.cn/data/list3/city" + provinceId + ".xml";
                if (!dao.isHasCity(provinceId)) {

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
                            Toast.makeText(ProvinceActivity.this, "网络连接错误或选择错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                    intent.putExtra(PROVINCE, provinceId);
                    intent.putExtra(PROVICEFLAG, 2);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }


}
