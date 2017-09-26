package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stunba.bankproject.Contracts;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/25/2017.
 */

public class DatabaseHandlerActualRate implements IDatabaseHandler {
    private RemoteDataSource remoteDataSource;
    private boolean isLoad = false;
    private DBHelper mHelper;

    DatabaseHandlerActualRate(Context context) {
        mHelper = new DBHelper(context);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void addRate(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, rate.getCurID());
        values.put(Contracts.KEY_DATE, rate.getDate());
        values.put(Contracts.KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, rate.getCurName());
        values.put(Contracts.KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        db.insertWithOnConflict(Contracts.TABLE_RATE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void getRateByAbb(final String abb, final OnTaskCompleted.LoadActualRate mainPresenterComplete) {
        final SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.query(Contracts.TABLE_RATE, new String[]{Contracts.KEY_ID,
                        Contracts.KEY_DATE, Contracts.KEY_CUR_ABB, Contracts.KEY_CUR_SCALE, Contracts.KEY_CUR_NAME, Contracts.KEY_CUR_OFF_RATE}, Contracts.KEY_CUR_ABB + "=?",
                new String[]{abb}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor.getCount() == 0) {
            if (!isLoad) {
                loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                    @Override
                    public void onLoadAllRate(List<ActualRate> o) {
                        if (o != null) {
                            for (ActualRate rate : o) {
                                addRate(rate);
                            }
                            isLoad = true;
                            cursor.close();
                            getRateByAbb(abb, mainPresenterComplete);
                        } else {
                            cursor.close();
                            mainPresenterComplete.onLoadRate(null);
                        }
                    }
                });
            } else {
                cursor.close();
                mainPresenterComplete.onLoadRate(null);
            }
        } else {
            ActualRate rate = new ActualRate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4), Double.parseDouble(cursor.getString(5)));
            cursor.close();
            mainPresenterComplete.onLoadRate(rate);
        }
    }

    @Override
    public void getAllRates(final OnTaskCompleted.LoadAllActualRate mainPresenter) {
        List<ActualRate> rateList = new ArrayList<ActualRate>();
        String selectQuery = "SELECT * FROM " + Contracts.TABLE_RATE;
        final SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                @Override
                public void onLoadAllRate(List<ActualRate> o) {
                    if (o != null) {
                        for (ActualRate rate : o) {
                            addRate(rate);
                        }
                        cursor.close();
                        getAllRates(mainPresenter);
                    } else {
                        cursor.close();
                        mainPresenter.onLoadAllRate(null);
                    }
                }

            });
        } else {
            if (cursor.moveToFirst()) {
                do {
                    ActualRate rate = new ActualRate();
                    rate.setCurID(Integer.parseInt(cursor.getString(0)));
                    rate.setDate(cursor.getString(1));
                    rate.setCurAbbreviation(cursor.getString(2));
                    rate.setCurScale(Integer.parseInt(cursor.getString(3)));
                    rate.setCurName(cursor.getString(4));
                    rate.setCurOfficialRate(Double.parseDouble(cursor.getString(5)));
                    rateList.add(rate);
                } while (cursor.moveToNext());
            }
            cursor.close();
            mainPresenter.onLoadAllRate(rateList);
        }
    }

    public void loadAllRate(OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        deleteAll();
        remoteDataSource.getAllRates(loadAllActualRate);
    }

    @Override
    public int updateRate(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_DATE, rate.getDate());
        values.put(Contracts.KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, rate.getCurName());
        values.put(Contracts.KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        return db.update(Contracts.TABLE_RATE, values, Contracts.KEY_ID + " = ?",
                new String[]{String.valueOf(rate.getCurID())});
    }

    @Override
    public void deleteRate(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_RATE, Contracts.KEY_ID + " = ?", new String[]{String.valueOf(rate.getCurID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_RATE, null, null);
        db.close();
    }


    public void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate calculatePresenterComplete) {
        remoteDataSource.getRateByDate(val, date, calculatePresenterComplete);
    }
}
