package com.bitshift.saams.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.bitshift.saams.model.FeeDetail;
import com.bitshift.saams.model.ReportCard;

import java.util.ArrayList;

public class FeeDetailsAdapter extends RecyclerView.Adapter<FeeDetailsAdapter.MyViewHolder> {

    // for load more
    public ArrayList<FeeDetail> mDataset;
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
    public FeeDetailsAdapter(Context context2, ArrayList<FeeDetail> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_fee_detail_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       FeeDetail s = mDataset.get(position);
        String receiptNo = s.getReceiptNo();
        View hl = new View(context);
        hl.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        View vl = new View(context);
        vl.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));

        TableRow tbrow0 = new TableRow(context);
        TextView tv0 = new TextView(context);
        tv0.setText("Receipt No : "+ receiptNo + " ("+s.getDateTime()+")");
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(16);
        tbrow0.addView(tv0);
        tbrow0.setBackgroundResource(R.color.colorAccent);
        tbrow0.setPadding(0,10,0,10);
        holder.tableView.addView(tbrow0);

        for(int i=0;i<s.getItems().size();i++) {
            TableRow tbrow = new TableRow(context);
            TextView t1v = new TextView(context);
            t1v.setText(s.getItems().get(i).getFeeName());
            t1v.setTextColor(Color.BLUE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(context);
            t2v.setText(s.getItems().get(i).getFeeAmount());
            t2v.setTextColor(Color.BLUE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            tbrow.setPadding(0,10,0,10);
            tbrow.setBackgroundResource(R.drawable.row_border);
            holder.tableView.addView(tbrow);
        }

        TableRow tbrow = new TableRow(context);
        TextView t1v = new TextView(context);
        t1v.setText("Total");
        t1v.setTypeface(t1v.getTypeface(), Typeface.BOLD);
        t1v.setTextColor(Color.BLUE);
        t1v.setGravity(Gravity.CENTER);
        t1v.setTextSize(16);
        tbrow.addView(t1v);
        TextView t2v = new TextView(context);
        t2v.setText(s.getTotalOfReceipt());
        t2v.setTextColor(Color.BLUE);
        t2v.setGravity(Gravity.CENTER);
        t2v.setTypeface(t2v.getTypeface(), Typeface.BOLD);
        t2v.setTextSize(16);
        tbrow.addView(t2v);

        tbrow.setPadding(0,10,0,10);
        tbrow.setBackgroundResource(R.drawable.row_border);
        holder.tableView.addView(tbrow);

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
