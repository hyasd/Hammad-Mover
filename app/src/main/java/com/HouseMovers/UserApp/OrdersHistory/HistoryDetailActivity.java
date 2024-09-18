package com.HouseMovers.UserApp.OrdersHistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HouseMovers.UserApp.ModelClass.OrderModelClass;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.HouseMovers.UserApp.R;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryDetailActivity extends AppCompatActivity {

     Toolbar toolbar;
     String orderId;
     DatabaseReference orderReference;
     FirebaseAuth mAuth;
     OrderModelClass orderModelClass;
     CardView cardFurnitureDetails, cardElectronicsDetails, cardLocation,cardKitchenDetails, cardAccessoriesDetails, cardMiscellaneousDetails, cardVehiclesDetails;
     TextView f_name,f_quantity,f_description,
            e_name,e_quantity,e_description,
            k_name,k_quantity,k_description,
            a_name,a_quantity,a_description,
            m_name,m_quantity,m_description,
            v_name,v_quantity,v_description,locationFrom,locationTo;

    private TextView tvDate, tvTime, tvOrderNo, tvTotalAmount,cost,shiftQategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);



        //toolbar and navigation
        toolbar = findViewById(R.id.toolbar);
        setTitle("History Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryDetailActivity.this, HistoryActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            orderId = bundle.getString("orderId");

        }
        initiationView();
        //initialization views
        tvDate = findViewById(R.id.date);
        tvOrderNo = findViewById(R.id.tvOrderNo);


        //data base reference
        mAuth = FirebaseAuth.getInstance();
        orderReference = FirebaseDatabase.getInstance().getReference().child("Order").child(orderId);


        getOrderDetails();



    }
    private void initiationView() {

        // cardView
        cardFurnitureDetails = findViewById(R.id.cardFurnitureDetails);
        cardElectronicsDetails = findViewById(R.id.cardElectronicsDetails);
        cardKitchenDetails = findViewById(R.id.cardKitchenDetails);
        cardAccessoriesDetails = findViewById(R.id.cardAccessoriesDetails);
        cardMiscellaneousDetails = findViewById(R.id.cardMiscellaneousDetails);
        cardVehiclesDetails = findViewById(R.id.cardVehiclesDetails);
        cardLocation = findViewById(R.id.cardLocation);
        shiftQategory = findViewById(R.id.shiftQategory);
        cost = findViewById(R.id.cost);


        // textView
        f_name = findViewById(R.id.f_name);
        f_quantity = findViewById(R.id.f_quantity);
        f_description = findViewById(R.id.f_description);

        e_name = findViewById(R.id.e_name);
        e_quantity = findViewById(R.id.e_quantity);
        e_description = findViewById(R.id.e_description);

        k_name = findViewById(R.id.k_name);
        k_quantity = findViewById(R.id.k_quantity);
        k_description = findViewById(R.id.k_description);

        a_name = findViewById(R.id.a_name);
        a_quantity = findViewById(R.id.a_quantity);
        a_description = findViewById(R.id.a_description);

        m_name = findViewById(R.id.m_name);
        m_quantity = findViewById(R.id.m_quantity);
        m_description = findViewById(R.id.m_description);

        v_name = findViewById(R.id.v_name);
        v_quantity = findViewById(R.id.v_quantity);
        v_description = findViewById(R.id.v_description);

        locationFrom = findViewById(R.id.locationFrom);
        locationTo = findViewById(R.id.locationTo);



    }

    private void getOrderDetails() {

        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    orderModelClass = dataSnapshot.getValue(OrderModelClass.class);
                    tvOrderNo.setText(orderModelClass.getOrderNumber());
                    tvDate.setText(getDate(orderModelClass.getTimestamp()));
                    cardLocation.setVisibility(View.VISIBLE);
                    locationFrom.setText(orderModelClass.getLocationFrom());
                    locationTo.setText(orderModelClass.getLocationTo());

                    if (!orderModelClass.getCoast().isEmpty())
                    {
                     cost.setText(orderModelClass.getCoast());
                    }
                    if (!orderModelClass.getShiftQategory().isEmpty())
                    {
                        shiftQategory.setText(orderModelClass.getShiftQategory());
                    }

                    if (!orderModelClass.getCategoryNameFurniture().isEmpty()) {
                        cardFurnitureDetails.setVisibility(View.VISIBLE);
                        f_name.setText(orderModelClass.getCategoryNameFurniture());
                        f_quantity.setText(orderModelClass.getCategoryQuantityFurniture());
                        f_description.setText(orderModelClass.getCategoryDescriptionFurniture());
                    }
                    if (!orderModelClass.getCategoryNameElectronics().isEmpty()) {
                        cardElectronicsDetails.setVisibility(View.VISIBLE);
                        e_name.setText(orderModelClass.getCategoryNameElectronics());
                        e_quantity.setText(orderModelClass.getCategoryQuantityElectronics());
                        e_description.setText(orderModelClass.getCategoryDescriptionElectronics());
                    }
                    if (!orderModelClass.getCategoryNameKitchen().isEmpty()) {
                        cardKitchenDetails.setVisibility(View.VISIBLE);
                        k_name.setText(orderModelClass.getCategoryNameKitchen());
                        k_quantity.setText(orderModelClass.getCategoryQuantityKitchen());
                        k_description.setText(orderModelClass.getCategoryDescriptionKitchen());
                    }
                    if (!orderModelClass.getCategoryNameAccessories().isEmpty()) {
                        cardAccessoriesDetails.setVisibility(View.VISIBLE);
                        a_name.setText(orderModelClass.getCategoryNameAccessories());
                        a_quantity.setText(orderModelClass.getCategoryQuantityAccessories());
                        a_description.setText(orderModelClass.getCategoryDescriptionAccessories());
                    }
                    if (!orderModelClass.getCategoryNameMiscellaneous().isEmpty()) {
                        cardMiscellaneousDetails.setVisibility(View.VISIBLE);
                        m_name.setText(orderModelClass.getCategoryNameMiscellaneous());
                        m_quantity.setText(orderModelClass.getCategoryQuantityMiscellaneous());
                        m_description.setText(orderModelClass.getCategoryDescriptionMiscellaneous());
                    }
                    if (!orderModelClass.getCategoryNameVehicles().isEmpty()){
                        cardVehiclesDetails.setVisibility(View.VISIBLE);
                        v_name.setText(orderModelClass.getCategoryNameVehicles());
                        v_quantity.setText(orderModelClass.getCategoryQuantityVehicles());
                        v_description.setText(orderModelClass.getCategoryDescriptionVehicles());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
