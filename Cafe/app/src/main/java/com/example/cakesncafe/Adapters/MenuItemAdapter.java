package com.example.cakesncafe.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cakesncafe.CustomerFoodPanel.CustomerSubFragment;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.CartItem;
import com.example.cakesncafe.Models.MainMenuItem;
import com.example.cakesncafe.Models.SubMenuItem;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private Context mContext;
    private List<SubMenuItem> updateDishModelList;
    DatabaseReference databaseReference;

    public MenuItemAdapter(Context mContext, List<SubMenuItem> updateDishModelList) {
        this.mContext = mContext;
        this.updateDishModelList = updateDishModelList;
    }

    @NonNull
    @Override
    public MenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_menu_dish, parent, false);
        return new MenuItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemAdapter.ViewHolder holder, int position) {
        final SubMenuItem updateDishModel = updateDishModelList.get(position);
        holder.dish_name.setText(updateDishModel.getName());
        holder.price.setText("₹" + updateDishModel.getPrice());
        holder.desc.setText(updateDishModel.getDesc());
        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart= CartHelper.getCart();
                CartItem cartItem=new CartItem();
                cartItem.setCartMenu(updateDishModel);
                cartItem.setUnitPrice(updateDishModel.getPrice());
                cartItem.setQuantity(1);
                cart.addItem(cartItem,mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateDishModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dish_name, price, desc;
        Button add_to_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.dish_desc);
            dish_name = itemView.findViewById(R.id.dish_name);
            price = itemView.findViewById((R.id.dish_price));
            add_to_cart = itemView.findViewById(R.id.add_to_cart);

        }
    }
}
