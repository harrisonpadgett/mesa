package com.example.mesaapp.calendarparts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesaapp.R;
import java.util.Calendar;


public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    private Calendar calendar;
    private Button saveButton;
    private String format = "";
    private String timeName;

    private TimePicker eventTimeInput;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        eventTimeInput = (TimePicker) findViewById(R.id.eventTimeInput);
        //time = (TextView) findViewById(R.id.eventTimeTV);


        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));


        // Listen for save button clicks
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WeekViewActivity.class));
                //overridePendingTransition(0,0);


            }
        });


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


}




