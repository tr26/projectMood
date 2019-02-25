package com.example.tr.project3oc.model;

import java.util.List;

/**
 * Created by tr on 21/02/2019.
 */

//Table which contains each Mood that had been saved
public class MoodTable {

    private List<Mood> mMoodList;

    public void addMood(Mood mood, int i){
        mMoodList.add(i, mood);
    }
}
