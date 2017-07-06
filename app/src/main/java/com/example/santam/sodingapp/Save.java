package com.example.santam.sodingapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Save extends AppCompatActivity implements View.OnClickListener {
    DBController mDBCntrl;
    EditText mDate, mID, mName, mDesc;
    Button mSave, mCancel;


    private static final String TAG = "SODING_SAVE_TASK";
    protected static String strDate = "";
    protected static String strID = "";
    protected static String strName = "";
    protected static String strDesc = "";
    protected static String generatedid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        mDate = (EditText) findViewById(R.id.txtdate);
        mID = (EditText) findViewById(R.id.txtID);
        mName = (EditText) findViewById(R.id.txtName);
        mDesc = (EditText) findViewById(R.id.txtDesc);

        mSave = (Button) findViewById(R.id.btnSave);
        mCancel = (Button) findViewById(R.id.btnCancel);

        mDBCntrl = new DBController(this);
        // set id & date.

        createID();

        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        mDate.setText(sdf.format(new Date()));

        strDate = mDate.getText().toString();
        strID = mID.getText().toString();
        strName = mName.getText().toString();
        strDesc = mDesc.getText().toString();



        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);


    }


    public void createID(){

       try{
           String prefix = "TSK-";
           int idlen = 0;
           int prefixLen = 0;

           Cursor data = mDBCntrl.IdGenerate();

           if (data.moveToNext()) {
               Log.d(TAG, "IdGenerate: Find ID");
               String maxId = data.getString(0);

               idlen = maxId.length();
               String onlyId = maxId.substring(4);
               prefixLen = prefix.length();

               int onlyID = Integer.parseInt(onlyId);

               onlyID++;
               String StringOnlyId = Integer.toString(onlyID);

               int zeroCount = idlen - prefixLen - StringOnlyId.length();
               StringBuffer zeroFill = new StringBuffer("0");
               for (int count = 1; count < zeroCount; count++) {
                   zeroFill.insert(count, "0");

               }
               if (zeroCount == 0)
                   generatedid = prefix + onlyID;
               else
                   generatedid = prefix + zeroFill + onlyID;

           } else {
               Log.d(TAG, "IdGenerate: Dont Find ID");
               generatedid = "TSK-001";
           }

       }catch (Exception e){
           generatedid = "TSK-001";
       }
        mID.setText(generatedid);
    }

    @Override
    public void onClick(View v) {

        if (v == mSave){
            if (mName.getText().toString().length() != 0 ) {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.US);
                boolean insertData = false;

                try {
                    insertData = mDBCntrl.AddTask(strID, mName.getText().toString(),mDesc.getText().toString(),(Date) format.parse(strDate),(Date) format.parse(strDate));
                    if (insertData){
                        Message("Successfully Task Inserted");
                        mName.setText("");
                        mDesc.setText("");
                        createID();
                    }
                    else
                        Message("Task not Inserted! Please Try Again");

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            else
                Message("Please Insert Name");
        }
        else if (v == mCancel){
            finish();
        }
    }

    public void Message(String msg){
        Toast.makeText(Save.this, msg, Toast.LENGTH_LONG).show();
    }
}
