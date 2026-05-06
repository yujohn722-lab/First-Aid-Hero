package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GuideDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TOPIC = "extra_topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Simple placeholder for now
        setContentView(R.layout.activity_main); 
    }
}
