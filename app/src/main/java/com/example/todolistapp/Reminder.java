package com.example.todolistapp;

import java.util.Calendar;

public class Reminder {
    private String reminder;
    private String notes;
    private boolean remindAtLocation;
    private String location;
    private boolean remindOnDay;
    //private String day;
    private int day;
    private int month;
    private int year;
    private boolean remindAtTime;
    private String time;
    private String repeat;
    private String priority;
    private int textId;


    public Reminder(String text)
    {
        Calendar calendar = Calendar.getInstance();
        reminder = text;
        notes = "";
        remindAtLocation = false;
        location = "";
        remindOnDay = false;
        // initialize to the current date
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        System.out.println(month);
        year = calendar.get(Calendar.YEAR);
        remindAtTime = false;
        time = "";
        repeat = "";
        priority = "";
    }

    public Reminder(String text, int day, int month, int year, boolean remindOnDay)
    {
        reminder = text;
        notes = "";
        remindAtLocation = false;
        location = "";
        remindOnDay = false;
        // initialize to the current date
        this.day = day;
        this.month = month;
        this.year = year;
        remindAtTime = false;
        time = "";
        repeat = "";
        priority = "";
    }

    public String getReminder()
    {

        return reminder;
    }

    public void setReminder(String text)
    {
        reminder = text;
    }

    public String toString()
    {
        // this needs updates, and then will have to update the main page as well
        return "reminder:" + reminder + ";notes:" + notes + ";\n";
    }

    public int getTextId()
    {
        return textId;
    }

    public void setTextId(int id)
    {
        textId = id;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public boolean getRemindOnADay()
    {
        return remindOnDay;
    }

    public void setRemondOnADay(boolean remindOnDay)
    {
        this.remindOnDay = remindOnDay;
    }

}
