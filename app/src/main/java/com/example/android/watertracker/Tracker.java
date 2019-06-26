package com.example.android.watertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class Tracker extends AppCompatActivity {

    Button water_tracker;
    Button see_past_records;// = findViewById(R.id.watertracker);
    MenuItem calendar;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        //calendar = findViewById(R.id.action_calendar);
        water_tracker = findViewById(R.id.watertracker);
        water_tracker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent today = new Intent(Tracker.this, WaterTracker.class);
                Tracker.this.startActivity(today);
            }
        });

        see_past_records=findViewById(R.id.past);
        see_past_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chart=new Intent(Tracker.this,PastData.class);
                Tracker.this.startActivity(chart);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                //datePicker.findViewById()
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
