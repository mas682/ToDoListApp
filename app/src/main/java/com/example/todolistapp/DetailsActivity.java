package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // design decision to not save changes unless user hits done or back button
        // if they close the app, results not saved
        setContentView(R.layout.activity_details);
        // get the intent that was sent over
        Intent intent = getIntent();
        // get the reminder text that was passed over
        String reminderText = intent.getStringExtra("reminder");
        // get the day
        int day = intent.getIntExtra("day", 1);
        // get the month
        int month = intent.getIntExtra("month", 0);
        // get the year
        int year = intent.getIntExtra("year", 2020);
        // get whether or not date set
        boolean remindOnDay = intent.getBooleanExtra("remindOnDay", false);
        // if a date was chosen, use this constructor
        if(remindOnDay) {
            reminder = new Reminder(reminderText, day, month, year, remindOnDay);
        }
        // if no date set, initialize to today
        else
        {
            reminder = new Reminder(reminderText);
        }
        // eventually:
        // if remidnOnDay
        // do this
            String dateString = formatDateAlarm(month, day, year);
            TextView dateText = (TextView)findViewById(R.id.alarmDateTextView);
            dateText.setText(dateString);
        // get the view for the reminder
        EditText text = (EditText)findViewById(R.id.editText3);
        // set the text
        text.setText(reminderText);
        // for testing
        Log.i("inside details", "value " + reminderText);
        SwitchCompat dateSwitch = (SwitchCompat)findViewById(R.id.switch2);
        dateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        addNumberPicker(day, month, year);

    }

    // left off here...this will be called when the text views are clicked
    // need to see what the current state is
    // if pickers visible, then set to not visible
    // if not visible set to visible
    // also change values in text views
    public void showDatePicker(View view)
    {
        NumberPicker paddingStartPicker = (NumberPicker)findViewById(R.id.paddingStartDatePicker);
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        NumberPicker datePicker = (NumberPicker)findViewById(R.id.datePicker);
        NumberPicker yearPicker = (NumberPicker)findViewById(R.id.yearPicker);
        NumberPicker paddingEndPicker = (NumberPicker)findViewById(R.id.paddingEndDatePicker);
        // you need to change the name of this in the xml file
        View divider = findViewById(R.id.divider8);
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        TextView alarmDateText = (TextView)findViewById(R.id.alarmDateTextView);
        //monthPicker.setVisibility();
        monthPicker.getVisibility();
        int visible = monthPicker.getVisibility();
        // if visible
        if(visible == 0)
        {
            paddingStartPicker.setVisibility(View.GONE);
            monthPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
            paddingEndPicker.setVisibility(View.GONE);
            // set this to a empty string
            String date = formatDateAlarm(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            ViewGroup.LayoutParams layoutParams = alarmDateText.getLayoutParams();
            layoutParams.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            alarmDateText.setLayoutParams(layoutParams);
            alarmDateText.setText(date);
            // this will need changed
            alarmText.setText("Alarm");
            // get the color black
            int colorWhite = Color.parseColor("#FFFFFF");
            alarmText.setTextColor(ColorStateList.valueOf(colorWhite));
            divider.setVisibility(View.GONE);
        }
        else
        {
            paddingStartPicker.setVisibility(View.VISIBLE);
            monthPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
            yearPicker.setVisibility(View.VISIBLE);
            paddingEndPicker.setVisibility(View.VISIBLE);
            alarmDateText.setText("");
            ViewGroup.LayoutParams layoutParams = alarmDateText.getLayoutParams();
            layoutParams.width = 1;
            alarmDateText.setLayoutParams(layoutParams);
            //alarmDateText.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            String date = formatDate(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            alarmText.setText(date);
            ViewGroup.LayoutParams layoutParams2 = alarmText.getLayoutParams();
            layoutParams2.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
           // alarmText.setLayoutParams(layoutParams2);
            //alarmText.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            // get the color colorAccent
            int colorAccent = Color.parseColor("#0078FF");
            alarmText.setTextColor(ColorStateList.valueOf(colorAccent));
            divider.setVisibility(View.VISIBLE);
        }
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
        reminder.setDay(newVal);
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        alarmText.setText(formatDate(monthPicker.getValue(), newVal, yearPicker.getValue()));
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
            reminder.setYear(newVal);
            TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
            alarmText.setText(formatDate(monthPicker.getValue(), datePicker.getValue(), newVal));
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
            // format the date as the year was changed
            reminder.setYear(newVal);
            TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
            alarmText.setText(formatDate(monthPicker.getValue(), datePicker.getValue(), newVal));
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
        // format the date as the year was changed
        reminder.setYear(newVal);
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        alarmText.setText(formatDate(monthPicker.getValue(), datePicker.getValue(), newVal));
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
        // format the date as the year was changed
        reminder.setMonth(newVal);
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        alarmText.setText(formatDate(newVal, datePicker.getValue(), yearPicker.getValue()));
    }

    public String formatDateAlarm(int month, int day, int year) {
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
            sdf.applyPattern("EEE, MM/d/yy");
            // get the date string
            stringDate = sdf.format(myDate);
            // for testing
            System.out.println(stringDate);
        } catch (ParseException e) {

        }
        return stringDate;
    }

    public String formatDate(int month, int day, int year)
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
        return stringDate;
        // here, would need to update the view to display the appropriate date
        // want to always keep date once set
        // originally want this set to the current date
        // but once set once, even if date not marked, set to the value
        // need to deal with storing somehow, can probably convert using the sdf like above
        // depending on boolean value, choose which view to show
    }

    public void addNumberPicker(int day, int month, int year)
    {
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues( new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" } );
        monthPicker.setValue(month);
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDays(picker, oldVal, newVal);
            }
        });
        NumberPicker dates = (NumberPicker)findViewById(R.id.datePicker);
        dates.setMinValue(1);
        dates.setMaxValue(30);
        dates.setValue(day);
        dates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateChanged(picker, oldVal, newVal);
            }
        });
        NumberPicker years = (NumberPicker)findViewById(R.id.yearPicker);
        years.setMinValue(2000);
        years.setMaxValue(3000);
        years.setValue(year);
        years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDaysYearChange(picker, oldVal, newVal);
            }
        });
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
