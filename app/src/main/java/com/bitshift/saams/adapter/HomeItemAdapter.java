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

import com.bitshift.saams.activity.AttendanceActivity;
import com.bitshift.saams.activity.EventsActivity;
import com.bitshift.saams.activity.FeeDetailsActivity;
import com.bitshift.saams.activity.GalleryActivity;
import com.bitshift.saams.activity.NoticeBoardActivity;
import com.bitshift.saams.activity.ReportCardActivity;
import com.bitshift.saams.activity.StaffsActivity;
import com.bitshift.saams.activity.TimeTableActivity;
import com.bitshift.saams.activity.LoginActivity;
import com.bitshift.saams.R;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.HomeItem;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {

    // for load more
    public ArrayList<HomeItem> mDataset;
    private Context context;
    Session session;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView courseIV;
        TextView txthomeItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            courseIV = itemView.findViewById(R.id.idIVcourse);
            txthomeItem = itemView.findViewById(R.id.txthomeItem);
        }


    }
    public HomeItemAdapter(Context context2, ArrayList<HomeItem> myDataset, Session s, Activity a) {
        context = context2;
        mDataset = myDataset;
        session = s;
        activity = a;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_items, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeItem s = mDataset.get(position);
        holder.courseIV.setImageResource(s.getImageId());
        holder.txthomeItem.setText(s.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,String.valueOf(s.getName()),Toast.LENGTH_SHORT).show();
                if(s.getName() == "Time Table")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN))
                    {
                        Intent i = new Intent(context, TimeTableActivity.class);
                        context.startActivity(i);
                    }
                    else
                    {
                        gotoLogin();
                    }
                }
                else if(s.getName() == "Events")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, EventsActivity.class);
                        context.startActivity(i);
                    }
                    else
                    {
                        gotoLogin();
                    }
                }
                else if(s.getName() == "Staffs")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, StaffsActivity.class);
                        context.startActivity(i);
                    }
                    else
                    {
                        gotoLogin();
                    }
                }
                else if(s.getName() == "Attendance")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, AttendanceActivity.class);
                        context.startActivity(i);
                    }
                    else
                        gotoLogin();

                }
                else if(s.getName() == "Notice Board")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, NoticeBoardActivity.class);
                        context.startActivity(i);
                    }
                    else
                        gotoLogin();

                }
                else if(s.getName() == "Report Card")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, ReportCardActivity.class);
                        context.startActivity(i);
                    }
                    else
                        gotoLogin();

                }
                else if(s.getName() == "Gallery")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, GalleryActivity.class);
                        context.startActivity(i);
                    }
                    else
                        gotoLogin();

                }
                else if(s.getName() == "Fees Details")
                {
                    if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                        Intent i = new Intent(activity, FeeDetailsActivity.class);
                        context.startActivity(i);
                    }
                    else
                        gotoLogin();
                }
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

    private  void gotoLogin()
    {
        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_login_required, null);
        ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        final Dialog mBottomSheetDialog = new Dialog(activity);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBottomSheetDialog.show();
        Button btnRetry = sheetView.findViewById(R.id.btnRetry);
        mBottomSheetDialog.setCancelable(true);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                context.startActivity(new Intent(activity, LoginActivity.class).putExtra(Constant.FROM, ""));
            }
        });
    }

}

