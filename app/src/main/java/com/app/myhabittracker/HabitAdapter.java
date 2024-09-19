package com.app.myhabittracker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myhabittracker.models.Habit;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habitList;
    private FirebaseRecyclerOptions<Habit> firebaseOptions;
    private boolean isFirebaseData;

    // Constructor for Firebase data
    public HabitAdapter(FirebaseRecyclerOptions<Habit> options) {
        this.firebaseOptions = options;
        this.isFirebaseData = true;
    }

    // Constructor for non-Firebase data (List-based)
    public HabitAdapter(List<Habit> habitList) {
        this.habitList = habitList;
        this.isFirebaseData = false;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit;
        if (isFirebaseData) {
            // FirebaseRecyclerOptions used
            habit = firebaseOptions.getSnapshots().get(position);
        } else {
            // List-based data
            habit = habitList.get(position);
        }

        // Bind the data to the ViewHolder
        bindHabit(holder, habit);
    }

    @Override
    public int getItemCount() {
        if (isFirebaseData) {
            return firebaseOptions.getSnapshots().size();
        } else {
            return habitList != null ? habitList.size() : 0;
        }
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitName, afterHabitDetail, habitDetail;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitNameText);
            afterHabitDetail = itemView.findViewById(R.id.afterhabitEditText);
            habitDetail = itemView.findViewById(R.id.habit_detail);
        }
    }

    private void bindHabit(@NonNull HabitViewHolder holder, @NonNull Habit habit) {
        holder.habitName.setText(habit.getHabitName());
        holder.afterHabitDetail.setText(habit.getAfterHabitDetail());
        holder.habitDetail.setText(habit.getHabitNameDetail());
    }

    // Optional method to update the list for non-Firebase data
    public void updateHabitList(List<Habit> newHabitList) {
        this.habitList = newHabitList;
        notifyDataSetChanged();
    }
}
