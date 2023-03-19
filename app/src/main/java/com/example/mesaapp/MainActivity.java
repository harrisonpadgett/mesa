package com.example.mesaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mesaapp.calendarparts.Event;
import com.example.mesaapp.calendarparts.WeekViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private Button eventButton;

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

                    LocalDate localDate = LocalDate.parse(currentDate);

                    // String timeName = time.getText().toString();
                    Event newEvent = new Event(currentEventName, localDate, currentTimeName);

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
