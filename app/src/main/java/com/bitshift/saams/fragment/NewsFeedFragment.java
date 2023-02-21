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

import com.bitshift.saams.adapter.NotificationAdapter;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.helper.VolleyCallback;
import com.bitshift.saams.model.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bitshift.saams.R;
import com.google.gson.Gson;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class NewsFeedFragment extends Fragment {
    View root;
    Session session;
    Activity activity;
    RecyclerView recyclerView;
    ArrayList<Notification> notifications;
    NotificationAdapter notificationAdapter;
    SwipeRefreshLayout swipeLayout;
    NestedScrollView scrollView;
    RelativeLayout tvAlert;
    int total = 0;
    LinearLayoutManager linearLayoutManager;
    int offset = 0;
    boolean isLoadMore = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_track_order, container, false);
        activity = getActivity();
        session = new Session(activity);
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        tvAlert = root.findViewById(R.id.tvAlert);
        scrollView = root.findViewById(R.id.scrollView);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        setHasOptionsMenu(true);

        if (ApiConfig.isConnected(activity)) {
            getNotificationData();
        }
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (notifications != null) {
                    notifications = null;
                }
                offset = 0;
                getNotificationData();
                swipeLayout.setRefreshing(false);
            }
        });

        return root;
    }

    void getNotificationData() {
        notifications = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        Map<String, String> params = new HashMap<>();
        String id = "";
        if (session.getBoolean(Constant.IS_USER_LOGIN)) {
            params.put(Constant.USER_ID, session.getData(Constant.ID));
            id = "?user_id="+session.getData(Constant.ID);
        }

        ApiConfig.RequestToVolleyGET(new VolleyCallback() {
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
                        JSONObject objectbject = new JSONObject(response);
                        if (!objectbject.getBoolean(Constant.ERROR)) {

                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray(Constant.DATA);

                            Gson g = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                if (jsonObject1 != null) {
                                    Notification notification = g.fromJson(jsonObject1.toString(), Notification.class);
                                    notifications.add(notification);
                                } else {
                                    break;
                                }

                            }
                            notificationAdapter = new NotificationAdapter(activity, notifications);
                            notificationAdapter.setHasStableIds(true);
                            recyclerView.setAdapter(notificationAdapter);

                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tvAlert.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {

                    }
                }
            }
        }, activity, Constant.GET_NOTIFICATON_URL+id, params, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.title_news_feed);
        activity.invalidateOptionsMenu();
        Session.setCount(Constant.UNREAD_NOTIFICATION_COUNT, 0, getContext());
        //ApiConfig.updateNavItemCounter(DrawerActivity.navigationView, R.id.toolbar_search, Session.getCount(Constant.UNREAD_NOTIFICATION_COUNT, getContext()));

        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // menu.findItem(R.id.toolbar_search).setVisible(true);
    }

}