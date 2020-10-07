package com.techdevbd.sokolbazar.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techdevbd.sokolbazar.model.ModelCartRoom;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleData(ModelCartRoom cartdb);

    @Update
    void updateSingleData(ModelCartRoom cartdb);

    @Delete
    void DeleteSingleData(ModelCartRoom cartdb);

    @Query("SELECT * FROM cartitem")
    LiveData<List<ModelCartRoom>> getAllData();


}
