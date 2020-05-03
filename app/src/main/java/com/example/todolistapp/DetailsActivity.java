package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        int day = intent.getIntExtra("day", -1);
        // get the month
        int month = intent.getIntExtra("month", -1);
        // get the year
        int year = intent.getIntExtra("year", -1);
        // if day is -1, no date has been set for this reminder yet, so use the current date
        if(day == -1)
        {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        // get whether or not date set
        boolean remindOnDay = intent.getBooleanExtra("remindOnDay", false);
        // create the reminder object
        reminder = new Reminder(reminderText, day, month, year, remindOnDay);
        // get the remindOnDay switch
        SwitchCompat daySwitch = (SwitchCompat)findViewById(R.id.daySwitch);
        SwitchCompat timeSwitch = (SwitchCompat)findViewById(R.id.timeSwitch);
        // if remind on a day boolean true, set the switch as checked
        if(remindOnDay)
        {
            daySwitch.setChecked(true);
        }
        // call this outside the if so that if the switch is not checked, the views that
        // should not be visible are not visible
        setDate(daySwitch);
        // get the view for the reminder
        EditText text = (EditText)findViewById(R.id.editText3);
        // set the text
        text.setText(reminderText);
        // initialize the values in the number picker so this is not called every time
        // a user changes their mind to switch the date
        initializeNumberPicker();
        initializeTimePicker();
        // add a listener to the remindOnDay switch
        daySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        // add a listener to the remind at a time switch
        timeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)  {
                setTime(v);
            }
        });
        // add a listenter to the remindAtTime switxh
        timeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setTime(v);
            }
        });
    }

    // this method is called when the field holding the Alarm text is touched
    // this is also called when the picker is visible and the user wants to close it
    public void showDatePicker(View view)
    {
        NumberPicker paddingStartPicker = (NumberPicker)findViewById(R.id.paddingStartDatePicker);
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        NumberPicker datePicker = (NumberPicker)findViewById(R.id.datePicker);
        NumberPicker yearPicker = (NumberPicker)findViewById(R.id.yearPicker);
        NumberPicker paddingEndPicker = (NumberPicker)findViewById(R.id.paddingEndDatePicker);
        View divider = findViewById(R.id.pickerTimeSwitchDivider);
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        TextView alarmDateText = (TextView)findViewById(R.id.alarmDateTextView);
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
            // set the values for the date picker
            addDatePicker(reminder.getDay(), reminder.getMonth(), reminder.getYear());
            paddingStartPicker.setVisibility(View.VISIBLE);
            monthPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
            yearPicker.setVisibility(View.VISIBLE);
            paddingEndPicker.setVisibility(View.VISIBLE);
            alarmDateText.setText("");
            // change the width to 1 so the full date can be visible
            ViewGroup.LayoutParams layoutParams = alarmDateText.getLayoutParams();
            layoutParams.width = 1;
            alarmDateText.setLayoutParams(layoutParams);
            String date = formatDate(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            alarmText.setText(date);
            // get the color colorAccent
            int colorAccent = Color.parseColor("#0078FF");
            alarmText.setTextColor(ColorStateList.valueOf(colorAccent));
            divider.setVisibility(View.VISIBLE);
        }
    }

    // this method is called when the field holding the Time text is touched
    // this is also called when the picker is visible and the user wants to close it
    public void showTimePicker(View view)
    {
        NumberPicker paddingStartPicker = (NumberPicker)findViewById(R.id.paddingStartTimePicker);
        NumberPicker hourPicker = (NumberPicker)findViewById(R.id.hourPicker);
        NumberPicker minutePicker = (NumberPicker)findViewById(R.id.minutePicker);
        NumberPicker amPmPicker = (NumberPicker)findViewById(R.id.amPmPicker);
        NumberPicker paddingEndPicker = (NumberPicker)findViewById(R.id.paddingEndTimePicker);
        View divider = findViewById(R.id.timeSwitchTextDivider);
        TextView timeText = (TextView)findViewById(R.id.timeTextView);
        TextView timeSetText = (TextView)findViewById(R.id.timeSetTextView);
        int visible = hourPicker.getVisibility();
        // if visible
        if(visible == 0)
        {
            paddingStartPicker.setVisibility(View.GONE);
            hourPicker.setVisibility(View.GONE);
            minutePicker.setVisibility(View.GONE);
            amPmPicker.setVisibility(View.GONE);
            paddingEndPicker.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            // set this to a empty string
            String time = "Test example";
                    //formatDateAlarm(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            ViewGroup.LayoutParams layoutParams = timeSetText.getLayoutParams();
            layoutParams.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            timeSetText.setLayoutParams(layoutParams);
            timeSetText.setText(time);
            // this will need changed
            timeText.setText("Time");
            // get the color black
            int colorWhite = Color.parseColor("#FFFFFF");
            timeText.setTextColor(ColorStateList.valueOf(colorWhite));
        }
        else
        {
            // set the values for the date picker
            //addDatePicker(reminder.getDay(), reminder.getMonth(), reminder.getYear());
            paddingStartPicker.setVisibility(View.VISIBLE);
            hourPicker.setVisibility(View.VISIBLE);
            minutePicker.setVisibility(View.VISIBLE);
            amPmPicker.setVisibility(View.VISIBLE);
            paddingEndPicker.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            timeSetText.setText("");
            // change the width to 1 so the full date can be visible
            ViewGroup.LayoutParams layoutParams = timeSetText.getLayoutParams();
            layoutParams.width = 1;
            timeSetText.setLayoutParams(layoutParams);
            String time = "test";
                    //formatDate(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            timeText.setText(time);
            // get the color colorAccent
            int colorAccent = Color.parseColor("#0078FF");
            timeText.setTextColor(ColorStateList.valueOf(colorAccent));
        }
    }


    // then deal with actual notifications or do the time part or time
    // the time part should be pretty similar to day part
    public void setTime(View view)
    {
        SwitchCompat timeSwitch = (SwitchCompat)view;
        TextView timeText = (TextView)findViewById(R.id.timeTextView);
        TextView timeSetText = (TextView)findViewById(R.id.timeSetTextView);
        View timeSwitchTextDivider = findViewById(R.id.timeSwitchTextDivider);
        View timeTextPickerDivider = findViewById(R.id.timeTextPickerDivider);

        if(timeSwitch.isChecked())
        {
            timeText.setVisibility(View.VISIBLE);
            timeSetText.setVisibility(View.VISIBLE);
            timeSwitchTextDivider.setVisibility(View.VISIBLE);
            timeTextPickerDivider.setVisibility(View.VISIBLE);
            // if this is checked, remindOnDay should be true
            // this will be:
            // reminder.setTimeReminder(true);
            // update the time string being displayed
            String timeString = "test";
            timeSetText.setText(timeString);
        }
        else
        {
            NumberPicker hourPicker = (NumberPicker)findViewById(R.id.hourPicker);
            if(hourPicker.getVisibility() == View.VISIBLE)
            {
                showTimePicker(null);
            }
            timeText.setVisibility(View.GONE);
            timeSetText.setVisibility(View.GONE);
            timeSwitchTextDivider.setVisibility(View.GONE);
            timeTextPickerDivider.setVisibility(View.GONE);
            // eventually
            //reminder.setTimeReminder(false);
        }
    }

    // this method is called when the date is changed
    // this simply updates the formatted date
    public void timeDateChanged(NumberPicker picker, int oldVal, int newVal)
    {
        picker.setMaxValue(picker.getMaxValue() + 1);
    }

    // this needs to display the fields under the remind me on a day switch
    // this will also need to close the date picker/time picker if open when this is switched off
    public void setDate(View view)
    {
        SwitchCompat remindSwitch = (SwitchCompat)view;
        TextView alarmText = (TextView)findViewById(R.id.alarmTextView);
        TextView alarmDateText = (TextView)findViewById(R.id.alarmDateTextView);
        View daySwitchTextDivider = findViewById(R.id.daySwitchTextDivider);
        View textNumPickerDivider = findViewById(R.id.textNumPickerDivider);
        SwitchCompat timeSwitch = (SwitchCompat)findViewById(R.id.timeSwitch);
        if(remindSwitch.isChecked())
        {
            alarmText.setVisibility(View.VISIBLE);
            alarmDateText.setVisibility(View.VISIBLE);
            daySwitchTextDivider.setVisibility(View.VISIBLE);
            textNumPickerDivider.setVisibility(View.VISIBLE);
            timeSwitch.setVisibility(View.VISIBLE);
            // if this is checked, remindOnDay should be true
            reminder.setRemindOnADay(true);
            // update the date string being displayed to the current date
            String dateString = formatDateAlarm(reminder.getMonth(), reminder.getDay(), reminder.getYear());
            alarmDateText.setText(dateString);
        }
        else
        {
            NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
            if(monthPicker.getVisibility() == View.VISIBLE)
            {
                showDatePicker(null);
            }
            alarmText.setVisibility(View.GONE);
            alarmDateText.setVisibility(View.GONE);
            daySwitchTextDivider.setVisibility(View.GONE);
            textNumPickerDivider.setVisibility(View.GONE);
            timeSwitch.setVisibility(View.GONE);
            reminder.setRemindOnADay(false);
        }
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
        reminder.setDay(datePicker.getValue());
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
                System.out.println("Changing date to 30 from: " + date);
            }
        }
        // update the string displaying the date as the month was changed
        // format the date as the year was changed
        reminder.setMonth(newVal);
        reminder.setDay(datePicker.getValue());
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
        } catch(ParseException e)
        {

        }
        return stringDate;
    }

    // used to set the values of the date picker
    public void addDatePicker(int day, int month, int year)
    {
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        monthPicker.setValue(month);

        NumberPicker dates = (NumberPicker)findViewById(R.id.datePicker);
        dates.setValue(day);

        NumberPicker years = (NumberPicker)findViewById(R.id.yearPicker);
        years.setValue(year);
    }

    // used to set the values of the date picker
    public void addTimePicker(int hour, int minute, int amPM)
    {
        NumberPicker hourPicker = (NumberPicker)findViewById(R.id.hourPicker);
        hourPicker.setValue(hour);

        NumberPicker minutePicker = (NumberPicker)findViewById(R.id.minutePicker);
        minutePicker.setValue(minute);

        NumberPicker amPmPicker = (NumberPicker)findViewById(R.id.amPmPicker);
        amPmPicker.setValue(amPM);
    }

    public void initializeNumberPicker()
    {
        NumberPicker monthPicker = (NumberPicker)findViewById(R.id.monthPicker);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues( new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" } );
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDays(picker, oldVal, newVal);
            }
        });
        NumberPicker dates = (NumberPicker)findViewById(R.id.datePicker);
        dates.setMinValue(1);
        dates.setMaxValue(30);
        dates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateChanged(picker, oldVal, newVal);
            }
        });
        NumberPicker years = (NumberPicker)findViewById(R.id.yearPicker);
        years.setMinValue(2000);
        years.setMaxValue(3000);
        years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDaysYearChange(picker, oldVal, newVal);
            }
        });
    }

    public void initializeTimePicker()
    {
        NumberPicker hourPicker = (NumberPicker)findViewById(R.id.hourPicker);
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        //hourPicker.setDisplayedValues( new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" } );
        /*hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                set(picker, oldVal, newVal);
            }
        });
        */
        NumberPicker minutePicker = (NumberPicker)findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setDisplayedValues( new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
        "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
        "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55",
        "56", "57", "58", "59"} );
        /*
        dates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateChanged(picker, oldVal, newVal);
            }
        });
         */
        NumberPicker amPmPicker = (NumberPicker)findViewById(R.id.amPmPicker);
        amPmPicker.setMinValue(0);
        amPmPicker.setMaxValue(1);
        /*
        years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setDaysYearChange(picker, oldVal, newVal);
            }
        });
         */
        amPmPicker.setDisplayedValues( new String[] {"AM", "PM"});
    }

    @Override
    public void onBackPressed() {
        Intent intentWithResult = new Intent();
        String reminderText = "";
        EditText tempText = (EditText)findViewById(R.id.editText3);
        reminderText = tempText.getText().toString();
        intentWithResult.putExtra("reminder_text", reminderText );
        intentWithResult.putExtra("index", getIntent().getIntExtra("index", -1));
        intentWithResult.putExtra("remindOnDay", reminder.getRemindOnADay());
        if(!reminder.getRemindOnADay())
        {
            reminder.setMonth(-1);
            reminder.setDay(-1);
            reminder.setYear(-1);
        }
        intentWithResult.putExtra("month", reminder.getMonth());
        intentWithResult.putExtra("day", reminder.getDay());
        intentWithResult.putExtra("year", reminder.getYear());
        setResult(1, intentWithResult);
        finish();
    }
}
