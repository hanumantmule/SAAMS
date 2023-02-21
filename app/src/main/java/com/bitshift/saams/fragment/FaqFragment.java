package com.bitshift.saams.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bitshift.saams.R;
import com.bitshift.saams.adapter.FaqAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.helper.VolleyCallback;
import com.bitshift.saams.model.Faq;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class FaqFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    ArrayList<Faq> Faqs;
    SwipeRefreshLayout swipeLayout;
    NestedScrollView scrollView;
    RelativeLayout tvAlert;
    FaqAdapter FaqAdapter;
    int total = 0;
    Activity activity;
    int offset = 0;
    Session session;
    boolean isLoadMore = false;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_faq, container, false);

        activity = getActivity();
        session = new Session(activity);


        scrollView = root.findViewById(R.id.scrollView);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        tvAlert = root.findViewById(R.id.tvAlert);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);

        setHasOptionsMenu(true);

        getFaqData();

        swipeLayout.setColorSchemeResources(R.color.colorPrimary);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                offset = 0;
                getFaqData();
            }
        });


        return root;
    }


    void getFaqData() {
        recyclerView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        Faqs = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        Map<String, String> params = new HashMap<>();

        ApiConfig.RequestToVolleyGET(new VolleyCallback() {
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
//                        System.out.println("====transection " + response);
                        JSONObject objectbject = new JSONObject(response);
                        if (!objectbject.getBoolean(Constant.ERROR)) {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray(Constant.DATA);

                            Gson g = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                if (jsonObject1 != null) {
                                    Faq faq = g.fromJson(jsonObject1.toString(), Faq.class);
                                    Faqs.add(faq);
                                } else {
                                    break;
                                }

                            }
                            FaqAdapter = new FaqAdapter(activity, Faqs);
                            FaqAdapter.setHasStableIds(true);
                            recyclerView.setAdapter(FaqAdapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tvAlert.setVisibility(View.VISIBLE);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                }
            }
        }, activity, Constant.FAQ_URL, params, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.faq);
        activity.invalidateOptionsMenu();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.toolbar_search).setVisible(false);
    }

}