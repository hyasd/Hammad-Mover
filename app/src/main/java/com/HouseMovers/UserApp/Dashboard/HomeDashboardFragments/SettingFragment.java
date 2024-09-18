package com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.HouseMovers.UserApp.Adapter.ViewPagerAdapter;
import com.HouseMovers.UserApp.Complain.ComplainActivity;
import com.HouseMovers.UserApp.OrdersHistory.HistoryActivity;
import com.HouseMovers.UserApp.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.Timer;


public class SettingFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private View view;

    private CardView historyCard;
    ViewPager viewPager;
    private SpringDotsIndicator springDotsIndicator;
    ViewPagerAdapter viewPagerAdapter;
    Timer timer;
    private int[] image = {R.drawable.a, R.drawable.b,R.drawable.c};
    private LinearLayout sendMessageLinear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);



        sendMessageLinear = view.findViewById(R.id.sendMessageLinear);
        historyCard = view.findViewById(R.id.historyCard);





        sendMessageLinear.setOnClickListener(this);
        historyCard.setOnClickListener(this);




        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == sendMessageLinear)
        {
            startActivity(new Intent(getContext(), ComplainActivity.class));
        }
        if (v == historyCard)
        {
            startActivity(new Intent(getContext(), HistoryActivity.class));
        }

    }
}