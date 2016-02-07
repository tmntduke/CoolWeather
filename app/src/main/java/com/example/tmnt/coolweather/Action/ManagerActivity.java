package com.example.tmnt.coolweather.Action;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tmnt.coolweather.Adapter.MyAdapter;
import com.example.tmnt.coolweather.DAO.PlaceDAO;
import com.example.tmnt.coolweather.MainActivity;
import com.example.tmnt.coolweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmnt on 2016/2/7.
 */
public class ManagerActivity extends Activity {
    private GridView gridView;
    private PlaceDAO dao;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
        MainActivity.finishActivity();
        gridView = (GridView) findViewById(R.id.gridView);
        dao = new PlaceDAO(getApplicationContext());
        list = dao.querySelect(true);
        final MyAdapter myAdapter = new MyAdapter(getApplicationContext(), list);
        Log.i("manager", "start");
        gridView.setAdapter(myAdapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                //Toast.makeText(ManagerActivity.this,"hhahahah",Toast.LENGTH_SHORT).show();
                //Log.i("actionmode","itemchange");
                SharedPreferences.Editor editor = getSharedPreferences("delete", MODE_PRIVATE).edit();
                editor.putInt("position", position);
                editor.commit();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.delete_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //Log.i("actionmode","actionclick");
                switch (item.getItemId()) {
                    case R.id.delete_menu:
                        SharedPreferences get = getSharedPreferences("delete", MODE_PRIVATE);
                        int i = get.getInt("position", 0);
                        //list.remove(i);
                        dao.updateSelect(false, list.get(i));
                        myAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(ManagerActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        myAdapter.notifyDataSetChanged();
    }


}
