package com.HouseMovers.UserApp.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HouseMovers.UserApp.Cart.InvoiceActivity;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity.HomeDashboardActivity;
import com.HouseMovers.UserApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SelectHomeCatActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference orderQueueRef, userRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_home_cat);

        firebaseInitialization();   //firebase initialization


        ElasticImageView back = findViewById(R.id.back);
        ElasticImageView closeF = findViewById(R.id.closeF);
        ElasticImageView closeE = findViewById(R.id.closeE);
        ElasticImageView closeK = findViewById(R.id.closeK);
        ElasticImageView closeA = findViewById(R.id.closeA);
        ElasticImageView closeM = findViewById(R.id.closeM);
        ElasticImageView closeV = findViewById(R.id.closeV);
        final RelativeLayout progress_bar_layout = findViewById(R.id.progress_bar_layout);
        final ElasticButton submitBtn = findViewById(R.id.submitBtn);


        final CardView furniture = findViewById(R.id.furniture);
        final CardView cardFurnitureDetails = findViewById(R.id.cardFurnitureDetails);
        final CardView electronics = findViewById(R.id.electronics);
        final CardView cardElectronicsDetails = findViewById(R.id.cardElectronicsDetails);
        final CardView kitchen = findViewById(R.id.kitchen);
        final CardView cardKitchenDetails = findViewById(R.id.cardKitchenDetails);
        final CardView accessories = findViewById(R.id.accessories);
        final CardView cardAccessoriesDetails = findViewById(R.id.cardAccessoriesDetails);
        final CardView miscellaneous = findViewById(R.id.miscellaneous);
        final CardView cardMiscellaneousDetails = findViewById(R.id.cardMiscellaneousDetails);
        final CardView Vehicles = findViewById(R.id.vehicles);
        final CardView cardVehiclesDetails = findViewById(R.id.cardVehiclesDetails);

        final EditText categoryNameFurniture = findViewById(R.id.categoryNameFurniture);
        final EditText categoryQuantityFurniture = findViewById(R.id.categoryQuantityFurniture);
        final EditText categoryDescriptionFurniture = findViewById(R.id.categoryDescriptionFurniture);

        final EditText categoryNameElectronics = findViewById(R.id.categoryNameElectronics);
        final EditText categoryQuantityElectronics = findViewById(R.id.categoryQuantityElectronics);
        final EditText categoryDescriptionElectronics = findViewById(R.id.categoryDescriptionElectronics);

        final EditText categoryNameKitchen = findViewById(R.id.categoryNameKitchen);
        final EditText categoryQuantityKitchen = findViewById(R.id.categoryQuantityKitchen);
        final EditText categoryDescriptionKitchen = findViewById(R.id.categoryDescriptionKitchen);

        final EditText categoryNameAccessories = findViewById(R.id.categoryNameAccessories);
        final EditText categoryQuantityAccessories = findViewById(R.id.categoryQuantityAccessories);
        final EditText categoryDescriptionAccessories = findViewById(R.id.categoryDescriptionAccessories);

        final EditText categoryNameMiscellaneous = findViewById(R.id.categoryNameMiscellaneous);
        final EditText categoryQuantityMiscellaneous = findViewById(R.id.categoryQuantityMiscellaneous);
        final EditText categoryDescriptionMiscellaneous = findViewById(R.id.categoryDescriptionMiscellaneous);

        final EditText categoryNameVehicles = findViewById(R.id.categoryNameVehicles);
        final EditText categoryQuantityVehicles = findViewById(R.id.categoryQuantityVehicles);
        final EditText categoryDescriptionVehicles = findViewById(R.id.categoryDescriptionVehicles);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        closeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                furniture.setVisibility(View.VISIBLE);
                cardFurnitureDetails.setVisibility(View.GONE);
            }
        });
        closeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electronics.setVisibility(View.VISIBLE);
                cardElectronicsDetails.setVisibility(View.GONE);
            }
        });
        closeK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchen.setVisibility(View.VISIBLE);
                cardKitchenDetails.setVisibility(View.GONE);
            }
        });
        closeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessories.setVisibility(View.VISIBLE);
                cardAccessoriesDetails.setVisibility(View.GONE);
            }
        });
        closeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miscellaneous.setVisibility(View.VISIBLE);
                cardMiscellaneousDetails.setVisibility(View.GONE);
            }
        });
        closeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicles.setVisibility(View.VISIBLE);
                cardVehiclesDetails.setVisibility(View.GONE);
            }
        });


        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                furniture.setVisibility(View.GONE);
                cardFurnitureDetails.setVisibility(View.VISIBLE);
            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electronics.setVisibility(View.GONE);
                cardElectronicsDetails.setVisibility(View.VISIBLE);
            }
        });
        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchen.setVisibility(View.GONE);
                cardKitchenDetails.setVisibility(View.VISIBLE);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessories.setVisibility(View.GONE);
                cardKitchenDetails.setVisibility(View.VISIBLE);
            }
        });
        miscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miscellaneous.setVisibility(View.GONE);
                cardMiscellaneousDetails.setVisibility(View.VISIBLE);
            }
        });
        Vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicles.setVisibility(View.GONE);
                cardVehiclesDetails.setVisibility(View.VISIBLE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if ((categoryNameFurniture.getText().toString()!=null )|| (categoryNameElectronics.getText().toString()!=null)
//                        || (categoryNameKitchen.getText().toString()!=null) || (categoryNameAccessories.getText().toString()!=null)
//                        || (categoryNameMiscellaneous.getText().toString()!=null) || (categoryNameVehicles.getText().toString()!=null)
//                )
//                {
//                    Toast.makeText(SelectHomeCatActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
//                }
//                else {
//
//
//                }


                submitBtn.setVisibility(View.GONE);
                progress_bar_layout.setVisibility(View.VISIBLE);

                String pushId = orderQueueRef.push().getKey();
                Map map = new HashMap();
                map.put("categoryNameFurniture", categoryNameFurniture.getText().toString());
                map.put("categoryQuantityFurniture", categoryQuantityFurniture.getText().toString());
                map.put("categoryDescriptionFurniture", categoryDescriptionFurniture.getText().toString());
                map.put("categoryNameElectronics", categoryNameElectronics.getText().toString());
                map.put("categoryQuantityElectronics", categoryQuantityElectronics.getText().toString());
                map.put("categoryDescriptionElectronics", categoryDescriptionElectronics.getText().toString());
                map.put("categoryNameKitchen", categoryNameKitchen.getText().toString());
                map.put("categoryQuantityKitchen", categoryQuantityKitchen.getText().toString());
                map.put("categoryDescriptionKitchen", categoryDescriptionKitchen.getText().toString());
                map.put("categoryNameAccessories", categoryNameAccessories.getText().toString());
                map.put("categoryQuantityAccessories", categoryQuantityAccessories.getText().toString());
                map.put("categoryDescriptionAccessories", categoryDescriptionAccessories.getText().toString());
                map.put("categoryNameMiscellaneous", categoryNameMiscellaneous.getText().toString());
                map.put("categoryQuantityMiscellaneous", categoryQuantityMiscellaneous.getText().toString());
                map.put("categoryDescriptionMiscellaneous", categoryDescriptionMiscellaneous.getText().toString());
                map.put("categoryNameVehicles", categoryNameVehicles.getText().toString());
                map.put("categoryQuantityVehicles", categoryQuantityVehicles.getText().toString());
                map.put("categoryDescriptionVehicles", categoryDescriptionVehicles.getText().toString());
                map.put("orderId", pushId);
                map.put("orderUserId", userId);
                map.put("timestamp", ServerValue.TIMESTAMP);

                orderQueueRef.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SelectHomeCatActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                            submitBtn.setVisibility(View.VISIBLE);
                            progress_bar_layout.setVisibility(View.GONE);
                            startActivity(new Intent(SelectHomeCatActivity.this,OrderActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            }
        });


    }

    private void firebaseInitialization() {
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        orderQueueRef = FirebaseDatabase.getInstance().getReference().child("OrderQueue");


    }


    private void submitDialog(final String type) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.order_box_dialoag, null);

        Button cancel_button, submitOrder;
        final EditText categoryName, categoryQuantity, categoryDescription;
        TextView categoryType;

        submitOrder = view.findViewById(R.id.submitOrder);
        cancel_button = view.findViewById(R.id.cancel_button);
        categoryName = view.findViewById(R.id.categoryName);
        categoryQuantity = view.findViewById(R.id.categoryQuantity);
        categoryDescription = view.findViewById(R.id.categoryDescription);
        categoryType = view.findViewById(R.id.type);
        categoryType.setText(type);


        builder.setView(view);
        final android.app.AlertDialog alertDialog = builder.create();


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (categoryName.getText().toString().isEmpty()) {
                    Toast.makeText(SelectHomeCatActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else {
                    String pushId = orderQueueRef.push().getKey();
                    Map map = new HashMap();
                    map.put("category_name", categoryName.getText().toString());
                    map.put("category_quantity", categoryQuantity.getText().toString());
                    map.put("category_description", categoryDescription.getText().toString());
                    map.put("orderId", pushId);
                    map.put("orderType", type);
                    map.put("orderUserId", userId);
                    map.put("timestamp", ServerValue.TIMESTAMP);

                    orderQueueRef.child(pushId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SelectHomeCatActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                        }
                    });


                }


            }
        });

        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();

            }
        });


        alertDialog.show();


    }


}