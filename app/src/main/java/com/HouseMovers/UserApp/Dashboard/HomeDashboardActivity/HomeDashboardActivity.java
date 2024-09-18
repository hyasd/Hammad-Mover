    package com.HouseMovers.UserApp.Dashboard.HomeDashboardActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.HouseMovers.UserApp.Auth.LoginActivity;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments.SettingFragment;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments.HomeFragment;
import com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments.ProfileFragment;
import com.HouseMovers.UserApp.R;
import com.skydoves.elasticviews.ElasticButton;


import java.util.Objects;

public class HomeDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    final FragmentManager manager = getSupportFragmentManager();
    private Toolbar toolbar;
    private TextView headName;
    private FirebaseAuth mAuth;
    String userId;
    private DatabaseReference mReference, userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        // toolbar code
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //bottom navigation
        bottomNav = findViewById(R.id.bottomNav);
        headName = findViewById(R.id.headName);

        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());


        //first time when activity runs set the first fragment
        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        toolbar.setVisibility(View.GONE);

        // bottom navigation tabs click listener
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.bottom_dashboard:
                        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        toolbar.setVisibility(View.GONE);
                        return true;
                    case R.id.bottom_Notification:
                        manager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                        headName.setText("Profile");
                        toolbar.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.bottom_profile:
                        manager.beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                        headName.setText("Dress Up Customer");
                        toolbar.setVisibility(View.VISIBLE);
                        return true;

                    default:
                        return false;
                }

            }
        });


    }

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeDashboardActivity.this);
        builder.setMessage("Are you sure want to do this ?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        if (userId!=null)
            mRootRef.child("active_users").child(userId).child("online").setValue(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            logoutFunction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void logoutFunction() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeDashboardActivity.this);
        View view = LayoutInflater.from(HomeDashboardActivity.this).inflate(R.layout.logout_layout, null);

        final ElasticButton logoutButton, cancelButton;
        logoutButton = view.findViewById(R.id.logoutButton);
        cancelButton = view.findViewById(R.id.cancelButton);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeDashboardActivity.this, LoginActivity.class));
                finish();


            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeDashboardActivity.this ,HomeDashboardActivity.class);
                startActivity(intent);
            }
        });


        builder.setView(view);
        builder.show();

    }
}
