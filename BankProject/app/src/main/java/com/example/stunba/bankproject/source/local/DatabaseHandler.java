package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bankManager";
    private static final String TABLE_RATE = "actual";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_CUR_ABB = "curAbbreviation";
    private static final String KEY_CUR_SCALE = "curScale";
    private static final String KEY_CUR_NAME = "curName";
    private static final String KEY_CUR_OFF_RATE = "curOfficialRate";
    private RemoteDataSource remoteDataSource;
    private boolean isLoad = false;


    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RATE_TABLE = "CREATE TABLE " + TABLE_RATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_CUR_ABB + " TEXT," + KEY_CUR_SCALE + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_OFF_RATE + " TEXT" + ")";
        db.execSQL(CREATE_RATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE);
        onCreate(db);
    }

    @Override
    public void addRate(ActualRate rate) {
        if (isTableExists(TABLE_RATE)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, rate.getCurID());
            values.put(KEY_DATE, rate.getDate());
            values.put(KEY_CUR_ABB, rate.getCurAbbreviation());
            values.put(KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
            values.put(KEY_CUR_NAME, rate.getCurName());
            values.put(KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
            db.insertWithOnConflict(TABLE_RATE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
        } else {
            onCreate(this.getWritableDatabase());
            addRate(rate);
        }
    }

    public void getRateByAbb(final String abb, final OnTaskCompleted.LoadActualRate mainPresenterComplete) {
        if (isTableExists(TABLE_RATE)) {
            SQLiteDatabase db = this.getReadableDatabase();
            final Cursor cursor = db.query(TABLE_RATE, new String[]{KEY_ID,
                            KEY_DATE, KEY_CUR_ABB, KEY_CUR_SCALE, KEY_CUR_NAME, KEY_CUR_OFF_RATE}, KEY_CUR_ABB + "=?",
                    new String[]{abb}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.getCount() == 0) {
                if (!isLoad) {
                    loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                        @Override
                        public void onLoadAllRate(List<ActualRate> o) {
                            for (ActualRate rate :  o) {
                                addRate(rate);
                            }
                            isLoad = true;
                            cursor.close();
                            getRateByAbb(abb, mainPresenterComplete);
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
        } else {
            onCreate(this.getWritableDatabase());
            loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                @Override
                public void onLoadAllRate(List<ActualRate> o) {
                    for (ActualRate rate : o) {
                        addRate(rate);
                    }
                    getRateByAbb(abb, mainPresenterComplete);
                }
            });
        }
    }

    @Override
    public void getAllRates(final OnTaskCompleted.LoadAllActualRate mainPresenter) {
        List<ActualRate> rateList = new ArrayList<ActualRate>();
        if (isTableExists(TABLE_RATE)) {
            String selectQuery = "SELECT * FROM " + TABLE_RATE;
            SQLiteDatabase db = this.getWritableDatabase();
            final Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                    @Override
                    public void onLoadAllRate(List<ActualRate> o) {
                        for (ActualRate rate : o) {
                            addRate(rate);
                        }
                        cursor.close();
                        getAllRates(mainPresenter);
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
        } else {
            onCreate(this.getWritableDatabase());
            loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
                @Override
                public void onLoadAllRate(List<ActualRate> o) {
                    for (ActualRate rate : o) {
                        addRate(rate);
                    }
                    getAllRates(mainPresenter);
                }
            });
        }
    }

    public void loadAllRate(OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        deleteAll();
        remoteDataSource.getAllRates(loadAllActualRate);
    }

    @Override
    public int updateRate(ActualRate rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, rate.getDate());
        values.put(KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(KEY_CUR_NAME, rate.getCurName());
        values.put(KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        return db.update(TABLE_RATE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(rate.getCurID())});
    }

    @Override
    public void deleteRate(ActualRate rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RATE, KEY_ID + " = ?", new String[]{String.valueOf(rate.getCurID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RATE, null, null);
        db.close();
    }


    public void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate calculatePresenterComplete) {
        remoteDataSource.getRateByDate(val, date, calculatePresenterComplete);
    }

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
