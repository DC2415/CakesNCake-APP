package com.example.cakesncafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.CartItem;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class OrderSingleItemAdapter extends RecyclerView.Adapter<OrderSingleItemAdapter.ViewHolder> {
    private Context mContext;
    private List<CartItem> cartItemList;
    private Fragment fragment;

    public OrderSingleItemAdapter(Context mContext, List<CartItem> updateDishModelList, Fragment fragment) {
        this.mContext = mContext;
        this.cartItemList = updateDishModelList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public OrderSingleItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_single_item, parent, false);
        return new OrderSingleItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSingleItemAdapter.ViewHolder holder, int position) {
        final CartItem updateDishModel = cartItemList.get(position);
        holder.dish_name.setText(updateDishModel.getCartMenu().getName());
        holder.price.setText("₹" + updateDishModel.getItemPrice());
        holder.item_qty.setText("Qty: " + updateDishModel.getQuantity());

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dish_name, price, item_qty;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.dish_name);
            price = itemView.findViewById((R.id.dish_price));
            item_qty=itemView.findViewById(R.id.item_qty);
            mView = itemView;
        }
    }
}
