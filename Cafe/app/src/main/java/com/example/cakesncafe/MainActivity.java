package com.example.cakesncafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cakesncafe.Models.FireUser;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
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
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    Window window;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    GoogleSignInButton btn;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.loginProgressBar);
        btn = findViewById(R.id.signInBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setLogo(R.drawable.logo)
                                .setIsSmartLockEnabled(true)
                                .build(),
                        RC_SIGN_IN);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginUser();
            }
        }, 3000);
    }

    private void LoginUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(firebaseAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            FireUser fireUser=document.toObject(FireUser.class);
                            if(!fireUser.isAdmin()){
                                startActivity(new Intent(MainActivity.this, CustFoodPanelActivity.class));
                            }else{
                                startActivity(new Intent(MainActivity.this, ChefFoodPanelActivity.class));
                            }
                            finish();

                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        btn.setVisibility(View.VISIBLE);
                    }

                }
            });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                final Handler handler = new Handler();
                //loading.setText(generateSpannableWhite("Please Wait..."));
                progressBar.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginUser();
                        Toast.makeText(MainActivity.this, "Sign in Successful", Toast.LENGTH_LONG).show();
                    }
                }, 1000);
                return;
            } else {

                // Sign in failed
                if (response == null) {
                    // User pressed back button
//                        Toasty.warning(getApplicationContext(), "", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "unknown sign in response", Toast.LENGTH_SHORT).show();

            }
        }

    }
}