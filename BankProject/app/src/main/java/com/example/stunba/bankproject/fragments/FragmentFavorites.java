package com.example.stunba.bankproject.fragments;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.service.JobSchedulerService;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class FragmentFavorites extends Fragment {
    private View view;
    private JobScheduler mJobScheduler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_favorites, container, false);
        Repository repository=Repository.getInstance(getContext());
        repository.getRateByAbb("USD", new OnTaskCompleted.MainPresenterComplete() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLoadRate(Object o) {
                ActualRate actualRate= (ActualRate) o;
                mJobScheduler = (JobScheduler)getContext().getSystemService( Context.JOB_SCHEDULER_SERVICE );
//                mJobScheduler.cancelAll();
                JobInfo.Builder builder = new JobInfo.Builder( 1,
                        new ComponentName( getContext().getPackageName(),
                                JobSchedulerService.class.getName() ) );
                builder.setOverrideDeadline(5000);
                mJobScheduler.schedule(builder.build());
            }
        });
        return view;
    }
}
