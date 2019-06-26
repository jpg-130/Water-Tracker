package com.example.android.watertracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener,DatePicker.OnDateChangedListener{

    DbHelper myDb = new DbHelper(getContext());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                Initialize a new DatePickerDialog

                DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack,
                    int year, int monthOfYear, int dayOfMonth)
             */
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
        return  dpd;
    }

    //@Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {

        myDb =new DbHelper(getContext());
        String date=Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(dayOfMonth);
        Log.d(TAG, "onDateSet: jigyasa"+date);//2018-09-17
        Cursor res=myDb.GetCustomData(date);
        res.moveToFirst();
        if (res.getCount()==0){
            ShowMessage("TRY EDIT","No Data Found");
        }
        //res.moveToLast();
        //if(res.moveToFirst()) {
        else{
            //res.moveToFirst();
            StringBuilder dateBuffer=new StringBuilder();
            StringBuilder intakeBuffer=new StringBuilder();
            dateBuffer.append(res.getString(0) + "\n");
            intakeBuffer.append(res.getString(1) + "\n");
            ShowMessage("Data",dateBuffer.toString()+intakeBuffer.toString());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onDateSet(DatePicker view, int year, int month, int day){
        // Do something with the chosen date
//        myDb =new DbHelper(getContext());
//        String date=Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(day);
//        Log.d(TAG, "onDateSet: jigyasa"+date);//2018-09-17
//        Cursor res=myDb.GetCustomData(date);
//        res.moveToFirst();
//        if (res.getCount()==0){
//            ShowMessage("TRY EDIT","No Data Found");
//        }
//        //res.moveToLast();
//        //if(res.moveToFirst()) {
//        else{
//            //res.moveToFirst();
//            StringBuilder dateBuffer=new StringBuilder();
//            StringBuilder intakeBuffer=new StringBuilder();
//            dateBuffer.append(res.getString(0) + "\n");
//            intakeBuffer.append(res.getString(1) + "\n");
//            ShowMessage("Data",dateBuffer.toString()+intakeBuffer.toString());
//        }
//       // else {
//       //     Toast.makeText(getContext(),"nope",Toast.LENGTH_LONG).show();
//        //}
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

        /*
TextView tv = (TextView) getActivity().findViewById(R.id.tv);
// Create a Date variable/object with user chosen date
Calendar cal = Calendar.getInstance();
cal.setTimeInMillis(0);
cal.set(year, month, day, 0, 0, 0);
Date chosenDate = cal.getTime();

// Format the date using style and locale
DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
String formattedDate = df.format(chosenDate);

// Display the chosen date to app interface
//tv.setText(formattedDate);
*/

}
