package com.example.cakesncafe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MainMenuItem implements Parcelable {
    String id, name, imageUrl, desc;

    public MainMenuItem() {

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

    protected MainMenuItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        desc = in.readString();
    }

    public static final Creator<MainMenuItem> CREATOR = new Creator<MainMenuItem>() {
        @Override
        public MainMenuItem createFromParcel(Parcel in) {
            return new MainMenuItem(in);
        }

        @Override
        public MainMenuItem[] newArray(int size) {
            return new MainMenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(desc);
    }
}
