package com.example.cakesncafe.CustomerFoodPanel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cakesncafe.Adapters.CustomerOrderItemAdapter;
import com.example.cakesncafe.Adapters.CustomerOrderSingleItemAdapter;
import com.example.cakesncafe.Adapters.OrderSingleItemAdapter;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.Order;
import com.example.cakesncafe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerOrderDetailsFragment extends Fragment{
    @Nullable
    RecyclerView cartView;
    Cart cart;
    private CustomerOrderSingleItemAdapter adapter;
    TextView orderTotal,orderStatus;
    RelativeLayout orderActionLayout;
    Order order;
    ProgressBar progressBar;
    TextView confirmedText;
    ImageView success,reject;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cust_order_single_view, null);
        getActivity().setTitle("Order Details");
       // Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            order = bundle.getParcelable("Order");
        }
        progressBar=v.findViewById(R.id.progressBarConfirm);
        confirmedText=v.findViewById(R.id.confirmed_txt);
        success=v.findViewById(R.id.successIcon);
        reject=v.findViewById(R.id.failureIcon);
        progressBar.setVisibility(View.INVISIBLE);
        cartView=v.findViewById(R.id.cart_item_view);
        orderTotal=v.findViewById(R.id.order_total_text);
        orderStatus=v.findViewById(R.id.order_status_text);
        orderActionLayout=v.findViewById(R.id.confirmLayout);

        cartView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(order.getOrderStatus().equalsIgnoreCase("PLACED")){
            orderActionLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }else {
            orderActionLayout.setVisibility(View.GONE);
        }
        orderTotal.setText("Total: ₹"+order.getCost());
        orderStatus.setText("Status: "+order.getOrderStatus());

        adapter = new CustomerOrderSingleItemAdapter(getContext(), order.getOrderList(),this);
        cartView.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("Orders").document(order.getOrderId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    Order updatedOrder=value.toObject(Order.class);
                    if(updatedOrder.getOrderStatus().equalsIgnoreCase("Accepted")){
                        confirmedText.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        success.setVisibility(View.VISIBLE);
                        orderStatus.setText("Status: "+updatedOrder.getOrderStatus());

                    }else  if(updatedOrder.getOrderStatus().equalsIgnoreCase("REJECTED")){
                        confirmedText.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        confirmedText.setText("Sorry we are unable to confirm your order. Please try in some time");
                        reject.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("Cust order detail page", " data: null");
                }
            }
        });
        return v;
    }
}
