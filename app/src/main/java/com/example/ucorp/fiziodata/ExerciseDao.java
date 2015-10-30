package com.example.ucorp.fiziodata;

import com.example.ucorp.fizioapp.R;

import java.util.Arrays;
import java.util.List;

public class ExerciseDao {
    private static List<Exercise> exercises = Arrays.asList(
            new Exercise(1, "Упражнение один", "Описание упражнения один", R.raw.big_buck_bunny),
            new Exercise(2, "Упражнение два", "Описание упражнения два", R.raw.big_buck_bunny),
            new Exercise(3, "Упражнение три", "Описание упражнения три", R.raw.big_buck_bunny));

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
