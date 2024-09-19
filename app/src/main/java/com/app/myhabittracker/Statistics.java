package com.app.myhabittracker;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    private PieChart pieChart;
    private TextView summaryTextView;
    private TextView titleTextView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics); // Assuming the XML layout file is named statistics.xml

        // Initialize your views if you want to interact with them

        pieChart = findViewById(R.id.piechart);
        summaryTextView = findViewById(R.id.summaryTextView);
        titleTextView = findViewById(R.id.titleTextView);
        backButton = findViewById(R.id.backButton);

        // Example data
        float completedHabits = 30;  // Replace with actual completed habits count
        float missedHabits = 10;     // Replace with actual missed habits count

        // If you want to set the text programmatically
        //titleTextView.setText("My Statistics");

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back
                finish();
            }
        });

        setupPieChart(completedHabits, missedHabits);
    }

    private void setupPieChart(float completed, float missed) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(completed, "Completed"));
        entries.add(new PieEntry(missed, "Missed"));

        PieDataSet dataSet = new PieDataSet(entries, "Habit Statistics");
        int colorCompleted = ContextCompat.getColor(this, R.color.light_blue); // Custom color for "Completed"
        int colorMissed = ContextCompat.getColor(this, R.color.purple);   // Custom color for "Missed"

        // Set custom colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(colorCompleted);
        colors.add(colorMissed);

        dataSet.setColors(colors); // Set custom colors
        dataSet.setValueTextSize(16f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // Refresh the chart

        Legend legend = pieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(20f); // Adjust legend text size

        // Update summary text programmatically
        String summary = "Completed Habits: " + completed + "\nMissed Habits: " + missed;
        summaryTextView.setText(summary);
        summaryTextView.setTextColor(ContextCompat.getColor(this, R.color.purple)); // Set text color
    }
}
