package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stunba.bankproject.Contracts;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.MetalName;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandlerMetalName implements IDatabaseHandlerMetalName {

    private DBHelper mHelper;
    private RemoteDataSource remoteDataSource;

    public DatabaseHandlerMetalName(Context context) {
        mHelper = new DBHelper(context);
        remoteDataSource = new RemoteDataSource();
    }


    @Override
    public void addMetal(MetalName metalName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, metalName.getId());
        values.put(Contracts.KEY_NAME, metalName.getName());
        values.put(Contracts.KEY_NAME_BEL, metalName.getNameBel());
        values.put(Contracts.KEY_NAME_ENG, metalName.getNameEng());
        db.insertWithOnConflict(Contracts.TABLE_METAL, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @Override
    public void getMetal(final int id, final OnTaskCompleted.MetalNamesLoad onTaskCompleted) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.query(Contracts.TABLE_METAL, new String[]{Contracts.KEY_ID,
                        Contracts.KEY_NAME, Contracts.KEY_NAME_BEL, Contracts.KEY_NAME_ENG}, Contracts.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor.getCount() == 0) {
            loadAllMetalNames(new OnTaskCompleted.MetalNamesLoadAll() {
                @Override
                public void onAllNames(List<MetalName> o) {
                    for (MetalName rate : o) {
                        addMetal(rate);
                    }
                    cursor.close();
                    getMetal(id, onTaskCompleted);
                }
            });
        } else {
            MetalName metalName = new MetalName(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
            onTaskCompleted.onNames(metalName);
        }
    }

    @Override
    public void getAllMetal(final OnTaskCompleted.MetalNamesLoadAll onTaskCompleted) {
        List<MetalName> rateList = new ArrayList<MetalName>();
        String selectQuery = "SELECT * FROM " + Contracts.TABLE_METAL;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            loadAllMetalNames(new OnTaskCompleted.MetalNamesLoadAll() {
                @Override
                public void onAllNames(List<MetalName> o) {
                    if (o != null) {
                        for (MetalName rate : o) {
                            addMetal(rate);
                        }
                        cursor.close();
                        getAllMetal(onTaskCompleted);
                    } else {
                        cursor.close();
                        onTaskCompleted.onAllNames(null);
                    }
                }
            });
        } else {
            if (cursor.moveToFirst()) {
                do {
                    MetalName rate = new MetalName();
                    rate.setId(Integer.parseInt(cursor.getString(0)));
                    rate.setName(cursor.getString(1));
                    rate.setNameBel(cursor.getString(2));
                    rate.setNameEng(cursor.getString(3));
                    rateList.add(rate);
                } while (cursor.moveToNext());
                cursor.close();
                onTaskCompleted.onAllNames(rateList);
            }
        }
    }

    private void loadAllMetalNames(OnTaskCompleted.MetalNamesLoadAll loadComplete) {
        deleteAll();
        remoteDataSource.getAllMetalNames(loadComplete);
    }

    @Override
    public int updateMetal(MetalName metalName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, metalName.getId());
        values.put(Contracts.KEY_NAME, metalName.getName());
        values.put(Contracts.KEY_NAME_BEL, metalName.getNameBel());
        values.put(Contracts.KEY_NAME_ENG, metalName.getNameEng());
        return db.update(Contracts.TABLE_METAL, values, Contracts.KEY_ID + " = ?",
                new String[]{String.valueOf(metalName.getId())});
    }

    @Override
    public void deleteMetal(MetalName rate) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_METAL, Contracts.KEY_ID + " = ?", new String[]{String.valueOf(rate.getId())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_METAL, null, null);
        db.close();
    }
}
