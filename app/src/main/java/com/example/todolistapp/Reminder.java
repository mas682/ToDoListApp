package com.example.todolistapp;

public class Reminder {
    private String reminder;
    private String notes;
    private boolean remindAtLocation;
    private String location;
    private boolean remindOnDay;
    private String day;
    private boolean remindAtTime;
    private String time;
    private String repeat;
    private String priority;


    public Reminder(String text)
    {
        reminder = text;
        notes = "";
        remindAtLocation = false;
        location = "";
        remindOnDay = false;
        day = "";
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
        return "reminder:" + reminder + ";notes:" + notes + ";\n";
    }
}
