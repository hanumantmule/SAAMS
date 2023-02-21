package com.bitshift.saams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.model.Subject;

import java.util.ArrayList;

public class ProfileSubjectsAdapter extends RecyclerView.Adapter<ProfileSubjectsAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Subject> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subName;

        public MyViewHolder(View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subjectName);
        }


    }
    public ProfileSubjectsAdapter(Context context2, ArrayList<Subject> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_profile_subjects, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Subject s = mDataset.get(position);
        holder.subName.setText(s.getSubject_name());
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

