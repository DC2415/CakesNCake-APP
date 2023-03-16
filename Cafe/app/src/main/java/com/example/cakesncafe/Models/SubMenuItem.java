package com.example.cakesncafe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SubMenuItem implements Parcelable{
    String id,name, imageUrl, desc,quantity;
    double price;
    public SubMenuItem() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    protected SubMenuItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        desc = in.readString();
        quantity = in.readString();
        price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(desc);
        dest.writeString(quantity);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubMenuItem> CREATOR = new Creator<SubMenuItem>() {
        @Override
        public SubMenuItem createFromParcel(Parcel in) {
            return new SubMenuItem(in);
        }

        @Override
        public SubMenuItem[] newArray(int size) {
            return new SubMenuItem[size];
        }
    };
}
