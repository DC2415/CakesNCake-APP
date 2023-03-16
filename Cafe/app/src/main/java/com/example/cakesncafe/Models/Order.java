package com.example.cakesncafe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Rohit on 12-04-2017.
 */

public class Order implements Parcelable {
    String orderId;
    String uid;
    long timeStamp;
    double cost;
    int NumberOfItems;
    String orderStatus;
    String reserved;
    String address;
    List<CartItem> orderList;
    String name;
    String mobileNumber;

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getNumberOfItems() {
        return NumberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        NumberOfItems = numberOfItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CartItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CartItem> orderList) {
        this.orderList = orderList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    protected Order(Parcel in) {
        orderId = in.readString();
        uid = in.readString();
        timeStamp = in.readLong();
        cost = in.readDouble();
        NumberOfItems = in.readInt();
        orderStatus = in.readString();
        reserved = in.readString();
        address = in.readString();
        orderList = in.createTypedArrayList(CartItem.CREATOR);
        name = in.readString();
        mobileNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(uid);
        dest.writeLong(timeStamp);
        dest.writeDouble(cost);
        dest.writeInt(NumberOfItems);
        dest.writeString(orderStatus);
        dest.writeString(reserved);
        dest.writeString(address);
        dest.writeTypedList(orderList);
        dest.writeString(name);
        dest.writeString(mobileNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
