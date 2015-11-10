package com.example.ucorp.fiziodata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExecutionDao {

    private static List<Execution> exercises = new ArrayList<Execution>() {{
        //week
        this.add(new Execution(1,  5,  10, 3.0f, DateStub("23.10.2015 10:20:56"), 1));
        this.add(new Execution(2,  6,  8,  3.5f, DateStub("24.10.2015 10:20:56"), 1));
        this.add(new Execution(3,  12, 12, 4.0f, DateStub("25.10.2015 10:20:56"), 1));
        this.add(new Execution(4,  3,  12, 1.5f, DateStub("23.10.2015 12:11:48"), 2));
        this.add(new Execution(5,  4,  10, 2.5f, DateStub("24.10.2015 12:11:48"), 2));
        this.add(new Execution(6,  5,  8,  3.5f, DateStub("25.10.2015 12:11:48"), 2));
        this.add(new Execution(7,  3,  10, 5.0f, DateStub("23.10.2015 09:01:03"), 3));
        this.add(new Execution(8,  4,  15, 2.5f, DateStub("24.10.2015 09:01:03"), 3));
        this.add(new Execution(9,  5,  20, 1.0f, DateStub("25.10.2015 09:01:03"), 3));
        //month
        this.add(new Execution(10, 5,  10, 3.0f, DateStub("01.10.2015 10:20:56"), 1));
        this.add(new Execution(11, 6,  8,  3.5f, DateStub("02.10.2015 10:20:56"), 1));
        this.add(new Execution(12, 12, 12, 4.0f, DateStub("03.10.2015 10:20:56"), 1));
        this.add(new Execution(13, 3,  12, 1.5f, DateStub("01.10.2015 12:11:48"), 2));
        this.add(new Execution(14, 4,  10, 2.5f, DateStub("02.10.2015 12:11:48"), 2));
        this.add(new Execution(15, 5,  8,  3.5f, DateStub("03.10.2015 12:11:48"), 2));
        this.add(new Execution(16, 3,  10, 5.0f, DateStub("01.10.2015 09:01:03"), 3));
        this.add(new Execution(17, 4,  15, 2.5f, DateStub("02.10.2015 09:01:03"), 3));
        this.add(new Execution(18, 5,  20, 1.0f, DateStub("03.10.2015 09:01:03"), 3));
        //year
        this.add(new Execution(19, 5,  10, 3.0f, DateStub("23.05.2015 10:20:56"), 1));
        this.add(new Execution(20, 6,  8,  3.5f, DateStub("24.05.2015 10:20:56"), 1));
        this.add(new Execution(21, 12, 12, 4.0f, DateStub("25.05.2015 10:20:56"), 1));
        this.add(new Execution(22, 3,  12, 1.5f, DateStub("23.05.2015 12:11:48"), 2));
        this.add(new Execution(23, 4,  10, 2.5f, DateStub("24.05.2015 12:11:48"), 2));
        this.add(new Execution(24, 5,  8,  3.5f, DateStub("25.05.2015 12:11:48"), 2));
        this.add(new Execution(25, 3,  10, 5.0f, DateStub("23.05.2015 09:01:03"), 3));
        this.add(new Execution(25, 4,  15, 2.5f, DateStub("24.05.2015 09:01:03"), 3));
        this.add(new Execution(26, 5,  20, 1.0f, DateStub("25.05.2015 09:01:03"), 3));
    }};

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
