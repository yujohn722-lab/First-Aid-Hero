package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class KitActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KitAdapter adapter;
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
        setupEmergencyButtons();
    }

    private void setupBottomNavigation() {
        View includedNav = findViewById(R.id.includedBottomNav);
        if (includedNav != null) {
            BottomNavigationView bottomNav = includedNav.findViewById(R.id.bottomNav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.nav_kit);

                bottomNav.setOnItemSelectedListener(item -> {
                    int id = item.getItemId();

                    if (id == R.id.nav_kit) {
                        return true;
                    } else if (id == R.id.nav_home) {
                        startActivity(new Intent(KitActivity.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (id == R.id.nav_guide) {
                        startActivity(new Intent(KitActivity.this, GuideActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    return false;
                });
            }
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            allItems = new ArrayList<>();
            allItems.add(new KitItem("Adhesive Bandages", "Great for covering small cuts and scrapes to keep them clean.", "#4CAF50"));
            allItems.add(new KitItem("Antiseptic Wipes", "Use these to clean the skin around a boo-boo.", "#2196F3"));
            allItems.add(new KitItem("Cotton Balls", "Soft puffs for applying cleaning liquid or dabbing wounds.", "#FF9800"));
            allItems.add(new KitItem("Medical Tape", "Sticky tape to hold gauze or bandages in place.", "#F44336"));
            allItems.add(new KitItem("Gauze Pads", "Soft pads for cleaning or covering larger scrapes.", "#4CAF50"));
            allItems.add(new KitItem("Safety Scissors", "Special scissors with rounded tips for cutting tape or bandages safely.", "#2196F3"));
            allItems.add(new KitItem("Thermometer", "Used to check if you have a fever when you feel warm.", "#FF9800"));
            allItems.add(new KitItem("Tweezers", "Helpful for carefully removing tiny splinters or stingers.", "#F44336"));

            adapter = new KitAdapter(new ArrayList<>(allItems));
            recyclerView.setAdapter(adapter);
        }
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

    private void setupSearch() {
        View header = findViewById(R.id.includedHeader);
        if (header != null) {
            TextView titleText = header.findViewById(R.id.headerTitleText);
            if (titleText != null) {
                titleText.setText("First Aid Kit");
            }

            SearchView searchView = header.findViewById(R.id.headerSearch);
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

        // Strict filtering: if nothing contains the search text, show empty list.
        adapter.updateItems(filteredItems);
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
