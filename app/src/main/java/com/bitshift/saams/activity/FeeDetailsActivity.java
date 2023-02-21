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
import com.bitshift.saams.adapter.FeeDetailsAdapter;
import com.bitshift.saams.adapter.ReportCardAdapter;
import com.bitshift.saams.adapter.StaffsAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.ExamScore;
import com.bitshift.saams.model.FeeDetail;
import com.bitshift.saams.model.FeeReceiptComponent;
import com.bitshift.saams.model.ReportCard;
import com.bitshift.saams.model.Staffs;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeeDetailsActivity extends AppCompatActivity {

    Activity activity;
    public Session session;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeLayout;
    int timerDelay = 0, timerWaiting = 0;
    RecyclerView fee_details_recyler_view;
    RelativeLayout lyt_home;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<FeeDetail> feeArrayList;
    public FeeDetailsAdapter mAdapter;
    TextView tvFeesPaid, tvFeesDue,tvTotalPayble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);

        session = new Session(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Fees Details");
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
        fee_details_recyler_view = findViewById(R.id.recyclerView);
        feeArrayList = new ArrayList<>();

        fee_details_recyler_view.setLayoutManager(new LinearLayoutManager(this));
        fee_details_recyler_view.setNestedScrollingEnabled(false);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        tvFeesPaid= findViewById(R.id.tvFeesPaid);
        tvFeesDue= findViewById(R.id.tvFeesDue);
        tvTotalPayble= findViewById(R.id.tvTotalPayble);

        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                nestedScrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }

        if (ApiConfig.isConnected(this)) {
            //getStaffData();
            getFeesData();
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
                    getFeesData();
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
        List<FeeReceiptComponent> receiptDetails = new ArrayList<>();
        receiptDetails.add(new FeeReceiptComponent("Registration Fee","5"));
        receiptDetails.add(new FeeReceiptComponent("Exam Fee","50"));
        receiptDetails.add(new FeeReceiptComponent("College Fee","15"));
        receiptDetails.add(new FeeReceiptComponent("Library Fee","15"));
        receiptDetails.add(new FeeReceiptComponent("Registration Fee","85"));

        tvFeesDue.setText(" ₹ " + " "+"2200");
        tvFeesPaid.setText(" ₹ " + " "+"2000");
        tvTotalPayble.setText(" ₹ " + " "+"4200");

        feeArrayList.add(new FeeDetail("2312","2020/10/10","200",receiptDetails ));
        feeArrayList.add(new FeeDetail("2313","2020/10/10","200",receiptDetails ));
        feeArrayList.add(new FeeDetail("2314","2020/10/10","200",receiptDetails ));
        feeArrayList.add(new FeeDetail("2315","2020/10/10","200",receiptDetails ));

        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
        mAdapter = new FeeDetailsAdapter(activity, feeArrayList);
        fee_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
        fee_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
        fee_details_recyler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    public void getFeesData() {
        nestedScrollView.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        feeArrayList.clear();

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGET((result, response) -> {
            if (result) {
                try {
                    JSONObject objectbject = new JSONObject(response);
                    if (!objectbject.getBoolean(Constant.ERROR)) {
                        if (objectbject.has("FeesDue"))
                        {
                            tvFeesDue.setText(" ₹ " + " "+objectbject.get("FeesDue"));
                        }
                        if (objectbject.has("FeesPaid"))
                        {
                            tvFeesPaid.setText(" ₹ " + " "+objectbject.get("FeesPaid"));
                        }
                        if (objectbject.has("TotalPayble"))
                        {
                            tvTotalPayble.setText(" ₹ " + " "+objectbject.get("TotalPayble"));
                        }
                        if (objectbject.has(Constant.DATA))
                        {
                            JSONArray jsonArray = objectbject.getJSONArray(Constant.DATA);
                            Gson gson = new Gson();
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    FeeDetail s = gson.fromJson(jsonObject.toString(), FeeDetail.class);
                                    feeArrayList.add(s);
                                }

                                if (feeArrayList.size() > 0) {
                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    mShimmerViewContainer.setVisibility(View.GONE);
                                    mShimmerViewContainer.stopShimmer();
                                    mAdapter = new FeeDetailsAdapter(activity, feeArrayList);
                                    fee_details_recyler_view.setLayoutManager(new LinearLayoutManager(activity));
                                    fee_details_recyler_view.setItemAnimator(new DefaultItemAnimator());
                                    fee_details_recyler_view.setAdapter(mAdapter);
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
        }, this, Constant.GET_FEESDETAILS_URL + session.getData(Constant.ID), params, false);
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