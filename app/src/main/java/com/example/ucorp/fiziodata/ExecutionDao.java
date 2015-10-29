package com.example.ucorp.fiziodata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExecutionDao {

    private static List<Execution> exercises = Arrays.asList(
            new Execution(43, 5, 10, 3., DateStub("19.10.2015 10:20:56"), 23),
            new Execution(44, 6, 11, 3.5, DateStub("20.10.2015 10:20:56"), 23),
            new Execution(99, 7, 8, 1.5, DateStub("21.10.2015 12:11:48"), 42),
            new Execution(136, 6, 4, 2., DateStub("23.10.2015 09:01:03"), 55));

    private static Date DateStub(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Execution getLastExecutionByExercise(Exercise exercise) {
        List<Execution> executions = getExecutionsByExercise(exercise);
        Collections.sort(executions, new Comparator<Execution>() {
            @Override
            public int compare(Execution lhs, Execution rhs) {
                if (lhs.getDate().before(rhs.getDate())) {
                    return 1;
                } else if (lhs.getDate().after(rhs.getDate())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return executions.get(0);
    }

    public static List<Execution> getExecutionsByExercise(Exercise exercise) {
        List<Execution> executions = new ArrayList<>();
        for (Execution execution : exercises) {
            if(execution.getExerciseId() == exercise.getId()) {
                executions.add(execution);
            }
        }
        return executions;
    }

}
