package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stunba.bankproject.Contracts;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandlerMetalRate implements IDatabaseHandlerMetalRate {

    private DBHelper mHelper;
    private static int count = 0;
    private RemoteDataSource remoteDataSource;

    public DatabaseHandlerMetalRate(Context context) {
        mHelper = new DBHelper(context);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void addIngot(ActualAllIngot allIngot) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, count++);
        values.put(Contracts.KEY_ID_METAL, allIngot.getMetalID());
        values.put(Contracts.KEY_DATE, allIngot.getDate());
        values.put(Contracts.KEY_NOMINAL, allIngot.getNominal());
        values.put(Contracts.KEY_NO_CER_DOL, allIngot.getNoCertificateDollars());
        values.put(Contracts.KEY_NO_CER_RUB, allIngot.getNoCertificateRubles());
        values.put(Contracts.KEY_CER_DOL, allIngot.getCertificateDollars());
        values.put(Contracts.KEY_CER_RUB, allIngot.getCertificateRubles());
        values.put(Contracts.KEY_BANKS_DOL, allIngot.getBanksDollars());
        values.put(Contracts.KEY_BANKS_RUB, allIngot.getBanksRubles());
        values.put(Contracts.KEY_ENTITIES_DOL, allIngot.getEntitiesDollars());
        values.put(Contracts.KEY_ENTITIES_RUB, allIngot.getEntitiesRubles());
        db.insertWithOnConflict(Contracts.TABLE_INGOTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @Override
    public void getIngot(final int id, final OnTaskCompleted.MetalLoad onTaskCompleted) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.query(Contracts.TABLE_INGOTS, new String[]{Contracts.KEY_ID, Contracts.KEY_ID_METAL,
                        Contracts.KEY_DATE, Contracts.KEY_NO_CER_DOL, Contracts.KEY_NO_CER_RUB, Contracts.KEY_CER_DOL, Contracts.KEY_CER_RUB, Contracts.KEY_BANKS_DOL, Contracts.KEY_BANKS_RUB, Contracts.KEY_ENTITIES_DOL, Contracts.KEY_ENTITIES_RUB}, Contracts.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor.getCount() == 0) {
            loadAllIngots(new OnTaskCompleted.MetalLoadAll() {
                @Override
                public void onAllIngot(List<ActualAllIngot> o) {
                    for (ActualAllIngot rate : o) {
                        addIngot(rate);
                    }
                    cursor.close();
                    getIngot(id, onTaskCompleted);
                }
            });
        } else {
            ActualAllIngot rate = new ActualAllIngot(cursor.getString(0), Integer.parseInt(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
            cursor.close();
            onTaskCompleted.onIngot(rate);
        }
    }

    @Override
    public void getAllIngots(final OnTaskCompleted.MetalLoadAll onTaskCompleted) {
        List<ActualAllIngot> rateList = new ArrayList<ActualAllIngot>();
        String selectQuery = "SELECT * FROM " + Contracts.TABLE_INGOTS;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            loadAllIngots(new OnTaskCompleted.MetalLoadAll() {
                @Override
                public void onAllIngot(List<ActualAllIngot> o) {
                    if (o != null) {
                        for (ActualAllIngot rate : o) {
                            addIngot(rate);
                        }
                        cursor.close();
                        getAllIngots(onTaskCompleted);
                    } else {
                        cursor.close();
                        onTaskCompleted.onAllIngot(null);
                    }
                }
            });
        } else {
            if (cursor.moveToFirst()) {
                do {
                    ActualAllIngot rate = new ActualAllIngot();
                    rate.setId(cursor.getInt(0));
                    rate.setMetalID(cursor.getInt(1));
                    rate.setDate(cursor.getString(2));
                    rate.setNominal(Double.parseDouble(cursor.getString(3)));
                    rate.setNoCertificateDollars(cursor.getString(4));
                    rate.setNoCertificateRubles(cursor.getString(5));
                    rate.setCertificateDollars(cursor.getString(6));
                    rate.setCertificateRubles(cursor.getString(7));
                    rate.setBanksDollars(cursor.getString(8));
                    rate.setBanksRubles(cursor.getString(9));
                    rate.setEntitiesDollars(cursor.getString(10));
                    rate.setEntitiesRubles(cursor.getString(11));
                    rateList.add(rate);
                } while (cursor.moveToNext());
            }
            cursor.close();
            onTaskCompleted.onAllIngot(rateList);
        }
    }

    private void loadAllIngots(OnTaskCompleted.MetalLoadAll loadComplete) {
        deleteAll();
        remoteDataSource.getAllIngots(loadComplete);
    }

    @Override
    public int updateIngot(ActualAllIngot allIngot) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID_METAL, allIngot.getMetalID());
        values.put(Contracts.KEY_DATE, allIngot.getDate());
        values.put(Contracts.KEY_NOMINAL, allIngot.getNominal());
        values.put(Contracts.KEY_NO_CER_DOL, allIngot.getNoCertificateDollars());
        values.put(Contracts.KEY_NO_CER_RUB, allIngot.getNoCertificateRubles());
        values.put(Contracts.KEY_CER_DOL, allIngot.getCertificateDollars());
        values.put(Contracts.KEY_CER_RUB, allIngot.getCertificateRubles());
        values.put(Contracts.KEY_BANKS_DOL, allIngot.getBanksDollars());
        values.put(Contracts.KEY_BANKS_RUB, allIngot.getBanksRubles());
        values.put(Contracts.KEY_ENTITIES_DOL, allIngot.getEntitiesDollars());
        values.put(Contracts.KEY_ENTITIES_RUB, allIngot.getEntitiesRubles());
        return db.update(Contracts.TABLE_INGOTS, values, Contracts.KEY_ID + " = ?",
                new String[]{String.valueOf(allIngot.getId())});
    }

    @Override
    public void deleteIngot(ActualAllIngot rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_INGOTS, Contracts.KEY_ID + " = ?", new String[]{String.valueOf(rate.getMetalID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_INGOTS, null, null);
        db.close();
    }

}
