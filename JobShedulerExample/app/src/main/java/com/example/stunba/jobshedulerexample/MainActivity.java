package com.example.stunba.jobshedulerexample;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private JobScheduler mJobScheduler;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler = (JobScheduler)
                getSystemService( Context.JOB_SCHEDULER_SERVICE );
        JobInfo.Builder builder = new JobInfo.Builder( 1,
                new ComponentName( getPackageName(),
                        JobSchedulerService.class.getName() ) );
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+1);
        builder.setOverrideDeadline(calendar.getTimeInMillis());
        if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
            //If something goes wrong
        }
    }
}
