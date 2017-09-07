package com.example.stunba.bankproject.service;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.Repository;

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
                            sendNotification();
                        }
                    }
                });
            }
        });

        return true;
    }

    private void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_date_range)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(112, mBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG", "Stop");
        return false;
    }

}
