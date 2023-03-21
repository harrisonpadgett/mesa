package com.example.mesaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mesaapp.calendarparts.WeekViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Profile extends AppCompatActivity {
    Button save;
    EditText text1, text2;

    String email;
    int phoneNumber;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Play selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Functionality of navigation menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.play:
                        startActivity(new Intent(getApplicationContext(), Play.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.log:
                        startActivity(new Intent(getApplicationContext(), Log.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), WeekViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;

            }
        });

        save = (Button)findViewById(R.id.BtnSave);
        text1 = (EditText)findViewById(R.id.editTextEmailAddress);
        text2 = (EditText)findViewById(R.id.editTextPhone);
        //to retrive the data from sharePreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //takes the data from the user input and sets the Space to the user input once the button is clicked
        String email1 = prefs.getString("email", email);
        text1.setText(email1);
        int phoneNumber1 = prefs.getInt("phoneNumber", phoneNumber);
        text2.setText(phoneNumber1);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = text1.getText().toString();
                phoneNumber = Integer.parseInt(text2.getText().toString());

                //this is ued to save data
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email);
                editor.putInt("phoneNumber", phoneNumber);
                editor.apply();
            }
        });

    }

    public void handleText(View v) {

    }

}
