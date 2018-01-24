package com.example.jmmar.flashstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmmar on 12/28/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final String DB_NAME = "flashStudy.db";
    private static final int DB_VERSION = 1;

    // Table for the flash cards
    public static final String CARDS_TABLE_NAME = "cards";
    public static final String CARDS_COLUMN_ID = "id";
    public static final String CARDS_COLUMN_SETNAME = "setname";
    public static final String CARDS_COLUMN_TERM = "term";
    public static final String CARDS_COLUMN_DEFINITION = "definition";

    public static final String CREATE_TABLE_CARDS = "CREATE TABLE " + CARDS_TABLE_NAME + "("
            + CARDS_COLUMN_ID + " INTEGER PRIMARY KEY, " + CARDS_COLUMN_SETNAME + " TEXT, "
            + CARDS_COLUMN_TERM + " TEXT, " + CARDS_COLUMN_DEFINITION + " TEXT)";

    public static final String DELETE_CARDS = "DROP TABLE IF EXISTS " + CARDS_TABLE_NAME;

    /* Table for the set names
    public static final String SETS_TABLE_NAME = "sets";
    public static final String SETS_COLUMN_ID = "id";
    public static final String SETS_COLUMN_SET_NAME = "set_name";

    public static final String CREATE_TABLE_SETS = "CREATE TABLE " + SETS_TABLE_NAME + "("
            + SETS_COLUMN_ID + " INTEGER PRIMARY KEY, " + SETS_COLUMN_SET_NAME + " TEXT)";

    public static final String DELETE_SETS = "DROP TABLE IF EXISTS " + SETS_TABLE_NAME;
    */

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CARDS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }

    public boolean insertCard(IndexCard card){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARDS_COLUMN_SETNAME,card.getSetname());
        values.put(CARDS_COLUMN_TERM,card.getTerm());
        values.put(CARDS_COLUMN_DEFINITION,card.getDef());
        db.insert(CARDS_TABLE_NAME,null,values);
        return true;
    }

    public Cursor getCards(String setname){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CARDS_TABLE_NAME + " WHERE "
                + CARDS_COLUMN_SETNAME + " = '" + setname + "'",null);
    }

    public Integer deleteCard(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CARDS_TABLE_NAME,CARDS_COLUMN_ID + " = ?",new String[]{id.toString()});
    }

    public Cursor getSetnames(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT " + CARDS_COLUMN_SETNAME
                + " FROM " + CARDS_TABLE_NAME,null);
    }

    public Integer deleteSetOfCards(String setname){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CARDS_TABLE_NAME,CARDS_COLUMN_SETNAME + " = ?",new String[]{setname});
    }
}
