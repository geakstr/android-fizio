package com.example.ucorp.fizioapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.ucorp.fiziodata.Execution;
import com.example.ucorp.fiziodata.ExecutionDao;
import com.example.ucorp.fiziodata.Exercise;
import com.example.ucorp.fiziodata.ExerciseDao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    ListView exerciseList;
    private LineChart mChart;
    private int exerciseId = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        exerciseList = (ListView) findViewById(R.id.exerciseList);
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ExerciseDao.getExercises());
        exerciseList.setAdapter(adapter);
        drawChart();
    }

    private void drawChart() {
        RadioButton repeatCount = (RadioButton) findViewById(R.id.repeatCount);
        RadioButton executionCount = (RadioButton) findViewById(R.id.executionCount);
        RadioButton weight = (RadioButton) findViewById(R.id.weight);
        RadioButton afterYear = (RadioButton) findViewById(R.id.afterYear);
        RadioButton afterMonth = (RadioButton) findViewById(R.id.afterMonth);
        Exercise exercise = ExerciseDao.getExerciseById(exerciseId);
        List<Execution> executions = ExecutionDao.getExecutionsByExercise(exercise);
        List<Execution> executionsByTime = new ArrayList<>();
        Date compareDate = new Date();
        Calendar cal = Calendar.getInstance();
        if (afterYear.isChecked()) {
            cal.setTime(compareDate);
            cal.add(Calendar.YEAR, -1);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            compareDate = cal.getTime();
        } else if (afterMonth.isChecked()) {
            cal.setTime(compareDate);
            cal.add(Calendar.MONTH, -1);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            compareDate = cal.getTime();
        } else {
            cal.setTime(compareDate);
            cal.add(Calendar.WEEK_OF_MONTH, -1);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            compareDate = cal.getTime();
        }
        for (Execution e : executions) {
            if (e.getDate().after(compareDate)) {
                executionsByTime.add(e);
            }
        }


        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription(getString(R.string.no_data));

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(50f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawLimitLinesBehindData(true);
        mChart.getAxisRight().setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();
        int i = 0;
        for (Execution e : executionsByTime) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            xVals.add(sdf.format(e.getDate()));
            yVals.add(new Entry(e.getExecutionCount(), i));
            i++;
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, exercise.getName());

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.parseColor("#ffa500"));
        set1.setCircleColor(Color.parseColor("#008000"));
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
        mChart.animateX(2000, Easing.EasingOption.EaseInOutQuart);
    }

}
