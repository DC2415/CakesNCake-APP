package com.example.cakesncafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.cakesncafe.CustFoodPanelActivity;
import com.example.cakesncafe.CustomerFoodPanel.CustomerCartFragment;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.CartItem;
import com.example.cakesncafe.Models.SubMenuItem;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context mContext;
    private List<CartItem> cartItemList;
    private Fragment fragment;

    public CartItemAdapter(Context mContext, List<CartItem> updateDishModelList, Fragment fragment) {
        this.mContext = mContext;
        this.cartItemList = updateDishModelList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_menu_item, parent, false);
        return new CartItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        final CartItem updateDishModel = cartItemList.get(position);
        holder.dish_name.setText(updateDishModel.getCartMenu().getName());
        holder.price.setText("₹" + updateDishModel.getItemPrice());
        holder.elegantNumberButton.setNumber("" + updateDishModel.getQuantity());
        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                CartHelper.getCart().update(updateDishModel, newValue);
                notifyItemChanged(holder.getAdapterPosition());
                holder.price.setText("₹" + updateDishModel.getItemPrice());

                String aa = String.format("%.2f", Cart.getTotalPrice());
                Button checkout = fragment.getView().findViewById(R.id.checkout_btn);
                checkout.setText("₹" + aa + " CHECKOUT");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dish_name, price, desc;
        public ElegantNumberButton elegantNumberButton;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.dish_name);
            price = itemView.findViewById((R.id.dish_price));
            elegantNumberButton = itemView.findViewById(R.id.numberbutton);
            mView = itemView;
        }
    }
}
