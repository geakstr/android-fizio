package com.example.ucorp.fiziodata;

import java.util.Date;

public class Execution {

    private int id;
    private int repeatCount;
    private int executionCount;
    private float weight;
    private Date date;
    private int exerciseId;

    public Execution(int id, int repeatCount, int executionCount, float weight, Date date, int exerciseId) {
        this.id = id;
        this.repeatCount = repeatCount;
        this.executionCount = executionCount;
        this.weight = weight;
        this.date = date;
        this.exerciseId = exerciseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(int executionCount) {
        this.executionCount = executionCount;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        exerciseId = exerciseId;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "id=" + id +
                ", repeatCount=" + repeatCount +
                ", executionCount=" + executionCount +
                ", weight=" + weight +
                ", date=" + date +
                ", ExerciseId=" + exerciseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Execution execution = (Execution) o;

        return id == execution.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
