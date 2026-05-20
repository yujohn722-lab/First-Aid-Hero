package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class GuideDetailActivity extends AppCompatActivity {

    // These must match exactly what GuideActivity sends
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TEXT = "EXTRA_TEXT";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_COLOR = "EXTRA_COLOR";
    public static final String EXTRA_STEPS = "EXTRA_STEPS";

    private final String[] themeColors = {"#F44336", "#4CAF50", "#FF9800", "#2196F3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        // 1. Get the data sent from GuideActivity
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String description = getIntent().getStringExtra(EXTRA_TEXT);
        int imageRes = getIntent().getIntExtra(EXTRA_IMAGE, R.drawable.allerfy);
        String colorHex = getIntent().getStringExtra(EXTRA_COLOR);
        ArrayList<String> stepsList = getIntent().getStringArrayListExtra(EXTRA_STEPS);

        // 2. Setup the top part (Title, Image, Description)
        setupViews(title, description, imageRes, colorHex);

        // 3. Build the "What to do" cards dynamically
        if (stepsList != null && !stepsList.isEmpty()) {
            populateSteps(stepsList);
        }
    }

    private void setupViews(String title, String description, int imageRes, String colorHex) {
        TextView tvHeaderTitle = findViewById(R.id.detailTitle);
        TextView tvTopicName = findViewById(R.id.detailTopicName);
        TextView tvDescription = findViewById(R.id.detailDescription);
        ImageView ivIllustration = findViewById(R.id.detailIllustration);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Find the card surrounding the image to change its background color
        View illustrationContainer = findViewById(R.id.detailIllustration);
        if (illustrationContainer != null && illustrationContainer.getParent() instanceof MaterialCardView) {
            MaterialCardView card = (MaterialCardView) illustrationContainer.getParent();
            if (colorHex != null) card.setCardBackgroundColor(Color.parseColor(colorHex));
        }

        if (tvHeaderTitle != null) tvHeaderTitle.setText(title != null ? title : "Guide");
        if (tvTopicName != null) tvTopicName.setText(title);
        if (tvDescription != null) tvDescription.setText(description);
        if (ivIllustration != null) ivIllustration.setImageResource(imageRes);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void populateSteps(List<String> steps) {
        LinearLayout container = findViewById(R.id.stepsContainer);
        if (container == null) return;

        container.removeAllViews(); // Clear any old cards

        for (int i = 0; i < steps.size(); i++) {
            // Inflate your card layout
            View view = getLayoutInflater().inflate(R.layout.item_guide_step, container, false);

            MaterialCardView card = view.findViewById(R.id.stepCard);
            TextView num = view.findViewById(R.id.stepNumber);
            TextView title = view.findViewById(R.id.stepTitle);
            TextView detail = view.findViewById(R.id.stepDetail);

            // 1. Set the big number on the left (1, 2, 3...)
            if (num != null) num.setText(String.valueOf(i + 1));

            // 2. REMOVE THE "STEP X" TITLE ENTIRELY
            if (title != null) {
                title.setVisibility(View.GONE);
            }

            // 3. Set the actual instruction text in the detail slot
            if (detail != null) {
                detail.setText(steps.get(i));
                detail.setTextSize(16);
                detail.setTypeface(null, Typeface.NORMAL); // Changed from BOLD to NORMAL
                detail.setTextColor(Color.WHITE);
            }

            // 4. Cycle colors: Red -> Green -> Orange -> Blue
            if (card != null) {
                String color = themeColors[i % themeColors.length];
                card.setCardBackgroundColor(Color.parseColor(color));
            }

            container.addView(view);
        }
    }
}