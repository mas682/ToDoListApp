package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FrequencyActivity extends AppCompatActivity {

    private int repeatValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);
        Intent intent = getIntent();
        int repeat = intent.getIntExtra("repeat", 1);
        setCheck(repeat);
        repeatValue = repeat;
    }

    // set the check to visible based off the value passed in
    public void setCheck(int repeat)
    {
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.frequencyBox);
        ImageButton tempButton = (ImageButton)parentLayout.getChildAt((3*repeat) - 1);
        tempButton.setVisibility(View.VISIBLE);
    }

    // return the repeat value that was clicked(1 = never, 2 = daily, etc.)
    public void setRepeatValue(View view)
    {
        repeatValue = Integer.parseInt((String)view.getTag());
        // for testing
        //setCheck(repeatValue);
        Intent intentWithResult = new Intent();
        intentWithResult.putExtra("repeat", repeatValue);
        setResult(1, intentWithResult);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intentWithResult = new Intent();
        intentWithResult.putExtra("repeat", repeatValue);
        setResult(1, intentWithResult);
        finish();
    }
}
