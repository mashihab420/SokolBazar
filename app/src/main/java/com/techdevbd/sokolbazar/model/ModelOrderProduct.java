package com.techdevbd.sokolbazar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrderProduct {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String Phone;

    @SerializedName("product_name")
    @Expose
    private String 	Product_name;

    @SerializedName("quantity")
    @Expose
    private int Quantity;

    @SerializedName("invoice_number")
    @Expose
    private String 	Invoice_number;

    @SerializedName("price")
    @Expose
    private int Price;

    @SerializedName("shop_name")
    @Expose
    private String 	Shop_name;

    @SerializedName("subtotal")
    @Expose
    private String 	Subtotal;

    @SerializedName("discount")
    @Expose
    private String 	Discount;

    @SerializedName("total")
    @Expose
    private String 	Total;

    @SerializedName("image_url")
    @Expose
    private String 	Image_url;


    @SerializedName("delivery_type")
    @Expose
    private String 	Delivery_type;

    @SerializedName("delivery_time")
    @Expose
    private String 	Delivery_time;

    @SerializedName("message")
    @Expose
    private String 	Message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getInvoice_number() {
        return Invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        Invoice_number = invoice_number;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getShop_name() {
        return Shop_name;
    }

    public void setShop_name(String shop_name) {
        Shop_name = shop_name;
    }

    public String getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(String subtotal) {
        Subtotal = subtotal;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getDelivery_time() {
        return Delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        Delivery_time = delivery_time;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDelivery_type() {
        return Delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        Delivery_type = delivery_type;
    }
}