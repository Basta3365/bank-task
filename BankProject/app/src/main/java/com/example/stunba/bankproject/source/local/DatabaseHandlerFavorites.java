package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class DatabaseHandlerFavorites extends SQLiteOpenHelper implements IDatabaseHandlerFavorites {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_CUR_ABB = "curAbbreviation";
    private static final String KEY_CUR_SCALE = "curScale";
    private static final String KEY_CUR_NAME = "curName";
    private static final String KEY_CUR_OFF_RATE = "curOfficialRate";


    public DatabaseHandlerFavorites(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RATE_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_CUR_ABB + " TEXT," + KEY_CUR_SCALE + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_OFF_RATE + " TEXT" + ")";
        db.execSQL(CREATE_RATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }
    @Override
    public void addFavorite(ActualRate rate) {
        if(isTableExists(TABLE_FAVORITES)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, rate.getCurID());
            values.put(KEY_DATE, rate.getDate());
            values.put(KEY_CUR_ABB, rate.getCurAbbreviation());
            values.put(KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
            values.put(KEY_CUR_NAME, rate.getCurName());
            values.put(KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
            db.insert(TABLE_FAVORITES, null, values);
            db.close();
        }else {
            onCreate(this.getWritableDatabase());
            addFavorite(rate);
        }
    }

    @Override
    public void getFavorite(int id, OnTaskCompleted.FavoritePresenter mainPresenterComplete) {
        if (isTableExists(TABLE_FAVORITES)) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_FAVORITES, new String[]{KEY_ID,
                            KEY_DATE, KEY_CUR_ABB, KEY_CUR_SCALE, KEY_CUR_NAME, KEY_CUR_OFF_RATE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.getCount() == 0) {
               mainPresenterComplete.onAllFavorites(null);
            } else {
                ActualRate rate = new ActualRate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4), Double.parseDouble(cursor.getString(5)));
                mainPresenterComplete.onAllFavorites(rate);
            }
        } else {
            onCreate(this.getWritableDatabase());
            getFavorite(id, mainPresenterComplete);
        }
    }


    @Override
    public void getAllFavorites(OnTaskCompleted.FavoritePresenter mainPresenterComplete) {
        List<ActualRate> rateList = new ArrayList<ActualRate>();
        if (isTableExists(TABLE_FAVORITES)) {
            String selectQuery = "SELECT * FROM " + TABLE_FAVORITES;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                mainPresenterComplete.onAllFavorites(null);
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
                    mainPresenterComplete.onAllFavorites(rateList);
                }
            }
        }else {
            onCreate(this.getWritableDatabase());
            getAllFavorites(mainPresenterComplete);
        }
    }

    @Override
    public int getFavoritesCount() {
        String countQuery = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateFavorite(ActualRate rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, rate.getDate());
        values.put(KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(KEY_CUR_NAME, rate.getCurName());
        values.put(KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        return db.update(TABLE_FAVORITES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(rate.getCurID())});
    }

    @Override
    public void deleteFavorite(ActualRate rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_ID + " = ?", new String[]{String.valueOf(rate.getCurID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, null, null);
        db.close();
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