package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuideActivity extends AppCompatActivity {

    private final List<TopicCard> topicCards = new ArrayList<>();
    private final Map<String, List<SubTopic>> subTopicMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);

        initializeSubTopics();
        setupHeader();
        setupBottomNavigation();
        setupTopicCards();
        setupSearch();
        setupEmergencyButtons();
        setupWindowInsets();
    }

    private void initializeSubTopics() {// --- ALLERGIES ---
        List<SubTopic> allergyOptions = new ArrayList<>();
        allergyOptions.add(new SubTopic("Severe Allergy (Anaphylaxis)",
                "A severe, life-threatening allergic reaction characterized by difficulty breathing, facial swelling, or significant dizziness.",
                Arrays.asList("Immediately alert an adult.", "Instruct the adult to contact emergency services.", "Locate the individual's epinephrine auto-injector (EpiPen) if available.", "Keep the individual seated and calm until emergency responders arrive."),
                R.drawable.allerfy));

        allergyOptions.add(new SubTopic("Mild Allergy",
                "A minor allergic reaction resulting in symptoms such as sneezing, watery eyes, or localized hives on the skin.",
                Arrays.asList("Inform an adult about the symptoms.", "Wash the affected skin area with cool water.", "Avoid scratching the affected area to prevent irritation.", "Consult an adult regarding the use of antihistamine medication or topical anti-itch creams."),
                R.drawable.allerfy));
        subTopicMap.put("Allergies", allergyOptions);

        // --- BURNS ---
        List<SubTopic> burnOptions = new ArrayList<>();
        burnOptions.add(new SubTopic("1st Degree Burn (Mild)",
                "A mild burn affecting only the outer layer of skin, characterized by localized redness and pain.",
                Arrays.asList("Submerge the burn under cool, running water for 10 minutes.", "Do not apply ice directly to the skin.", "Gently pat the area dry with a clean cloth."),
                R.drawable.burns));

        burnOptions.add(new SubTopic("2nd Degree Burn (Moderate)",
                "A moderate burn that penetrates deeper skin layers, characterized by severe redness, intense pain, and the formation of blisters.",
                Arrays.asList("Immediately inform an adult.", "Run cool water over the affected area for 10 to 15 minutes.", "Do not puncture any blisters to avoid infection.", "Have an adult cover the burn loosely with a sterile, dry bandage."),
                R.drawable.burns));

        burnOptions.add(new SubTopic("3rd Degree Burn (Severe)",
                "A severe burn causing deep tissue damage. The skin may appear charred, leathery, or white, and pain may be absent due to nerve damage.",
                Arrays.asList("Alert an adult to contact emergency services immediately.", "Do not apply water, ice, or ointments to the burn.", "Ensure the individual remains calm and safely away from the heat source.", "Wait for emergency medical personnel to arrive."),
                R.drawable.burns));
        subTopicMap.put("Burns", burnOptions);

        // --- INSECT BITES ---
        List<SubTopic> bugBiteOptions = new ArrayList<>();
        bugBiteOptions.add(new SubTopic("Bee or Wasp Sting",
                "A puncture wound from a stinging insect, causing immediate localized pain, redness, and swelling.",
                Arrays.asList("Inform an adult. If the stinger remains in the skin, an adult should gently scrape it away.", "Wash the affected area with soap and water.", "Apply a cold compress for 10 minutes to reduce swelling."),
                R.drawable.insect));

        bugBiteOptions.add(new SubTopic("Mosquito Bite",
                "A minor puncture resulting in a small, raised, and itchy welt on the skin.",
                Arrays.asList("Refrain from scratching the bite to prevent potential infection.", "Wash the area with soap and cool water.", "Apply a cold cloth or seek an adult's help for a topical anti-itch cream."),
                R.drawable.insect));

        bugBiteOptions.add(new SubTopic("Spider Bite",
                "A bite causing a localized red welt, occasionally accompanied by a central blister, pain, or itching.",
                Arrays.asList("Notify an adult immediately to assess the bite.", "Wash the area with soap and water.", "Apply a cold compress.", "Seek immediate medical attention if systemic symptoms occur."),
                R.drawable.insect));
        subTopicMap.put("Insect Bites", bugBiteOptions);

        // --- SPRAINS ---
        List<SubTopic> sprainOptions = new ArrayList<>();
        sprainOptions.add(new SubTopic("Ankle Sprain",
                "An injury to the ligaments of the ankle, typically caused by sudden twisting or rolling, resulting in pain and swelling.",
                Arrays.asList("Cease physical activity and sit down immediately.", "Apply the R.I.C.E. method: Rest, Ice, Compress, and Elevate.", "Avoid bearing weight on the affected foot until medically cleared."),
                R.drawable.sprain));

        sprainOptions.add(new SubTopic("Wrist Sprain",
                "A ligament injury in the wrist, often occurring from attempting to break a fall, leading to localized pain and swelling.",
                Arrays.asList("Inform an adult of the injury.", "Minimize movement of the fingers and wrist.", "Apply a cold compress wrapped in a cloth to the wrist.", "Rest the arm in an elevated position or utilize a sling."),
                R.drawable.sprain));
        subTopicMap.put("Sprains", sprainOptions);
    }

    private void setupTopicCards() {
        // Categories with sub-menus
        setupTopicCard(R.id.cardAllergies, R.id.rowAllergiesBruises, "Allergies", "", R.drawable.allerfy, "#4CAF50", null);
        setupTopicCard(R.id.cardBurns, R.id.rowBurnsChoking, "Burns", "", R.drawable.burns, "#FF9800", null);
        setupTopicCard(R.id.cardInsectBites, R.id.rowCutsInsectBites, "Insect Bites", "", R.drawable.insect, "#2196F3", null);
        setupTopicCard(R.id.cardSprains, R.id.rowNosebleedSprains, "Sprains", "", R.drawable.sprain, "#F44336", null);

        // Direct items
        setupTopicCard(R.id.cardBruises, R.id.rowAllergiesBruises, "Bruises",
                "A discoloration of the skin caused by ruptured blood vessels underneath the surface, typically resulting from a blunt impact.",
                R.drawable.bruises, "#2196F3",
                Arrays.asList("Rest the affected body part.", "Apply an ice pack wrapped in a clean cloth to the area for 10 to 15 minutes.", "Monitor for excessive pain or swelling and report any concerns to an adult."));

        setupTopicCard(R.id.cardChoking, R.id.rowBurnsChoking, "Choking",
                "A blockage of the airway by a foreign object, preventing normal breathing, speaking, or coughing.",
                R.drawable.choking, "#F44336",
                Arrays.asList("Ask the individual if they are choking.", "If they are able to cough, encourage them to continue coughing forcefully.", "If they are unable to speak, breathe, or cough, immediately seek an adult's assistance.", "Contact emergency services without delay."));

        setupTopicCard(R.id.cardCuts, R.id.rowCutsInsectBites, "Cuts",
                "A laceration or abrasion of the skin resulting in bleeding, typically caused by contact with sharp objects or falls.",
                R.drawable.cuts, "#4CAF50",
                Arrays.asList("Wash hands thoroughly with soap and water before treating the wound.", "Apply firm, direct pressure to the cut with a clean cloth to stop the bleeding.", "Once bleeding ceases, gently cleanse the wound with warm water and soap.", "Dry the area and apply a sterile adhesive bandage."));

        setupTopicCard(R.id.cardNosebleed, R.id.rowNosebleedSprains, "Nosebleed",
                "Hemorrhage from the nasal cavity, often caused by dry air, mild trauma, or nasal irritation.",
                R.drawable.nosebleed, "#FF9800",
                Arrays.asList("Sit upright and lean slightly forward to prevent swallowing blood.", "Firmly pinch the soft portion of the nose just below the nasal bridge.", "Breathe consistently through the mouth.", "Maintain pressure for 5 to 10 minutes. If bleeding persists, notify an adult."));
    }

    private void setupTopicCard(int cardId, int rowId, String topicName, String description, int imageRes, String colorHex, List<String> steps) {
        View cardView = findViewById(cardId);
        if (cardView != null) {
            topicCards.add(new TopicCard(topicName, cardView, findViewById(rowId)));
            cardView.setOnClickListener(v -> {
                if (subTopicMap.containsKey(topicName)) {
                    showSubSelectionDialog(topicName, colorHex);
                } else {
                    navigateToDetail(topicName, description, steps, imageRes, colorHex);
                }
            });
        }
    }

    private void showSubSelectionDialog(String topicName, String colorHex) {
        List<SubTopic> options = subTopicMap.get(topicName);
        if (options == null) return;

        String[] itemNames = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            itemNames[i] = options.get(i).name;
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle("Select " + topicName + " Type")
                .setItems(itemNames, (dialog, which) -> {
                    SubTopic selected = options.get(which);
                    navigateToDetail(selected.name, selected.description, selected.steps, selected.imageRes, colorHex);
                })
                .show();
    }

    private void navigateToDetail(String title, String description, List<String> steps, int imageRes, String colorHex) {
        Intent intent = new Intent(GuideActivity.this, GuideDetailActivity.class);
        intent.putExtra("EXTRA_TITLE", title);
        intent.putExtra("EXTRA_TEXT", description);
        intent.putExtra("EXTRA_IMAGE", imageRes);
        intent.putExtra("EXTRA_COLOR", colorHex);
        if (steps != null) {
            intent.putStringArrayListExtra("EXTRA_STEPS", new ArrayList<>(steps));
        }
        startActivity(intent);
    }

    private void setupHeader() {
        View header = findViewById(R.id.includedHeader);
        if (header != null) {
            TextView title = header.findViewById(R.id.headerTitleText);
            if (title != null) title.setText("First Aid Guide");
        }
    }

    private void setupBottomNavigation() {
        View includedNav = findViewById(R.id.includedBottomNav);
        if (includedNav != null) {
            BottomNavigationView bottomNav = includedNav.findViewById(R.id.bottomNav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.nav_guide);
                bottomNav.setOnItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if (id == R.id.nav_guide) return true;
                    else if (id == R.id.nav_home) {
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    } else if (id == R.id.nav_kit) {
                        startActivity(new Intent(GuideActivity.this, KitActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    }
                    return false;
                });
            }
        }
    }

    private void setupEmergencyButtons() {
        MaterialButton btnCall911 = findViewById(R.id.btnCall911);
        MaterialButton btnNotifyAdult = findViewById(R.id.btnNotifyAdult);
        if (btnCall911 != null) btnCall911.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:911"));
            startActivity(intent);
        });
        if (btnNotifyAdult != null) btnNotifyAdult.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);
        });
    }

    private void setupSearch() {
        View header = findViewById(R.id.includedHeader);
        if (header != null) {
            SearchView searchView = header.findViewById(R.id.headerSearch);
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
    }

    private void filterTopics(String query) {
        String searchText = query == null ? "" : query.trim().toLowerCase();
        if (searchText.isEmpty()) {
            for (TopicCard topicCard : topicCards) {
                topicCard.card.setVisibility(View.VISIBLE);
                topicCard.row.setVisibility(View.VISIBLE);
            }
            updateGuideRows();
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
            boolean hasVisibleCard = firstCard.getVisibility() == View.VISIBLE || secondCard.getVisibility() == View.VISIBLE;
            row.setVisibility(hasVisibleCard ? View.VISIBLE : View.GONE);
        }
    }

    private void setupWindowInsets() {
        View rootView = findViewById(R.id.main);
        if (rootView != null) ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
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

    private static class SubTopic {
        final String name;
        final String description;
        final List<String> steps;
        final int imageRes;

        SubTopic(String name, String description, List<String> steps, int imageRes) {
            this.name = name;
            this.description = description;
            this.steps = steps;
            this.imageRes = imageRes;
        }
    }
}