package com.techdevbd.sokolbazar.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;
import com.techdevbd.sokolbazar.room.MyRoomDataBase;
import com.techdevbd.sokolbazar.room.OdersDao;
import com.techdevbd.sokolbazar.room.OrdersRoomDataBase;
import com.techdevbd.sokolbazar.room.RoomDao;

import java.util.List;

public class OrdersRepository {

    private OdersDao odersDao;
    private OrdersRoomDataBase roomDataBase;
    private LiveData<List<ModelOrdersRoom>> allData;
    private Context context;

    public OrdersRepository(Context context) {
        this.context = context;
        roomDataBase = OrdersRoomDataBase.getInstance(context);
        odersDao = roomDataBase.odersDao();
        allData = odersDao.getAllOrders();
    }

    public LiveData<List<ModelOrdersRoom>> getAllData(){
        return  this.allData;
    }

    public void insertSingleData(ModelOrdersRoom orderdb)
    {
        new OrdersRepository.InsetData(odersDao).execute(orderdb);
    }



    public void delete(ModelOrdersRoom orderdb)
    {
        new OrdersRepository.DeleteData(odersDao).execute(orderdb);
    }


    private class InsetData extends AsyncTask<ModelOrdersRoom,Void,Void> {
        OdersDao odersDao;
        public InsetData(OdersDao odersDao) {
            this.odersDao = odersDao;
        }

        @Override
        protected Void doInBackground(ModelOrdersRoom... modelCartRooms) {
            odersDao.insertSingleData(modelCartRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Data insert to order", Toast.LENGTH_SHORT).show();
        }
    }



    private class DeleteData extends AsyncTask<ModelOrdersRoom,Void,Void>{
        OdersDao odersDao;
        public DeleteData(OdersDao odersDao) {
            this.odersDao = odersDao;
        }

        @Override
        protected Void doInBackground(ModelOrdersRoom... modelCartRooms) {
            odersDao.DeleteSingleData(modelCartRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
