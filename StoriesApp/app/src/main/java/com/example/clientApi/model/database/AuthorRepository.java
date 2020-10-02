package com.example.clientApi.model.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**Class for repository for data
 * access from the ViewModel class
 * */
public class AuthorRepository {

    //
    private static AuthorRepository repInstance;
    //
    public LiveData<List<Authors>>  mAuthors;
    //Create Database instance
    private AuthorsDatabase mDb;
    //
    private Executor executor = Executors.newSingleThreadExecutor();
    //
    private static final String TAG = AuthorRepository.class.getSimpleName();

    public static AuthorRepository getInstance(Context context){
        if (repInstance == null) {
            repInstance = new AuthorRepository(context);
        }

        return repInstance;
    }

    private AuthorRepository(Context context){
        mDb = AuthorsDatabase.getDatabase(context);
        mAuthors = getAllAuthors();
        Log.i(TAG,"Data From database: "+mAuthors.toString());
    }


    public void insertAuthor(final Authors authors){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.authorDeo().insertOne(authors);
            }
        });
    }



    public LiveData<List<Authors>> getAllAuthors(){
        return mDb.authorDeo().getAllAuthors();
    }
}
