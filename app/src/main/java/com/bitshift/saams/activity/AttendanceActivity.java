package com.bitshift.saams.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.MainActivity;
import com.bitshift.saams.adapter.AttendanceAdapter;
import com.bitshift.saams.adapter.FeeDetailsAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.Attendance;
import com.bitshift.saams.model.AttendanceYearDetails;
import com.bitshift.saams.model.FeeDetail;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    Activity activity;
    public Session session;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeLayout;
    int timerDelay = 0, timerWaiting = 0;
    RecyclerView attendance_details_recyler_view;
    RelativeLayout lyt_home;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<Attendance> attendanceArrayList;
    List<AttendanceYearDetails> attendanceDeatils ;

    public AttendanceAdapter mAdapter;
    TextView tvTotalDays, tvPresent,tvAbsent,tvPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        session = new Session(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Attendance Details");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Constant.FROM, "");
                startActivity(i);
                finish();
            }
        });
        activity = this;

        swipeLayout = findViewById(R.id.swipeLayout);
        attendance_details_recyler_view = findViewById(R.id.recyclerView);
        attendanceArrayList = new ArrayList<>();
        attendanceDeatils = new ArrayList<>();

        attendance_details_recyler_view.setLayoutManager(new LinearLayoutManager(this));
        attendance_details_recyler_view.setNestedScrollingEnabled(false);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        tvTotalDays= findViewById(R.id.tvTotalDays);
        tvPresent= findViewById(R.id.tvPresent);
        tvAbsent= findViewById(R.id.tvAbsent);
        tvPercentage= findViewById(R.id.tvPercentage);

        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                nestedScrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }

        if (ApiConfig.isConnected(this)) {
            //getStaffData();
            getAttendance();
        } else {
            nestedScrollView.setVisibility(View.VISIBLE);
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmer();
        }

        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ApiConfig.isConnected(activity)) {
                    // getStaffData();
                    getAttendance();
                }
                else
                {
                    nestedScrollView.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmer();
                }
                swipeLayout.setRefreshing(false);
            }
        });
    }

    public void test()
    {
        List<AttendanceYearDetails> attendanceDeatils = new ArrayList<>();
        attendanceDeatils.add(new AttendanceYearDetails("Jan","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Feb","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Mar","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Apr","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("May","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Jun","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("July","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Aug","30","24","6","75"));
        attendanceDeatils.add(new AttendanceYearDetails("Sep","30","24","6","75"));


        attendanceArrayList.add(new Attendance("2022",attendanceDeatils));

        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
        mAdapter = new AttendanceAdapter(activity, attendanceArrayList);
        attendance_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
        attendance_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
        attendance_details_recyler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    public void getAttendance() {
        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        attendanceArrayList.clear();
        attendanceDeatils.clear();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGET((result, response) -> {
            if (result) {
                try {
                    JSONObject objectbject = new JSONObject(response);
                    if (!objectbject.getBoolean(Constant.ERROR)) {
                        if (objectbject.has("totalDays"))
                        {
                            tvTotalDays.setText(objectbject.get("totalDays").toString());
                        }
                        if (objectbject.has("presentDays"))
                        {
                            tvPresent.setText(objectbject.get("presentDays").toString());
                        }
                        if (objectbject.has("absentDays"))
                        {
                            tvAbsent.setText(objectbject.get("absentDays").toString());
                        }
                        if (objectbject.has("percentage"))
                        {
                            tvPercentage.setText(objectbject.get("percentage").toString());
                        }
                        if (objectbject.has(Constant.DATA))
                        {
                            JSONArray jsonArray = objectbject.getJSONArray(Constant.DATA);
                            Gson gson = new Gson();
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    AttendanceYearDetails s = gson.fromJson(jsonObject.toString(), AttendanceYearDetails.class);
                                    attendanceDeatils.add(s);
                                }

                                if (attendanceDeatils.size() > 0) {
                                    attendanceArrayList.add(new Attendance("2022",attendanceDeatils));

                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    mShimmerViewContainer.setVisibility(View.GONE);
                                    mShimmerViewContainer.stopShimmer();
                                    mAdapter = new AttendanceAdapter(activity, attendanceArrayList);
                                    attendance_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
                                    attendance_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
                                    attendance_details_recyler_view.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Swipe Down to refresh again",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                nestedScrollView.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.setVisibility(View.GONE);
                                mShimmerViewContainer.stopShimmer();
                            }
                        }
                    } else {
                        nestedScrollView.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmer();
                        //lyt_home.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    nestedScrollView.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmer();
                    // lyt_home.setVisibility(View.GONE);
                }
            } else {
                nestedScrollView.setVisibility(View.VISIBLE);
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
                // lyt_home.setVisibility(View.GONE);
            }
        }, this, Constant.GET_ATTENDANCE_URL + session.getData(Constant.ID), params, false);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Constant.FROM, "");
        startActivity(i);
        finish();
    }
}