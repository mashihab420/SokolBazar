package com.techdevbd.sokolbazar.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;

@Database(entities = ModelOrdersRoom.class,version = 1,exportSchema = false)
public abstract class OrdersRoomDataBase extends RoomDatabase {

    private static OrdersRoomDataBase ordersRoomDataBase = null;

    public abstract OdersDao odersDao();

    public static synchronized OrdersRoomDataBase getInstance(Context context)
    {
        if (ordersRoomDataBase==null)
        {
            ordersRoomDataBase = Room.databaseBuilder(context,OrdersRoomDataBase.class,"orderdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return ordersRoomDataBase;
    }

}
