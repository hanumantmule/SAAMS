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
import android.widget.Toast;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.MainActivity;
import com.bitshift.saams.adapter.StaffsAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.Staffs;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaffsActivity extends AppCompatActivity {

    Activity activity;
    public Session session;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeLayout;
    int timerDelay = 0, timerWaiting = 0;
    RecyclerView staff_details_recyler_view;
    RelativeLayout lyt_home;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<Staffs> staffsArrayList;
    public StaffsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffs);

        session = new Session(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Staffs");
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
        staff_details_recyler_view = findViewById(R.id.recyclerView);
        staffsArrayList = new ArrayList<>();

        staff_details_recyler_view.setLayoutManager(new LinearLayoutManager(this));
        staff_details_recyler_view.setNestedScrollingEnabled(false);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);

        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                nestedScrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }

        if (ApiConfig.isConnected(this)) {
            getStaffData();
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
                   getStaffData();
                    //test();
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

    /*public void test()
    {
        ArrayList<String> sub = new ArrayList<>();
        sub.add("Sub1");
        sub.add("Sub2");

        staffsArrayList.clear();
        staffsArrayList.add(new Staffs("Hanumant1","M.Tech",sub,"http://via.placeholder.com/300.png"));

        staffsArrayList.add(new Staffs("Hanumant2","M.Tech",sub,"http://via.placeholder.com/300.png"));

        staffsArrayList.add(new Staffs("Hanumant3","M.Tech",sub,"http://via.placeholder.com/300.png"));

        staffsArrayList.add(new Staffs("Hanumant4","M.Tech",sub,"http://via.placeholder.com/300.png"));

        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
        mAdapter = new StaffsAdapter(activity, staffsArrayList);
        staff_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
        staff_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
        staff_details_recyler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
     */
    public void getStaffData() {
        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        staffsArrayList.clear();

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
                                    Staffs s = gson.fromJson(jsonObject.toString(), Staffs.class);
                                    staffsArrayList.add(s);
                                }

                                if (staffsArrayList.size() > 0) {
                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    mShimmerViewContainer.setVisibility(View.GONE);
                                    mShimmerViewContainer.stopShimmer();
                                    mAdapter = new StaffsAdapter(activity, staffsArrayList);
                                    staff_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
                                    staff_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
                                    staff_details_recyler_view.setAdapter(mAdapter);
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
        }, this, Constant.GET_STAFFS_URL + session.getData(Constant.ID), params, false);
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