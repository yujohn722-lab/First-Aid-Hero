package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class KitActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KitAdapter adapter;
    private List<KitItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kit);

        setupWindowInsets();
        setupBottomNavigation();
        setupRecyclerView();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.kit);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.kit) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(KitActivity.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.guide) {
                    startActivity(new Intent(KitActivity.this, GuideActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            });
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            list = new ArrayList<>();
            // Row 1 colors: Green (#4CAF50), Blue (#2196F3)
            // Row 2 colors: Orange (#FF9800), Red (#F44336)
            list.add(new KitItem("Adhesive Bandages", "Great for covering small cuts and scrapes to keep them clean.", "#4CAF50"));
            list.add(new KitItem("Antiseptic Wipes", "Use these to clean the skin around a boo-boo.", "#2196F3"));
            list.add(new KitItem("Cotton Balls", "Soft puffs for applying cleaning liquid or dabbing wounds.", "#FF9800"));
            list.add(new KitItem("Medical Tape", "Sticky tape to hold gauze or bandages in place.", "#F44336"));
            list.add(new KitItem("Gauze Pads", "Soft pads for cleaning or covering larger scrapes.", "#4CAF50"));
            list.add(new KitItem("Safety Scissors", "Special scissors with rounded tips for cutting tape or bandages safely.", "#2196F3"));
            list.add(new KitItem("Thermometer", "Used to check if you have a fever when you feel warm.", "#FF9800"));
            list.add(new KitItem("Tweezers", "Helpful for carefully removing tiny splinters or stingers.", "#F44336"));

            adapter = new KitAdapter(list);
            recyclerView.setAdapter(adapter);
        }
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