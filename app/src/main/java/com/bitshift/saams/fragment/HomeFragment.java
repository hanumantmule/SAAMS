package com.bitshift.saams.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bitshift.saams.R;
import com.bitshift.saams.adapter.HomeItemAdapter;
import com.bitshift.saams.adapter.SliderAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.HomeItem;
import com.bitshift.saams.model.Slider;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {

    View root;
    Session session;
    Activity activity;
    GridView gridItems;
    NestedScrollView scrollView;
    SwipeRefreshLayout swipeLayout;
    ArrayList<Slider> sliderDataArrayList ;
    private ShimmerFrameLayout mShimmerViewContainer;
    SliderView sliderView;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        session = new Session(activity);

        swipeLayout = root.findViewById(R.id.swipeLayout);
        scrollView = root.findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);
        recyclerView = root.findViewById(R.id.recyclerView);

        sliderView = root.findViewById(R.id.imageSlider);
        if (scrollView != null) {
            scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                scrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }
        //#TODO Add code to populate the slider data here
        if (ApiConfig.isConnected(activity)) {
            getHomeSliderImages();

        } else {
            scrollView.setVisibility(View.VISIBLE);
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmer();
        }
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeSliderImages();
                swipeLayout.setRefreshing(false);
            }
        });

        ArrayList<HomeItem> homeModelArrayList = new ArrayList<>();
        homeModelArrayList.add(new HomeItem("Time Table", R.drawable.profile));
        homeModelArrayList.add(new HomeItem("Events", R.drawable.ic_events));
        homeModelArrayList.add(new HomeItem("Staffs", R.drawable.ic_staffs));
        homeModelArrayList.add(new HomeItem("Attendance", R.drawable.ic_attendance));
        homeModelArrayList.add(new HomeItem("Notice Board", R.drawable.ic_noticeboard));
        homeModelArrayList.add(new HomeItem("Report Card", R.drawable.ic_gradecards));
        homeModelArrayList.add(new HomeItem("Gallery", R.drawable.ic_gallery));
        homeModelArrayList.add(new HomeItem("Fees Details", R.drawable.ic_fees));

        HomeItemAdapter mAdapter = new HomeItemAdapter(activity, homeModelArrayList,session,activity);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return root;
    }

    private void getHomeSliderImages() {
        sliderDataArrayList = new ArrayList<>();
        scrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_HOME_SLIDER_IMAGES, Constant.GetVal);

        ApiConfig.RequestToVolleyGET((result, response) -> {
            if (result) {
                try {
                    JSONObject objectbject = new JSONObject(response);
                    if (!objectbject.getBoolean(Constant.ERROR)) {
                        if (objectbject.has(Constant.DATA)) {
                            JSONObject jsonObj = objectbject.getJSONObject(Constant.DATA);
                            JSONArray imageArray = jsonObj.getJSONArray("images");
                            String filePath = jsonObj.get("path").toString();
                            Gson gson = new Gson();
                            if (imageArray != null && imageArray.length() > 0) {
                                for (int i = 0; i < imageArray.length(); i++) {
                                    JSONObject jsonObject = imageArray.getJSONObject(i);
                                    Slider s = gson.fromJson(jsonObject.toString(), Slider.class);
                                    sliderDataArrayList.add(s);
                                }

                                SliderAdapter slideradapter = new SliderAdapter(activity, sliderDataArrayList,filePath);
                                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                                sliderView.setSliderAdapter(slideradapter);
                                sliderView.setScrollTimeInSec(3);
                                sliderView.setAutoCycle(true);
                                sliderView.startAutoCycle();
                                scrollView.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.setVisibility(View.GONE);
                                mShimmerViewContainer.stopShimmer();
                            }
                            else
                            {
                                //#TODO Call Function to Load Default Images
                                Toast.makeText(activity,"Looks like Internet is slow. Try Refreshing the Page",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(activity,"Looks like Internet is slow. Try Refreshing the Page",Toast.LENGTH_SHORT).show();
                            //#TODO Call Function to Load Default Images
                        }
                    } else {
                        scrollView.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmer();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    scrollView.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmer();
                }
            } else {
                scrollView.setVisibility(View.VISIBLE);
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }
        }, activity, Constant.GET_HOME_SLIDER_IMAGES_URL, params, false);
    }


}