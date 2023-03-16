package com.example.cakesncafe.CustomerFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cakesncafe.MainActivity;
import com.example.cakesncafe.R;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerProfileFragment extends Fragment{
    Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_profile, null);
        getActivity().setTitle("Profile");
        logout=v.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"successfully signed out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });
        return v;
    }
}
