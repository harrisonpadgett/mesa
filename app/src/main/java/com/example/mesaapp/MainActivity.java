package com.example.mesaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import java.util.Calendar;
import android.widget.Button;
import android.widget.Toast;

import com.example.mesaapp.calendarparts.Event;
import com.example.mesaapp.calendarparts.WeekViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button eventButton;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference eventsDBReference = firebaseDatabase.getReference().child("Events");
    String id = eventsDBReference.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCreatedEvents();
        setContentView(R.layout.activity_main);



        eventButton = findViewById(R.id.viewEventsButton);

        // Listen for Today's Event presses
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WeekViewActivity.class));

            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Functionality of navigation menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.play:
                        startActivity(new Intent(getApplicationContext(), Play.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), WeekViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.log:
                        startActivity(new Intent(getApplicationContext(), Log.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);

                int timeInMins;

                // Check each current event for today and notify
                for(int i = 0; i < Event.eventsList.size(); i++)
                {
                    timeInMins = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE);

                    if(Event.eventsList.get(i).getDate().equals(LocalDate.now()) && checkTwoTimes(Event.eventsList.get(i).getTime(), Integer.toString(timeInMins)) && Event.eventsList.get(i).getNotifyCheck().equals("No"))
                    {
                        // Notify them of the reminder
                        System.out.println("Worked");
                    }


                    //System.out.println(Event.eventsList.get(i).getName());
                    //System.out.println(Event.eventsList.get(i).getTime());
                    //System.out.println(checkTwoTimes(Event.eventsList.get(i).getTime(), Integer.toString(timeInMins)));
                }

            }
        }, delay);
        super.onResume();
    }


    private boolean checkTwoTimes(String time, String secondTime) {

        if(time.contains("PM"))
        {
            time = (12 + Integer.parseInt(time.substring(0, time.indexOf(":")))) + time.substring(time.indexOf(":"));
        }


        int timeMins = (60 * Integer.parseInt(time.substring(0, time.indexOf(":")))) + Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(" ")));

        //System.out.println(timeMins + " and " + secondTime);

        if(timeMins - Integer.parseInt(secondTime) <= 5 && timeMins - Integer.parseInt(secondTime) >= 0)
        {
            return true;
        }
        return false;

    }


    public void setCreatedEvents()
    {
        // Get already created events and make them appear
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String currentEventName = ds.child("Name").getValue(String.class);
                    String currentTimeName = ds.child("Time").getValue(String.class);
                    String currentDate = ds.child("Date").getValue(String.class);
                    String notifyCheck = ds.child("Sent Notification").getValue(String.class);

                    LocalDate localDate = LocalDate.parse(currentDate);

                    // String timeName = time.getText().toString();
                    Event newEvent = new Event(currentEventName, localDate, currentTimeName, notifyCheck);

                    int count = 0;
                    for(Event e : Event.eventsList)
                    {
                        if(e.getName().equals(newEvent.getName()) && e.getTime().equals(newEvent.getTime()))
                        {
                            count++;
                        }
                    }
                    if(count == 0){
                        Event.eventsList.add(newEvent);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        eventsDBReference.addListenerForSingleValueEvent(eventListener);
    }

}
