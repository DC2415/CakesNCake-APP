package com.example.cakesncafe.CustomerFoodPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.cakesncafe.Adapters.CustomerHomeAdapter;
import com.example.cakesncafe.Adapters.MenuItemAdapter;
import com.example.cakesncafe.Models.MainMenuItem;
import com.example.cakesncafe.Models.SubMenuItem;
import com.example.cakesncafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomerSubFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    private List<SubMenuItem> updateDishModelList;
    private MenuItemAdapter adapter;
      SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Sub Items";
    MainMenuItem category=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_items, null);
        getActivity().setTitle("Items");
        //Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
             category = bundle.getParcelable("Category");
        }
        recyclerView = v.findViewById(R.id.recycler_sub_menu_customer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.Red);

        MainMenuItem finalCategory = category;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                db.collection("Menu").document(finalCategory.getId()).collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SubMenuItem subMenuItem = document.toObject(SubMenuItem.class);
                                updateDishModelList.add(subMenuItem);
                            }
                            adapter = new MenuItemAdapter(getContext(), updateDishModelList);
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
        customerMenu(category.getId());
    }

    private void customerMenu(String category) {
        swipeRefreshLayout.setRefreshing(true);
     db.collection("Menu").document(category).collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        SubMenuItem subMenuItem = document.toObject(SubMenuItem.class);
                        updateDishModelList.add(subMenuItem);
                    }
                    adapter = new MenuItemAdapter(getContext(), updateDishModelList);
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
