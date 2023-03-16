package com.example.cakesncafe.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cakesncafe.CustomerFoodPanel.CustomerSubFragment;
import com.example.cakesncafe.Models.MainMenuItem;
import com.example.cakesncafe.Models.UpdateDishModel;
import com.example.cakesncafe.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {
    private Context mContext;
    private List<MainMenuItem> updateDishModelList;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context mContext, List<MainMenuItem> updateDishModelList) {
        this.mContext = mContext;
        this.updateDishModelList = updateDishModelList;
    }

    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customer_menu_dish, parent, false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {
        final MainMenuItem updateDishModel = updateDishModelList.get(position);
        Glide.with(mContext).load(updateDishModel.getImageUrl()).into(holder.imageView);
        holder.dish_name.setText(updateDishModel.getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("Category",updateDishModel);
                CustomerSubFragment fragment2 = new CustomerSubFragment();
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

        ImageView imageView;
        TextView dish_name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_image);
            dish_name = itemView.findViewById(R.id.dish_name);
        }
    }
}
