package com.example.cakesncafe.helpers;


import com.example.cakesncafe.Models.Cart;

/**
 * Created by Rohit on 25-02-2017.
 */

public class CartHelper {

    private static Cart cart ;

    /**
     * Retrieve the shopping cart. Call this before perform any manipulation on the shopping cart.
     *
     * @return the shopping cart
     */
    public static synchronized Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }
}
