package com.example.stunba.bankproject;

import com.example.stunba.bankproject.source.remote.IBankAPI;

import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public class Settings {
    public final static IBankAPI RETROFIT = new Retrofit.Builder()
            .baseUrl("http://www.nbrb.by/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IBankAPI.class);
    public static int COUNT = 100;
    public final static Calendar CALENDAR = Calendar.getInstance();

    public static String getDate(int year, int month, int dayOfMonth) {
        String sMonth = String.valueOf(month + 1);
        String sDay = String.valueOf(dayOfMonth);
        return year + "-" + sMonth + "-" + sDay;
    }

//    mJobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                mJobScheduler.cancelAll();
//    JobInfo.Builder builder = new JobInfo.Builder( 1,
//            new ComponentName( getPackageName(),
//                    JobSchedulerService.class.getName() ) );
//    int hour= Settings.CALENDAR.get(Calendar.HOUR);
//    int minute= Settings.CALENDAR.get(Calendar.MINUTE);
//    int day=Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
//        if(hour<12){
//        long time=Settings.CALENDAR.getTimeInMillis();
//        Settings.CALENDAR.set(Calendar.HOUR,12);
//        Settings.CALENDAR.set(Calendar.MINUTE,0);
//        long timeCall=Settings.CALENDAR.getTimeInMillis();
//        Settings.CALENDAR.set(Calendar.HOUR,hour);
//        Settings.CALENDAR.set(Calendar.MINUTE,minute);
//        builder.setOverrideDeadline(timeCall-time);
//    }else {
//        long time=Settings.CALENDAR.getTimeInMillis();
//        Settings.CALENDAR.set(Calendar.DAY_OF_MONTH,day+1);
//        Settings.CALENDAR.set(Calendar.HOUR,12);
//        Settings.CALENDAR.set(Calendar.MINUTE,0);
//        long timeCall=Settings.CALENDAR.getTimeInMillis();
//        Settings.CALENDAR.set(Calendar.HOUR,hour);
//        Settings.CALENDAR.set(Calendar.MINUTE,minute);
//        Settings.CALENDAR.set(Calendar.DAY_OF_MONTH,day);
//        builder.setOverrideDeadline(timeCall-time);
//    }
//                mJobScheduler.schedule(builder.build());
}
