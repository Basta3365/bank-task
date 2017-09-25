package com.example.stunba.bankproject.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.stunba.bankproject.Contracts;
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

public class DatabaseHandlerCurrency implements IDatabaseHandlerCurrency {

    private RemoteDataSource remoteDataSource;
    private DBHelper mHelper;

    public DatabaseHandlerCurrency(Context context) {
        mHelper = new DBHelper(context);
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void addCurrency(Currency currency) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_ID, currency.getCurID());
        values.put(Contracts.KEY_PARENT_ID, currency.getCurParentID());
        values.put(Contracts.KEY_CODE, currency.getCurCode());
        values.put(Contracts.KEY_CUR_ABB, currency.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(currency.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, currency.getCurName());
        values.put(Contracts.KEY_CUR_NAME_BEL, currency.getCurNameBel());
        values.put(Contracts.KEY_CUR_NAME_ENG, currency.getCurNameEng());
        values.put(Contracts.KEY_QUOT_NAME, currency.getCurQuotName());
        values.put(Contracts.KEY_QUOT_NAME_BEL, currency.getCurQuotNameBel());
        values.put(Contracts.KEY_QUOT_NAME_ENG, currency.getCurQuotNameEng());
        values.put(Contracts.KEY_MUL_NAME, currency.getCurNameMulti());
        values.put(Contracts.KEY_MUL_NAME_BEL, currency.getCurNameBelMulti());
        values.put(Contracts.KEY_MUL_NAME_ENG, currency.getCurNameEngMulti());
        values.put(Contracts.KEY_PERIODICITY, currency.getCurPeriodicity());
        values.put(Contracts.KEY_DATE_START, currency.getCurDateStart());
        values.put(Contracts.KEY_DATE_END, currency.getCurDateEnd());
        db.insertWithOnConflict(Contracts.TABLE_CURRENCY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @Override
    public void getAllCurrencies(final OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        List<Currency> rateList = new ArrayList<Currency>();
        String selectQuery = "SELECT * FROM " + Contracts.TABLE_CURRENCY;
        final SQLiteDatabase db1=mHelper.getReadableDatabase();
        final Cursor cursor = db1.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            loadAllCurrency(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if(o!=null) {
                        cursor.close();
                        addLoadCurrencies(o);
                        getAllCurrencies(loadAllCurrencies);
                    }else {
                        cursor.close();
                        loadAllCurrencies.onAllCurrencyLoad(null);
                    }
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
    }

    public void addLoadCurrencies(List<Currency> currencies) {
        Set<Integer> itemToDelete = new HashSet<>();
        Map<Integer, Currency> currencyMap = new HashMap();
        for (Currency cur : currencies) {
            currencyMap.put(cur.getCurID(), cur);
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
            for (Currency currencySecond : currencyMap.values()) {
                if (currency.getCurAbbreviation().equals(currencySecond.getCurAbbreviation()) & currency.getCurID() != currencySecond.getCurID()) {
                    t = checkEarlier(currency, currencySecond);
                    itemToDelete.add(t);
                }
            }
        }
        for (Integer item : itemToDelete) {
            currencyMap.remove(item);
        }
        for (Currency cur : currencyMap.values()) {
            addCurrency(cur);
        }
    }

    public void loadAllCurrency(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        deleteAll();
        remoteDataSource.getAllCurrencies(loadAllCurrencies);
    }

    @Override
    public int updateCurrency(Currency currency) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.KEY_PARENT_ID, currency.getCurParentID());
        values.put(Contracts.KEY_CODE, currency.getCurCode());
        values.put(Contracts.KEY_DATE_START, currency.getCurDateStart());
        values.put(Contracts.KEY_DATE_END, currency.getCurDateEnd());
        values.put(Contracts.KEY_CUR_ABB, currency.getCurAbbreviation());
        values.put(Contracts.KEY_CUR_SCALE, String.valueOf(currency.getCurScale()));
        values.put(Contracts.KEY_CUR_NAME, currency.getCurName());
        values.put(Contracts.KEY_CUR_NAME_BEL, currency.getCurNameBel());
        values.put(Contracts.KEY_CUR_NAME_ENG, currency.getCurNameEng());
        values.put(Contracts.KEY_QUOT_NAME, currency.getCurQuotName());
        values.put(Contracts.KEY_QUOT_NAME_BEL, currency.getCurQuotNameBel());
        values.put(Contracts.KEY_QUOT_NAME_ENG, currency.getCurQuotNameEng());
        values.put(Contracts.KEY_MUL_NAME, currency.getCurNameMulti());
        values.put(Contracts.KEY_MUL_NAME_BEL, currency.getCurNameBelMulti());
        values.put(Contracts.KEY_MUL_NAME_ENG, currency.getCurNameEngMulti());
        values.put(Contracts.KEY_PERIODICITY, currency.getCurPeriodicity());
        return db.update(Contracts.TABLE_CURRENCY, values, Contracts.KEY_ID + " = ?",
                new String[]{String.valueOf(currency.getCurID())});
    }

    @Override
    public void deleteCurrency(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_CURRENCY, Contracts.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Contracts.TABLE_CURRENCY, null, null);
        db.close();
    }

    private int checkEarlier(Currency curDateEnd, Currency dateEnd) {
        String curDateFirst[] = curDateEnd.getCurDateEnd().split("-");
        String curDateSecond[] = dateEnd.getCurDateEnd().split("-");
        int deleteItem;
        if (Integer.valueOf(curDateFirst[0]) > Integer.valueOf(curDateSecond[0])) {
            deleteItem = dateEnd.getCurID();
        } else {
            deleteItem = curDateEnd.getCurID();
        }
        return deleteItem;
    }
}
