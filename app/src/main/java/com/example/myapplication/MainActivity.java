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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.kit) {
                Intent intent = new Intent(MainActivity.this, KitActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setupCarousel() {
        viewPager = findViewById(R.id.heroViewPager);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);

        List<CarouselItem> items = new ArrayList<>();
        items.add(new CarouselItem("#FFB300", R.drawable.hero_mascot, 
                "YOU DON'T NEED POWERS\nTO BE A HERO", 
                "Be the help they need.\nLearn to handle cuts, burns, and more!"));
        items.add(new CarouselItem("#F77F00", R.drawable.hero_aid, 
                "FIRST AID GUIDE", 
                "Short description for first aid guide lorem ipsum"));
        items.add(new CarouselItem("#409B22", R.drawable.hero_kit, 
                "FIRST AID KIT", 
                "Description for first aid kit lorem ipsum"));

        CarouselAdapter adapter = new CarouselAdapter(items);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicators(position % items.size());
            }
        });

        // Set initial position to somewhere in the middle for infinite-like feel
        viewPager.setCurrentItem(items.size() * 100, false);
    }

    private void updateIndicators(int position) {
        // Reset all dots
        setDotSize(dot1, 8);
        setDotSize(dot2, 8);
        setDotSize(dot3, 8);
        dot1.setBackgroundColor(Color.LTGRAY);
        dot2.setBackgroundColor(Color.LTGRAY);
        dot3.setBackgroundColor(Color.LTGRAY);

        // Highlight and expand selected dot
        View selectedDot = (position == 0) ? dot1 : (position == 1) ? dot2 : dot3;
        setDotSize(selectedDot, 30);
        selectedDot.setBackgroundColor(Color.BLACK);
    }

    private void setDotSize(View dot, int widthDp) {
        ViewGroup.LayoutParams params = dot.getLayoutParams();
        params.width = (int) (widthDp * getResources().getDisplayMetrics().density);
        dot.setLayoutParams(params);
    }

    private void setupWindowInsets() {
        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}