package com.example.stunba.bankproject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.stunba.bankproject.fragments.DynamicActivity;
import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.Repository;

import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/31/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final int NOTIFY_ID = 11;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("TAG", "Start");
//        Repository.getInstance(getBaseContext()).updateAllCurrencies();
        Repository.getInstance(getBaseContext()).updateAllRates(new OnTaskCompleted.MainPresenterComplete() {
            @Override
            public void onLoadRate(Object o) {
                Repository.getInstance(getBaseContext()).updateFavorites(new OnTaskCompleted.FavoritePresenter() {
                    @Override
                    public void onAllFavorites(Object o) {
                        if (o != null) {
                            for (Map.Entry<String,Double> entry:((Map<String,Double> )o).entrySet()){
                                sendNotification(entry.getKey(),entry.getValue());
                            }
                        }
                    }
                });
            }
        });

        return true;
    }

    private void sendNotification(String key,Double value) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_date_range)
                        .setContentTitle(key+" cheaper!!!!!!!")
                        .setContentText("View statistics for 3 months");
        Intent intent=new Intent(this,DynamicActivity.class);
        intent.putExtra("abb",key);
        intent.putExtra("cheaper",value);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DynamicActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(112, mBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG", "Stop");
        return false;
    }

}
