package com.bitshift.saams.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.model.Gallery;
import com.bitshift.saams.model.ReportCard;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    // for load more
    public ArrayList<Gallery> mDataset;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView galleryImages ;
        CardView lytMain;
        TextView txtGroupName;
        public MyViewHolder(View itemView) {
            super(itemView);
            galleryImages= itemView.findViewById(R.id.galleryImages);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            lytMain = itemView.findViewById(R.id.lytMain);
        }


    }
    public GalleryAdapter(Context context2, ArrayList<Gallery> myDataset) {
        context = context2;
        mDataset = myDataset;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_gallery_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gallery s = mDataset.get(position);
        String groupName = s.getGroup();
        holder.txtGroupName.setText(groupName);

        GalleryImagesAdapter mAdapter = new GalleryImagesAdapter(context, s.getItems());
        holder.galleryImages.setLayoutManager(new GridLayoutManager(context, 3));
        holder.galleryImages.setItemAnimator(new DefaultItemAnimator());
        holder.galleryImages.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
