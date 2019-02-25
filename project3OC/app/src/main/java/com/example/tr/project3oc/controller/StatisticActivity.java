package com.example.tr.project3oc.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tr.project3oc.R;
import com.example.tr.project3oc.model.Mood;

import java.io.IOException;
import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    private TextView mTextViewOne;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        mTextViewOne = (TextView) findViewById(R.id.text_stats);
        mPreferences = getSharedPreferences(MainActivity.MY_OBJECT, 0);
        ArrayList<Mood> newTable = new ArrayList<>();

        try {
            newTable = (ArrayList<Mood>) ObjectSerializer.deserialize(mPreferences.getString(MainActivity.MY_OBJECT, null));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        if (mPreferences.contains(MainActivity.MY_OBJECT)) {
            mTextViewOne.setText(firstDisplayMood(newTable));
            Toast.makeText(this, "toto "+newTable.get(0).getComment(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no preference", Toast.LENGTH_SHORT).show();
        }
    }

    public String firstDisplayMood(ArrayList<Mood> myTableMood){
        int nb_of_Mood = myTableMood.size() - 1;
        String newLine = System.lineSeparator();
        String textToDisplay = "";
        for (int index = nb_of_Mood; index >= 0; index--)
            textToDisplay += index + " " + myTableMood.get(index).getComment() + newLine;
        return textToDisplay;
    }

}
