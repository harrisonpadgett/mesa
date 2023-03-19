package com.example.mesaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mesaapp.calendarparts.WeekViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class Log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);




        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.log);

        // Functionality of navigation menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.play:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    public void giveHint(View view)
    {
        TextView hint;

        switch (view.getId())
        {
            case R.id.answer1Hint:
                hint = (TextView) findViewById(R.id.answer1Hint);


                Toast.makeText(Log.this, "It begins with a 'C'.", Toast.LENGTH_LONG).show();

                break;
            case R.id.answer2Hint:
                hint = (TextView) findViewById(R.id.answer2Hint);

                Toast.makeText(Log.this, "You're in your 60s.", Toast.LENGTH_LONG).show();

                break;
            case R.id.answer3Hint:
                hint = (TextView) findViewById(R.id.answer3Hint);

                Toast.makeText(Log.this, "The sky.", Toast.LENGTH_LONG).show();

                break;
            case R.id.answer4Hint:
                hint = (TextView) findViewById(R.id.answer4Hint);

                Toast.makeText(Log.this, "Lucy and Conrad", Toast.LENGTH_LONG).show();

                break;
        }

    }

    public void checkResponse(View view)
    {
        TextView response;

        switch (view.getId())
        {
            case R.id.answer1Submit:
                response = (TextView) findViewById(R.id.answer1);

                if(response.getText().toString().toLowerCase().equals("cindy"))
                {

                    Toast.makeText(Log.this, "Correct! Well done!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Log.this, "Incorrect. Try again or use a hint.", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.answer2Submit:
                response = (TextView) findViewById(R.id.answer2);

                if(response.getText().toString().toLowerCase().equals("64"))
                {

                    Toast.makeText(Log.this, "Correct! Well done!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Log.this, "Incorrect. Try again or use a hint.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.answer3Submit:
                response = (TextView) findViewById(R.id.answer3);

                if(response.getText().toString().toLowerCase().equals("blue"))
                {

                    Toast.makeText(Log.this, "Correct! Well done!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Log.this, "Incorrect. Try again or use a hint.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.answer4Submit:
                response = (TextView) findViewById(R.id.answer4);

                if(response.getText().toString().toLowerCase().equals("2"))
                {

                    Toast.makeText(Log.this, "Correct! Well done!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Log.this, "Incorrect. Try again or use a hint.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
