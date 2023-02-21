package com.bitshift.saams.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.MainActivity;
import com.bitshift.saams.adapter.NoticeBoardAdapter;
import com.bitshift.saams.adapter.TimeTableAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.Notice;
import com.bitshift.saams.model.TimeTable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeTableActivity extends AppCompatActivity {

    Activity activity;
    public Session session;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeLayout;
    int timerDelay = 0, timerWaiting = 0;
    RecyclerView timetable_recyler_view;
    RelativeLayout lyt_home;
    private ShimmerFrameLayout mShimmerViewContainer;
    TimeTableAdapter mAdapter;
    ArrayList<TimeTable> timetableArrayList;

    private Button[] btn = new Button[6];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btnMon, R.id.btnTue, R.id.btnWed, R.id.btnThu,R.id.btnFri,R.id.btnSat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        session = new Session(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Time Table");
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
        timetableArrayList =  new ArrayList<>();
        swipeLayout = findViewById(R.id.swipeLayout);
        timetable_recyler_view = findViewById(R.id.recyclerView);

        timetable_recyler_view.setLayoutManager(new LinearLayoutManager(this));
        timetable_recyler_view.setNestedScrollingEnabled(false);

        nestedScrollView = findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
        }

        btn_unfocus = btn[0];
        setFocus(btn_unfocus, btn[0]);
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                nestedScrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }

        if (ApiConfig.isConnected(this)) {
            getNoticeData();
            //test();
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
                    getNoticeData();
                    //test();
                    setFocus(btn_unfocus, btn[0]);
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

    public void getNoticeData() {
        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        timetableArrayList.clear();

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGET((result, response) -> {
            if (result) {
                try {
                    JSONObject objectbject = new JSONObject(response);
                    if (!objectbject.getBoolean(Constant.ERROR)) {
                        if (objectbject.has(Constant.DATA))
                        {
                            JSONArray jsonArray = objectbject.getJSONArray(Constant.DATA);
                            Gson gson = new Gson();
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    TimeTable s = gson.fromJson(jsonObject.toString(), TimeTable.class);
                                    timetableArrayList.add(s);
                                }

                                if (timetableArrayList.size() > 0) {
                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    mShimmerViewContainer.setVisibility(View.GONE);
                                    mShimmerViewContainer.stopShimmer();
                                    mAdapter = new TimeTableAdapter(activity, timetableArrayList);
                                    timetable_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
                                    timetable_recyler_view.setItemAnimator(new DefaultItemAnimator());
                                    timetable_recyler_view.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                    filter("0");
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
        }, this, Constant.GET_TIMETABLE_URL +session.getData(Constant.ID), params, false);
    }

    /*
    public void test()
    {

        timetableArrayList.clear();
        timetableArrayList.add(new TimeTable("English","10:00 AM - 10:30 AM","Hanumant Mule","MON"));
        timetableArrayList.add(new TimeTable("English","10:00 AM - 10:30 AM","Hanumant Mule","MON"));
        timetableArrayList.add(new TimeTable("English","10:00 AM - 10:30 AM","Hanumant Mule","TUE"));
        timetableArrayList.add(new TimeTable("English","10:00 AM - 10:30 AM","Hanumant Mule","WED"));
        timetableArrayList.add(new TimeTable("English","10:00 AM - 10:30 AM","Hanumant Mule","THU"));

        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
        mAdapter = new TimeTableAdapter(activity, timetableArrayList);
        timetable_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
        timetable_recyler_view.setItemAnimator(new DefaultItemAnimator());
        timetable_recyler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        filter("MON");

    }

     */
    @SuppressLint("ResourceAsColor")
    public void OnBtnClick(View view) {
        int id = view.getId();
        if(id == R.id.btnMon)
        {
            filter("0");
            setFocus(btn_unfocus, btn[0]);
        }
        else if(id == R.id.btnTue)
        {
            filter("1");
            setFocus(btn_unfocus, btn[1]);
        }
        else if(id == R.id.btnWed)
        {
            filter("2");
            setFocus(btn_unfocus, btn[2]);
        }
        else if(id == R.id.btnThu)
        {
            filter("3");
            setFocus(btn_unfocus, btn[3]);
        }
        else if(id == R.id.btnFri)
        {
            filter("4");
            setFocus(btn_unfocus, btn[4]);
        }
        else if(id == R.id.btnSat)
        {
            filter("5");
            setFocus(btn_unfocus, btn[5]);
        }
    }

    private void filter(String day) {
        // creating a new array list to filter our data.
        ArrayList<TimeTable> filteredlist = new ArrayList<TimeTable>();

        // running a for loop to compare elements.
        for (TimeTable item : timetableArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getDay().equals(day)) {
                filteredlist.add(item);
            }
        }

        mAdapter.filterList(filteredlist);
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(55, 0, 179));
        btn_unfocus.setBackgroundColor(Color.rgb(255, 255, 255));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
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