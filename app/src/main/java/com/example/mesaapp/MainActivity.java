package com.example.mesaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
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
                        int mins = calcDifference(Event.eventsList.get(i).getTime(), Integer.toString(timeInMins));
                        createNotif(Event.eventsList.get(i).getName() + " in " + mins + " minute(s)!", "Event Reminder");
                        Event.eventsList.get(i).setNotifyCheck("Yes");
                        setCreatedEvents();



                    }

                }

            }
        }, delay);
        super.onResume();
    }


    private void createNotif(String message, String title)
    {
        String id = "my_channel_id_01";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if(channel == null)
            {
                channel = new NotificationChannel(id, "Channel Title", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[Channel description]");
                channel.enableVibration(true);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        builder.setContentIntent(contentIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1, builder.build());


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

    private int calcDifference(String time, String secondTime)
    {
        if(time.contains("PM"))
        {
            time = (12 + Integer.parseInt(time.substring(0, time.indexOf(":")))) + time.substring(time.indexOf(":"));
        }


        int timeMins = (60 * Integer.parseInt(time.substring(0, time.indexOf(":")))) + Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(" ")));


        return timeMins - Integer.parseInt(secondTime);
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
