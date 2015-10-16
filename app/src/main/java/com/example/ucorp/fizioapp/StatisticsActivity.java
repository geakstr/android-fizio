package com.example.ucorp.fizioapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        exerciseList = (ListView) findViewById(R.id.exerciseList);

        List<String> exercises = Arrays.asList("Exercise one", "Exercise two", "Exercise three");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercises);
        exerciseList.setAdapter(adapter);
    }
}
