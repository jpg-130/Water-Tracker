package com.example.android.watertracker;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PastData extends AppCompatActivity {

    DbHelper myDb;
    TabLayout.Tab tab1,tab2;
    TextView intakeData,dateData;
    //DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_data);
//        datePicker = findViewById(R.id.datePicker);
//        int month =datePicker.getMonth()+1;
//        int year=datePicker.getYear();
//        int day=datePicker.getDayOfMonth();
        ViewAll();

    }


    //    public void topBarCalendar(){
//
//    }
    public void ViewAll(){
        myDb =new DbHelper(this);
        Cursor res = myDb.GetAllData();

        if (res.getCount()==0){
            ShowMessage("Error","No Data Found");
        }
        StringBuilder dateBuffer=new StringBuilder();
        StringBuilder intakeBuffer=new StringBuilder();
        res.moveToLast();
        do{
            dateBuffer.append(res.getString(0)+"\n");
            intakeBuffer.append(res.getString(1)+"\n");
        }while (res.moveToPrevious());
        //ShowMessage("Data",dateBuffer.toString()+intakeBuffer.toString());
        intakeData=findViewById(R.id.intakeData);
        intakeData.setText(intakeBuffer);
        dateData=findViewById(R.id.dateData);
        dateData.setText(dateBuffer);

    }

    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
