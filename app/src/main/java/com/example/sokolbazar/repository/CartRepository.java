package com.example.sokolbazar.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.sokolbazar.model.ModelCartRoom;
import com.example.sokolbazar.room.MyRoomDataBase;
import com.example.sokolbazar.room.RoomDao;

import java.util.List;

public class CartRepository {

    private RoomDao roomDao;
    private MyRoomDataBase roomDataBase;
    private LiveData<List<ModelCartRoom>> allData;
    private Context context;

    public CartRepository(Context context) {
        this.context = context;
        roomDataBase = MyRoomDataBase.getInstance(context);
        roomDao = roomDataBase.roomDao();
        allData = roomDao.getAllData();
    }

    public LiveData<List<ModelCartRoom>> getAllData(){
        return  this.allData;
    }

    public void insertSingleData(ModelCartRoom cartdb)
    {
        new InsetData(roomDao).execute(cartdb);
    }

    private class InsetData extends AsyncTask<ModelCartRoom,Void,Void> {
        RoomDao roomDao;
        public InsetData(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(ModelCartRoom... modelCartRooms) {
            roomDao.insertSingleData(modelCartRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Insertion Successful !", Toast.LENGTH_SHORT).show();
        }
    }
}
