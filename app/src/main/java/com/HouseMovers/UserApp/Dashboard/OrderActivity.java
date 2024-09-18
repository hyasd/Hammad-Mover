package com.HouseMovers.UserApp.Dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity.HomeDashboardActivity;
import com.HouseMovers.UserApp.ModelClass.OrderModelClass;
import com.HouseMovers.UserApp.R;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {


    CardView cardFurnitureDetails, cardElectronicsDetails, cardKitchenDetails, cardAccessoriesDetails, cardMiscellaneousDetails, cardVehiclesDetails;
    FirebaseAuth mAuth;
    DatabaseReference orderRef, userRef,orderQueueRef;
    String userId,shiftCategory;
    int coast = 0;
    Button submitBtn;
    EditText locationFrom,locationTo;
    ImageView back;
    Spinner exerciseSpinner;
    RelativeLayout progress_bar_layout,top_progress_bar_layout;
    TextView f_name,f_quantity,f_description,
            e_name,e_quantity,e_description,
            k_name,k_quantity,k_description,
            a_name,a_quantity,a_description,
            m_name,m_quantity,m_description,
            v_name,v_quantity,v_description,cost;
    private OrderModelClass orderModelClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseInitialization();
        initiationView();
        getOrderData();
        confirmOrder();
        clickListeners();
        setSpinnerData();


    }

    private void setSpinnerData() {

        String[] ITEMS = {"Select Shift Category", "Only Shift", "Shift and Setting"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);
        exerciseSpinner.setDropDownVerticalOffset(50);
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                shiftCategory = exerciseSpinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void clickListeners() {
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    });
    }

    private void confirmOrder() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shiftCategory.equals("Select Exercise")){

                    Toast.makeText(OrderActivity.this, "Please Select Shift Category", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Random r = new Random();
                    final String orderNumber = String.format("%04d", r.nextInt(1001));


                    progress_bar_layout.setVisibility(View.VISIBLE);
                    String pushId = orderRef.push().getKey();
                    Map map = new HashMap();
                    map.put("categoryNameFurniture", orderModelClass.getCategoryNameFurniture());
                    map.put("categoryQuantityFurniture",orderModelClass.getCategoryQuantityFurniture());
                    map.put("categoryDescriptionFurniture", orderModelClass.getCategoryDescriptionFurniture());
                    map.put("categoryNameElectronics", orderModelClass.getCategoryNameElectronics());
                    map.put("categoryQuantityElectronics", orderModelClass.getCategoryQuantityElectronics());
                    map.put("categoryDescriptionElectronics", orderModelClass.getCategoryDescriptionElectronics());
                    map.put("categoryNameKitchen", orderModelClass.getCategoryNameKitchen());
                    map.put("categoryQuantityKitchen", orderModelClass.getCategoryQuantityKitchen());
                    map.put("categoryDescriptionKitchen", orderModelClass.getCategoryDescriptionKitchen());
                    map.put("categoryNameAccessories", orderModelClass.getCategoryNameAccessories());
                    map.put("categoryQuantityAccessories", orderModelClass.getCategoryQuantityAccessories());
                    map.put("categoryDescriptionAccessories", orderModelClass.getCategoryDescriptionAccessories());
                    map.put("categoryNameMiscellaneous", orderModelClass.getCategoryNameMiscellaneous());
                    map.put("categoryQuantityMiscellaneous", orderModelClass.getCategoryQuantityMiscellaneous());
                    map.put("categoryDescriptionMiscellaneous", orderModelClass.getCategoryDescriptionMiscellaneous());
                    map.put("categoryNameVehicles", orderModelClass.getCategoryNameVehicles());
                    map.put("categoryQuantityVehicles", orderModelClass.getCategoryQuantityVehicles());
                    map.put("categoryDescriptionVehicles", orderModelClass.getCategoryDescriptionVehicles());
                    map.put("orderId", pushId);
                    map.put("orderUserId", userId);
                    map.put("orderNumber", orderNumber);
                    map.put("timestamp", ServerValue.TIMESTAMP);
                    map.put("locationFrom", locationFrom.getText().toString());
                    map.put("locationTo", locationTo.getText().toString());
                    map.put("shiftQategory", shiftCategory);
                    map.put("coast", String.valueOf(coast));

                    orderRef.child(pushId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progress_bar_layout.setVisibility(View.GONE);
                                submittDialog();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }





            }
        });



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

                orderQueueRef.child(userId).removeValue();
                Toast.makeText(OrderActivity.this, "Your order was placed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrderActivity.this, HomeDashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                alertDialog.dismiss();

            }
        });


        alertDialog.show();


    }


    private void getOrderData() {
        orderQueueRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    orderModelClass = dataSnapshot.getValue(OrderModelClass.class);

                    if (!orderModelClass.getCategoryNameFurniture().isEmpty()) {
                        coast = coast+500;
                        cardFurnitureDetails.setVisibility(View.VISIBLE);
                        f_name.setText(orderModelClass.getCategoryNameFurniture());
                        f_quantity.setText(orderModelClass.getCategoryQuantityFurniture());
                        f_description.setText(orderModelClass.getCategoryDescriptionFurniture());
                    }
                    if (!orderModelClass.getCategoryNameElectronics().isEmpty()) {
                        coast = coast+500;
                        cardElectronicsDetails.setVisibility(View.VISIBLE);
                        e_name.setText(orderModelClass.getCategoryNameElectronics());
                        e_quantity.setText(orderModelClass.getCategoryQuantityElectronics());
                        e_description.setText(orderModelClass.getCategoryDescriptionElectronics());
                    }
                    if (!orderModelClass.getCategoryNameKitchen().isEmpty()) {
                        coast = coast+500;
                        cardKitchenDetails.setVisibility(View.VISIBLE);
                        k_name.setText(orderModelClass.getCategoryNameKitchen());
                        k_quantity.setText(orderModelClass.getCategoryQuantityKitchen());
                        k_description.setText(orderModelClass.getCategoryDescriptionKitchen());
                    }
                    if (!orderModelClass.getCategoryNameAccessories().isEmpty()) {
                        coast = coast+500;
                        cardAccessoriesDetails.setVisibility(View.VISIBLE);
                        a_name.setText(orderModelClass.getCategoryNameAccessories());
                        a_quantity.setText(orderModelClass.getCategoryQuantityAccessories());
                        a_description.setText(orderModelClass.getCategoryDescriptionAccessories());
                    }
                    if (!orderModelClass.getCategoryNameMiscellaneous().isEmpty()) {
                        coast = coast+500;
                        cardMiscellaneousDetails.setVisibility(View.VISIBLE);
                        m_name.setText(orderModelClass.getCategoryNameMiscellaneous());
                        m_quantity.setText(orderModelClass.getCategoryQuantityMiscellaneous());
                        m_description.setText(orderModelClass.getCategoryDescriptionMiscellaneous());
                    }
                    if (!orderModelClass.getCategoryNameVehicles().isEmpty()){
                        coast = coast+500;
                        cardVehiclesDetails.setVisibility(View.VISIBLE);
                        v_name.setText(orderModelClass.getCategoryNameVehicles());
                        v_quantity.setText(orderModelClass.getCategoryQuantityVehicles());
                        v_description.setText(orderModelClass.getCategoryDescriptionVehicles());
                    }

                    cost.setText(String.valueOf(coast));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initiationView() {

        // cardView
        cardFurnitureDetails = findViewById(R.id.cardFurnitureDetails);
        cardElectronicsDetails = findViewById(R.id.cardElectronicsDetails);
        cardKitchenDetails = findViewById(R.id.cardKitchenDetails);
        cardAccessoriesDetails = findViewById(R.id.cardAccessoriesDetails);
        cardMiscellaneousDetails = findViewById(R.id.cardMiscellaneousDetails);
        cardVehiclesDetails = findViewById(R.id.cardVehiclesDetails);
        back = findViewById(R.id.back);
        exerciseSpinner = findViewById(R.id.exerciseSpinner);
        cost = findViewById(R.id.coast);


        // button
        submitBtn = findViewById(R.id.submitBtn);
        progress_bar_layout = findViewById(R.id.progress_bar_layout);
        top_progress_bar_layout = findViewById(R.id.top_progress_bar_layout);

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

    private void firebaseInitialization() {
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order");
        orderQueueRef = FirebaseDatabase.getInstance().getReference().child("OrderQueue");



    }

}