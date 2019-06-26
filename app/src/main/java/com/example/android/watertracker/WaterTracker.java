package com.example.android.watertracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WaterTracker extends AppCompatActivity{

    ImageButton increase;
    ImageButton decrease;
    TextView score,todaydate;
    DbHelper myDb;
    DatePickerFragment datePickerFragment;
    SubMenu Ontoday,Oncustom,OnallDays;
    public static final int DIAlOG_ID=0;
    int year_x,month_x,day_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);
        increase= findViewById(R.id.Increase);
        decrease= findViewById(R.id.Decrease);
        score=findViewById(R.id.Score);
        todaydate=findViewById(R.id.Todaydate);
        myDb =new DbHelper(this);

        final Calendar calendar = Calendar.getInstance();
        year_x= calendar.get(Calendar.YEAR);
        month_x=calendar.get(Calendar.MONTH);
        day_x=calendar.get(Calendar.DAY_OF_MONTH);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
        if (!myDb.GetTodaysData(date).moveToFirst()){
            score.setText("0");
        }
        else{
            Cursor today = myDb.GetTodaysData(date);
            //StringBuffer buffer=new StringBuffer();
            today.moveToFirst();
            //String TodayScore = myDb.GetTodaysData().getString(1);
            score.setText(today.getString(0));
            today.close();
        }
        //ViewToday();

        todaydate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myScore=score.getText().toString();
                int result;
                result=Integer.parseInt(myScore)+1;
                myScore=Integer.toString(result);
                score.setText(myScore);
                myDb =new DbHelper(increase.getContext());
                score = findViewById(R.id.Score);
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                boolean isUpdated= myDb.UpdateData(date,score.getText().toString());
                if (!isUpdated){
                    myDb.insertData(date,score.getText().toString());
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myScore=score.getText().toString();
                int result;
                result=Integer.parseInt(myScore)-1;
                if(result>0){
                    myScore=Integer.toString(result);
                    score.setText(myScore);
                }
                else
                    score.setText("0");

                myDb =new DbHelper(decrease.getContext());
                score = findViewById(R.id.Score);
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                boolean isUpdated= myDb.UpdateData(date,score.getText().toString());
                if (!isUpdated){
                    myDb.insertData(date,score.getText().toString());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.getItem(0).getSubMenu().getItem(0).setVisible(true);
        menu.getItem(0).getSubMenu().getItem(1).setVisible(true);
        menu.getItem(0).getSubMenu().getItem(2).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        switch (item.getItemId()){
            case R.id.OnallDays:
                Intent chart=new Intent(WaterTracker.this,PastData.class);
                WaterTracker.this.startActivity(chart);
                Toast.makeText(this.getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                return true;
            case R.id.Oncustom:
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
                //showDialog(DIAlOG_ID);
            case R.id.Ontoday:
                //Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_LONG).show();
                ViewToday();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed(){
        myDb =new DbHelper(this);
        score = findViewById(R.id.Score);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        boolean isUpdated= myDb.UpdateData(date,score.getText().toString());
        if (!isUpdated){
            myDb.insertData(date,score.getText().toString());
            //boolean isInserted = myDb.insertData(date,score.getText().toString());
            //if (!isInserted)
                //Toast.makeText(WaterTracker.this,"Not Inserted",Toast.LENGTH_LONG).show();
            //Toast.makeText(WaterTracker.this,"Inserted",Toast.LENGTH_LONG).show();
        }
        finish();
        //moveTaskToBack(true);
    }

    public void ViewToday(){
        myDb =new DbHelper(this);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
        Cursor res = myDb.GetCustomData(date);
        res.moveToFirst();
        if (res.getCount()==0){
            ShowMessage("Hello","Lets Start");
        }
        StringBuilder dateBuffer=new StringBuilder();
        StringBuilder intakeBuffer=new StringBuilder();
        //res.moveToLast();
        //do{
            dateBuffer.append(res.getString(0)+"\n");
            intakeBuffer.append(res.getString(1)+"\n");
        //}while (res.moveToPrevious());
        ShowMessage("Data",dateBuffer.toString()+intakeBuffer.toString());
    }

    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}

