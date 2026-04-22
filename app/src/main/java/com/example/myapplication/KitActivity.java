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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        // Set the Kit item as selected by default since we are in the Kit screen
        bottomNavigationView.setSelectedItemId(R.id.kit);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                finish(); // Go back to MainActivity
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        list.add(new KitItem("Bandages", "Used to cover wounds and stop bleeding."));
        list.add(new KitItem("Medical Gloves", "Protects hands from infection."));
        list.add(new KitItem("Rubbing Alcohol", "Used for disinfecting."));
        list.add(new KitItem("Pills & Medication", "Basic medicines for common issues."));

        adapter = new KitAdapter(list);
        recyclerView.setAdapter(adapter);

        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
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
}