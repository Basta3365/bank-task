package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.MetalName;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandlerMetalName extends SQLiteOpenHelper implements IDatabaseHandlerMetalName {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bankManager";
    private static final String TABLE_METAL = "metalName";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NAME_BEL = "nameBel";
    private static final String KEY_NAME_ENG = "nameEng";
    private RemoteDataSource remoteDataSource;

    public DatabaseHandlerMetalName(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_METAL_TABLE = "CREATE TABLE " + TABLE_METAL + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_NAME_BEL + " TEXT," + KEY_NAME_ENG + " TEXT" + ")";
        db.execSQL(CREATE_METAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_METAL);
        onCreate(db);
    }

    @Override
    public void addMetal(MetalName metalName) {
        if (isTableExists(TABLE_METAL)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, metalName.getId());
            values.put(KEY_NAME, metalName.getName());
            values.put(KEY_NAME_BEL, metalName.getNameBel());
            values.put(KEY_NAME_ENG, metalName.getNameEng());
            db.insertWithOnConflict(TABLE_METAL, null, values,SQLiteDatabase.CONFLICT_REPLACE );
            db.close();
        } else {
            onCreate(this.getWritableDatabase());
            addMetal(metalName);
        }
    }

    @Override
    public void getMetal(final int id, final OnTaskCompleted.LoadComplete onTaskCompleted) {
        if (isTableExists(TABLE_METAL)) {
            SQLiteDatabase db = this.getReadableDatabase();
            final Cursor cursor = db.query(TABLE_METAL, new String[]{KEY_ID,
                            KEY_NAME, KEY_NAME_BEL, KEY_NAME_ENG}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.getCount() == 0) {
                loadAllMetalNames(new OnTaskCompleted.LoadComplete() {
                    @Override
                    public void onLoadComplete(Object o) {
                        for (MetalName rate : (List<MetalName>) o) {
                            addMetal(rate);
                        }
                        cursor.close();
                        getMetal(id, onTaskCompleted);
                    }
                });
            } else {
                MetalName metalName = new MetalName(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                cursor.close();
                onTaskCompleted.onLoadComplete(metalName);
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllMetalNames(new OnTaskCompleted.LoadComplete() {
                @Override
                public void onLoadComplete(Object o) {
                    for (MetalName rate : (List<MetalName>) o) {
                        addMetal(rate);
                    }
                    getMetal(id, onTaskCompleted);
                }
            });
        }
    }

    @Override
    public void getAllMetal(final OnTaskCompleted.LoadComplete onTaskCompleted) {
        if (isTableExists(TABLE_METAL)) {
            List<MetalName> rateList = new ArrayList<MetalName>();
            String selectQuery = "SELECT * FROM " + TABLE_METAL;
            SQLiteDatabase db = this.getWritableDatabase();
            final Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                loadAllMetalNames(new OnTaskCompleted.LoadComplete() {
                    @Override
                    public void onLoadComplete(Object o) {
                        for (MetalName rate : (List<MetalName>) o) {
                            addMetal(rate);
                        }
                        cursor.close();
                        getAllMetal(onTaskCompleted);
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
                    onTaskCompleted.onLoadComplete(rateList);
                }
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllMetalNames(new OnTaskCompleted.LoadComplete() {
                @Override
                public void onLoadComplete(Object o) {
                    for (MetalName rate : (List<MetalName>) o) {
                        addMetal(rate);
                    }
                    getAllMetal(onTaskCompleted);
                }
            });
        }
    }

    private void loadAllMetalNames(OnTaskCompleted.LoadComplete loadComplete) {
            deleteAll();
            remoteDataSource.getAllMetalNames(loadComplete);
    }

    @Override
    public int updateMetal(MetalName metalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, metalName.getName());
        values.put(KEY_NAME_BEL, metalName.getNameBel());
        values.put(KEY_NAME_ENG, metalName.getNameEng());
        return db.update(TABLE_METAL, values, KEY_ID + " = ?",
                new String[]{String.valueOf(metalName.getId())});
    }

    @Override
    public void deleteMetal(MetalName rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_METAL, KEY_ID + " = ?", new String[]{String.valueOf(rate.getId())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_METAL, null, null);
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
