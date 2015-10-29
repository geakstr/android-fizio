package com.example.ucorp.fiziodata;

import com.example.ucorp.fizioapp.R;

import java.util.Arrays;
import java.util.List;

public class ExerciseDao {

    private static List<Exercise> exercises = Arrays.asList(
            new Exercise(23, "Упражнение один", "Описание упражнения один", R.drawable.pic1),
            new Exercise(42, "Упражнение два", "Описание упражнения два", R.drawable.pic2),
            new Exercise(55, "Упражнение три", "Описание упражнения три", R.drawable.pic3));

    public static List<Exercise> getExercises() {
        return exercises;
    }

    public static int getSize() {
        return exercises.size();
    }

    public static int getExerciseId(int idx) {
        return exercises.get(idx).getId();
    }

    public static Exercise getExerciseById(int id) {
        for (Exercise c: exercises) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

}
