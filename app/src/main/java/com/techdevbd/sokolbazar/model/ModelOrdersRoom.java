package com.techdevbd.sokolbazar.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orderstitem")
public class ModelOrdersRoom {

    @PrimaryKey(autoGenerate = true)
    int id;
    String phone;
    String orderNumber;
    String dateTime;
    String deliverytype;


    public ModelOrdersRoom() {

    }


    public ModelOrdersRoom(String phone, String orderNumber, String dateTime,String deliverytype) {
        this.phone = phone;
        this.orderNumber = orderNumber;
        this.dateTime = dateTime;
        this.deliverytype = deliverytype;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }
}
