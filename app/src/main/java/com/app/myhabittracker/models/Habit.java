package com.app.myhabittracker.models;

public class Habit {
    private String habitId;
    private String habitName;
    private String afterHabitDetail;
    private String habitNameDetail;

    // No-argument constructor (required for Firebase)
    public Habit() {
        // Default constructor required for Firebase
    }

    // Constructor that accepts all fields
    public Habit(String habitId, String habitName, String afterHabitDetail, String habitNameDetail) {
        this.habitId = habitId;
        this.habitName = habitName;
        this.afterHabitDetail = afterHabitDetail;
        this.habitNameDetail = habitNameDetail;
    }

    // Getters and setters
    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getAfterHabitDetail() {
        return afterHabitDetail;
    }

    public void setAfterHabitDetail(String afterHabitDetail) {
        this.afterHabitDetail = afterHabitDetail;
    }

    public String getHabitNameDetail() {
        return habitNameDetail;
    }

    public void setHabitNameDetail(String habitNameDetail) {
        this.habitNameDetail = habitNameDetail;
    }
}
