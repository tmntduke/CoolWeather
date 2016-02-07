package com.example.tmnt.coolweather.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.tmnt.coolweather.MainActivity;
import com.example.tmnt.coolweather.R;

/**
 * Created by tmnt on 2016/2/7.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences get=getSharedPreferences("data",MODE_PRIVATE);
        String name=get.getString("countName","");
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, i, 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification_layout);
        views.setImageViewResource(R.id.notification_ico, R.drawable.cloudy1_night);
        views.setTextViewText(R.id.county_name, name);
        views.setTextViewText(R.id.praShow, "-7°C");
        Notification.Builder show = new Notification.Builder(getApplicationContext());
        show.setSmallIcon(R.drawable.hail);
        show.setContent(views);
        show.setContentIntent(pendingIntent);
        Notification notification = show.build();
        //manager.notify(001, notification);
        startForeground(001,notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
