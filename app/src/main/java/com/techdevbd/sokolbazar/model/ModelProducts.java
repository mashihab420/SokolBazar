package com.techdevbd.sokolbazar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProducts {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_price")
    @Expose
    private String pPrice;
    @SerializedName("offers")
    @Expose
    private String offers;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("p_description")
    @Expose
    private String description;


    @SerializedName("shop_name")
    @Expose
    private String shopName;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPPrice() {
        return pPrice;
    }

    public void setPPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
