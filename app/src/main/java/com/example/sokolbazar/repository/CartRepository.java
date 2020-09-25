package com.example.sokolbazar.repository;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.sokolbazar.model.ModelCart;
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

    public void UpdateSingleData(ModelCartRoom cartdb)
    {
        new UpdateData(roomDao).execute(cartdb);
    }

    public void delete(ModelCartRoom cartdb)
    {
        new DeleteData(roomDao).execute(cartdb);
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
            Toast.makeText(context, "Add to Cart", Toast.LENGTH_SHORT).show();
        }
    }


    private class UpdateData extends AsyncTask<ModelCartRoom,Void,Void> {
        RoomDao roomDao;


        public  UpdateData(RoomDao roomDao) {
            this.roomDao = roomDao;

        }

        @Override
        protected Void doInBackground(ModelCartRoom... modelCartRooms) {
            roomDao.updateSingleData(modelCartRooms[0]);
            return null;
        }
    }

    private class DeleteData extends AsyncTask<ModelCartRoom,Void,Void>{
        RoomDao roomDao;
        public DeleteData(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(ModelCartRoom... modelCartRooms) {
            roomDao.DeleteSingleData(modelCartRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
        }
    }


}
