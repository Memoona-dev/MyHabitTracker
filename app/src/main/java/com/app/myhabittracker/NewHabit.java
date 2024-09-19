package com.app.myhabittracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myhabittracker.models.Habit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewHabit extends AppCompatActivity {

    private EditText habitNameText, afterHabitDetailText, habitNameDetailText;
    private Button createNewHabitButton;
    RecyclerView recyclerView;
    HabitAdapter habitAdapter;
    private ImageButton backbutton;
    private DatabaseReference databaseReference;

    // Declare the day TextViews
    private TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    // Initialize a Handler
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("habits");

        // Initialize views
        habitNameText = findViewById(R.id.habitNameText);
        afterHabitDetailText = findViewById(R.id.afterhabitEditText);
        habitNameDetailText = findViewById(R.id.habitNameEditText);
        createNewHabitButton = findViewById(R.id.createNewHabitButton);
        backbutton = findViewById(R.id.back_btn);

        // Initialize the day TextViews
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);

        // Set click listeners for each day
        setDayClickListener(monday);
        setDayClickListener(tuesday);
        setDayClickListener(wednesday);
        setDayClickListener(thursday);
        setDayClickListener(friday);
        setDayClickListener(saturday);
        setDayClickListener(sunday);

        // Set onClick listener for the back button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Close current activity and return to previous
            }
        });

        // Set onClick listener for the create habit button
        createNewHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewHabit", "Create New Habit button clicked");
                createNewHabit();  // Calls the method to create a new habit
            }
        });
    }

    // Handle activity pause
    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null); // Cancel any pending handler tasks
        }
    }

    // Handle activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NewHabit", "Activity resumed");
    }

    // Handle activity stop
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("NewHabit", "Activity stopped");
    }

    private void createNewHabit() {
        // Retrieve input from EditTexts
        String habitName = habitNameText.getText().toString().trim();
        String afterHabitDetail = afterHabitDetailText.getText().toString().trim();
        String habitNameDetail = habitNameDetailText.getText().toString().trim();

        // Input validation: Make sure no field is empty
        if (habitName.isEmpty() || afterHabitDetail.isEmpty() || habitNameDetail.isEmpty()) {
            if (!isFinishing()) {
                Toast.makeText(NewHabit.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // Generate a unique ID for the habit
        String habitId = databaseReference.push().getKey();

        // Create a new Habit object with the collected data
        Habit habit = new Habit(habitId, habitName, afterHabitDetail, habitNameDetail);

        // Push the new habit to the Firebase database using the generated ID
        DatabaseReference newHabitRef = databaseReference.child(habitId);
        newHabitRef.setValue(habit)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (!isFinishing() && !isDestroyed()) {
                            // Show a success message
                            Toast.makeText(NewHabit.this, "Habit added successfully", Toast.LENGTH_SHORT).show();

                            // Navigate to AllHabit activity to display all habits
                            Intent intent = new Intent(NewHabit.this, AllHabit.class);
                            startActivity(intent);
                            finish();  // Close the current activity
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (!isFinishing() && !isDestroyed()) {
                            // Show an error message in case of failure
                            Toast.makeText(NewHabit.this, "Failed to add habit: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    // Set click listener for each day TextView
    private void setDayClickListener(final TextView dayTextView) {
        dayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = dayTextView.isSelected();
                // Toggle the selected state
                dayTextView.setSelected(!isSelected);
            }
        });
    }
}

