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
import com.bitshift.saams.model.ReportCard;

import java.util.ArrayList;

public class ReportCardAdapter extends RecyclerView.Adapter<ReportCardAdapter.MyViewHolder> {

    // for load more
    public ArrayList<ReportCard> mDataset;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TableLayout tableView ;
        CardView lytMain;
        TextView txtSubName;
        public MyViewHolder(View itemView) {
            super(itemView);
            tableView= itemView.findViewById(R.id.tableView);
            txtSubName = itemView.findViewById(R.id.txtSubName);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public ReportCardAdapter(Context context2, ArrayList<ReportCard> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_report_card_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       ReportCard s = mDataset.get(position);
       String subjectName = s.getSubjectName();
        holder.txtSubName.setText(subjectName);
        View hl = new View(context);
        hl.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        View vl = new View(context);
        vl.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));

        TableRow tbrow0 = new TableRow(context);
        TextView tv0 = new TextView(context);
        tv0.setText(" Exam Name ");
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(16);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(context);
        tv1.setText(" Date ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextSize(16);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(context);
        tv2.setText(" Marks ");
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(16);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(context);
        tv3.setText(" Grade ");
        tv3.setTextColor(Color.WHITE);
        tv3.setTextSize(16);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);
        tbrow0.setBackgroundResource(R.color.colorAccent);
        tbrow0.setPadding(0,10,0,10);
        holder.tableView.addView(tbrow0);

        for(int i = 0; i<s.getResults().size(); i++) {
            TableRow tbrow = new TableRow(context);
            TextView t1v = new TextView(context);
            t1v.setText(s.getResults().get(i).getExamName());
            t1v.setTextColor(Color.BLUE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(context);
            t2v.setText(s.getResults().get(i).getDateTime());
            t2v.setTextColor(Color.BLUE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(context);
            t3v.setText(s.getResults().get(i).getMarksObtaibed()+"/"+s.getResults().get(i).getMarksOutOf());
            t3v.setTextColor(Color.BLUE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(context);
            t4v.setText(s.getResults().get(i).getGrade());
            t4v.setTextColor(Color.BLUE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            tbrow.setPadding(0,10,0,10);
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
