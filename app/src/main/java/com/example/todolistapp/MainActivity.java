package com.example.todolistapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void addNewReminderElement(View view) {
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        EditText textView = new EditText(MainActivity.this);
        textView.setId(View.generateViewId());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        //textView.setHeight(100);
        textView.setTextColor(Color.BLUE);
        textView.setText("Child Row");
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.GRAY);
        parentLayout.addView(textView, 0);
        ConstraintSet set = new ConstraintSet();
        set.clone(parentLayout);
        set.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 200);
        set.connect(textView.getId(), ConstraintSet.RIGHT, parentLayout.getId(), ConstraintSet.RIGHT, 16);
        set.connect(textView.getId(), ConstraintSet.LEFT, parentLayout.getId(), ConstraintSet.LEFT, 16);
        set.connect(textView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 150);
        set.applyTo(parentLayout);
    }

}
