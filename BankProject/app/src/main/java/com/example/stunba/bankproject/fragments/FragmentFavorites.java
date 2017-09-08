package com.example.stunba.bankproject.fragments;

import android.app.job.JobScheduler;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.stunba.bankproject.FavoriteSceen;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.presenters.FavoriteScreenPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class FragmentFavorites extends Fragment implements FavoriteSceen.FavoriteView {
    private View view;
    private RecyclerView mRecyclerView;
    private JobScheduler mJobScheduler;
    private FavoriteScreenPresenter presenter;
    private Button buttonAdd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_favorites, container, false);
        if (savedInstanceState == null) {
            presenter = new FavoriteScreenPresenter(getContext(),this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        initView();
        presenter.loadInfo();
        return view;
//        Repository repository=Repository.getInstance(getContext());
//        repository.getRateByAbb("EUR", new OnTaskCompleted.MainPresenterComplete() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onLoadRate(Object o) {
//                //TODO kostyl
//                ActualRate actualRate= (ActualRate) o;
//                actualRate.setCurOfficialRate(3.99);
//                Repository.getInstance(getContext()).addFavorite(actualRate);
//                mJobScheduler = (JobScheduler)getContext().getSystemService( Context.JOB_SCHEDULER_SERVICE );
////                mJobScheduler.cancelAll();
//                JobInfo.Builder builder = new JobInfo.Builder( 1,
//                        new ComponentName( getContext().getPackageName(),
//                                JobSchedulerService.class.getName() ) );
//                builder.setOverrideDeadline(5000);
//                mJobScheduler.schedule(builder.build());
//            }
//        });
    }

    private void initView() {
        buttonAdd= (Button) view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addFavorite();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_favorites);
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(presenter.getRecyclerViewAdapterFavorites());
    }

    @Override
    public void showFavorite() {
        mRecyclerView.setAdapter(presenter.getRecyclerViewAdapterFavorites());
    }
}
