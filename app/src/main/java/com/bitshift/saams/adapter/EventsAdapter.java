package com.bitshift.saams.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.activity.EventDetailedActivity;
import com.bitshift.saams.R;
import com.bitshift.saams.model.Events;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Events> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle;
        TextView tvdescr;
        TextView tvdate;
        TextView tvlocation;
        ImageView coverImage;

        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvEventTitle);
            tvdescr = itemView.findViewById(R.id.tvDescription);
            tvdate = itemView.findViewById(R.id.tvDate);
            tvlocation = itemView.findViewById(R.id.tvLocation);

            coverImage = itemView.findViewById(R.id.imgEvent);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public EventsAdapter(Context context2, ArrayList<Events> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_events_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       Events s = mDataset.get(position);
       holder.tvtitle.setText(String.valueOf(s.getTitle()));
       holder.tvdescr.setText(String.valueOf(s.getDescription()));
       holder.tvdate.setText(String.valueOf(s.getDate()));
       holder.tvlocation.setText(String.valueOf(s.getLocation()));

       Glide.with(holder.itemView)
                .load(s.getcoverImage())
                .fitCenter()
                .centerCrop()
               .placeholder(R.drawable.logo_login)
                .into(holder.coverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getId()),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EventDetailedActivity.class);
                intent.putExtra("CLICKED_EVENT", s);
                context.startActivity(intent);
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
