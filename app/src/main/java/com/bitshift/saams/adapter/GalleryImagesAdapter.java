package com.bitshift.saams.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.AttendanceActivity;
import com.bitshift.saams.activity.EventsActivity;
import com.bitshift.saams.activity.FeeDetailsActivity;
import com.bitshift.saams.activity.GalleryActivity;
import com.bitshift.saams.activity.LoginActivity;
import com.bitshift.saams.activity.NoticeBoardActivity;
import com.bitshift.saams.activity.ReportCardActivity;
import com.bitshift.saams.activity.StaffsActivity;
import com.bitshift.saams.activity.TimeTableActivity;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.GalleryItems;
import com.bitshift.saams.model.HomeItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GalleryImagesAdapter extends RecyclerView.Adapter<GalleryImagesAdapter.MyViewHolder> {

    // for load more
    public ArrayList<GalleryItems> mDataset;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView idImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            idImage = itemView.findViewById(R.id.idImage);
        }


    }
    public GalleryImagesAdapter(Context context2, ArrayList<GalleryItems> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_gallery_item_image, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        GalleryItems s = mDataset.get(position);
        Glide.with(holder.itemView)
                .load(s.getImage())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.logo_login)
                .into(holder.idImage);
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

