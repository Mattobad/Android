package com.example.sarbo.myapplication;

//this type of class is needed specially when we need more than one tables in the database
// class that represents the table in database
//for each table we need this type of class for representing table
public class Alrams {

    public  static abstract class Alraminfo{
        public static final String TABLE_ALARMS = "alarms";
        public static final String COLUMN_ID ="_id";
        public static final String COLUMN_TASKNAME ="taskname";
        public static final String COLUMN_LOCATIONNAME ="locationname";
        public static final String COLUMN_NOTE ="note";
        public static final String COLUMN_LONGITUDE="longitude";
        public static final String COLUMN_LATITUDE="latitude";




    }

}
