package com.example.cakesncafe.CustomerFoodPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakesncafe.Adapters.CustomerOrderItemAdapter;
import com.example.cakesncafe.Adapters.OrderItemAdapter;
import com.example.cakesncafe.Models.Order;
import com.example.cakesncafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderFragment extends Fragment {

        RecyclerView pendingOrderView;
        TextView noOrders;
        private List<Order> orderList;
        private CustomerOrderItemAdapter adapter;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_chef_pending_orders,null);
            getActivity().setTitle("All Orders");
            pendingOrderView=v.findViewById(R.id.pending_orders_recycle_view);
            noOrders=v.findViewById(R.id.no_pending_orders);
            pendingOrderView.setHasFixedSize(true);
            pendingOrderView.setLayoutManager(new LinearLayoutManager(getContext()));
            orderList = new ArrayList<>();
            FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        orderList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Order mainMenuItem = document.toObject(Order.class);
                            orderList.add(mainMenuItem);
                        }
                        if(orderList.isEmpty()){
                            noOrders.setText("You don't have any orders");
                        }else{
                            noOrders.setVisibility(View.GONE);
                            adapter = new CustomerOrderItemAdapter(getContext(), orderList);
                            pendingOrderView.setAdapter(adapter);
                        }

                    } else {
                        Log.d("Pending orders", "Error getting documents: ", task.getException());
                    }
                }
            });;
            return v;
    }
}
