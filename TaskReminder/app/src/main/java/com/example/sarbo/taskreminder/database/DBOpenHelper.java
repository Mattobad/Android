package com.example.sarbo.taskreminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for Db name  and Version
    private static final String  DATABASE_NAME ="taskReminder.db";
    private static final int DATABASE_VERSION =1;

    //Constants for identifying table and column
    public static final String TABLE_TASK ="tasks";
    public static final String TASK_ID = "_id";
    public static final String TASK_TEXT= "taskText";
    public static final String TASK_CREATED = "taskCreated";

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE "+ TABLE_TASK + " ("+
                    TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASK_TEXT + " TEXT, " +
                    TASK_CREATED + " TEXT default CURRENT_TIMESTAMP"+
                    ")";


    public static final String[] ALL_COLUMNS =
            {TASK_ID, TASK_TEXT,TASK_CREATED};

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXITS "+ TABLE_TASK);
            onCreate(db);
    }
}
