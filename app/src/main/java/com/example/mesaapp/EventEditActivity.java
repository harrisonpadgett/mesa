package com.example.mesaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesaapp.R;
import java.util.Calendar;

import java.time.LocalTime;

import com.example.mesaapp.calendarparts.CalendarUtils;
import com.example.mesaapp.calendarparts.Event;
import com.example.mesaapp.calendarparts.WeekViewActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    private Calendar calendar;
    private Button saveButton;
    private String format = "";
    private String timeName;

    private TimePicker eventTimeInput;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference eventsDBReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        eventTimeInput = (TimePicker) findViewById(R.id.eventTimeInput);
        //time = (TextView) findViewById(R.id.eventTimeTV);


        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));





    }

    public void showTime(int hour, String min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeName = hour + ":" + min + " " + format;

        //time.setText(new StringBuilder().append(hour).append(":").append(min).append(" ").append(format));
    }



    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameInput);
        eventDateTV = findViewById(R.id.eventDateTV);
        //time = findViewById(R.id.eventTimeTV);
        eventTimeInput = (TimePicker) findViewById(R.id.eventTimeInput);
    }

    public void saveEventAction(View view)
    {
        Log.d("D","SAVE EVENT ACTION WAS CALLED");
        System.out.println("SAVE EVENT ACTION WAS CALLED");
        int hour = eventTimeInput.getHour();
        int intMin = eventTimeInput.getMinute();
        String min = String.valueOf(intMin);

        if (intMin < 10)
        {
            min = "0" + min;
        }



        showTime(hour, min);

        String eventName = eventNameET.getText().toString();
        String date = CalendarUtils.selectedDate.toString();

        if (eventName.equals(""))
        {
            eventName = "Event";
        }
        // String timeName = time.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, timeName);
        Event.eventsList.add(newEvent);

        // Insert new event into Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        eventsDBReference = firebaseDatabase.getReference().child("Events");
        String id = eventsDBReference.push().getKey();



        eventsDBReference.child(id).child("Name").setValue(eventName);
        eventsDBReference.child(id).child("Time").setValue(timeName);
        eventsDBReference.child(id).child("Date").setValue(date);

        finish();

    }


}




