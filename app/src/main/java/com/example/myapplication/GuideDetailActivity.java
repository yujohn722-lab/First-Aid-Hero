package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class GuideDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_TEXT = "extra_text";
    public static final String EXTRA_IMAGE = "extra_image";
    public static final String EXTRA_COLOR = "extra_color";

    // Cycle through these background colors for the steps: Red, Green, Orange, Blue
    private final String[] themeColors = {"#F44336", "#4CAF50", "#FF9800", "#2196F3"};

    public static class StepData {
        String title;
        String detail;
        StepData(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String description = getIntent().getStringExtra(EXTRA_TEXT);
        int imageRes = getIntent().getIntExtra(EXTRA_IMAGE, R.drawable.hero_mascot);
        String colorHex = getIntent().getStringExtra(EXTRA_COLOR);

        setupViews(title, description, imageRes, colorHex);
        populateSteps(getMedicalSteps(title));
    }

    private void setupViews(String title, String description, int imageRes, String colorHex) {
        TextView tvHeaderTitle = findViewById(R.id.detailTitle);
        TextView tvTopicName = findViewById(R.id.detailTopicName);
        TextView tvDescription = findViewById(R.id.detailDescription);
        ImageView ivIllustration = findViewById(R.id.detailIllustration);
        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialCardView illustrationCard = (MaterialCardView) ivIllustration.getParent();

        tvHeaderTitle.setText(title != null ? title : "Guide");
        tvTopicName.setText(title);
        tvDescription.setText(description);
        ivIllustration.setImageResource(imageRes);

        if (colorHex != null && illustrationCard != null) {
            illustrationCard.setCardBackgroundColor(Color.parseColor(colorHex));
        }

        // Ensure btnBack calls finish()
        btnBack.setOnClickListener(v -> finish());
    }

    private void populateSteps(List<StepData> steps) {
        LinearLayout container = findViewById(R.id.stepsContainer);
        container.removeAllViews();

        for (int i = 0; i < steps.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.item_guide_step, container, false);
            
            MaterialCardView card = view.findViewById(R.id.stepCard);
            TextView num = view.findViewById(R.id.stepNumber);
            TextView title = view.findViewById(R.id.stepTitle);
            TextView detail = view.findViewById(R.id.stepDetail);

            StepData step = steps.get(i);
            num.setText(String.valueOf(i + 1));
            title.setText(step.title);
            detail.setText(step.detail);
            
            // Color cycling logic (Red, Green, Orange, Blue)
            String color = themeColors[i % themeColors.length];
            card.setCardBackgroundColor(Color.parseColor(color));

            container.addView(view);
        }
    }

    private List<StepData> getMedicalSteps(String topic) {
        List<StepData> steps = new ArrayList<>();
        if (topic == null) return steps;

        switch (topic) {
            case "Bruises":
                steps.add(new StepData("Apply Ice", "Apply a cold pack or ice wrapped in a towel for 15-20 minutes."));
                steps.add(new StepData("Elevate", "Keep the bruised area raised above your heart level if possible."));
                steps.add(new StepData("Rest", "Avoid straining the injured area to allow blood vessels to heal."));
                break;
            case "Cuts":
                steps.add(new StepData("Stop Bleeding", "Apply firm, direct pressure with a clean cloth until bleeding stops."));
                steps.add(new StepData("Clean Wound", "Rinse the cut with clear water to remove dirt and debris."));
                steps.add(new StepData("Disinfect", "Apply a small amount of antibiotic ointment to the area."));
                steps.add(new StepData("Cover", "Protect the wound with a sterile bandage or gauze."));
                steps.add(new StepData("Monitor", "Watch for signs of infection like redness or swelling."));
                break;
            case "Burns":
                steps.add(new StepData("Cool Down", "Run cool water over the burn for at least 10 minutes."));
                steps.add(new StepData("Protect", "Cover loosely with a sterile bandage or plastic wrap."));
                steps.add(new StepData("Pain Relief", "Take over-the-counter pain relievers if necessary."));
                steps.add(new StepData("Don't Pop", "Never pop blisters as it increases infection risk."));
                break;
            case "Choking":
                steps.add(new StepData("Stand Behind", "Wrap your arms around their waist and lean them forward."));
                steps.add(new StepData("Back Blows", "Give 5 sharp blows between the shoulder blades."));
                steps.add(new StepData("Abdominal Thrusts", "Perform 5 quick upward thrusts (Heimlich maneuver)."));
                steps.add(new StepData("Repeat", "Continue until the object is forced out."));
                break;
            case "Allergies":
                steps.add(new StepData("Identify Source", "Immediately move away from what caused the reaction."));
                steps.add(new StepData("Medication", "Use an antihistamine or an EpiPen if symptoms are severe."));
                steps.add(new StepData("Check Breathing", "Monitor for shortness of breath or swelling of the throat."));
                steps.add(new StepData("Call Help", "Seek emergency medical attention if breathing is difficult."));
                break;
            case "Insect Bites":
                steps.add(new StepData("Remove Stinger", "Gently scrape the stinger away with a flat object."));
                steps.add(new StepData("Cleanse", "Wash the bite area thoroughly with soap and water."));
                steps.add(new StepData("Reduce Itching", "Apply calamine lotion or a cold compress."));
                break;
            case "Nosebleed":
                steps.add(new StepData("Lean Forward", "Sit up straight and tilt your head slightly forward."));
                steps.add(new StepData("Pinch Nose", "Pinch the soft part of your nose shut for 10 minutes."));
                steps.add(new StepData("Wait", "Do not release the pressure to check if bleeding stopped."));
                steps.add(new StepData("Rest", "Avoid blowing your nose for several hours."));
                break;
            case "Sprains":
                steps.add(new StepData("Rest", "Stop using the injured joint immediately."));
                steps.add(new StepData("Ice", "Apply ice every few hours for 20 minutes at a time."));
                steps.add(new StepData("Compress", "Wrap with an elastic bandage to reduce swelling."));
                steps.add(new StepData("Elevate", "Keep the joint elevated above your heart."));
                break;
            default:
                steps.add(new StepData("Assess", "Quickly evaluate the person's condition."));
                steps.add(new StepData("Call for Help", "Dial emergency services if the situation is serious."));
                steps.add(new StepData("Stay Calm", "Comfort the person and wait for professional help."));
                break;
        }
        return steps;
    }
}
