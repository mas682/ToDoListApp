package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    // this method is called when the date is changed
    // this simply updates the formatted date
    public void dateChanged(NumberPicker picker, int oldVal, int newVal)
    {
        // get the month picker to get it's value
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        // get the year picker to get it's value
        NumberPicker yearPicker = (NumberPicker)findViewById(R.id.yearPicker);
        formatDate(monthPicker.getValue(), newVal, yearPicker.getValue());
    }

    // this method deals with changes to the year when it is a leap year to change
    // the values listed as dates in February
    public void setDaysYearChange(NumberPicker picker, int oldVal, int newVal)
    {
        // get the date picker
        NumberPicker datePicker = (NumberPicker)findViewById(R.id.datePicker);
        // get the month picker
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        // if the old month was not a leap year and the new year is not a leap year, return
        if(oldVal % 4 != 0 && newVal % 4 != 0)
        {
            // format the date as the year was changed
            formatDate(monthPicker.getValue(), datePicker.getValue(), newVal);
            return;
        }
        // get the date
        int date = datePicker.getValue();
        // get the month
        int month = monthPicker.getValue();
        // if the month is not February, simply return
        if(month != 1)
        {
            // format the date as the year was changed
            formatDate(monthPicker.getValue(), datePicker.getValue(), newVal);
            return;
        }
        // if the old year was a leap year and the new year is not
        if(oldVal % 4 == 0 && newVal % 4 != 0)
        {
            datePicker.setMaxValue(28);
            if(date > 28)
            {
                datePicker.setValue(28);
            }
        }
        // if the old year was not a leap year and the new year is
        else
        {
            datePicker.setMaxValue(29);
            if(date > 29)
            {
                datePicker.setValue(29);
            }
        }
        // format the date as the year was changed
        formatDate(monthPicker.getValue(), datePicker.getValue(), newVal);
    }

    // this method sets the numbers for the date picker on a months change
    public void setDays(NumberPicker picker, int oldVal, int newVal) {
        // get the date picker
        NumberPicker datePicker = (NumberPicker)findViewById(R.id.datePicker);
        // get the year picker
        NumberPicker yearPicker = (NumberPicker)findViewById(R.id.yearPicker);
        int date = datePicker.getValue();
        // January, March, May, July, August, October, December
        if(newVal == 0 || newVal == 2 || newVal == 4|| newVal == 6|| newVal == 7|| newVal == 9|| newVal == 11)
        {
            datePicker.setMaxValue(31);
        }
        // Febrary
        else if(newVal == 1)
        {
            int year = yearPicker.getValue();
            if(year % 4 != 0)
            {
                datePicker.setMaxValue(28);
                if(date > 28)
                {
                    datePicker.setValue(28);
                }
            }
            // leap year
            else
            {
                datePicker.setMaxValue(29);
                if(date > 29)
                {
                    datePicker.setValue(29);
                }
            }
        }
        // all other months
        else
        {
            datePicker.setMaxValue(30);
            if(date > 30)
            {
                datePicker.setValue(30);
            }
        }
        // update the string displaying the date as the month was changed
        formatDate(newVal, datePicker.getValue(), yearPicker.getValue());
    }

    public void formatDate(int month, int day, int year)
    {
        // specify the format the date is in
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH);
        // increment the month by 1 as it will be off by 1 otherwise
        month = month + 1;
        // hold the formatted string
        String stringDate = "";
        try {
            // set the date
            Date myDate = sdf.parse(month + "/" + day + "/" + year);
            // set the pattern expected back
            sdf.applyPattern("EEEE, MMMM d, yyyy");
            // get the date string
            stringDate = sdf.format(myDate);
            // for testing
            System.out.println(stringDate);
        } catch(ParseException e)
        {

        }
        // here, would need to update the view to display the appropriate date
        // want to always keep date once set
        // originally want this set to the current date
        // but once set once, even if date not marked, set to the value
        // need to deal with storing somehow, can probably convert using the sdf like above
        // depending on boolean value, choose which view to show
    }

    public void addNumberPicker()
    {
        NumberPicker numPicker = (NumberPicker)findViewById(R.id.monthPicker);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(11);
        numPicker.setDisplayedValues( new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" } );
        numPicker.setValue(3);
        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDays(picker, oldVal, newVal);
            }
        });
        NumberPicker dates = (NumberPicker)findViewById(R.id.datePicker);
        dates.setMinValue(1);
        dates.setMaxValue(30);
        dates.setValue(28);
        dates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateChanged(picker, oldVal, newVal);
            }
        });
        NumberPicker years = (NumberPicker)findViewById(R.id.yearPicker);
        years.setMinValue(2000);
        years.setMaxValue(3000);
        years.setValue(2020);
        years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDaysYearChange(picker, oldVal, newVal);
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 28);
        System.out.println(calendar.get(calendar.DATE));

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
