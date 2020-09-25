package com.example.sokolbazar.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartitem")
public class ModelCartRoom {

    @PrimaryKey(autoGenerate = true)
    int id;
    String p_name;
    String p_price;
    String quantity;
    String offers;
    String url;
    String c_logo;

    public ModelCartRoom (){

    }

    public ModelCartRoom(String p_name, String p_price, String quantity,String offers, String url, String c_logo) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.quantity = quantity;
        this.offers = offers;
        this.url = url;
        this.c_logo = c_logo;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getC_logo() {
        return c_logo;
    }

    public void setC_logo(String c_logo) {
        this.c_logo = c_logo;
    }
}
