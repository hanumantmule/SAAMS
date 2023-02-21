package com.bitshift.saams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.model.TimeTable;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {

    // for load more
    public ArrayList<TimeTable> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvsubjectname;
        TextView tvTeacherName;
        TextView tvTime;
        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvsubjectname = itemView.findViewById(R.id.tvsubjectname);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvTime = itemView.findViewById(R.id.tvTime);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public TimeTableAdapter(Context context2, ArrayList<TimeTable> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_timetable_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        TimeTable s = mDataset.get(position);
        holder.tvsubjectname.setText(String.valueOf(s.getSubjectName()));
        holder.tvTeacherName.setText(String.valueOf(s.getTeacherName()));
        holder.tvTime.setText(String.valueOf(s.getTimeSlot()));

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getId()),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(context, NoticeDetailedActivity.class);
                //intent.putExtra("CLICKED_NOTICE", s);
                //context.startActivity(intent);
            }
        });
    }

    public void filterList(ArrayList<TimeTable> filterlist) {
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
