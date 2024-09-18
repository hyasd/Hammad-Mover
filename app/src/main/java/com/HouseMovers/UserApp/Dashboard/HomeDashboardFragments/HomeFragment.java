package com.HouseMovers.UserApp.Dashboard.HomeDashboardFragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.HouseMovers.UserApp.Dashboard.SelectHomeCatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.HouseMovers.UserApp.Adapter.ViewPagerAdapter;
import com.HouseMovers.UserApp.R;
import com.skydoves.elasticviews.ElasticButton;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import java.util.Timer;
import java.util.TimerTask;
public class HomeFragment extends Fragment {


    String userId;
    ViewPager viewPager;
    private final int[] image = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.h};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // firebase Reference
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        viewPager = view.findViewById(R.id.myViewPagerHome);
        SpringDotsIndicator springDotsIndicator = view.findViewById(R.id.spring_dots_indicator);
        ElasticButton nextButton = view.findViewById(R.id.nextButton);


        // set ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), image);
        viewPager.setAdapter(viewPagerAdapter);
        springDotsIndicator.setVisibility(View.GONE);
        springDotsIndicator.setViewPager(viewPager);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % image.length);
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 4000, 4000);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), SelectHomeCatActivity.class));
            }
        });


        return view;


    }


}
