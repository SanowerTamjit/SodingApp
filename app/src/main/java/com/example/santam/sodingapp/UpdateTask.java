package com.example.santam.sodingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class UpdateTask extends AppCompatActivity implements View.OnClickListener{

    DBController mDBCntrl;
    EditText mName, mDesc;
    Button mUpdate, mCancel;


    private static final String TAG = "SODING_SAVE_TASK";
    protected static String strDate = "";
    protected static String strID = "";
    protected static String strName = "";
    protected static String strDesc = "";
    protected static String generatedid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatedialog);


        mName = (EditText) findViewById(R.id.txtUName);
        mDesc = (EditText) findViewById(R.id.txtUDesc);

        mUpdate = (Button) findViewById(R.id.btnUpdate);
        mCancel = (Button) findViewById(R.id.btnCancel1);

        mDBCntrl = new DBController(this);

//        Calendar cal = new GregorianCalendar();
//        cal.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));
//        String myFormat = "yyyy-MM-dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        strName = mName.getText().toString();
        strDesc = mDesc.getText().toString();



        mUpdate.setOnClickListener(this);
        mCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == mUpdate){
            if (mName.getText().toString().length() != 0 ) {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.US);
                boolean updateData = false;

                try {
                    updateData = mDBCntrl.updateTask(strID, mName.getText().toString(),strDesc,(Date) format.parse(strDate));
                    if (updateData){
                        Message("Successfully Task Updated");
                    }
                    else
                        Message("Task not Updated! Please Try Again");

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
        Toast.makeText(UpdateTask.this, msg, Toast.LENGTH_LONG).show();
    }
}
