package com.HouseMovers.UserApp.OrdersHistory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HouseMovers.UserApp.ModelClass.OrderModelClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity.HomeDashboardActivity;
import com.HouseMovers.UserApp.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.ishankhanna.UberProgressView;

public class HistoryActivity extends AppCompatActivity {


     FirebaseAuth mAuth;
     String currentUserId;
     RecyclerView recyclerView;
     DatabaseReference databaseReference;
     RelativeLayout top_progress_bar_layout;
     FirebaseRecyclerAdapter<OrderModelClass, HistoryViewHolder> adapter;
     ArrayList<OrderModelClass> allOrderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        // toolbar code
        Toolbar toolbar = findViewById(R.id.toolbarHistory);

        setSupportActionBar(toolbar);
        setTitle("Order History");
        toolbar.setNavigationIcon(R.drawable.ic_back);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
          String  from = bundle.getString("from");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(HistoryActivity.this, HomeDashboardActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        // firebase Reference
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");


//        //initialization of views
//        progressBar = findViewById(R.id.progressBar);

        top_progress_bar_layout = findViewById(R.id. top_progress_bar_layout);

        //show history recycler
        recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getOrders();
    }

    private void getOrders() {
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<OrderModelClass>()
                .setQuery(databaseReference.orderByChild("orderUserId").equalTo(currentUserId), OrderModelClass.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<OrderModelClass, HistoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final HistoryViewHolder historyViewHolder, int i, @NonNull final OrderModelClass ordersHistory) {

                final String key = getRef(i).getKey();
                if (key != null) {


                    databaseReference.child(key)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        final OrderModelClass ordersHistory = dataSnapshot.getValue(OrderModelClass.class);
                                        allOrderList.add(ordersHistory);

                                        System.out.println("timedate::" + ordersHistory.getTimestamp());
                                        historyViewHolder.tvDate.setText(getDate(ordersHistory.getTimestamp()));
                                        historyViewHolder.tvTime.setText(getTime(ordersHistory.getTimestamp()));
                                        historyViewHolder.tvOrderNo.setText(ordersHistory.getOrderNumber());

                                        historyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                                                intent.putExtra("orderId", ordersHistory.getOrderId());
                                                startActivity(intent);
                                            }
                                        });

                                        historyViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View view) {

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                                                builder.setMessage("Are you want to delete your order?");
                                                builder.setCancelable(true);
                                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.cancel();
                                                    }
                                                });

                                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        databaseReference.child(ordersHistory.getOrderId()).removeValue();
                                                        Toast.makeText(HistoryActivity.this, "Order successfully deleted", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                                AlertDialog alertDialog = builder.create();
                                                alertDialog.show();

                                                return false;
                                            }
                                        });




                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                }

            }

            @NonNull
            @Override
            public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_history, parent, false);
                HistoryViewHolder viewHolder = new HistoryViewHolder(view);


                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvTime, tvOrderNo,date;
        private ImageView imgViewProduct;


        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);

        }


    }


    public static String getTime(long timestamp) {
        try {
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static String getDate(long timestamp) {
        try {
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

}
