package com.bitshift.saams.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bitshift.saams.activity.LoginActivity;
import com.bitshift.saams.adapter.FeeDetailsAdapter;
import com.bitshift.saams.adapter.ProfileSubjectsAdapter;
import com.bitshift.saams.adapter.TimeTableAdapter;
import com.bitshift.saams.model.FeeDetail;
import com.bitshift.saams.model.Subject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.DrawerActivity;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.helper.Utils;
import com.bitshift.saams.helper.VolleyCallback;
import com.bitshift.saams.ui.CircleTransform;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;


public class ProfileFragment extends Fragment {

    public ImageView imgProfile;
    View root;
    TextView orgName, studentName, studStandard, studRollNo, studAddress, studPhone, studEmail;
    Session session;
    Activity activity;
    RecyclerView recyclerView;
    ProfileSubjectsAdapter mAdapter;
    NestedScrollView nestedScrollView;
    ArrayList<Subject> subjects;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        activity = getActivity();

        session = new Session(getContext());
        setHasOptionsMenu(true);
        orgName = root.findViewById(R.id.orgName);
        imgProfile = root.findViewById(R.id.imgProfile);
        studentName = root.findViewById(R.id.studentName);
        studStandard = root.findViewById(R.id.studStandard);
        studRollNo = root.findViewById(R.id.studRollNo);
        studAddress = root.findViewById(R.id.studAddress);
        studPhone = root.findViewById(R.id.studPhone);
        studEmail = root.findViewById(R.id.studEmail);
        recyclerView = root.findViewById(R.id.recyclerView);
        nestedScrollView = root.findViewById(R.id.nestedScrollView);
        subjects = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setNestedScrollingEnabled(false);

        orgName.setText(session.getData(Constant.MAINORG_NAME));
        studentName.setText(session.getData(Constant.NAME));

        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                Rect scrollBounds = new Rect();
                nestedScrollView.getHitRect(scrollBounds);
                activity.invalidateOptionsMenu();
            });
        }
        if (!session.getBoolean(Constant.IS_USER_LOGIN)) {
            gotoLogin();
        }
        else
        {
            if (ApiConfig.isConnected(activity)) {
                //getStaffData();
                getProfileData();
            } else {
                nestedScrollView.setVisibility(View.VISIBLE);
            }
        }

        return root;
    }

   /* private void test() {

       ArrayList<String> subjects = new ArrayList<>();
        subjects.add("Subject 1");
        subjects.add("Subject 2");
        subjects.add("Subject 3");
        subjects.add("Subject 4");
        subjects.add("Subject 5");

        studStandard.setText(studStandard.getText()+" "+ "XI - A");
        studRollNo.setText(studRollNo.getText()+" "+"123456");
        studAddress.setText(studAddress.getText()+" "+"Mahagaon Tal-Barshi Dist-Solapur Maharashtra-413403");
        mAdapter = new ProfileSubjectsAdapter(activity, subjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if(session.getData(Constant.PROFILE) != "") {
            Picasso.get()
                    .load(session.getData(Constant.PROFILE))
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .transform(new CircleTransform())
                    .into(imgProfile);
        }
        else
        {
            Picasso.get()
                    .load("drawable://" + R.drawable.ic_profile_placeholder)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .transform(new CircleTransform())
                    .into(imgProfile);
        }

    }

    */


    private void getProfileData()
    {
        nestedScrollView.setVisibility(View.VISIBLE);
        subjects.clear();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGET((result, response) -> {
            if (result) {
                try {
                    JSONObject objectbject = new JSONObject(response);
                    if (!objectbject.getBoolean(Constant.ERROR)) {
                        if (objectbject.has(Constant.DATA))
                        {
                            JSONObject jsonObj = objectbject.getJSONObject(Constant.DATA);
                            studStandard.setText(studStandard.getText()+ (jsonObj.has("class_name")?jsonObj.get("class_name").toString() :" -"));
                            studRollNo.setText(studRollNo.getText()+ (jsonObj.has("roll_number")?jsonObj.get("roll_number").toString():" -"));
                            studAddress.setText(studAddress.getText()+ (jsonObj.has("address")?jsonObj.get("address").toString():" -"));
                            studPhone.setText(jsonObj.has("mobile_no")?jsonObj.get("mobile_no").toString():" - ");
                            studEmail.setText(jsonObj.has("studentemail")?jsonObj.get("studentemail").toString():" - ");
                            if(jsonObj.has("pic") && jsonObj.get("pic").toString() != "") {
                                Picasso.get()
                                        .load(jsonObj.get("pic").toString())
                                        .fit()
                                        .centerInside()
                                        .placeholder(R.drawable.ic_profile_placeholder)
                                        .error(R.drawable.ic_profile_placeholder)
                                        .transform(new CircleTransform())
                                        .into(imgProfile);
                            }

                            JSONArray jsonArray = jsonObj.getJSONArray("subjects");
                            Gson gson = new Gson();
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     Subject s = gson.fromJson(jsonObject.toString(), Subject.class);
                                     subjects.add(s);
                                }

                                if (subjects.size() > 0) {
                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    mAdapter = new ProfileSubjectsAdapter(activity, subjects);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    Toast.makeText(activity.getApplicationContext(),"Swipe Down to refresh again",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                nestedScrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        nestedScrollView.setVisibility(View.VISIBLE);
                        //lyt_home.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    nestedScrollView.setVisibility(View.VISIBLE);
                    // lyt_home.setVisibility(View.GONE);
                }
            } else {
                nestedScrollView.setVisibility(View.VISIBLE);
                // lyt_home.setVisibility(View.GONE);
            }
        }, activity, Constant.GET_PROFILE_URL + session.getData(Constant.ID), params, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.profile);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.toolbar_logout).setVisible(true);
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
        mBottomSheetDialog.setCancelable(false);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                activity.startActivity(new Intent(activity, LoginActivity.class).putExtra(Constant.FROM, ""));
            }
        });
    }
}
