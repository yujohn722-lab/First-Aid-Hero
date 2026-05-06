package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class GuideDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TOPIC = "extra_topic";

    private String topicName;
    // Signature colors: Red, Green, Blue, Orange
    private final String[] colors = {"#F44336", "#4CAF50", "#2196F3", "#FF9800"};

    private static class Step {
        String title;
        String detail;
        Step(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        topicName = getIntent().getStringExtra(EXTRA_TOPIC);
        if (topicName == null) topicName = "First Aid";

        setupViews();
        populateSteps();
    }

    private void setupViews() {
        TextView tvTitle = findViewById(R.id.detailTitle);
        TextView tvTopicName = findViewById(R.id.detailTopicName);
        TextView tvDescription = findViewById(R.id.detailDescription);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageView ivIllustration = findViewById(R.id.detailIllustration);

        tvTitle.setText(topicName);
        tvTopicName.setText(topicName);
        
        // Task: Initialize btnBack to call finish()
        btnBack.setOnClickListener(v -> finish());

        tvDescription.setText(getTopicDescription(topicName));
        
        // Set illustration if specific ones are available, otherwise use mascot
        ivIllustration.setImageResource(R.drawable.hero_mascot);
    }

    private String getTopicDescription(String topic) {
        switch (topic) {
            case "Allergies": return "An allergic reaction happens when the body's immune system reacts to a normally harmless substance.";
            case "Bruises": return "Bruises are marks on the skin caused by blood trapped under the surface after an injury.";
            case "Burns": return "Burns are tissue damage that results from heat, overexposure to the sun or other radiation, or chemical or electrical contact.";
            case "Choking": return "Choking occurs when a foreign object lodges in the throat or windpipe, blocking the flow of air.";
            case "Cuts": return "A cut is a break or opening in the skin. It may be deep, smooth, or jagged.";
            case "Insect Bites": return "Most insect bites and stings cause a mild skin reaction. Some people have life-threatening allergic reactions.";
            case "Nosebleed": return "Nosebleeds are common and usually not serious. They often happen due to dry air or nose picking.";
            case "Sprains": return "A sprain is a stretching or tearing of ligaments — the tough bands of fibrous tissue that connect two bones together in your joints.";
            default: return "Follow these steps to provide immediate care and assistance.";
        }
    }

    private void populateSteps() {
        LinearLayout stepsContainer = findViewById(R.id.stepsContainer);
        List<Step> steps = getTopicSteps(topicName);

        // Task: Use a loop to inflate item_guide_step.xml into the stepsContainer
        for (int i = 0; i < steps.size(); i++) {
            View stepView = LayoutInflater.from(this).inflate(R.layout.item_guide_step, stepsContainer, false);
            
            MaterialCardView card = stepView.findViewById(R.id.stepCard);
            TextView tvNumber = stepView.findViewById(R.id.stepNumber);
            TextView tvTitle = stepView.findViewById(R.id.stepTitle);
            TextView tvDetail = stepView.findViewById(R.id.stepDetail);

            Step step = steps.get(i);
            tvNumber.setText(String.valueOf(i + 1));
            tvTitle.setText(step.title);
            tvDetail.setText(step.detail);
            
            // Task: Cycle through signature colors (Red, Green, Blue, Orange)
            String color = colors[i % colors.length];
            card.setCardBackgroundColor(Color.parseColor(color));

            stepsContainer.addView(stepView);
        }
    }

    private List<Step> getTopicSteps(String topic) {
        List<Step> steps = new ArrayList<>();
        switch (topic) {
            case "Allergies":
                steps.add(new Step("Safety First", "Remove the person from the source of the allergen."));
                steps.add(new Step("Check Medication", "Ask if they have an EpiPen and help them use it if needed."));
                steps.add(new Step("Monitor", "Monitor their breathing and keep them calm."));
                steps.add(new Step("Medical Help", "Seek medical help if symptoms are severe or worsening."));
                break;
            case "Bruises":
                steps.add(new Step("Apply Cold", "Apply an ice pack wrapped in a cloth for 10-20 minutes."));
                steps.add(new Step("Elevate", "Elevate the bruised area if possible to reduce swelling."));
                steps.add(new Step("Avoid Aspirin", "Avoid taking aspirin as it can increase bleeding."));
                steps.add(new Step("Rest", "Rest the injured part."));
                break;
            case "Burns":
                steps.add(new Step("Cool Down", "Cool the burn with cool (not cold) running water for 10-20 minutes."));
                steps.add(new Step("Remove Jewelry", "Remove jewelry or tight clothing before the area swells."));
                steps.add(new Step("Cover Burn", "Cover the burn loosely with a sterile bandage or clean cloth."));
                steps.add(new Step("Do Not Pop", "Do not pop blisters or apply ointments/butter."));
                break;
            case "Choking":
                steps.add(new Step("Verify", "Ask 'Are you choking?' and encourage them to cough."));
                steps.add(new Step("Back Blows", "Give 5 back blows between the shoulder blades."));
                steps.add(new Step("Abdominal Thrusts", "Give 5 abdominal thrusts (Heimlich maneuver)."));
                steps.add(new Step("Repeat", "Repeat until the object is forced out or help arrives."));
                break;
            case "Cuts":
                steps.add(new Step("Stop Bleeding", "Apply direct pressure with a clean cloth to stop bleeding."));
                steps.add(new Step("Clean Wound", "Clean the wound with clear water once bleeding stops."));
                steps.add(new Step("Protect", "Apply an antibiotic ointment and cover with a bandage."));
                steps.add(new Step("Check Severity", "Seek help if the cut is deep, gaping, or won't stop bleeding."));
                break;
            case "Insect Bites":
                steps.add(new Step("Remove Stinger", "Remove the stinger if present by scraping it off."));
                steps.add(new Step("Wash Area", "Wash the area with soap and water."));
                steps.add(new Step("Reduce Swelling", "Apply a cold pack to reduce swelling and pain."));
                steps.add(new Step("Soothe", "Use hydrocortisone cream or calamine lotion for itching."));
                break;
            case "Nosebleed":
                steps.add(new Step("Posture", "Sit up straight and lean slightly forward."));
                steps.add(new Step("Pinch", "Pinch the soft part of the nose for 10-15 minutes."));
                steps.add(new Step("Breathe", "Breathe through the mouth while pinching."));
                steps.add(new Step("Recovery", "Avoid blowing your nose for several hours after."));
                break;
            case "Sprains":
                steps.add(new Step("Rest", "Rest the injured limb."));
                steps.add(new Step("Ice", "Ice the area to reduce swelling."));
                steps.add(new Step("Compression", "Compress the area with an elastic bandage."));
                steps.add(new Step("Elevation", "Elevate the injured limb above the level of the heart."));
                break;
            default:
                steps.add(new Step("Assess", "Assess the situation and ensure safety."));
                steps.add(new Step("Emergency Call", "Call for emergency help if needed."));
                steps.add(new Step("Comfort", "Provide comfort and stay with the person."));
                break;
        }
        return steps;
    }
}
