package com.example.cakesncafe.Models;

/**
 * Created by Rohit on 25-02-2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class Cart implements Serializable {

    private static final long serialVersionUID = 42L;
    static List<CartItem> cartItemList;
    private static double totalPrice = 0;
    private static int totalQuantity = 0;

    public Cart() {
        cartItemList = new ArrayList<>();
    }

    public static double getTotalPrice() {
        return totalPrice;
    }

    public static int getTotalQuantity() {
        return totalQuantity;
    }

    public void addItem(final CartItem cartItem, final Context mContext) {
        boolean updateFlag = false;

        for (CartItem c :
                cartItemList) {
            if (c.cartMenu.id.equalsIgnoreCase(cartItem.cartMenu.id)) {
                updateFlag = true;
            }
        }

        if (!updateFlag) {
            cartItem.setItemPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getItemPrice();
            cartItemList.add(cartItem);
            Toast.makeText(mContext, cartItem.getQuantity() + " Item(s) Added to cart", Toast.LENGTH_SHORT).show();
        }else{
            update(cartItem, cartItem.getQuantity());
            Toast.makeText(mContext, cartItem.getQuantity() + " Item(s) Updated to cart", Toast.LENGTH_SHORT).show();

        }

    }

    public void update(CartItem cartItem, int quantity) {
        int ind = cartItemList.indexOf(cartItem);
        int prevq = cartItem.getQuantity();
        totalQuantity -= prevq;
        double currprice = prevq * cartItem.getUnitPrice();
        cartItem.setQuantity(quantity);
        totalQuantity += cartItem.getQuantity();
        cartItem.setItemPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
        totalPrice -= currprice;
        totalPrice += cartItem.getItemPrice();
    }

    public void deleteItem(CartItem cartItem, int position) {
        totalQuantity -= cartItem.getQuantity();
        totalPrice -= cartItem.getItemPrice();
        cartItemList.remove(position);
    }

    public void emptyCart() {
        cartItemList = new ArrayList<>();
        totalPrice = 0;
        totalQuantity = 0;
    }

    public List<CartItem> getList() {
        return cartItemList;
    }

}
