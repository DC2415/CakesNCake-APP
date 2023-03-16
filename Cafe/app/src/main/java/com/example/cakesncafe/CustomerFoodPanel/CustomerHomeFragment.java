package com.example.cakesncafe.CustomerFoodPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cakesncafe.Adapters.CustomerHomeAdapter;
import com.example.cakesncafe.Models.MainMenuItem;
import com.example.cakesncafe.Models.UpdateDishModel;
import com.example.cakesncafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    private List<MainMenuItem> updateDishModelList;
    private CustomerHomeAdapter adapter;
      SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "User Home";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_home, null);
        getActivity().setTitle("Home");
      //  Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);

        recyclerView = v.findViewById(R.id.recycler_menu_customer);
        recyclerView.setHasFixedSize(true);
      //  recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);

        updateDishModelList = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.Red);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                db.collection("Menu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MainMenuItem mainMenuItem = document.toObject(MainMenuItem.class);
                                updateDishModelList.add(mainMenuItem);
                            }
                            adapter = new CustomerHomeAdapter(getContext(), updateDishModelList);
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });;
            }
        });
        return v;
    }

    @Override
    public void onRefresh() {
        customerMenu();
    }

    private void customerMenu() {
        swipeRefreshLayout.setRefreshing(true);
     db.collection("Menu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MainMenuItem mainMenuItem = document.toObject(MainMenuItem.class);
                        updateDishModelList.add(mainMenuItem);
                    }
                    adapter = new CustomerHomeAdapter(getContext(), updateDishModelList);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });;


    }
}
