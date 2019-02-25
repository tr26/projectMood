package com.example.tr.project3oc.model;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.media.Image;
import android.support.annotation.DrawableRes;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * Created by tr on 16/02/2019.
 */

public class Mood implements Serializable{

    private String mDrawableString;
    private int mNbTag;
    private String mComment;
    //private LocalDateTime mCurrentTime;
    private int mColor;

    /*public Mood(String drawable, int nbTag, String comment, Date date, int color) {
        mDrawableString = drawable;
        mNbTag = nbTag;
        mComment = comment;
        mDate = date;
        mColor = color;
    }*/

    public String getDrawableMood() {
        return mDrawableString;
    }

    public void setDrawableString(String drawable) {
        mDrawableString = drawable;
    }

    public int getNbTag() {
        return mNbTag;
    }

    public void setNbTag(int nbTag) {

        mNbTag = nbTag;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

   /* public LocalDateTime getCurrentTime() {
        return mCurrentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.mCurrentTime = currentTime;
    }*/

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }


}
