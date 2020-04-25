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
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int RESULT_CODE = 0;
    private ArrayList<Reminder> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* TODO
            left off making it so that you could pass the text of a reminder to the details page
            by getting it out of the arraylist
            things to fix/handle:
            2. may want to use other threads for some of this stuff?
            3. start messing with date/time notifications
            4. then deal with location
         */
        setContentView(R.layout.activity_main);
        // this currently only works when changing the rotation of the screen
        /*
        if ((savedInstanceState != null) && (savedInstanceState.getString("reminder1") != null)) {
            text = savedInstanceState.getString("reminder1");
            Log.i(" On Create", "text value: " + text);
            t.setText(text);
        }
         */
        boolean opened = openRemindersFile();
        if(!opened)
        {
            addReminder("");
        }
        // consider using asynctask to load data?
        int count = 0;
        while(count < reminders.size())
        {
            addNewReminderElement(reminders.get(count).getReminder(), count);
            count++;
        }
    }

    // this method reads a file which contains all the reminder data
    // returns true if the file was found and successfully read
    // returns false otherwise
    public boolean openRemindersFile()
    {
        // file name
        String file = "reminders";
        // boolean to return
        boolean success = false;

        try {
            FileInputStream fis = MainActivity.this.openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                addReminder(line);
                i++;
            }
            success = true;
        } catch (FileNotFoundException e)
        {
            System.out.println("No reminders found");
            success = false;
        } catch (IOException e)
        {
            System.out.println("Error reading the file");
            success = false;
        }
        return success;
    }

    public void addReminder(String reminderString)
    {
        // tokenize the line in the form of: reminder:reminder1;notes:notes1;
        String tokens[] = reminderString.split(";");
        // string to temporarily hold the reminder text
        String reminder = "";
        // string to temporarily hold the notes
        String notes = "";
        // array to temporarily hold a value associated with corresponding values
        String values[];
        for(int i = 0; i < tokens.length; i++)
        {
            values = tokens[i].split(":");
            if(i == 0)
            {
                if(values.length > 1)
                {
                    reminder = values[1];
                }
                else {
                    reminder = "";
                }
            }
            else if(i == 1)
            {
                if(values.length > 1)
                {
                    notes = values[1];
                }
                else
                {
                    notes = "";
                }
            }
        }
        // create a new reminder
        Reminder tempReminder = new Reminder(reminder);
        // eventually want to set notes here

        if(reminders == null)
        {
            // initialize reminders if not done yet
            reminders = new ArrayList<Reminder>();
        }
        // add the reminder to the reminder array list
        reminders.add(tempReminder);
    }

    public boolean saveReminders()
    {
        if(reminders == null)
        {
            return false;
        }
        if(reminders.size() < 1)
        {
            return false;
        }
        // get the updated values before saving them
        // do not think this is needed
        //updateReminders();
        boolean success = false;
        String file = "reminders";
        String data = "";
        // combine the data into one string
        for(int i = 0; i < reminders.size(); i++)
        {
            data = data + reminders.get(i).toString();
        }
        try {
            FileOutputStream fOut = MainActivity.this.openFileOutput(file, Context.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
            // the directory is /data/user/0/com.example.todolistapp/files
            //Log.i(" OnCreate", "succeeded " + getFilesDir());
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /*
        method used to retrieve updated values from the edit text views
        and update the remidners array list to the new values
     */
    public void updateReminders()
    {
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.activity_main);
        int numViews = parent.getChildCount();
        // starting index of the first edit text view
        int i = 3;
        int reminderCount = 0;
        Reminder tempReminder;
        EditText textView;
        // while there are views still to check
        // added reminder count < reminders size as a fail safe
        while(i < numViews && reminderCount < reminders.size())
        {
            // get the current reminders text field
            textView = (EditText)parent.getChildAt(i);
            // get the reminder object associated with this view
            tempReminder = reminders.get(reminderCount);
            // update the text of the reminder
            tempReminder.setReminder(textView.getText().toString());
            i += 4;
            reminderCount += 1;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        //state.putSerializable("starttime", startTime);
        //EditText reminder = findViewById(R.id.editText);
        //String text = reminder.getText().toString();
        Log.i("on save!!!!", "saving");
        //state.putString("reminder1", text);
        saveReminders();
    }

    private void fail(String sdcard_not_available) {
    }

    public void setDetails(View view) {
        // get the intent to pass data to the details activity
        Intent intent = new Intent(this, DetailsActivity.class);
        // get the index into the Reminders ArrayList
        int index = Integer.parseInt((String)view.getTag());
        // pass the reminder text
        intent.putExtra("reminder", reminders.get(index).getReminder());
        // pass the index into the Reminders array as this is needed when returning
        intent.putExtra("index", index);
        // call the activity with the intent
        startActivityForResult(intent, RESULT_CODE);
    }

    // save the reminders when the back button is pushed
    @Override
    public void onBackPressed() {
        saveReminders();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // get the value of the reminder field that is returned
        String messageReturned = data.getStringExtra("reminder_text");
        // get the index into the Reminders array
        int index = data.getIntExtra("index", -1);
        if(index != -1)
        {
            // update the reminder in the array
            reminders.get(index).setReminder(messageReturned);
            // get the view id from the reminder
            EditText tempView = (EditText)findViewById(reminders.get(index).getTextId());
            // update the view
            tempView.setText(messageReturned);
        }
        // for testing
        System.out.println("Result code = " + resultCode);
    }

    /** Called when the user taps the Send button */
    public void addNewReminderElement(View view) {
        // add a new reminder to the array
        addReminder("");
        int index = reminders.size() - 1;
        // Get the activity main constraint layout
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        int numViewElems = parentLayout.getChildCount();
        // get the last added divider view's index
        int lastDivider = numViewElems - 1;
        // get the last added divider view
        View lastDiv = parentLayout.getChildAt(lastDivider);
        // create a new EditText view
        EditText textView = new EditText(MainActivity.this);
        // set the EditText view's attirbutes
        setEditTextValues(textView, index);
        // create a new RadioButton view
        RadioButton radioButton = new RadioButton(MainActivity.this);
        // set the RadioButton view's attributes
        setRadioButtonValues(radioButton);
        // create a new FloatingAction view
        FloatingActionButton infoButton = new FloatingActionButton(MainActivity.this);
        // set the FloatingActionButton view's attributes
        setInfoButtonValues(infoButton, index);
        // create a new View for the divider
        View divider = new View(MainActivity.this);
        // set the View's attributes
        setDividerViewValues(divider);
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

    /*
        This method adds a reminder with a string.
        This is called by onCreate to initialize the view.
        At some point, should probably break this method down so you don't need 2 copies of it.
    */
    public void addNewReminderElement(String text, int index) {
        // Get the activity main constraint layout
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        int numViewElems = parentLayout.getChildCount();
        // get the last added divider view's index
        int lastDivider;
        // get the last added divider view id
        View lastDiv;
        // get the last elements Id
        int lastViewId;
        // only do this if the ConstraintLayout has reminder elements already
        if(numViewElems > 2)
        {
            // get the last added divider view's index
            lastDivider = numViewElems - 1;
            // get the last added divider view
            lastDiv = parentLayout.getChildAt(lastDivider);
            lastViewId = lastDiv.getId();
        }
        else
        {
            // if this is the first view being added, just use the parents id
            lastViewId = parentLayout.getId();
        }
        // create a new EditText view
        EditText textView = new EditText(MainActivity.this);
        // set the EditText view's attributes
        setEditTextValues(textView, index);
        // create a new RadioButton view
        RadioButton radioButton = new RadioButton(MainActivity.this);
        // set the RadioButton view's attributes
        setRadioButtonValues(radioButton);
        // create a new FloatingAction view
        FloatingActionButton infoButton = new FloatingActionButton(MainActivity.this);
        // set the FloatingActionButton view's attributes
        setInfoButtonValues(infoButton, index);
        // create a new View for the divider
        View divider = new View(MainActivity.this);
        // set the View's attributes
        setDividerViewValues(divider);
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
        // radio button constraints
        set.connect(radioButton.getId(), ConstraintSet.START, parentLayout.getId(), ConstraintSet.START, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // info button constraints
        set.connect(infoButton.getId(), ConstraintSet.END, parentLayout.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // divider constraints
        set.connect(divider.getId(), ConstraintSet.TOP, textView.getId(), ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        set.connect(divider.getId(), ConstraintSet.START, radioButton.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()));
        set.connect(divider.getId(), ConstraintSet.END, parentLayout.getId(), ConstraintSet.END, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics()));
        // these constraint values depend on if the lastViewId is the parent(the else case) or if they are the last divider(the if case)
        if(numViewElems > 2)
        {
            // info button constraints
            set.connect(infoButton.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14,
                    getResources().getDisplayMetrics()));
            // radio button constraints
            set.connect(radioButton.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                    getResources().getDisplayMetrics()));
            // text view constraints
            set.connect(textView.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.BOTTOM, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                    getResources().getDisplayMetrics()));
        }
        else
        {
            // info button constraints
            set.connect(infoButton.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.TOP, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14,
                    getResources().getDisplayMetrics()));
            // radio button constraints
            set.connect(radioButton.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.TOP, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                    getResources().getDisplayMetrics()));
            // text view constraints
            set.connect(textView.getId(), ConstraintSet.TOP, lastViewId, ConstraintSet.TOP, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                    getResources().getDisplayMetrics()));
        }
        set.applyTo(parentLayout);
    }

    /*
        This method will set set up a View's attributes.
        The divider argument is the view to be changed.
    */
    public void setDividerViewValues(View divider)
    {
        // get a Id for the new divider
        divider.setId(View.generateViewId());
        // set the color of the divider
        divider.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGray));
        // set the height and width of the divider
        divider.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                getResources().getDisplayMetrics())));
    }

    /*
        This method will set set up a FloatingActionButton view's attributes.
        The infoButton argument is the view to be changed.
        The index is the index into the arrayList for the reminders.
    */
    public void setInfoButtonValues(FloatingActionButton infoButton, int index)
    {
        // get a Id for the new info button
        infoButton.setId(View.generateViewId());
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
        // set the tag to the index into the reminders arraylist
        infoButton.setTag(Integer.toString(index));
        // set the function to call when clicked
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setDetails(v);
            }
        });
    }

    /*
        This method will set set up a RadioButton view's attributes.
        The radioButton argument is the view to be changed.
    */
    public void setRadioButtonValues(RadioButton radioButton)
    {
        // get a Id for the new radio view
        radioButton.setId(View.generateViewId());
        // set the radio buttons width and height
        radioButton.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // get the value of the color accent
        int colorAccent = Color.parseColor("#0078FF");
        //set the color of the button
        radioButton.setButtonTintList(ColorStateList.valueOf(colorAccent));
        // set the size of the button
        radioButton.setScaleX((float)1.5);
        radioButton.setScaleY((float)1.5);
    }

    /*
        This method will set set up a EditText view's attributes.
        The textView argument is the view to be changed.
        The index is the index into the arrayList for the reminders.
    */
    public void setEditTextValues(EditText textView, int index)
    {
        // get a Id for the new text view
        textView.setId(View.generateViewId());
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
        // set the text to the passed string
        textView.setText(reminders.get(index).getReminder());
        textView.addTextChangedListener(new TextWatcherExtended(index) {
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("Text", s.toString());
                reminders.get(this.getIndex()).setReminder(s.toString());
            }
        });
        // set the id for the text element in the reminder array
        // this is done to make it easier to update the textView on changes
        reminders.get(index).setTextId(textView.getId());
    }
}
