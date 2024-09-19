package com.app.myhabittracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CurrentHabits extends AppCompatActivity {

    private TextView allHabitsText;
    private TextView createButton;
    private TextView stats;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_habits); // Set the layout for this activity

        // Initialize the "All Habits" button (TextView)
        allHabitsText = findViewById(R.id.allHabitsText);

        // Set OnClickListener for the "All Habits" button
        allHabitsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the All Habit screen
                Intent intent = new Intent(CurrentHabits.this, AllHabit.class);
                startActivity(intent);
            }
        });

        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the New Habit screen
                Intent intent = new Intent(CurrentHabits.this, NewHabit.class);
                startActivity(intent);
            }
        });

        stats = findViewById(R.id.stats);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the New Habit screen
                Intent intent = new Intent(CurrentHabits.this, Statistics.class);
                startActivity(intent);
            }
        });
    }
}

