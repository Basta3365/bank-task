package com.example.stunba.bankproject.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stunba.bankproject.Contracts;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    DBHelper(Context context) {
        super(context, Contracts.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contracts.CREATE_RATE_TABLE);
        db.execSQL(Contracts.CREATE_CURRENCY_TABLE);
        db.execSQL(Contracts.CREATE_FAVORITE_TABLE);
        db.execSQL(Contracts.CREATE_METAL_TABLE);
        db.execSQL(Contracts.CREATE_INGOT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contracts.TABLE_RATE);
        db.execSQL("DROP TABLE IF EXISTS " + Contracts.TABLE_CURRENCY);
        db.execSQL("DROP TABLE IF EXISTS " + Contracts.TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + Contracts.TABLE_METAL);
        db.execSQL("DROP TABLE IF EXISTS " + Contracts.TABLE_INGOTS);
        onCreate(db);
    }

}
