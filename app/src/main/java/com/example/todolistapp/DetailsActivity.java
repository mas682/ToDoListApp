package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // design decision to not save changes unless user hits done or back button
        // if they close the app, results not saved
        setContentView(R.layout.activity_details);
        // get the intent that was sent over
        Intent intent = getIntent();
        // get the reminder text that was passed over
        String reminder = intent.getStringExtra("reminder");
        // get the view for the reminder
        EditText text = (EditText)findViewById(R.id.editText3);
        // set the text
        text.setText(reminder);
        // for testing
        Log.i("inside details", "value " + reminder);
        SwitchCompat dateSwitch = (SwitchCompat)findViewById(R.id.switch2);
        dateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        addNumberPicker();

    }

    public void setDate(View view)
    {
        SwitchCompat temp = (SwitchCompat)view;
        System.out.println("Switch checked: " + temp.isChecked());

    }

    public void addNumberPicker()
    {
        NumberPicker numPicker = (NumberPicker)findViewById(R.id.monthPicker);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(6);
        numPicker.setDisplayedValues( new String[] { "January", "February", "March", "April", "May", "June", "July" } );
        numPicker.setValue(3);
        NumberPicker dates = (NumberPicker)findViewById(R.id.datePicker);
        dates.setMinValue(1);
        dates.setMaxValue(31);
        dates.setValue(15);
        NumberPicker years = (NumberPicker)findViewById(R.id.yearPicker);
        years.setMinValue(2000);
        years.setMaxValue(3000);
        years.setValue(2020);
    }

    @Override
    public void onBackPressed() {
        Intent intentWithResult = new Intent();
        String reminderText = "";
        EditText tempText = (EditText)findViewById(R.id.editText3);
        reminderText = tempText.getText().toString();
        intentWithResult.putExtra("reminder_text", reminderText );
        intentWithResult.putExtra("index", getIntent().getIntExtra("index", -1));
        setResult(1, intentWithResult);
        finish();
    }
}
