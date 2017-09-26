package com.example.stunba.bankproject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.stunba.bankproject.Settings;
import com.example.stunba.bankproject.activity.DynamicActivity;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.Repository;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/31/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d("TAG", "Start");
        Repository.getInstance(getBaseContext()).updateAllCurrencies();
        Repository.getInstance(getBaseContext()).updateAllRates(new OnTaskCompleted.LoadSuccessfully() {
            @Override
            public void onLoadSuccess(boolean o) {
                Repository.getInstance(getBaseContext()).updateFavorites(new OnTaskCompleted.LoadFavoriteMap() {
                    @Override
                    public void onLoadMap(Map<String, Double> changes) {
                        if (changes != null) {
                            for (Map.Entry<String, Double> entry : changes.entrySet()) {
                                sendNotification(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                });
            }
        });
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancel(Settings.SERVICE_ID);
        JobInfo.Builder builder = new JobInfo.Builder(Settings.SERVICE_ID,
                new ComponentName(getPackageName(),
                        JobSchedulerService.class.getName()));
        int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        long time = Settings.CALENDAR.getTimeInMillis();
        Settings.CALENDAR.set(Calendar.DAY_OF_MONTH, day + 1);
        long timeCall = Settings.CALENDAR.getTimeInMillis();
        Settings.CALENDAR.set(Calendar.DAY_OF_MONTH, day);
        builder.setOverrideDeadline(timeCall - time);
        mJobScheduler.schedule(builder.build());
        return true;
    }

    private void sendNotification(String key, Double value) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_date_range)
                        .setContentTitle(key + " cheaper!!!!!!! ")
                        .setContentText("View statistics for 3 months");
        Intent intent = new Intent(this, DynamicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Settings.ABBREVIATION, key);
        intent.putExtra(Settings.CHEAPER, value);
        Settings.COUNT++;
        intent.putExtra(Settings.NOTIFICATION_ID, Settings.COUNT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DynamicActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        Settings.COUNT,
                        0
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(Settings.COUNT, mBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG", "Stop");
        return false;
    }

}
