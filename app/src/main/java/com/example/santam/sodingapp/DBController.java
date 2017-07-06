package com.example.santam.sodingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DBController extends SQLiteOpenHelper{


    private static final String TAG = "SODING";
    private static final String DBNAME = "soding.db";
    private static final String TABLE_NAME = "task";
    private static final String CL1 = "id";
    private static final String CL2 = "name";
    private static final String CL3 = "description";
    private static final String CL4 = "dateCreated";
    private static final String CL5 = "dateUpdated";

    public DBController(Context context) {

        super(context,TABLE_NAME,null,3);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_NAME +" ("+CL1+" VARCHAR(7) PRIMARY KEY, "
                                                            + CL2 +" TEXT,"
                                                            + CL3 +" TEXT,"
                                                            + CL4 +" DATE,"
                                                            + CL5 +" DATE)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createTable = "DROP IF TABLE EXISTS "+TABLE_NAME ;
        db.execSQL(createTable);
    }

    // Add Data to db

    public boolean AddTask (String id, String name, String desc, Date create, Date update){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CL1, id);
        contentValues.put(CL2, name);
        contentValues.put(CL3, desc);
        contentValues.put(CL4, dateFormat.format(create));
        contentValues.put(CL5, dateFormat.format(update));
        Log.d(TAG,"Task Adding " + name + " to "+ TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return  true;

    }

    // For get full data from db.

    public Cursor getFullData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // get Task ID

    public Cursor getTaskID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + CL2 + " FROM " + TABLE_NAME +
                " WHERE " + CL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // get Task Desc

    public Cursor getDesc_CrtDate(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + CL3 + ", "+ CL4 +" FROM " + TABLE_NAME +
                " WHERE " + CL1 + " = '" + ID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // Update data to db

    public boolean updateTask(String id, String name, String desc, Date update){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String updateDate = dateFormat.format(update);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CL2, name);
        contentValues.put(CL3, desc);
        contentValues.put(CL5, dateFormat.format(update));
//        String query = "UPDATE " + TABLE_NAME + " SET " + CL2 +" = '" + name + "'," + CL3 +" = '" + desc + "' " +
//                "," + CL5 +" = '" + updateDate + "' WHERE " + CL1 + " = '" + id + "'";
//        Log.d(TAG, "updateTask: query: " + query);

        long result = db.update(TABLE_NAME,contentValues,"id='" +id+"'",null);
        if (result == -1)
            return false;
        else
            return  true;
    }

    // Delete data from db

    public boolean deleteTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + CL1 + " = '" + id + "'";
//        Log.d(TAG, "deleteTask: query: " + query);
        long result = db.delete(TABLE_NAME,"id='"+id+"'",null);
        if (result == -1)
            return false;
        else
            return  true;
//        db.execSQL(query);
    }


// auto id generate
    public Cursor IdGenerate() {


        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(ID) FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return  data;

    }

}

