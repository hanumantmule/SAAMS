package com.bitshift.saams.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.model.Attendance;
import com.bitshift.saams.model.AttendanceYearDetails;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Attendance> mDataset;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TableLayout tableView ;
        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tableView= itemView.findViewById(R.id.tableView);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public AttendanceAdapter(Context context2, ArrayList<Attendance> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_attendance_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       Attendance s = mDataset.get(position);
           TableRow tbrow0 = new TableRow(context);
           TextView tv0 = new TextView(context);
           tv0.setText("Month");
           tv0.setTextColor(Color.WHITE);
           tv0.setGravity(Gravity.CENTER);
           tv0.setTextSize(16);
           tbrow0.addView(tv0);
           TextView tv1 = new TextView(context);
           tv1.setText("Days");
           tv1.setTextColor(Color.WHITE);
           tv1.setGravity(Gravity.CENTER);
           tv1.setTextSize(16);
           tbrow0.addView(tv1);
           TextView tv2 = new TextView(context);
           tv2.setText("Present");
           tv2.setTextColor(Color.WHITE);
           tv2.setGravity(Gravity.CENTER);
           tv2.setTextSize(16);
           tbrow0.addView(tv2);
           TextView tv3 = new TextView(context);
           tv3.setText("Absent");
           tv3.setTextColor(Color.WHITE);
           tv3.setGravity(Gravity.CENTER);
           tv3.setTextSize(16);
           tbrow0.addView(tv3);
           TextView tv4 = new TextView(context);
           tv4.setText("Percentage");
           tv4.setTextColor(Color.WHITE);
           tv4.setGravity(Gravity.CENTER);
           tv4.setTextSize(16);
           tbrow0.addView(tv4);
           tbrow0.setBackgroundResource(R.color.colorAccent);
           tbrow0.setPadding(0, 10, 0, 10);
           holder.tableView.addView(tbrow0);

        for(int i=0;i<s.getAttendanceDetails().size();i++)
        {
            TableRow tbrow = new TableRow(context);
            TextView tv00 = new TextView(context);
            tv00.setText(s.getAttendanceDetails().get(i).getMonth());
            tv00.setTextColor(Color.BLUE);
            tv00.setGravity(Gravity.CENTER);
            tv00.setTextSize(16);
            tbrow.addView(tv00);
            TextView tv01 = new TextView(context);
            tv01.setText(s.getAttendanceDetails().get(i).getDays());
            tv01.setTextColor(Color.BLUE);
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextSize(16);
            tbrow.addView(tv01);
            TextView tv02 = new TextView(context);
            tv02.setText(s.getAttendanceDetails().get(i).getPresent());
            tv02.setTextColor(Color.BLUE);
            tv02.setGravity(Gravity.CENTER);
            tv02.setTextSize(16);
            tbrow.addView(tv02);
            TextView tv03 = new TextView(context);
            tv03.setText(s.getAttendanceDetails().get(i).getAbsent());
            tv03.setTextColor(Color.BLUE);
            tv03.setGravity(Gravity.CENTER);
            tv03.setTextSize(16);
            tbrow.addView(tv03);
            TextView tv04 = new TextView(context);
            tv04.setText(s.getAttendanceDetails().get(i).getPercentage());
            tv04.setTextColor(Color.BLUE);
            tv04.setGravity(Gravity.CENTER);
            tv04.setTextSize(16);
            tbrow.addView(tv04);
            tbrow.setPadding(0, 10, 0, 10);
            tbrow.setBackgroundResource(R.drawable.row_border);
            holder.tableView.addView(tbrow);
        }
        holder.tableView.setShrinkAllColumns(true);
        holder.tableView.setStretchAllColumns(true);
        holder.tableView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getId()),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(context, EventDetailedActivity.class);
                //intent.putExtra("CLICKED_EVENT", s);
                //context.startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return mDataset.size();
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        return 0;
    }


}
