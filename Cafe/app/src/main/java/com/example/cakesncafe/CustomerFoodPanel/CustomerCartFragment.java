package com.example.cakesncafe.CustomerFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakesncafe.Adapters.CartItemAdapter;
import com.example.cakesncafe.Adapters.CustomerHomeAdapter;
import com.example.cakesncafe.Models.Cart;
import com.example.cakesncafe.R;
import com.example.cakesncafe.helpers.CartHelper;

public class CustomerCartFragment extends Fragment{
    @Nullable
    LinearLayout noCartItems;
    RecyclerView cartView;
    Cart cart;
    public Button checkout;
    private CartItemAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_cart, null);
        getActivity().setTitle("Cart");
        noCartItems=v.findViewById(R.id.no_cart_items);
       // Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);

        cartView=v.findViewById(R.id.cust_cart);
        checkout=v.findViewById(R.id.checkout_btn);
        cartView.setHasFixedSize(true);
      //  cartView.startAnimation(animation);
        cartView.setLayoutManager(new LinearLayoutManager(getContext()));
        cart= CartHelper.getCart();
        if(cart.getList().isEmpty()){
            noCartItems.setVisibility(View.VISIBLE);
            cartView.setVisibility(View.GONE);
            checkout.setVisibility(View.GONE);
        }else {
            noCartItems.setVisibility(View.GONE);
            cartView.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.VISIBLE);
            adapter = new CartItemAdapter(getContext(), CartHelper.getCart().getList(),this);
            cartView.setAdapter(adapter);
        }
     checkout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             CheckoutFragment checkoutFragment = new CheckoutFragment();
             FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = manager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container, checkoutFragment);
             fragmentTransaction.commit();
         }
     });
        return v;
    }
}
