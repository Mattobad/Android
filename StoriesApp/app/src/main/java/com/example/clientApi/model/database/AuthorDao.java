package com.example.clientApi.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
/**
 * Class for data access object
 * */
@Dao
public interface AuthorDao {

    @Query("SELECT * FROM authors ORDER BY id DESC LIMIT 10")
    LiveData<List<Authors>>  getAllAuthors();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Authors authors);

    @Query("DELETE FROM authors")
    int deleteAll();
}
