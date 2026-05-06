package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

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
    private List<KitItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kit);

        setupWindowInsets();
        setupBottomNavigation();
        setupRecyclerView();
        setupSearch();
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

            allItems = new ArrayList<>();
            // Row 1 colors: Green (#4CAF50), Blue (#2196F3)
            // Row 2 colors: Orange (#FF9800), Red (#F44336)
            // All items currently use the placeholder icon.
            // You can replace R.drawable.placeholder with specific icons later.
            // int icon = R.drawable.placeholder;
            allItems.add(new KitItem("Adhesive Bandages", "Great for covering small cuts and scrapes to keep them clean.", "#4CAF50", R.drawable.kit_adhesive_bandages));
            allItems.add(new KitItem("Antiseptic Wipes", "Use these to clean the skin around a boo-boo.", "#2196F3", R.drawable.kit_antiseptic_wipes));
            allItems.add(new KitItem("Cotton Balls", "Soft puffs for applying cleaning liquid or dabbing wounds.", "#FF9800", R.drawable.kit_cotton_balls));
            allItems.add(new KitItem("Medical Tape", "Sticky tape to hold gauze or bandages in place.", "#F44336", R.drawable.kit_medical_tape));
            allItems.add(new KitItem("Gauze Pads", "Soft pads for cleaning or covering larger scrapes.", "#4CAF50", R.drawable.kit_gauze_pads));
            allItems.add(new KitItem("Safety Scissors", "Special scissors with rounded tips for cutting tape or bandages safely.", "#2196F3", R.drawable.kit_safety_scissors));
            allItems.add(new KitItem("Thermometer", "Used to check if you have a fever when you feel warm.", "#FF9800", R.drawable.kit_thermometer));
            allItems.add(new KitItem("Tweezers", "Helpful for carefully removing tiny splinters or stingers.", "#F44336", R.drawable.kit_tweezers));

            list = new ArrayList<>(allItems);
            adapter = new KitAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.mainSearch);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterKitItems(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterKitItems(newText);
                    return true;
                }
            });
        }
    }

    private void filterKitItems(String query) {
        if (adapter == null || allItems == null) {
            return;
        }

        String searchText = query == null ? "" : query.trim().toLowerCase();
        if (searchText.isEmpty()) {
            adapter.updateItems(new ArrayList<>(allItems));
            return;
        }

        List<KitItem> filteredItems = new ArrayList<>();
        for (KitItem item : allItems) {
            String searchableText = (item.getTitle() + " " + item.getDescription()).toLowerCase();
            if (searchableText.contains(searchText)) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            filteredItems.add(findClosestItem(searchText));
        }

        adapter.updateItems(filteredItems);
    }

    private KitItem findClosestItem(String searchText) {
        KitItem closestItem = allItems.get(0);
        int bestScore = getDistance(searchText, closestItem.getTitle().toLowerCase());

        for (KitItem item : allItems) {
            int score = getDistance(searchText, item.getTitle().toLowerCase());
            if (score < bestScore) {
                closestItem = item;
                bestScore = score;
            }
        }

        return closestItem;
    }

    private int getDistance(String first, String second) {
        int[][] distance = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i <= first.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= second.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                distance[i][j] = Math.min(
                        Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                        distance[i - 1][j - 1] + cost);
            }
        }

        return distance[first.length()][second.length()];
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
