package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

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

        setupHeader();
        setupBottomNavigation();
        setupCarousel();
        setupEmergencyButtons();
        setupWindowInsets();
    }

    private void setupHeader() {
        View header = findViewById(R.id.includedHeader);
        if (header != null) {
            TextView title = header.findViewById(R.id.headerTitleText);
            View searchContainer = header.findViewById(R.id.searchContainerInHeader);

            if (title != null) title.setText("First Aid Hero");
            
            // Hide the search bar container only on the Main Activity
            if (searchContainer != null) {
                searchContainer.setVisibility(View.GONE);
            }
        }
    }

    private void setupBottomNavigation() {
        View includedNav = findViewById(R.id.includedBottomNav);
        if (includedNav != null) {
            BottomNavigationView bottomNav = includedNav.findViewById(R.id.bottomNav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.nav_home);
                bottomNav.setOnItemSelectedListener(item -> {
                    int id = item.getItemId();

                    if (id == R.id.nav_home) {
                        return true;
                    } else if (id == R.id.nav_guide) {
                        startActivity(new Intent(MainActivity.this, GuideActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (id == R.id.nav_kit) {
                        startActivity(new Intent(MainActivity.this, KitActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    return false;
                });
            }
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

    private void setupEmergencyButtons() {
        MaterialButton btnCall911 = findViewById(R.id.btnCall911);
        MaterialButton btnNotifyAdult = findViewById(R.id.btnNotifyAdult);

        if (btnCall911 != null) {
            btnCall911.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:911"));
                startActivity(intent);
            });
        }

        if (btnNotifyAdult != null) {
            btnNotifyAdult.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
            });
        }
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
        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }
    }
}
