package com.example.stunba.bankproject;

/**
 * Created by Kseniya_Bastun on 9/25/2017.
 */

public class Contracts {
    public static final String DATABASE_NAME = "bankManager";

    public static final String TABLE_RATE = "actual";
    public static final String KEY_ID = "id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CUR_ABB = "curAbbreviation";
    public static final String KEY_CUR_SCALE = "curScale";
    public static final String KEY_CUR_NAME = "curName";
    public static final String KEY_CUR_OFF_RATE = "curOfficialRate";

    public static final String TABLE_CURRENCY = "currency";
    public static final String KEY_PARENT_ID = "parentId";
    public static final String KEY_CODE = "code";
    public static final String KEY_DATE_START = "dateStart";
    public static final String KEY_DATE_END = "dateEnd";
    public static final String KEY_CUR_NAME_ENG = "curNameENG";
    public static final String KEY_CUR_NAME_BEL = "curNameBEL";
    public static final String KEY_MUL_NAME = "curNameMul";
    public static final String KEY_MUL_NAME_ENG = "curNameENGMul";
    public static final String KEY_MUL_NAME_BEL = "curNameBELMul";
    public static final String KEY_QUOT_NAME = "curNameQuot";
    public static final String KEY_QUOT_NAME_ENG = "curNameENGQuot";
    public static final String KEY_QUOT_NAME_BEL = "curNameBELQuot";
    public static final String KEY_PERIODICITY = "periodicity";

    public static final String TABLE_FAVORITES = "favorites";

    public static final String TABLE_METAL = "metalName";
    public static final String KEY_NAME = "name";
    public static final String KEY_NAME_BEL = "nameBel";
    public static final String KEY_NAME_ENG = "nameEng";

    public static final String TABLE_INGOTS = "ingots";
    public static final String KEY_ID_METAL = "metalId";
    public static final String KEY_NOMINAL = "nominal";
    public static final String KEY_NO_CER_DOL = "noCertificateDollars";
    public static final String KEY_NO_CER_RUB = "noCertificateRubles";
    public static final String KEY_CER_DOL = "certificateDollars";
    public static final String KEY_CER_RUB = "certificateRubles";
    public static final String KEY_BANKS_DOL = "banksDollars";
    public static final String KEY_BANKS_RUB = "banksRubles";
    public static final String KEY_ENTITIES_DOL = "entitiesDollars";
    public static final String KEY_ENTITIES_RUB = "entitiesRubles";

    public static String CREATE_RATE_TABLE = "CREATE TABLE " + TABLE_RATE + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
            + KEY_CUR_ABB + " TEXT," + KEY_CUR_SCALE + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_OFF_RATE + " TEXT" + ")";
    public static String CREATE_CURRENCY_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PARENT_ID + " INTEGER," + KEY_CODE + " TEXT,"
            + KEY_CUR_ABB + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_NAME_BEL + " TEXT," + KEY_CUR_NAME_ENG + " TEXT," + KEY_QUOT_NAME + " TEXT," + KEY_QUOT_NAME_BEL + " TEXT," + KEY_QUOT_NAME_ENG + " TEXT," + KEY_MUL_NAME + " TEXT," + KEY_MUL_NAME_BEL + " TEXT," + KEY_MUL_NAME_ENG + " TEXT," + KEY_CUR_SCALE + " INTEGER," + KEY_PERIODICITY + " INTEGER," + KEY_DATE_START + " TEXT," + KEY_DATE_END + " TEXT" + ")";
    public static String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
            + KEY_CUR_ABB + " TEXT," + KEY_CUR_SCALE + " TEXT," + KEY_CUR_NAME + " TEXT," + KEY_CUR_OFF_RATE + " TEXT" + ")";
    public static String CREATE_METAL_TABLE = "CREATE TABLE " + TABLE_METAL + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_NAME_BEL + " TEXT," + KEY_NAME_ENG + " TEXT" + ")";
    public static String CREATE_INGOT_TABLE = "CREATE TABLE " + TABLE_INGOTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_METAL + " INTEGER ," + KEY_DATE + " TEXT,"
            + KEY_NOMINAL + " TEXT," + KEY_NO_CER_DOL + " TEXT," + KEY_NO_CER_RUB + " TEXT," + KEY_CER_DOL + " TEXT," + KEY_CER_RUB + " TEXT," + KEY_BANKS_DOL + " TEXT," + KEY_BANKS_RUB + " TEXT," + KEY_ENTITIES_DOL + " TEXT," + KEY_ENTITIES_RUB + " TEXT" + ")";

}
