package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stunba.bankproject.Contracts;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class DatabaseHandlerFavorites implements IDatabaseHandlerFavorites {

    private DBHelper mHelper;

    DatabaseHandlerFavorites(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void addFavorite(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, rate.getCurID());
        values.put(Contracts.KEY_DATE, rate.getDate());
        values.put(Contracts.KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, rate.getCurName());
        values.put(Contracts.KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        db.insertWithOnConflict(Contracts.TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


    @Override
    public void getAllFavorites(OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        List<ActualRate> rateList = new ArrayList<ActualRate>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Contracts.TABLE_FAVORITES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            loadAllActualRate.onLoadAllRate(null);
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
                cursor.close();
                loadAllActualRate.onLoadAllRate(rateList);
            }
        }
    }

    @Override
    public int updateFavorite(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_DATE, rate.getDate());
        values.put(Contracts.KEY_CUR_ABB, rate.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(rate.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, rate.getCurName());
        values.put(Contracts.KEY_CUR_OFF_RATE, String.valueOf(rate.getCurOfficialRate()));
        return db.update(Contracts.TABLE_FAVORITES, values, Contracts.KEY_ID + " = ?",
                new String[]{String.valueOf(rate.getCurID())});
    }

    @Override
    public void deleteFavorite(ActualRate rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_FAVORITES, Contracts.KEY_ID + " = ?", new String[]{String.valueOf(rate.getCurID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_FAVORITES, null, null);
        db.close();
    }
}
