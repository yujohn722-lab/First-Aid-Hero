package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private final List<TopicCard> topicCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);

        setupHeader();
        setupBottomNavigation();
        setupTopicCards();
        setupSearch();
        setupWindowInsets();
    }

    private void setupHeader() {
        View header = findViewById(R.id.headerContainer);
        if (header != null) {
            TextView title = header.findViewById(R.id.titleText);
            if (title != null) title.setText("First Aid Guide");
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.guide);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.guide) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.kit) {
                    startActivity(new Intent(GuideActivity.this, KitActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
    }

    private void setupTopicCards() {
        setupTopicCard(R.id.cardAllergies, R.id.rowAllergiesBruises, "Allergies",
                "An allergic reaction happens when the body's immune system reacts to a normally harmless substance.");
        setupTopicCard(R.id.cardBruises, R.id.rowAllergiesBruises, "Bruises",
                "Bruises are marks on the skin caused by blood trapped under the surface after an injury.");
        setupTopicCard(R.id.cardBurns, R.id.rowBurnsChoking, "Burns",
                "Burns are tissue damage that results from heat, overexposure to the sun, or chemical contact.");
        setupTopicCard(R.id.cardChoking, R.id.rowBurnsChoking, "Choking",
                "Choking occurs when a foreign object lodges in the throat, blocking the flow of air.");
        setupTopicCard(R.id.cardCuts, R.id.rowCutsInsectBites, "Cuts",
                "A cut is a break or opening in the skin. It may be deep, smooth, or jagged.");
        setupTopicCard(R.id.cardInsectBites, R.id.rowCutsInsectBites, "Insect Bites",
                "Most insect bites and stings cause a mild skin reaction, but some can be serious.");
        setupTopicCard(R.id.cardNosebleed, R.id.rowNosebleedSprains, "Nosebleed",
                "Nosebleeds are common and usually not serious, often caused by dry air or minor injury.");
        setupTopicCard(R.id.cardSprains, R.id.rowNosebleedSprains, "Sprains",
                "A sprain is a stretching or tearing of ligaments, often occurring in the ankle or wrist.");
    }

    private void setupTopicCard(int cardId, int rowId, String topicName, String description) {
        View card = findViewById(cardId);
        if (card != null) {
            topicCards.add(new TopicCard(topicName, card, findViewById(rowId)));
            card.setOnClickListener(v -> {
                // Using generic keys if GuideDetailActivity constants are missing
                Intent intent = new Intent(GuideActivity.this, GuideDetailActivity.class);
                intent.putExtra("EXTRA_TITLE", topicName);
                intent.putExtra("EXTRA_TEXT", description);
                intent.putExtra("EXTRA_IMAGE", R.drawable.hero_mascot);
                startActivity(intent);
            });
        }
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.mainSearch);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterTopics(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterTopics(newText);
                    return true;
                }
            });
        }
    }

    private void filterTopics(String query) {
        String searchText = query == null ? "" : query.trim().toLowerCase();

        if (searchText.isEmpty()) {
            for (TopicCard topicCard : topicCards) {
                topicCard.card.setVisibility(View.VISIBLE);
                topicCard.row.setVisibility(View.VISIBLE);
            }
            return;
        }

        for (TopicCard topicCard : topicCards) {
            boolean isVisible = topicCard.name.toLowerCase().contains(searchText);
            topicCard.card.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        updateGuideRows();
    }

    private void updateGuideRows() {
        updateGuideRow(R.id.rowAllergiesBruises, R.id.cardAllergies, R.id.cardBruises);
        updateGuideRow(R.id.rowBurnsChoking, R.id.cardBurns, R.id.cardChoking);
        updateGuideRow(R.id.rowCutsInsectBites, R.id.cardCuts, R.id.cardInsectBites);
        updateGuideRow(R.id.rowNosebleedSprains, R.id.cardNosebleed, R.id.cardSprains);
    }

    private void updateGuideRow(int rowId, int firstCardId, int secondCardId) {
        View row = findViewById(rowId);
        View firstCard = findViewById(firstCardId);
        View secondCard = findViewById(secondCardId);
        if (row != null && firstCard != null && secondCard != null) {
            boolean hasVisibleCard = firstCard.getVisibility() == View.VISIBLE
                    || secondCard.getVisibility() == View.VISIBLE;
            row.setVisibility(hasVisibleCard ? View.VISIBLE : View.GONE);
        }
    }

    private static class TopicCard {
        final String name;
        final View card;
        final View row;

        TopicCard(String name, View card, View row) {
            this.name = name;
            this.card = card;
            this.row = row;
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