package com.app.myhabittracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myhabittracker.models.Habit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllHabit extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList;
    private TextView homeText;
    private TextView createButton;
    private TextView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habit);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Habits");

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.habitsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        habitList = new ArrayList<>();
        habitAdapter = new HabitAdapter(habitList);
        recyclerView.setAdapter(habitAdapter);

        // Load habits from Firebase
        loadHabitsFromFirebase();

        // Initialize the "Today" button (TextView)
        homeText = findViewById(R.id.homeText);

        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the New Habit screen
                Intent intent = new Intent(AllHabit.this, NewHabit.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the "Today" button
        homeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Current Habit screen
                Intent intent = new Intent(AllHabit.this, CurrentHabits.class);
                startActivity(intent);
            }
        });

        stats = findViewById(R.id.stats);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the New Habit screen
                Intent intent = new Intent(AllHabit.this, Statistics.class);
                startActivity(intent);
            }
        });


    }

    private void loadHabitsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                habitList.clear(); // Clear list before adding new data
                for (DataSnapshot habitSnapshot : dataSnapshot.getChildren()) {
                    Habit habit = habitSnapshot.getValue(Habit.class); // Map data to Habit object
                    if (habit != null) {
                        habitList.add(habit); // Add each habit to the list
                    }
                }
                habitAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AllHabit.this, "Failed to load habits: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
