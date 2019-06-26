package com.example.android.watertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="Tracker";
    private static final int DB_VER=3;
    private static final String DB_TABLE1="WaterTracker";
    public static final String DB_COLUMN1_1 = "Date";
    public static final String DB_COLUMN1_2="WaterConsumption";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE1 + "("+DB_COLUMN1_1+" DATE PRIMARY KEY,"+DB_COLUMN1_2+" INTEGER );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String date, String waterConsumption){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_COLUMN1_1,date);
        contentValues.put(DB_COLUMN1_2,waterConsumption);
        //long result=
        db.insert(DB_TABLE1,null,contentValues);
        //return result != -1;
    }

    public boolean UpdateData(String date, String waterConsumption){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //contentValues.put(DB_COLUMN1_1,date);
        contentValues.put(DB_COLUMN1_2,waterConsumption);
        int result = db.update(DB_TABLE1,contentValues,DB_COLUMN1_1+"=?",new String[]{date});
        return result > 0;
    }

    public Cursor GetAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ DB_TABLE1,null);
    }

    public Cursor GetTodaysData(String date){
        SQLiteDatabase db=this.getWritableDatabase();
       //String Intake="";
        Cursor res;
        //String date;
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
        try{
            res = db.rawQuery("SELECT "+DB_COLUMN1_2+" FROM "+DB_TABLE1+" WHERE "+DB_COLUMN1_1+" = ?",new String[]{date});
        }catch (CursorIndexOutOfBoundsException c){
            res=null;
        }
        return res;
        //return db.rawQuery("SELECT * FROM "+ DB_TABLE1+" WHERE "+DB_COLUMN1_1+" =?",new String[]{date});
    }
    public Cursor GetCustomData(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        //String Intake="";
        Cursor res;
        //String date;
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
        try{
            res = db.rawQuery("SELECT "+DB_COLUMN1_1+","+DB_COLUMN1_2+" FROM "+DB_TABLE1+" WHERE "+DB_COLUMN1_1+" = ?",new String[]{date});
        }catch (CursorIndexOutOfBoundsException c){
            res=null;
        }
        return res;
        //return db.rawQuery("SELECT * FROM "+ DB_TABLE1+" WHERE "+DB_COLUMN1_1+" =?",new String[]{date});
    }

}
