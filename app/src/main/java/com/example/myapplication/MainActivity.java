package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private View dot1, dot2, dot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
        setupCarousel();
        setupWindowInsets();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.home);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.home) {
                    return true;
                } else if (id == R.id.guide) {
                    startActivity(new Intent(MainActivity.this, GuideActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.kit) {
                    startActivity(new Intent(MainActivity.this, KitActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
    }

    private void setupCarousel() {
        viewPager = findViewById(R.id.heroViewPager);
        ViewGroup indicatorContainer = findViewById(R.id.indicatorContainer);
        if (indicatorContainer != null) {
            dot1 = indicatorContainer.getChildAt(0);
            dot2 = indicatorContainer.getChildAt(1);
            dot3 = indicatorContainer.getChildAt(2);
        }

        List<CarouselItem> items = new ArrayList<>();
        items.add(new CarouselItem("#FFB300", R.drawable.hero_mascot, 
                "YOU DON'T NEED POWERS\nTO BE A HERO", 
                "Be the help they need.\nLearn to handle cuts, burns, and more!"));
        items.add(new CarouselItem("#F77F00", R.drawable.hero_aid, 
                "FIRST AID GUIDE", 
                "Learn step-by-step instructions for emergencies."));
        items.add(new CarouselItem("#409B22", R.drawable.hero_kit,
                "FIRST AID KIT", 
                "Essential items you need in your medical kit."));

        CarouselAdapter adapter = new CarouselAdapter(items);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicators(position % items.size());
            }
        });

        viewPager.setCurrentItem(items.size() * 100, false);
    }

    private void updateIndicators(int position) {
        setDotSize(dot1, 8);
        setDotSize(dot2, 8);
        setDotSize(dot3, 8);
        if (dot1 != null) dot1.setBackgroundColor(Color.parseColor("#CCCCCC"));
        if (dot2 != null) dot2.setBackgroundColor(Color.parseColor("#CCCCCC"));
        if (dot3 != null) dot3.setBackgroundColor(Color.parseColor("#CCCCCC"));

        View selectedDot = (position == 0) ? dot1 : (position == 1) ? dot2 : dot3;
        if (selectedDot != null) {
            setDotSize(selectedDot, 32);
            selectedDot.setBackgroundColor(Color.BLACK);
        }
    }

    private void setDotSize(View dot, int widthDp) {
        if (dot == null) return;
        ViewGroup.LayoutParams params = dot.getLayoutParams();
        params.width = (int) (widthDp * getResources().getDisplayMetrics().density);
        dot.setLayoutParams(params);
    }

    private void setupWindowInsets() {
        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Setting bottom padding to 0 to fix the nav bar gap
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
    }
}
