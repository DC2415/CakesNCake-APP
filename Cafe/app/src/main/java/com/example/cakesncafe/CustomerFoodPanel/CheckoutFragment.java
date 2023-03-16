package com.example.cakesncafe.CustomerFoodPanel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cakesncafe.Adapters.MenuItemAdapter;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.Models.MainMenuItem;
import com.example.cakesncafe.Models.Order;
import com.example.cakesncafe.Models.SubMenuItem;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CheckoutFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Sub Items";
    MainMenuItem category=null;
    RelativeLayout relativeLayout;
    Button confirmBtn;

    EditText editTextTextPostalAddress,mobileNumber;
    ProgressBar progressBar;
    TextView confirmedText;
    ImageView success,reject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checkout, null);
        getActivity().setTitle("Checkout");
        relativeLayout=v.findViewById(R.id.confirmLayout);
        confirmBtn=v.findViewById(R.id.place_order_btn);
        editTextTextPostalAddress=v.findViewById(R.id.editTextTextPostalAddress);
        progressBar=v.findViewById(R.id.progressBarCheckout);
        confirmedText=v.findViewById(R.id.confirmed_txt);
        mobileNumber=v.findViewById(R.id.mobile_number);

        success=v.findViewById(R.id.successIcon);
        reject=v.findViewById(R.id.failureIcon);
        progressBar.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextTextPostalAddress.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Please add address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobileNumber.getText().toString().isEmpty() ||mobileNumber.getText().toString().length()!=10){
                    Toast.makeText(getContext(),"Please add valid mobile number",Toast.LENGTH_SHORT).show();
                    return;
                }
             confirmBtn.setEnabled(false);
                confirmBtn.setText("Please wait");
                editTextTextPostalAddress.setEnabled(false);
                editTextTextPostalAddress.clearFocus();
                Order order=new Order();
            Cart cart= CartHelper.getCart();
            order.setOrderStatus("PLACED");
            order.setName(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            order.setUid(FirebaseAuth.getInstance().getUid());
            order.setOrderList(cart.getList());
            order.setTimeStamp(new Date().getTime());
            order.setCost(Cart.getTotalPrice());
            order.setNumberOfItems(Cart.getTotalQuantity());
            order.setAddress(editTextTextPostalAddress.getText().toString());
            order.setOrderId(generateID());
            order.setMobileNumber(mobileNumber.getText().toString());
            db.collection("Orders").document(order.getOrderId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressBar.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    editTextTextPostalAddress.setEnabled(false);
                    confirmBtn.setText("Order Placed");
                    confirmBtn.setBackgroundColor(Color.GRAY);
                    db.collection("Orders").document(order.getOrderId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null && value.exists()) {
                               Order updatedOrder=value.toObject(Order.class);
                               if(updatedOrder.getOrderStatus().equalsIgnoreCase("Accepted")){
                                   confirmedText.setVisibility(View.VISIBLE);
                                   progressBar.setVisibility(View.INVISIBLE);
                                   success.setVisibility(View.VISIBLE);
                               }else  if(updatedOrder.getOrderStatus().equalsIgnoreCase("REJECTED")){
                                    confirmedText.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    confirmedText.setText("Sorry we are unable to confirm your order. Please try in some time");
                                    reject.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Log.d(TAG, " data: null");
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    confirmBtn.setEnabled(true);
                    confirmBtn.setText("PLACE ORDER");
                    editTextTextPostalAddress.setEnabled(true);
                }
            });
            }
        });
        return v;
    }
 String generateID(){
     Random r = new Random( System.currentTimeMillis() );
     int num= 10000 + r.nextInt(20000);
        return "CNF"+num;
 }
    @Override
    public void onRefresh() {
        customerMenu(category.getId());
    }

    private void customerMenu(String category) {
     db.collection("Orders").document(category).collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        SubMenuItem subMenuItem = document.toObject(SubMenuItem.class);
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });;
    }
}
