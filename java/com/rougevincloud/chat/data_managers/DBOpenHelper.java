package com.rougevincloud.chat.data_managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME   = "CYS.db" ;
    public  final static String DATABASE_TABLE_FRIENDS = "Friend_Challenges" ;
    public  final static String DATABASE_TABLE_ALL = "All_Challenges" ;
    public  final static String DATABASE_TABLE_ALL_FRIENDS = "All_Friends" ;
    private final static int DATABASE_VERSION  = 1 ;

    private static boolean justCreated = false;

    public static final String COLUMN_ID    = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC  = "desc";
    public static final String COLUMN_IMG  = "img";
    public static final String COLUMN_USERNAME  = "username";

    private static DBOpenHelper instance = null;

    public static DBOpenHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBOpenHelper(context);
        return instance;
    }

    public static boolean isJustCreated() {
        return justCreated;
    }

    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ DATABASE_TABLE_FRIENDS
                +"("+ COLUMN_ID +" integer primary key, "+
                COLUMN_TITLE +" text not null, "+
                COLUMN_IMG +" text not null, "+
                COLUMN_DESC  +" text not null);");
        sqLiteDatabase.execSQL("create table "+ DATABASE_TABLE_ALL
                +"("+ COLUMN_ID +" integer primary key, "+
                COLUMN_TITLE +" text not null, "+
                COLUMN_IMG +" text not null, "+
                COLUMN_DESC  +" text not null);");
        sqLiteDatabase.execSQL("create table "+ DATABASE_TABLE_ALL_FRIENDS
                +"("+ COLUMN_ID +" integer primary key, "+
                COLUMN_USERNAME +" text not null);");
        justCreated = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FRIENDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ALL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ALL_FRIENDS);
        onCreate(sqLiteDatabase);
    }
}