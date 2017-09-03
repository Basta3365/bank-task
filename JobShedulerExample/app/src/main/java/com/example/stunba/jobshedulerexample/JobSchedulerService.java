package com.example.stunba.jobshedulerexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by stunba on 9/3/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage( Message msg ) {
            Toast.makeText( getApplicationContext(),
                    "JobService task running", Toast.LENGTH_SHORT )
                    .show();
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    } );

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("TAG","Start");
        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG","Stop");
        mJobHandler.removeMessages( 1 );
        return false;
    }

}
