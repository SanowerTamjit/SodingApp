package com.example.santam.sodingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home_Activity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DBController mDBcntrl;
    private RecyclerView recyclerView;
//    private CustomAdapter adapter;
    private List<TaskData> data_list;
    static AlertDialog showDialog;
    EditText mName,mDesc;
    Button mDelete;
    public ProgressDialog PD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDBcntrl = new DBController(this);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading Donor Data...");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        data_list  = new ArrayList<>();
        getData();

    }

    // Fill Data to RecycleView
    private void getData() {
        boolean chkData = false;
        PD.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "populateListView: Displaying data on the RecylerViews.");
//        data_list.clear();

        //get the data and append to a list
        final Cursor data = mDBcntrl.getFullData();

        int count = 0;

        while(data.moveToNext()){
            chkData = true;
            Log.d(TAG,"Row Count= "+count);

            TaskData taskdata = new TaskData(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4));
//            Toast.makeText(getApplicationContext(), data.getString(1), Toast.LENGTH_LONG).show();
            data_list.add(taskdata);

            CustomAdapter adapter = new CustomAdapter(this,data_list);

            recyclerView.setAdapter(adapter);

            count++;

        }PD.dismiss();
        if (!chkData){
            Toast.makeText(this, "There is no Data at this moment. Please Add Task!!", Toast.LENGTH_LONG).show();
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {


                        mDelete = (Button) findViewById(R.id.btnDelete);

                        TextView taskID = (TextView) view.findViewById(R.id.viewID);
                        final String taskIDString = taskID.getText().toString().trim();

                        TextView taskName = (TextView) view.findViewById(R.id.viewName);
                        String taskNameString = taskName.getText().toString().trim();


                        Cursor description = mDBcntrl.getDesc_CrtDate(taskIDString);

                        final AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                        final LayoutInflater inflater = LayoutInflater.from(Home_Activity.this);
                        final View upDialogLayout = inflater.inflate(R.layout.updatedialog,null);

                        builder.setView(upDialogLayout);
                        showDialog = builder.create();

                        showDialog.show();

                        // Control Update Activity
                        mName = (EditText) upDialogLayout.findViewById(R.id.txtUName);
                        mDesc = (EditText) upDialogLayout.findViewById(R.id.txtUDesc);

                        TextView mTitle = (TextView) upDialogLayout.findViewById(R.id.txtTitle);
                        TextView mCreate = (TextView) upDialogLayout.findViewById(R.id.lblCreateDate);

                        mTitle.setText("Update (ID: "+taskIDString+")?");
                        mName.setText(taskNameString);

                        if (description.moveToNext()){
                            mCreate.setText("Created at: "+description.getString(1));
                            mDesc.setText(description.getString(0));

                        }else{
                            mDesc.setText("");
                        }


                        Button mUpdate = (Button) upDialogLayout.findViewById(R.id.btnUpdate);
                        Button mCancel = (Button) upDialogLayout.findViewById(R.id.btnCancel1);

                        mCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog.dismiss();
                            }
                        });

                        mUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mName.getText().toString().length() != 0 ) {
                                    updateTask(mName.getText().toString(), mDesc.getText().toString(),taskIDString);
                                }
                                else
                                    Message("Please Insert Name");
                            }
                        });

                    }
                }));

    }
// Just for hide alertdialog
    public void DltClick(){
        showDialog.dismiss();
    }

/// Update Task
    private void updateTask(String name, String desc, String id) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        boolean updateData = false;
        String strDate = sdf.format(new Date());
        try {
            updateData = mDBcntrl.updateTask(id, name,desc,(Date) sdf.parse(strDate));
            if (updateData){

                Message("Successfully Task Updated");

            }
            else {
                Message("Task not Updated! Please Try Again");

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

// Create Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

// Menu Controller
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.add_menu){
            Intent intent = new Intent(getApplicationContext(), Save.class);
            startActivity(intent);
        }
        else if(id == R.id.reload){

            startActivity(new Intent(this, Home_Activity.class));
//            Home_Activity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                            getData();
//        //                    adapter.notifyDataSetChanged();
////
//                }
//            });
        }


        return super.onOptionsItemSelected(item);
    }
/// On BackPressed
    @Override
    public void onBackPressed() {
        finish();
    }
// On Resume
    @Override
    public void onResume(){
        super.onResume();

//        getData();
//        adapter.notifyDataSetChanged();
    }
// Custom Toast
    public void Message(String msg){
        Toast msg1 = Toast.makeText(Home_Activity.this, msg, Toast.LENGTH_LONG);
        msg1.setGravity(Gravity.TOP,0,0);
        msg1.show();
    }


}
