package com.example.santam.sodingapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<TaskData> my_data;

    public CustomAdapter(Context context, List<TaskData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklistview,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String getid = my_data.get(position).getId();
        String getname = my_data.get(position).getName();
        String getdate = my_data.get(position).getDateUp();
        String finalDate = "<html><font color=Blue>Updated at</font><br>"+getdate+"</html>";
//        String phncountry = "<html>"+phone+"<br>("+country+")</html>";

        holder.mID.setText(getid);
        holder.mName.setText(getname);
        holder.mDate.setText(Html.fromHtml(finalDate));
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = holder.mID.getText().toString();
                Toast.makeText(context,""+ id,Toast.LENGTH_SHORT).show();
                Home_Activity a = new Home_Activity();
                a.DltClick();
                DBController  mDBcntrl = new DBController(context);
                if ( mDBcntrl.deleteTask(id)){
                    Toast.makeText(context,"Successfully Delete this task: "+ id,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"Unsuccessfully Delete this task: "+ id,Toast.LENGTH_SHORT).show();
                }
//                new Home_Activity().DltClick(holder.mID.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView mID;
        public TextView mName;
        public TextView mDate;
        public Button mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mID = (TextView) itemView.findViewById(R.id.viewID);
            mName = (TextView) itemView.findViewById(R.id.viewName);
            mDate = (TextView) itemView.findViewById(R.id.viewDate);
            mDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}
