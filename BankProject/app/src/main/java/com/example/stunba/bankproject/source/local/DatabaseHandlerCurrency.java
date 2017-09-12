package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.remote.RemoteDataSource;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public class DatabaseHandlerCurrency extends SQLiteOpenHelper implements IDatabaseHandlerCurrency {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bankManager";
    private static final String TABLE_CURRENCY = "currency";
    private static final String KEY_ID = "id";
    private static final String KEY_PARENT_ID = "parentId";
    private static final String KEY_CODE = "code";
    private static final String KEY_DATE_START = "dateStart";
    private static final String KEY_DATE_END = "dateEnd";
    private static final String KEY_CUR_ABB = "curAbbreviation";
    private static final String KEY_CUR_SCALE = "curScale";
    private static final String KEY_CUR_NAME = "curName";
    private static final String KEY_CUR_NAME_ENG = "curNameENG";
    private static final String KEY_CUR_NAME_BEL = "curNameBEL";
    private static final String KEY_MUL_NAME = "curNameMul";
    private static final String KEY_MUL_NAME_ENG = "curNameENGMul";
    private static final String KEY_MUL_NAME_BEL = "curNameBELMul";
    private static final String KEY_QUOT_NAME = "curNameQuot";
    private static final String KEY_QUOT_NAME_ENG = "curNameENGQuot";
    private static final String KEY_QUOT_NAME_BEL = "curNameBELQuot";
    private static final String KEY_PERIODICITY = "periodicity";
    private RemoteDataSource remoteDataSource;

    public DatabaseHandlerCurrency(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CURRENCY_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PARENT_ID + " INTEGER," + KEY_CODE + " TEXT,"
                + KEY_CUR_ABB + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_NAME_BEL + " TEXT," + KEY_CUR_NAME_ENG + " TEXT," + KEY_QUOT_NAME + " TEXT," + KEY_QUOT_NAME_BEL + " TEXT," + KEY_QUOT_NAME_ENG + " TEXT," + KEY_MUL_NAME + " TEXT," + KEY_MUL_NAME_BEL + " TEXT," + KEY_MUL_NAME_ENG + " TEXT," + KEY_CUR_SCALE + " INTEGER," + KEY_PERIODICITY + " INTEGER," + KEY_DATE_START + " TEXT," + KEY_DATE_END + " TEXT" + ")";
        db.execSQL(CREATE_CURRENCY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);
    }

    @Override
    public void addCurrency(Currency currency) {
        if(isTableExists(TABLE_CURRENCY)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, currency.getCurID());
            values.put(KEY_PARENT_ID, currency.getCurParentID());
            values.put(KEY_CODE, currency.getCurCode());
            values.put(KEY_CUR_ABB, currency.getCurAbbreviation());
            values.put(KEY_CUR_SCALE, String.valueOf(currency.getCurScale()));
            values.put(KEY_CUR_NAME, currency.getCurName());
            values.put(KEY_CUR_NAME_BEL, currency.getCurNameBel());
            values.put(KEY_CUR_NAME_ENG, currency.getCurNameEng());
            values.put(KEY_QUOT_NAME, currency.getCurQuotName());
            values.put(KEY_QUOT_NAME_BEL, currency.getCurQuotNameBel());
            values.put(KEY_QUOT_NAME_ENG, currency.getCurQuotNameEng());
            values.put(KEY_MUL_NAME, currency.getCurNameMulti());
            values.put(KEY_MUL_NAME_BEL, currency.getCurNameBelMulti());
            values.put(KEY_MUL_NAME_ENG, currency.getCurNameEngMulti());
            values.put(KEY_PERIODICITY, currency.getCurPeriodicity());
            values.put(KEY_DATE_START, currency.getCurDateStart());
            values.put(KEY_DATE_END, currency.getCurDateEnd());
            db.insert(TABLE_CURRENCY, null, values);
            db.close();
        }else {
            onCreate(this.getWritableDatabase());
            addCurrency(currency);
        }
    }

    @Override
    public void getCurrency(final int id, final OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        if(isTableExists(TABLE_CURRENCY)) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CURRENCY, new String[]{KEY_ID, KEY_PARENT_ID, KEY_CODE, KEY_CUR_ABB, KEY_CUR_NAME, KEY_CUR_NAME_BEL, KEY_CUR_NAME_ENG, KEY_QUOT_NAME, KEY_QUOT_NAME_BEL, KEY_QUOT_NAME_ENG, KEY_MUL_NAME, KEY_MUL_NAME_BEL, KEY_MUL_NAME_ENG, KEY_CUR_SCALE, KEY_PERIODICITY, KEY_DATE_START, KEY_DATE_END}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            if (cursor.getCount() == 0) {
                loadAllCurrency(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
                    @Override
                    public void onAllCurrencyLoad(Object o) {
                        for (Currency cur:(List<Currency>)o) {
                            addCurrency(cur);
                        }
                        getCurrency(id,dynamicPresenterCompleteCurrency);
                    }
                });
            }else {
                Currency currency = new Currency(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), Integer.parseInt(cursor.getString(13)), Integer.parseInt(cursor.getString(14)), cursor.getString(15), cursor.getString(16));
                dynamicPresenterCompleteCurrency.onAllCurrencyLoad(currency);
            }
        }else {
            onCreate(this.getWritableDatabase());
            loadAllCurrency(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
                @Override
                public void onAllCurrencyLoad(Object o) {
                    for (Currency cur:(List<Currency>)o) {
                        addCurrency(cur);
                    }
                    getCurrency(id,dynamicPresenterCompleteCurrency);
                }
            });
            getCurrency(id,dynamicPresenterCompleteCurrency);
        }
    }

    @Override
    public void getAllCurrencies(final OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        if (isTableExists(TABLE_CURRENCY)) {
            List<Currency> rateList = new ArrayList<Currency>();
            String selectQuery = "SELECT * FROM " + TABLE_CURRENCY;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                loadAllCurrency(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
                    @Override
                    public void onAllCurrencyLoad(Object o) {
                        for (Currency cur:(List<Currency>)o) {
                            addCurrency(cur);
                        }
                        getAllCurrencies(dynamicPresenterCompleteCurrency);
                    }
                });
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        Currency rate = new Currency();
                        rate.setCurID(Integer.parseInt(cursor.getString(0)));
                        rate.setCurParentID(Integer.parseInt(cursor.getString(1)));
                        rate.setCurCode(cursor.getString(2));
                        rate.setCurAbbreviation(cursor.getString(3));
                        rate.setCurName(cursor.getString(4));
                        rate.setCurNameBel(cursor.getString(5));
                        rate.setCurNameEng(cursor.getString(6));
                        rate.setCurQuotName(cursor.getString(7));
                        rate.setCurQuotNameBel(cursor.getString(8));
                        rate.setCurQuotNameEng(cursor.getString(9));
                        rate.setCurNameMulti(cursor.getString(10));
                        rate.setCurNameBelMulti(cursor.getString(11));
                        rate.setCurNameEngMulti(cursor.getString(12));
                        rate.setCurScale(Integer.parseInt(cursor.getString(13)));
                        rate.setCurPeriodicity(Integer.parseInt(cursor.getString(14)));
                        rate.setCurDateStart(cursor.getString(15));
                        rate.setCurDateEnd(cursor.getString(16));
                        rateList.add(rate);
                    } while (cursor.moveToNext());
                }
                dynamicPresenterCompleteCurrency.onAllCurrencyLoad(rateList);
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllCurrency(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
                @Override
                public void onAllCurrencyLoad(Object o) {
                    for (Currency cur:(List<Currency>)o) {
                        addCurrency(cur);
                    }
                    getAllCurrencies(dynamicPresenterCompleteCurrency);
                }
            });
        }
    }

    public void loadAllCurrency(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        if (isTableExists(TABLE_CURRENCY)) {
            deleteAll();
            remoteDataSource.getAllCurrencies(dynamicPresenterCompleteCurrency);
        } else {
            onCreate(this.getWritableDatabase());
            loadAllCurrency(dynamicPresenterCompleteCurrency);
        }
    }

    @Override
    public int getCurrenciesCount() {
        String countQuery = "SELECT * FROM " + TABLE_CURRENCY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateCurrency(Currency currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT_ID, currency.getCurParentID());
        values.put(KEY_CODE, currency.getCurCode());
        values.put(KEY_DATE_START, currency.getCurDateStart());
        values.put(KEY_DATE_END, currency.getCurDateEnd());
        values.put(KEY_CUR_ABB, currency.getCurAbbreviation());
        values.put(KEY_CUR_SCALE, String.valueOf(currency.getCurScale()));
        values.put(KEY_CUR_NAME, currency.getCurName());
        values.put(KEY_CUR_NAME_BEL, currency.getCurNameBel());
        values.put(KEY_CUR_NAME_ENG, currency.getCurNameEng());
        values.put(KEY_QUOT_NAME, currency.getCurQuotName());
        values.put(KEY_QUOT_NAME_BEL, currency.getCurQuotNameBel());
        values.put(KEY_QUOT_NAME_ENG, currency.getCurQuotNameEng());
        values.put(KEY_MUL_NAME, currency.getCurNameMulti());
        values.put(KEY_MUL_NAME_BEL, currency.getCurNameBelMulti());
        values.put(KEY_MUL_NAME_ENG, currency.getCurNameEngMulti());
        values.put(KEY_PERIODICITY, currency.getCurPeriodicity());
        return db.update(TABLE_CURRENCY, values, KEY_ID + " = ?",
                new String[]{String.valueOf(currency.getCurID())});
    }

    @Override
    public void deleteCurrency(Currency currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, KEY_ID + " = ?", new String[]{String.valueOf(currency.getCurID())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, null, null);
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
