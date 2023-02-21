package com.bitshift.saams.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.activity.NoticeDetailedActivity;
import com.bitshift.saams.R;
import com.bitshift.saams.model.Notice;

import java.util.ArrayList;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Notice> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle;
        TextView tvdescr;
        TextView tvDateTime;
        TextView tvBy;

        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvTitle);
            tvdescr = itemView.findViewById(R.id.tvDescription);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvBy = itemView.findViewById(R.id.tvByTeacher);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public NoticeBoardAdapter(Context context2, ArrayList<Notice> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_notice_items, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(MyViewHolder holder, int position) {
       Notice s = mDataset.get(position);
       holder.tvtitle.setText(String.valueOf(s.getTitle()));
       holder.tvdescr.setText(String.valueOf(s.getDescription()));
       holder.tvDateTime.setText(String.valueOf(s.getDatetime()));
       holder.tvBy.setText("By : " + s.getNoticeBy());
        if(s.getIsRead().equals("0"))
        {
            holder.tvtitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.tvdescr.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.tvDateTime.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.tvBy.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getId()),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NoticeDetailedActivity.class);
                intent.putExtra("CLICKED_NOTICE", s);
                context.startActivity(intent);
            }
        });
    }

    public void filterList(ArrayList<Notice> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        mDataset = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
