package com.example.cakesncafe.Models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rohit on 25-02-2017.
 */

public class CartItem implements Parcelable {

    SubMenuItem cartMenu;
    private double itemPrice;
    private double unitPrice;
    private int quantity;
    private int Weight;
    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public CartItem() {
    }

    private int costId;

    protected CartItem(Parcel in) {
        cartMenu = in.readParcelable(SubMenuItem.class.getClassLoader());
        itemPrice = in.readDouble();
        unitPrice = in.readDouble();
        quantity = in.readInt();
        Weight = in.readInt();
        costId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cartMenu, flags);
        dest.writeDouble(itemPrice);
        dest.writeDouble(unitPrice);
        dest.writeInt(quantity);
        dest.writeInt(Weight);
        dest.writeInt(costId);
    }

    public SubMenuItem getCartMenu() {
        return cartMenu;
    }

    public void setCartMenu(SubMenuItem cartMenu) {
        this.cartMenu = cartMenu;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getCostId() {
        return costId;
    }

    public void setCostId(int costId) {
        this.costId = costId;
    }
}
