package com.example.clientApi.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Class for database connection
 * */
@Database(entities = {Authors.class},version = 1,exportSchema = false)
public abstract class AuthorsDatabase extends RoomDatabase {

    private static AuthorsDatabase INSTANCE;
    //Name of the database
    private static final String DB_NAME = "authers.db";
    //object for synchronization
    private static final Object LOCK = new Object();

    //abstract function for AuthorDao
    public abstract AuthorDao authorDeo();
    //
    public static AuthorsDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (LOCK){
                if (INSTANCE ==  null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        AuthorsDatabase.class,DB_NAME).build();
                }

            }
        }

        return INSTANCE;
    }

}
