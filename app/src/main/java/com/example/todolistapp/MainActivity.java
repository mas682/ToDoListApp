package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText t = (EditText)findViewById(R.id.editText);
        String text = "";
        // this currently only works when changing the rotation of the screen
        if ((savedInstanceState != null) && (savedInstanceState.getString("reminder1") != null)) {
            text = savedInstanceState.getString("reminder1");
            Log.i(" On Create", "text value: " + text);
            t.setText(text);
        }
        //parentLayout.addView(t)

        /*
        String file = "reminders";
        String data= "Hello World!";
        try {
            FileOutputStream fOut = MainActivity.this.openFileOutput(file,Context.MODE_APPEND);
            fOut.write(data.getBytes());
            fOut.close();
            // the directory is /data/user/0/com.example.todolistapp/files
            Log.i(" OnCreate", "succeeded " + getFilesDir());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        //state.putSerializable("starttime", startTime);
        EditText reminder = findViewById(R.id.editText);
        String text = reminder.getText().toString();
        Log.i("on save!!!!", "text: " + text);
        state.putString("reminder1", text);
    }


    private void fail(String sdcard_not_available) {
    }

    /** Called when the user taps the Send button */
    public void setDetails(View view) {
        // get the intent to pass data to the details activity
        Intent intent = new Intent(this, DetailsActivity.class);
        // have the info button set to the index of the edit text view
        // get the index of the edit text
        // in the future, this will be the index into the array/list to get the information
        int elem = Integer.parseInt((String)view.getTag());
        // for testing
        Log.i("elem", "elem number: " + elem);
        // all for testing
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        EditText reminder = (EditText)parentLayout.getChildAt(elem);
        // this will be changed to pass values from reminder object you create
        intent.putExtra("reminder", reminder.getText().toString());
        // for testing
        Log.i("reminder", "in main: " + reminder.getText());
        // call the activity with the intent
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    public void addNewReminderElement(View view) {
        // Get the activity main constraint layout
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        int numViewElems = parentLayout.getChildCount();
        // get the last added divider view's index
        int lastDivider = numViewElems - 1;
        // get the last added divider view
        View lastDiv = parentLayout.getChildAt(lastDivider);
        // create a new EditText view
        EditText textView = new EditText(MainActivity.this);
        // create a new RadioButton view
        RadioButton radioButton = new RadioButton(MainActivity.this);
        // create a new FloatingAction view
        FloatingActionButton infoButton = new FloatingActionButton(MainActivity.this);
        // create a new View for the divider
        View divider = new View(MainActivity.this);
        // get a Id for the new text view
        textView.setId(View.generateViewId());
        // get a Id for the new radio view
        radioButton.setId(View.generateViewId());
        // get a Id for the new info button
        infoButton.setId(View.generateViewId());
        // get a Id for the new divider
        divider.setId(View.generateViewId());
        // set the width and height of the new view
        textView.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        // set the type of the text view
        textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        // set the padding for the textview
        textView.setPadding(0, 0, 0, 0);
        // set the background of the textview
        textView.setBackground(null);
        // set the text color to white
        textView.setTextColor(Color.WHITE);
        // set the text size to 18sp
        textView.setTextSize(18);
        // for testing
        //textView.setText("sdcard found successfully");
        // set the radio buttons width and height
        radioButton.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // get the value of the color accent
        int colorAccent = Color.parseColor("#0078FF");
       // int buttonColor = Color.parseColor(String.valueOf(ContextCompat.getColor(MainActivity.this, R.color.brightBlue)));
        //set the color of the button
        radioButton.setButtonTintList(ColorStateList.valueOf(colorAccent));
        // set the size of the button
        radioButton.setScaleX((float)1.5);
        radioButton.setScaleY((float)1.5);
        // set the height and width for the info button
        infoButton.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        // get the color black
        int colorPrimaryDark = Color.parseColor("#000000");
        // set the background tint
        infoButton.setBackgroundTintList(ColorStateList.valueOf(colorPrimaryDark));
        infoButton.setRippleColor(colorPrimaryDark);
        // set the button as clickable
        infoButton.setClickable(true);
        // set the size to 30dp
        infoButton.setCustomSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
                getResources().getDisplayMetrics()));
        // set the image source
        infoButton.setImageResource(R.drawable.baseline_info_24);
        // make the icon scale to the size of the button
        infoButton.setScaleType(ImageView.ScaleType.CENTER);
        // set the tag to the index of the new EditText view
        infoButton.setTag(Integer.toString(numViewElems + 1));
        // set the function to call when clicked
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setDetails(v);
            }
        });
        // set the color of the divider
        divider.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGray));
        // set the height and width of the divider
        divider.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                getResources().getDisplayMetrics())));
        // add the view to the activity main layout
        // the numViewElems is the index at which to add the view
        parentLayout.addView(radioButton, numViewElems);
        parentLayout.addView(textView, numViewElems + 1);
        parentLayout.addView(infoButton, numViewElems + 2);
        parentLayout.addView(divider, numViewElems + 3);
        // set the constraints on where the new view should go inside the activity main layout
        ConstraintSet set = new ConstraintSet();
        set.clone(parentLayout);
        // text view constraints
        set.connect(textView.getId(), ConstraintSet.START, radioButton.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        set.connect(textView.getId(), ConstraintSet.END, infoButton.getId(), ConstraintSet.START, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        set.connect(textView.getId(), ConstraintSet.TOP, lastDiv.getId(), ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // radio button constraints
        set.connect(radioButton.getId(), ConstraintSet.START, parentLayout.getId(), ConstraintSet.START, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        set.connect(radioButton.getId(), ConstraintSet.TOP, lastDiv.getId(), ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // info button constraints
        set.connect(infoButton.getId(), ConstraintSet.TOP, lastDiv.getId(), ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14,
                getResources().getDisplayMetrics()));
        set.connect(infoButton.getId(), ConstraintSet.END, parentLayout.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // divider constraints
        set.connect(divider.getId(), ConstraintSet.TOP, textView.getId(), ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        set.connect(divider.getId(), ConstraintSet.START, radioButton.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        set.connect(divider.getId(), ConstraintSet.END, parentLayout.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        set.applyTo(parentLayout);
    }

}
