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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        if (isTableExists(TABLE_CURRENCY)) {
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
            db.insertWithOnConflict(TABLE_CURRENCY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
        } else {
            onCreate(this.getWritableDatabase());
            addCurrency(currency);
        }
    }

    @Override
    public void getAllCurrencies(final OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        if (isTableExists(TABLE_CURRENCY)) {
            List<Currency> rateList = new ArrayList<Currency>();
            String selectQuery = "SELECT * FROM " + TABLE_CURRENCY;
            SQLiteDatabase db = this.getWritableDatabase();
            final Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() == 0) {
                loadAllCurrency(new OnTaskCompleted.LoadAllCurrencies() {
                    @Override
                    public void onAllCurrencyLoad(List<Currency> o) {
                       addLoadCurrencies(o);
                        cursor.close();
                        getAllCurrencies(loadAllCurrencies);
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
                cursor.close();
                Collections.sort(rateList, new Comparator<Currency>() {
                    @Override
                    public int compare(Currency o1, Currency o2) {
                        return o1.getCurAbbreviation().compareTo(o2.getCurAbbreviation());
                    }
                });
                loadAllCurrencies.onAllCurrencyLoad(rateList);
            }
        } else {
            onCreate(this.getWritableDatabase());
            loadAllCurrency(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    addLoadCurrencies( o);
                    getAllCurrencies(loadAllCurrencies);
                }
            });
        }
    }

    private void addLoadCurrencies(List<Currency> currencies) {
        Set<Integer> itemToDelete = new HashSet<>();
        Map<Integer,Currency> currencyMap=new HashMap();
        for (Currency cur : currencies) {
            currencyMap.put(cur.getCurID(),cur);
        }
        for (Map.Entry<Integer, Currency> currency : currencyMap.entrySet()) {
            if (currency.getKey() != currency.getValue().getCurParentID()) {
                itemToDelete.add(currency.getValue().getCurParentID());
            }
        }
        for (Integer item : itemToDelete) {
            currencyMap.remove(item);
        }
        itemToDelete.clear();
        int t;
        for (Currency currency : currencyMap.values()) {
            for (Currency currencySecond :currencyMap.values()) {
                if (currency.getCurAbbreviation().equals(currencySecond.getCurAbbreviation()) & currency.getCurID()!=currencySecond.getCurID()) {
                    t=checkEarlier(currency,currencySecond);
                    itemToDelete.add(t);
                }
            }
        }
        for (Integer item : itemToDelete) {
            currencyMap.remove(item);
        }
        for (Currency cur: currencyMap.values()) {
            addCurrency(cur);
        }
    }

    public void loadAllCurrency(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        deleteAll();
        remoteDataSource.getAllCurrencies(loadAllCurrencies);
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
    public void deleteCurrency(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, KEY_ID + " = ?", new String[]{String.valueOf(id)});
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

    private int checkEarlier(Currency curDateEnd, Currency dateEnd) {
        String curDateFirst[] = curDateEnd.getCurDateEnd().split("-");
        String curDateSecond[] = dateEnd.getCurDateEnd().split("-");
        int deleteItem;
        if(Integer.valueOf(curDateFirst[0])>Integer.valueOf(curDateSecond[0])){
            deleteItem=dateEnd.getCurID();
        }else {
            deleteItem=curDateEnd.getCurID();
        }
        return deleteItem;
    }
}
