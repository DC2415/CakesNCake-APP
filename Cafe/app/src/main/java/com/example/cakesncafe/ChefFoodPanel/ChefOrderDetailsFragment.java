package com.example.cakesncafe.ChefFoodPanel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cakesncafe.Adapters.CartItemAdapter;
import com.example.cakesncafe.Adapters.OrderSingleItemAdapter;
import com.example.cakesncafe.CustomerFoodPanel.CheckoutFragment;
import com.example.cakesncafe.MainActivity;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.Order;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChefOrderDetailsFragment extends Fragment{
    @Nullable
    RecyclerView cartView;
    Cart cart;
    public Button rejectBtn, acceptBtn;
    private OrderSingleItemAdapter adapter;
    TextView orderTotal,orderStatus,orderMobile,orderAddress;
    LinearLayout orderActionLayout;
    Order order;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_single_view, null);
        getActivity().setTitle("Order Details");
       // Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            order = bundle.getParcelable("Order");
        }
        cartView=v.findViewById(R.id.cart_item_view);
        acceptBtn=v.findViewById(R.id.accept_order_btn);
        rejectBtn=v.findViewById(R.id.reject_order_btn);
        orderTotal=v.findViewById(R.id.order_total_text);
        orderStatus=v.findViewById(R.id.order_status_text);
        orderMobile=v.findViewById(R.id.order_mobile_text);
        orderAddress=v.findViewById(R.id.order_address_text);

        orderActionLayout=v.findViewById(R.id.orderStatusView);
        cartView.setHasFixedSize(true);
      //  cartView.startAnimation(animation);
        cartView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(order.getOrderStatus().equalsIgnoreCase("PLACED")){
            orderActionLayout.setVisibility(View.VISIBLE);
        }else {
            orderActionLayout.setVisibility(View.GONE);
        }
        orderTotal.setText("Total: ₹"+order.getCost());
        orderStatus.setText("Status: "+order.getOrderStatus());
        orderMobile.setText("Mobile No: "+order.getMobileNumber());
        orderAddress.setText("Address: "+order.getAddress());
        adapter = new OrderSingleItemAdapter(getContext(), order.getOrderList(),this);
        cartView.setAdapter(adapter);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             acceptBtn.setEnabled(false);
             rejectBtn.setEnabled(false);
             acceptBtn.setText("Please Wait");
             acceptBtn.setBackgroundColor(Color.GRAY);
             order.setOrderStatus("ACCEPTED");
             FirebaseFirestore.getInstance().collection("Orders").document(order.getOrderId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void unused) {
                     orderActionLayout.setVisibility(View.GONE);
                     orderStatus.setText("Status: "+order.getOrderStatus());
                     Toast.makeText(getContext(), "Order Accepted", Toast.LENGTH_LONG).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     acceptBtn.setEnabled(true);
                     rejectBtn.setEnabled(true);
                     acceptBtn.setText("ACCEPT");
                 }
             });
         }
     });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptBtn.setEnabled(false);
                rejectBtn.setEnabled(false);
                rejectBtn.setText("Please Wait");
                rejectBtn.setBackgroundColor(Color.GRAY);
                order.setOrderStatus("REJECTED");
                FirebaseFirestore.getInstance().collection("Orders").document(order.getOrderId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        orderActionLayout.setVisibility(View.GONE);
                        orderStatus.setText("Status: "+order.getOrderStatus());
                        Toast.makeText(getContext(), "Order Rejected", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        acceptBtn.setEnabled(true);
                        rejectBtn.setEnabled(true);
                        rejectBtn.setText("REJECT");
                    }
                });
            }
        });
        return v;
    }
}
