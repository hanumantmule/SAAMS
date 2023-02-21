package com.bitshift.saams.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.MainActivity;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.SelectOrgForLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectOrgAdapter extends RecyclerView.Adapter<SelectOrgAdapter.MyViewHolder> {

    // for load more
    public ArrayList<SelectOrgForLogin> mDataset;
    private Context context;
    String fcm_id;
    String jwt_token;
    String mobileorEmail;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrgName;
        TextView tvsuborgName;
        TextView tvSection;


        CardView lytMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvOrgName = itemView.findViewById(R.id.tvorgName);
            tvsuborgName = itemView.findViewById(R.id.tvsubOrg);
            tvSection = itemView.findViewById(R.id.tvsectionName);
            lytMain = itemView.findViewById(R.id.lytSelectOrgMain);
        }


    }
    public SelectOrgAdapter(String me, Context context2, ArrayList<SelectOrgForLogin> myDataset, String fi, String jk) {
        context = context2;
        mDataset = myDataset;
        fcm_id = fi;
        jwt_token = jk;
        mobileorEmail= me;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_select_org, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
       SelectOrgForLogin s = mDataset.get(position);
       holder.tvOrgName.setText(String.valueOf(s.getMainorgName()));
       holder.tvsuborgName.setText(String.valueOf(s.getSubOrgName()));
       holder.tvSection.setText(s.getSection_name());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Select Sub Organization");
                alertDialog.setMessage("Are you sure to login to the selected sub organization ?");
                alertDialog.setCancelable(false);
                final AlertDialog alertDialog1 = alertDialog.create();

                // Setting OK Button
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            new Session(context).createUserLoginSession(
                                    "./sample_image.jpg",
                                    fcm_id,
                                    s.getId(),
                                    s.getStudent_name(),
                                    mobileorEmail,
                                    mobileorEmail,
                                    s.getMainorgName(),
                                    s.getSubOrgName(),
                                    s.getSection_name(),
                                    jwt_token);

                            Constant.token = jwt_token;
                            MainActivity.homeClicked = false;
                            MainActivity.notificationClicked = false;
                            MainActivity.profileClicked = false;
                            Register_FCM(fcm_id,s.getId());
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constant.FROM, "");
                            intent.putExtra(Constant.FROM, "");

                            context.startActivity(intent);

                        } catch (Exception ignored) {
                            System.out.println(ignored.toString());
                        }
                    }
                });

                alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog1.dismiss();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
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

    public void Register_FCM(String token, String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, userId);
        params.put(Constant.FCM_ID, token);

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                    }
                } catch (JSONException ignored) {
                    System.out.println(ignored.toString());
                }

            }
        }, (Activity) context, Constant.REGISTER_DEVICE_URL, params, false);
    }

}
