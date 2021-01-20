package com.techdevbd.sokolbazar.activity;

public interface OnDataSend {
    void totalPrice(String subtotal,String discount);
    void orderprice(String subtotal,String total,String discount);
}
