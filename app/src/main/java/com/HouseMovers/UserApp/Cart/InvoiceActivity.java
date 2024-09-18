package com.HouseMovers.UserApp.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HouseMovers.UserApp.Dashboard.SelectHomeCatActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity.HomeDashboardActivity;
import com.HouseMovers.UserApp.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;
    private String currentUserId;
    private DatabaseReference cartItemReference, ordersReference, userReference, historyReference, OrdersUserRef;
    private RecyclerView recyclerInvoice;
    private TextView txtViewTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Invoice");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvoiceActivity.this, SelectHomeCatActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        //fire base user id and database reference
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        cartItemReference = FirebaseDatabase.getInstance().getReference().child("CartItems").child(currentUserId);
        ordersReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        OrdersUserRef = FirebaseDatabase.getInstance().getReference().child("OrdersUser");
        userReference = FirebaseDatabase.getInstance().getReference("Users");
        historyReference = FirebaseDatabase.getInstance().getReference("History");
        getUserData();


        //add items to cart recycler
        recyclerInvoice = findViewById(R.id.recyclerInvoice);
        recyclerInvoice.setLayoutManager(new LinearLayoutManager(this));


        //initialization of views
        TextView  tvUserName = findViewById(R.id.tvUserName);
        TextView  tvUserPhone = findViewById(R.id.tvUserPhone);
        TextView tvUserAddress = findViewById(R.id.tvUserAddress);
        txtViewTotalAmount = findViewById(R.id.txtViewTotalAmount);
        btnSubmit = findViewById(R.id.btnSubmit);


        //on view click
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {

            addOrders();

        }
    }

    private void getUserData() {

        userReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {

//                    Users users = dataSnapshot.getValue(Users.class);
//
//                    userName = users.getUserName();
//                    userAddress = users.getUserAddress();
//                    userPhone = users.getUserPhoneNo();
//
//
//
//                    tvUserName.setText(userName);
//                    tvUserAddress.setText(userAddress);
//                    tvUserPhone.setText(userPhone);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class CartItemsHolder extends RecyclerView.ViewHolder {
        private TextView productName, productTotalCost, productQuantity;


        public CartItemsHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productTotalCost = itemView.findViewById(R.id.productTotalCost);
            productQuantity = itemView.findViewById(R.id.productQuantity);

//            profile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mClickListener.onItemClick(view, getAdapterPosition());
//                }
//            });


        }


    }
    private void addOrders() {


        final String orderId = ordersReference.push().getKey();
        //to generate random number for order number
        Random r = new Random();
        final String orderNumber = String.format("%04d", r.nextInt(1001));

    }

    private void visaBoxPayment() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.vise_box_dialoag, null);

        Button continueBtn;
        final EditText payment, installment;

        continueBtn = view.findViewById(R.id.finishBtn);
        payment = view.findViewById(R.id.payment);

        builder.setView(view);

        final android.app.AlertDialog alertDialog = builder.create();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentS = payment.getText().toString();
                if (paymentS.isEmpty()) {
                    Toast.makeText(InvoiceActivity.this, "please enter VISA card number", Toast.LENGTH_SHORT).show();
                } else {
                    submittDialog();
                    alertDialog.dismiss();
                }


            }
        });


        alertDialog.show();


    }


    private void submittDialog() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.submit_box_dialoag, null);

        Button continueBtn;
        CardView payDayLoan, installment;

        continueBtn = view.findViewById(R.id.finishBtn);

        builder.setView(view);

        final android.app.AlertDialog alertDialog = builder.create();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(InvoiceActivity.this, "Your order was placed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InvoiceActivity.this, HomeDashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                alertDialog.dismiss();

            }
        });


        alertDialog.show();


    }



}
