package com.example.stunba.bankproject.presenters;


import android.os.Bundle;

import com.example.stunba.bankproject.presenters.ipresenters.BaseInterface;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public class PresenterManager {

    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
    private static PresenterManager instance;
    private final Cache<Long,BaseInterface> presenters;
    private final AtomicLong currentId;


    PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();
        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();

    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }


    public BaseInterface restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
        BaseInterface presenter = presenters.getIfPresent(presenterId);
        presenters.invalidate(presenterId);
        return presenter;

    }


    public void savePresenter(BaseInterface presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
    }

}
