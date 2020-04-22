package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
