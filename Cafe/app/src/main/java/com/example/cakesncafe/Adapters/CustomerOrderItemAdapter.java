package com.example.cakesncafe.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cakesncafe.ChefFoodPanel.ChefOrderDetailsFragment;
import com.example.cakesncafe.CustomerFoodPanel.CustomerOrderDetailsFragment;
import com.example.cakesncafe.Models.Order;
import com.example.cakesncafe.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerOrderItemAdapter extends RecyclerView.Adapter<CustomerOrderItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Order> updateDishModelList;

    public CustomerOrderItemAdapter(Context mContext, List<Order> updateDishModelList) {
        this.mContext = mContext;
        this.updateDishModelList = updateDishModelList;
    }

    @NonNull
    @Override
    public CustomerOrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pending_order, parent, false);
        return new CustomerOrderItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderItemAdapter.ViewHolder holder, int position) {
        final Order updateDishModel = updateDishModelList.get(position);
        holder.orderFrom.setText("From:"+updateDishModel.getName());
        holder.orderId.setText("Order ID:"+updateDishModel.getOrderId());
        holder.price.setText("₹" + updateDishModel.getCost());
        holder.quantity.setText("Qty:"+updateDishModel.getNumberOfItems());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("Order",updateDishModel);
                CustomerOrderDetailsFragment fragment2 = new CustomerOrderDetailsFragment();
                fragment2.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateDishModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, price, quantity,orderFrom;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderFrom = itemView.findViewById(R.id.order_from);
            price = itemView.findViewById((R.id.dish_price));
            quantity = itemView.findViewById(R.id.quantity);
            mView = itemView;

        }
    }
}
