package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandlerMetalRate extends SQLiteOpenHelper implements IDatabaseHandlerMetalRate {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bankManager";
    private static final String TABLE_INGOTS = "ingots";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_METAL = "metalId";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOMINAL = "nominal";
    private static final String KEY_NO_CER_DOL = "noCertificateDollars";
    private static final String KEY_NO_CER_RUB = "noCertificateRubles";
    private static final String KEY_CER_DOL = "certificateDollars";
    private static final String KEY_CER_RUB = "certificateRubles";
    private static final String KEY_BANKS_DOL = "banksDollars";
    private static final String KEY_BANKS_RUB = "banksRubles";
    private static final String KEY_ENTITIES_DOL = "entitiesDollars";
    private static final String KEY_ENTITIES_RUB = "entitiesRubles";
    private static int count = 0;
    private RemoteDataSource remoteDataSource;

    public DatabaseHandlerMetalRate(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INGOT_TABLE = "CREATE TABLE " + TABLE_INGOTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_METAL + " INTEGER ," + KEY_DATE + " TEXT,"
                + KEY_NOMINAL + " TEXT," + KEY_NO_CER_DOL + " TEXT," + KEY_NO_CER_RUB + " TEXT," + KEY_CER_DOL + " TEXT," + KEY_CER_RUB + " TEXT," + KEY_BANKS_DOL + " TEXT," + KEY_BANKS_RUB + " TEXT," + KEY_ENTITIES_DOL + " TEXT," + KEY_ENTITIES_RUB + " TEXT" + ")";
        db.execSQL(CREATE_INGOT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGOTS);
        onCreate(db);
    }


    @Override
    public void addIngot(ActualAllIngot allIngot) {
        if (isTableExists(TABLE_INGOTS)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, count++);
            values.put(KEY_ID_METAL, allIngot.getMetalID());
            values.put(KEY_DATE, allIngot.getDate());
            values.put(KEY_NOMINAL, String.valueOf(allIngot.getNominal()));
            values.put(KEY_NO_CER_DOL, String.valueOf(allIngot.getNoCertificateDollars()));
            values.put(KEY_NO_CER_RUB, String.valueOf(allIngot.getNoCertificateRubles()));
            values.put(KEY_CER_DOL, String.valueOf((allIngot.getCertificateDollars())));
            values.put(KEY_CER_RUB, String.valueOf(allIngot.getCertificateRubles()));
            values.put(KEY_BANKS_DOL, String.valueOf(allIngot.getBanksDollars()));
            values.put(KEY_BANKS_RUB, String.valueOf(allIngot.getBanksRubles()));
            values.put(KEY_ENTITIES_DOL, String.valueOf(allIngot.getEntitiesDollars()));
            values.put(KEY_ENTITIES_RUB, String.valueOf(allIngot.getEntitiesRubles()));
            db.insertWithOnConflict(TABLE_INGOTS, null, values,SQLiteDatabase.CONFLICT_REPLACE );
            db.close();
        } else {
            onCreate(this.getWritableDatabase());
            addIngot(allIngot);
        }
    }

    @Override
    public void getIngot(final int id, final OnTaskCompleted.LoadComplete onTaskCompleted) {
        if (isTableExists(TABLE_INGOTS)) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_INGOTS, new String[]{KEY_ID, KEY_ID_METAL,
                            KEY_DATE, KEY_NO_CER_DOL, KEY_NO_CER_RUB, KEY_CER_DOL, KEY_CER_RUB, KEY_BANKS_DOL, KEY_BANKS_RUB, KEY_ENTITIES_DOL, KEY_ENTITIES_RUB}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.getCount() == 0) {
                loadAllIngots(new OnTaskCompleted.LoadComplete() {
                    @Override
                    public void onLoadComplete(Object o) {
                        for (ActualAllIngot rate : (List<ActualAllIngot>) o) {
                            addIngot(rate);
                        }
                        getIngot(id, onTaskCompleted);
                    }
                });
            } else {
                ActualAllIngot rate = new ActualAllIngot(cursor.getString(0), Integer.parseInt(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
                onTaskCompleted.onLoadComplete(rate);
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllIngots(new OnTaskCompleted.LoadComplete() {
                @Override
                public void onLoadComplete(Object o) {
                    for (ActualAllIngot rate : (List<ActualAllIngot>) o) {
                        addIngot(rate);
                    }
                    getIngot(id, onTaskCompleted);
                }
            });
        }
    }

    @Override
    public void getAllIngots(final OnTaskCompleted.LoadComplete onTaskCompleted) {
        if (isTableExists(TABLE_INGOTS)) {
            List<ActualAllIngot> rateList = new ArrayList<ActualAllIngot>();
            String selectQuery = "SELECT * FROM " + TABLE_INGOTS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                loadAllIngots(new OnTaskCompleted.LoadComplete() {
                    @Override
                    public void onLoadComplete(Object o) {
                        for (ActualAllIngot rate : (List<ActualAllIngot>) o) {
                            addIngot(rate);
                        }
                        getAllIngots(onTaskCompleted);
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
                onTaskCompleted.onLoadComplete(rateList);
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllIngots(new OnTaskCompleted.LoadComplete() {
                @Override
                public void onLoadComplete(Object o) {
                    for (ActualAllIngot rate : (List<ActualAllIngot>) o) {
                        addIngot(rate);
                    }
                    getAllIngots(onTaskCompleted);
                }
            });
        }
    }

    private void loadAllIngots(OnTaskCompleted.LoadComplete loadComplete) {
        if (isTableExists(TABLE_INGOTS)) {
            deleteAll();
            remoteDataSource.getAllIngots(loadComplete);
        } else {
            onCreate(this.getWritableDatabase());
            loadAllIngots(loadComplete);
        }
    }

    @Override
    public int getIngotsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INGOTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateIngot(ActualAllIngot allIngot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_METAL, allIngot.getMetalID());
        values.put(KEY_DATE, allIngot.getDate());
        values.put(KEY_NOMINAL, String.valueOf(allIngot.getNominal()));
        values.put(KEY_NO_CER_DOL, String.valueOf(allIngot.getNoCertificateDollars()));
        values.put(KEY_NO_CER_RUB, String.valueOf(allIngot.getNoCertificateRubles()));
        values.put(KEY_CER_DOL, String.valueOf((allIngot.getCertificateDollars())));
        values.put(KEY_CER_RUB, String.valueOf(allIngot.getCertificateRubles()));
        values.put(KEY_BANKS_DOL, String.valueOf(allIngot.getBanksDollars()));
        values.put(KEY_BANKS_RUB, String.valueOf(allIngot.getBanksRubles()));
        values.put(KEY_ENTITIES_DOL, String.valueOf(allIngot.getEntitiesDollars()));
        values.put(KEY_ENTITIES_RUB, String.valueOf(allIngot.getEntitiesRubles()));
        return db.update(TABLE_INGOTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(allIngot.getId())});
    }

    @Override
    public void deleteIngot(ActualAllIngot rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGOTS, KEY_ID + " = ?", new String[]{String.valueOf(rate.getMetalID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGOTS, null, null);
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
