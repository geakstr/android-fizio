package com.example.ucorp.fizioapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.ucorp.fiziodata.Execution;
import com.example.ucorp.fiziodata.ExecutionDao;
import com.example.ucorp.fiziodata.Exercise;
import com.example.ucorp.fiziodata.ExerciseDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StatisticsActivity.class);
                startActivity(intent);
            }
        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return ExerciseDao.getSize();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int exerciseId = ExerciseDao.getExerciseId(position);
            return ExerciseDao.getExerciseById(exerciseId).getName();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ImageView imgView = (ImageView) rootView.findViewById(R.id.imageView);
            int exerciseNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            final int exerciseId = ExerciseDao.getExerciseId(exerciseNumber - 1);
            final Exercise exercise = ExerciseDao.getExerciseById(exerciseId);
            textView.setText(exercise.getDesc());
            imgView.setImageResource(exercise.getImageId());

            imgView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSettings();
                }

                private void changeSettings() {
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.num_picker, null);
                    NumberPicker repeatCountPicker = (NumberPicker) dialogView.findViewById(R.id.repeatCountPicker);
                    NumberPicker executionCountPicker = (NumberPicker) dialogView.findViewById(R.id.executionCountPicker);
                    NumberPicker weightPicker = (NumberPicker) dialogView.findViewById(R.id.weightPicker);
                    Execution execution = ExecutionDao.getLastExecutionByExercise(ExerciseDao.getExerciseById(exerciseId));
                    List<String> numbersList = new ArrayList<>();
                    for (int i = 1; i < 100; i++) {
                        numbersList.add(String.valueOf(i));
                    }
                    final String[] numbersInt = new String[numbersList.size()];
                    numbersList.toArray(numbersInt);
                    repeatCountPicker.setDisplayedValues(numbersInt);
                    repeatCountPicker.setMaxValue(numbersInt.length - 1);
                    repeatCountPicker.setMinValue(0);
                    repeatCountPicker.setFormatter(new NumberPicker.Formatter() {
                        @Override
                        public String format(int i) {
                            return String.format("%02d", i);
                        }
                    });
                    repeatCountPicker.setValue(execution.getRepeatCount() - 1);

                    executionCountPicker.setDisplayedValues(numbersInt);
                    executionCountPicker.setMaxValue(numbersInt.length - 1);
                    executionCountPicker.setMinValue(0);
                    executionCountPicker.setFormatter(new NumberPicker.Formatter() {
                        @Override
                        public String format(int i) {
                            return String.format("%02d", i);
                        }
                    });
                    executionCountPicker.setValue(execution.getExecutionCount() - 1);

                    numbersList = new ArrayList<>();
                    for (double i = .5; i < 50.5; i += .5) {
                        numbersList.add(String.valueOf(i));
                    }
                    final String[] numbersDbl = new String[numbersList.size()];
                    numbersList.toArray(numbersDbl);
                    weightPicker.setDisplayedValues(numbersDbl);
                    weightPicker.setMaxValue(numbersDbl.length - 1);
                    weightPicker.setMinValue(0);
                    weightPicker.setValue(numbersList.indexOf(String.valueOf(execution.getWeight())));

                    AlertDialog.Builder chooseSettings = new AlertDialog.Builder(getActivity());
                    chooseSettings.setTitle(getString(R.string.settings));
                    chooseSettings.setView(dialogView);
                    chooseSettings.setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog alertDialog = (AlertDialog) dialog;
                                    sendInfo(alertDialog);
                                }

                                private void sendInfo(AlertDialog alertDialog) {
                                    Intent intent = new Intent(getContext(), ExternalActivity.class);
                                    NumberPicker repeatCountPicker = (NumberPicker) alertDialog.findViewById(R.id.repeatCountPicker);
                                    NumberPicker executionCountPicker = (NumberPicker) alertDialog.findViewById(R.id.executionCountPicker);
                                    NumberPicker weightPicker = (NumberPicker) alertDialog.findViewById(R.id.weightPicker);
                                    intent.putExtra("EXERCISE_ID", exerciseId);
                                    intent.putExtra("REPEAT_COUNT", Integer.parseInt(numbersInt[repeatCountPicker.getValue()]));
                                    intent.putExtra("EXECUTION_COUNT", Integer.parseInt(numbersInt[executionCountPicker.getValue()]));
                                    intent.putExtra("WEIGHT", Double.parseDouble(numbersDbl[weightPicker.getValue()]));
                                    startActivity(intent);
                                }
                            });
                    chooseSettings.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    chooseSettings.show();
                }
            });
            return rootView;
        }
    }
}
