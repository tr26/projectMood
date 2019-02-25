package com.example.tr.project3oc.controller;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tr.project3oc.R;
import com.example.tr.project3oc.model.Mood;
import com.example.tr.project3oc.model.MoodTable;
import com.example.tr.project3oc.model.Toto;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final int SWIPE_THREASHOLD = 100;
    public static final int LIM_DOWN = -2;
    public static final int LIM_UP = 2;
    public static final String MY_OBJECT = "toto";

    private ImageView mImageView;
    public ImageView mImagePopUp;
    public ImageButton mButtonAdd;
    public EditText mInputComment;
    public Mood mMood;
    public String mDrawableString;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public ImageButton mImgBtnStats;
    public LinearLayout mContainerPopUp;
    public int mColor;



    private GestureDetector mDetector;
    public int mPosition; // this help us to display the good Image

    private Dialog mMyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView2);
        mImagePopUp = (ImageView) findViewById(R.id.image_popup);
        mButtonAdd = (ImageButton) findViewById(R.id.btnMood);
        mInputComment = (EditText) findViewById(R.id.comment_input);
        mImgBtnStats = (ImageButton) findViewById(R.id.image_button_statistics);
        //mDrawable = mImagePopUp.getDrawable();
        mPosition = 0;
        mDetector = new GestureDetector(this);
        mMyDialog = new Dialog(this);

        mImgBtnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent statActivityIntent = new Intent(MainActivity.this, StatisticActivity.class);
                startActivity(statActivityIntent);
            }
        });
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
        boolean result = false;
        float diffY = motionEvent1.getY() - motionEvent.getY();
        float diffX = motionEvent1.getX() - motionEvent.getX();

        if (Math.abs(diffY) > Math.abs(diffX))
            if (Math.abs(diffY) > SWIPE_THREASHOLD && Math.abs(vY) > 100) {
                if (diffY > 0) {

                    if (mPosition > LIM_DOWN)
                    {
                        onSwipeDown();
                        result = true;
                    }
                    else { result = false; }

                } else

                {
                    if (mPosition < LIM_UP)
                    {
                        onSwipeUp();
                        result = true;
                    }
                    else { result = false; }
                }
            }
            else {
                // we don't care about swipe left or right
            }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void putTheRightImage(ImageView imageView){
        switch (mPosition){
            case -2:
                imageView.setImageResource(R.drawable.verybadday);
                break;
            case -1:
                imageView.setImageResource(R.drawable.badday);
                break;
            case 0:
                imageView.setImageResource(R.drawable.normalday);
                break;
            case 1:
                imageView.setImageResource(R.drawable.goodday);
                break;
            case 2:
                imageView.setImageResource(R.drawable.verygoodday);
                break;
            default:
        }

    }

    public int putTheRightBackGround(int mPosition){
        switch (mPosition){
            case -2:
                mContainerPopUp.setBackgroundResource(R.color.veryBadDay);
                return R.color.veryBadDay;
            case -1:
                mContainerPopUp.setBackgroundResource(R.color.badDay);
                return R.color.badDay;
            case 0:
                mContainerPopUp.setBackgroundResource(R.color.normalDay);
                return R.color.normalDay;
            case 1:
                mContainerPopUp.setBackgroundResource(R.color.goodDay);
                return R.color.goodDay;
            case 2:
                mContainerPopUp.setBackgroundResource(R.color.veryGoodDay);
                return R.color.veryGoodDay;
            default:
        }

        return mPosition;
    }

    private void onSwipeUp() {
        mPosition += 1;
        putTheRightImage(mImageView);

    }

    private void onSwipeDown() {

        mPosition -= 1;
        putTheRightImage(mImageView);
    }

    public void showPopUp(View v) {
        mMyDialog.setContentView(R.layout.add_mood_popup);
        TextView mCloseView;
        Button mBtnSubmit;
        mContainerPopUp = (LinearLayout) mMyDialog.findViewById(R.id.popup_container);
        mImagePopUp = (ImageView) mMyDialog.findViewById(R.id.image_popup);
        mInputComment = (EditText) mMyDialog.findViewById(R.id.comment_input);
        mCloseView = (TextView) mMyDialog.findViewById(R.id.close_popup);
        mBtnSubmit = (Button) mMyDialog.findViewById(R.id.btn_submit);

        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.dismiss();
            }
        });
        mBtnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //LocalDateTime mCurrentTime = LocalDateTime.now();
                String mCommentTextView;

                mDrawableString = mImagePopUp.getDrawable().toString();

                if (mInputComment.getText().toString().isEmpty()){
                    mCommentTextView = "nothing to signal";
                } else{
                    mCommentTextView = mInputComment.getText().toString();
                }

                Mood mMood = new Mood();
                mMood.setDrawableString("tahar");
                mMood.setNbTag(mPosition);
                mMood.setComment(mCommentTextView);
                //mMood.setCurrentTime(mCurrentTime);
                mMood.setColor(mColor);

                SharedPreferences sharedP = getSharedPreferences(MY_OBJECT, 0);

                //if Something has already been created, I just have to add into my List a new Mood

                if(sharedP.contains(MY_OBJECT)){


//I create a new ArrayList of Mood
                    ArrayList<Mood> mYTableauNew = new ArrayList<Mood>();
//I deserialize the one existed
                    try {
                        mYTableauNew = (ArrayList<Mood>) ObjectSerializer.deserialize(sharedP.getString(MY_OBJECT, ObjectSerializer.serialize(new ArrayList<String>())));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
//to add one, I need to know the length of the one existed
                    int mIndexTable = mYTableauNew.size();
//I add the newMood which just have been created.
                    mYTableauNew.add(mIndexTable, mMood);
// I have to delete the first one
                    sharedP.edit().clear();
//and edit the newSerialized
                    try {
                        sharedP.edit()
                                .putString(MY_OBJECT, ObjectSerializer.serialize(mYTableauNew))
                                .apply();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
//else I have to create my List and to put into it, the mood which just have been created
                else {
                    ArrayList<Mood> myTableau = new ArrayList<Mood>();
                    myTableau.add(0, mMood);

                    try {
                        String toto = ObjectSerializer.serialize(myTableau);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        sharedP.edit().putString(MY_OBJECT, ObjectSerializer.serialize(myTableau)).apply();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }

                mMyDialog.dismiss();
            }
        });

        int mColor = putTheRightBackGround(mPosition);

        putTheRightImage(mImagePopUp);

        mMyDialog.show();
    }





    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }


}


























