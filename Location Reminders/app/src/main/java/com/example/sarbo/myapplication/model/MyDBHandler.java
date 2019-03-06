package com.example.sarbo.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = (int) 2.0;
    private static final String DATABASE_NAME = "ALARMS.DB";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened....");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + Alrams.Alraminfo.TABLE_ALARMS + "(" +
                Alrams.Alraminfo.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Alrams.Alraminfo.COLUMN_TASKNAME + " TEXT UNIQUE NOT NULL, " +
                Alrams.Alraminfo.COLUMN_LOCATIONNAME + " TEXT NOT NULL, " +
                Alrams.Alraminfo.COLUMN_NOTE + " TEXT NOT NULL, " +
                Alrams.Alraminfo.COLUMN_LATITUDE + " DOUBLE NOT NULL, "+
                Alrams.Alraminfo.COLUMN_LONGITUDE + " DOUBLE NOT NILL "+

                ");";

        db.execSQL(query1);
        Log.e("DATABASE OPERATION", "Table  created....");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Alrams.Alraminfo.TABLE_ALARMS);
        onCreate(db);
    }

    //Add a new row to the database with day and date

    public void addAlarm(String taskname, String locationname, String note,double latitude,double longitude,SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(Alrams.Alraminfo.COLUMN_TASKNAME, taskname);
        values.put(Alrams.Alraminfo.COLUMN_LOCATIONNAME, locationname);
        values.put(Alrams.Alraminfo.COLUMN_NOTE, note);
        values.put(Alrams.Alraminfo.COLUMN_LATITUDE, latitude);
        values.put(Alrams.Alraminfo.COLUMN_LONGITUDE, longitude);


        db.insert(Alrams.Alraminfo.TABLE_ALARMS, null, values);
        Log.e("DATABASE OPERATION", "One row is inserted with day and time..."+longitude +" & " + latitude);

        db.close();
    }

    //Add alarm without day and date
 /*   public void addAlarm(String taskname, String locationname, String note,SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(Alrams.Alraminfo.COLUMN_TASKNAME, taskname);
        values.put(Alrams.Alraminfo.COLUMN_LOCATIONNAME, locationname);
        values.put(Alrams.Alraminfo.COLUMN_NOTE, note);
           db.insert(Alrams.Alraminfo.TABLE_ALARMS, null, values);
        Log.e("DATABASE OPERATION", "One row is inserted...");

        db.close();
    }*/


    //Printing out the database


    public Cursor getInformation(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {Alrams.Alraminfo.COLUMN_ID, Alrams.Alraminfo.COLUMN_TASKNAME, Alrams.Alraminfo.COLUMN_LOCATIONNAME, Alrams.Alraminfo.COLUMN_NOTE};
        cursor = db.query(Alrams.Alraminfo.TABLE_ALARMS, projections, null, null, null, null, null);
        return cursor;
    }


    public Cursor getInformationWithDayDate(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {Alrams.Alraminfo.COLUMN_ID, Alrams.Alraminfo.COLUMN_TASKNAME, Alrams.Alraminfo.COLUMN_LOCATIONNAME, Alrams.Alraminfo.COLUMN_NOTE,
                Alrams.Alraminfo.COLUMN_LATITUDE, Alrams.Alraminfo.COLUMN_LONGITUDE};
        cursor = db.query(Alrams.Alraminfo.TABLE_ALARMS, projections, null, null, null, null, null);
        return cursor;
    }



    //finding id from taskName
  /*  public int findIdByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + Alrams.Alraminfo.COLUMN_ID + " FROM " + Alrams.Alraminfo.TABLE_ALARMS + " WHERE " + Alrams.Alraminfo.COLUMN_TASKNAME + "=\"" + name + "\";";
        Cursor cursor = db.rawQuery(query, null);
        int id = -1;
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id_project"));
            Log.i("LOGGING:", " FIND ID BY NAME: ID=" + id);
        }
        return id;
    }*/

    public int  updateAlarm(String old_task,String task, String loc,String note,double latitude,double longitude,
                            SQLiteDatabase sqLiteDatabase){
       ContentValues contentValues = new ContentValues();
        contentValues.put(Alrams.Alraminfo.COLUMN_TASKNAME,task);
        contentValues.put(Alrams.Alraminfo.COLUMN_LOCATIONNAME,loc);
        contentValues.put(Alrams.Alraminfo.COLUMN_NOTE,note);
        contentValues.put(Alrams.Alraminfo.COLUMN_LATITUDE,latitude);
        contentValues.put(Alrams.Alraminfo.COLUMN_LONGITUDE,longitude);


        String selection = Alrams.Alraminfo.COLUMN_TASKNAME + " LIKE ?";
        String[] selection_args ={old_task};
        int count = sqLiteDatabase.update(Alrams.Alraminfo.TABLE_ALARMS,contentValues,selection,selection_args);
        return count;
    }

    //delete a taskName from database
    public void deleteAlarm(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Alrams.Alraminfo.TABLE_ALARMS + " WHERE " + Alrams.Alraminfo.COLUMN_TASKNAME + "=\"" + name + "\";");
    }
}
