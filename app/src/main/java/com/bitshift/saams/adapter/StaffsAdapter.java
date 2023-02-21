package com.bitshift.saams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.model.Staffs;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StaffsAdapter extends RecyclerView.Adapter<StaffsAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Staffs> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        TextView tvqual;
        TextView tvsubjects;
        ImageView profilepic;

        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvStaffName);
            tvqual = itemView.findViewById(R.id.tvQualification);
            tvsubjects = itemView.findViewById(R.id.tvSubjects);
            profilepic = itemView.findViewById(R.id.imgStaff);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public StaffsAdapter(Context context2, ArrayList<Staffs> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_staffs_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       Staffs s = mDataset.get(position);
       holder.tvname.setText(String.valueOf(s.getName()));

       holder.tvqual.setText(String.valueOf(s.getQualification()));
       holder.tvsubjects.setText(String.join(",", s.getSubject()));
       Glide.with(holder.itemView)
                .load(s.getProfile_pic())
                .fitCenter()
                .centerCrop()
               .placeholder(R.drawable.profile)
                .into(holder.profilepic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getId()),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(context, MyPlotDetailedActivity.class);
                //intent.putExtra("CLICKED_PLOT", s);
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
