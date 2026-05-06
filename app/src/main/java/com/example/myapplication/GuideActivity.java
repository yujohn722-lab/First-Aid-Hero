package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        setupWindowInsets();
        setupBottomNavigation();
        setupTopicCards();
        setupSearch();
    }

    private void setupTopicCards() {
        setupTopicCard(R.id.cardAllergies, R.id.rowAllergiesBruises, "Allergies");
        setupTopicCard(R.id.cardBruises, R.id.rowAllergiesBruises, "Bruises");
        setupTopicCard(R.id.cardBurns, R.id.rowBurnsChoking, "Burns");
        setupTopicCard(R.id.cardChoking, R.id.rowBurnsChoking, "Choking");
        setupTopicCard(R.id.cardCuts, R.id.rowCutsInsectBites, "Cuts");
        setupTopicCard(R.id.cardInsectBites, R.id.rowCutsInsectBites, "Insect Bites");
        setupTopicCard(R.id.cardNosebleed, R.id.rowNosebleedSprains, "Nosebleed");
        setupTopicCard(R.id.cardSprains, R.id.rowNosebleedSprains, "Sprains");
    }

    private void setupTopicCard(int cardId, int rowId, String topicName) {
        View card = findViewById(cardId);
        if (card != null) {
            topicCards.add(new TopicCard(topicName, card, findViewById(rowId)));
            card.setOnClickListener(v -> {
                Intent intent = new Intent(GuideActivity.this, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.EXTRA_TOPIC, topicName);
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

        List<TopicCard> visibleTopics = new ArrayList<>();
        for (TopicCard topicCard : topicCards) {
            if (topicCard.name.toLowerCase().contains(searchText)) {
                visibleTopics.add(topicCard);
            }
        }

        if (visibleTopics.isEmpty()) {
            visibleTopics.add(findClosestTopic(searchText));
        }

        for (TopicCard topicCard : topicCards) {
            boolean isVisible = visibleTopics.contains(topicCard);
            topicCard.card.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        updateGuideRows();
    }

    private TopicCard findClosestTopic(String searchText) {
        TopicCard closestTopic = topicCards.get(0);
        int bestScore = getDistance(searchText, closestTopic.name.toLowerCase());

        for (TopicCard topicCard : topicCards) {
            int score = getDistance(searchText, topicCard.name.toLowerCase());
            if (score < bestScore) {
                closestTopic = topicCard;
                bestScore = score;
            }
        }

        return closestTopic;
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

    private void setupWindowInsets() {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                // Fix: Remove bottom padding (set to 0) to remove the gap under the navbar
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }
    }
}
