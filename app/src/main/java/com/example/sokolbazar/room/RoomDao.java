package com.example.sokolbazar.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sokolbazar.model.ModelCartRoom;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleData(ModelCartRoom cartdb);

    @Update
    void updateSingleDAta(ModelCartRoom cartdb);

    @Delete
    void DeleteSingleData(ModelCartRoom cartdb);

    @Query("SELECT *FROM cartitem")
    LiveData<List<ModelCartRoom>> getAllData();


}
