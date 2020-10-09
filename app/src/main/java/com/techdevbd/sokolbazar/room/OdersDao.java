package com.techdevbd.sokolbazar.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;

import java.util.List;

@Dao
public interface OdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleData(ModelOrdersRoom orderdb);


    @Delete
    void DeleteSingleData(ModelOrdersRoom orderdb);

    @Query("SELECT * FROM orderstitem")
    LiveData<List<ModelOrdersRoom>> getAllOrders();
}
