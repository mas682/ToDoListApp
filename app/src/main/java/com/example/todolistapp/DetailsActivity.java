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
