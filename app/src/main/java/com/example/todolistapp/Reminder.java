package com.example.todolistapp;

import java.util.Calendar;

public class Reminder {
    private String reminder;
    private String notes;
    private boolean remindAtLocation;
    private String location;
    private boolean remindOnDay;
    private int day;
    private int month;
    private int year;
    private boolean remindAtTime;
    private int hour;
    private int minute;
    private int amPm;
    private int repeat;
    private String priority;
    private int textId;
    private int UID;


    public Reminder(String text, int UID)
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
        hour = 0;
        minute = 0;
        amPm = 0;
        repeat = 1;
        priority = "";
        this.UID = UID;
    }

    public Reminder(String text, int day, int month, int year, boolean remindOnDay, boolean remindAtTime, int hour, int minute, int amPm, int UID, int repeat)
    {
        reminder = text;
        notes = "";
        remindAtLocation = false;
        location = "";
        this.remindOnDay = remindOnDay;
        this.day = day;
        this.month = month;
        this.year = year;
        this.remindAtTime = remindAtTime;
        this.hour = hour;
        this.minute = minute;
        this.amPm = amPm;
        this.repeat = repeat;
        priority = "";
        this.UID = UID;
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
        return "reminder:" + reminder + ";notes:" + notes + ";remindOnDay:" + remindOnDay + ";month:" + month + ";day:" + day + ";year:" + year + ";remindAtTime:" +
                remindAtTime + ";hour:" + hour + ";minute:" + minute + ";amPm:" + amPm + ";UID:" + UID + ";repeat:" + repeat + "\n";
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

    public void setRemindOnADay(boolean remindOnDay)
    {
        this.remindOnDay = remindOnDay;
    }

    public int getHour()
    {
        return hour;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAmPm()
    {
        return amPm;
    }

    public void setAmPm(int amPm)
    {
        this.amPm = amPm;
    }

    public boolean getRemindAtTime()
    {
        return remindAtTime;
    }

    public void setRemindAtTime(boolean remindAtTime)
    {
        this.remindAtTime = remindAtTime;
    }

    public int getUID()
    {
        return UID;
    }

    public void setUID(int UID)
    {
        this.UID = UID;
    }

    public void setRepeat(int repeat)
    {
        this.repeat = repeat;
    }

    public int getRepeat()
    {
        return repeat;
    }

}
