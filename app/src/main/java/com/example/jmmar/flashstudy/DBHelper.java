package com.example.jmmar.flashstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by jmmar on 12/28/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final String DB_NAME = "flashStudy.db";
    private static final int DB_VERSION = 2;

    // Table for the set names
    public static final String SETS_TABLE_NAME = "sets";
    public static final String SETS_COLUMN_SET_NAME = "setName";

    public static final String CREATE_TABLE_SETS = "CREATE TABLE " + SETS_TABLE_NAME + "("
            + SETS_COLUMN_SET_NAME + " TEXT PRIMARY KEY)";

    public static final String DELETE_SETS = "DROP TABLE IF EXISTS " + SETS_TABLE_NAME;

    // Table for the flash cards
    // Use a set name as the table name for cards
    public static final String CARDS_COLUMN_TERM = "term";
    public static final String CARDS_COLUMN_DEFINITION = "definition";

    // Append BEGIN + "setName" + END to create new table for cards
    public static final String CREATE_TABLE_CARDS_BEGIN = "CREATE TABLE ";
    public static final String CREATE_TABLE_CARDS_END =  "("
            + CARDS_COLUMN_TERM + " TEXT PRIMARY KEY, "
            + CARDS_COLUMN_DEFINITION + " TEXT)";

    // Append the setName to drop table of cards
    public static final String DELETE_CARDS = "DROP TABLE IF EXISTS ";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_SETS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }

    // Returns true if set was added, false if error occurred...set already exists
    public boolean addSet(String setName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SETS_COLUMN_SET_NAME,setName);
        if (db.insert(SETS_TABLE_NAME,null,values) == -1)
            return false;
        else{
            String tableName = makeValidTableName(setName);
            db.execSQL(CREATE_TABLE_CARDS_BEGIN + tableName + CREATE_TABLE_CARDS_END);
            return true;
        }
    }

    public Cursor getSets() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SETS_TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    // Returns true if card was added, false if error occurred
    public boolean addCard(String setName,IndexCard card){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARDS_COLUMN_TERM,card.getTerm());
        values.put(CARDS_COLUMN_DEFINITION,card.getDef());

        String tableName = makeValidTableName(setName);

        if (db.insert(tableName,null,values) == -1)
            return false;
        else
            return true;
    }

    public Cursor getCards(String setName){
        SQLiteDatabase db = this.getReadableDatabase();

        String tableName = makeValidTableName(setName);

        Cursor cursor =  db.query(tableName, new String[]{CARDS_COLUMN_TERM,CARDS_COLUMN_DEFINITION},
                null, null, null, null, null);
        return cursor;
    }

    public void deleteSet(String setName){
        SQLiteDatabase db = this.getWritableDatabase();
        deleteCards(setName);
        db.delete(SETS_TABLE_NAME,SETS_COLUMN_SET_NAME + " = ?",new String[]{setName});
    }

    public void deleteCards(String setName){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = makeValidTableName(setName);
        db.execSQL(DELETE_CARDS + tableName);
    }

    public void deleteCard(String setName,String term){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = makeValidTableName(setName);
        db.delete(tableName,CARDS_COLUMN_TERM + " = ?",new String[]{term});
    }

    public int numCardsInSet(String setName){
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = makeValidTableName(setName);
        Cursor cursor = db.query(tableName,null,null,null,null,null,null);
        return cursor.getCount();
    }

    private String makeValidTableName(String setName){
        StringTokenizer stringTokenizer = new StringTokenizer(setName," ",false);
        String tableName = "";
        while (stringTokenizer.hasMoreTokens())
            tableName = tableName + stringTokenizer.nextToken() + "_";
        tableName = tableName.substring(0,tableName.length() - 1);
        return tableName;
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }
}
