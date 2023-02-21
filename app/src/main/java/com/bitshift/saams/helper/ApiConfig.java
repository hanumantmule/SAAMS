package com.bitshift.saams.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bitshift.saams.model.Slider;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.bitshift.saams.R;
import com.google.android.material.navigation.NavigationView;

public class ApiConfig extends Application {
    public static final String TAG = ApiConfig.class.getSimpleName();
    static ApiConfig mInstance;
    static AppEnvironment appEnvironment;
    static boolean isDialogOpen = false;
    RequestQueue mRequestQueue;

    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection !";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!!";
            } else if (error instanceof AuthFailureError) {
                message = "Auth Failure";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {

        }
        return message;
    }


    @SuppressLint("DefaultLocale")
    public static String StringFormat(String number) {
        return String.format("%.2f", Double.parseDouble(number));
    }

    public static void setWindowFlag(final int bits, boolean on, Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static String getMonth(int monthNo) {
        String month = "";

        switch (monthNo) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
            default:
                break;
        }
        return month;
    }

    public static String getDayOfWeek(int dayNo) {
        String month = "";

        switch (dayNo) {
            case 1:
                month = "Sun";
                break;
            case 2:
                month = "Mon";
                break;
            case 3:
                month = "Tue";
                break;
            case 4:
                month = "Wed";
                break;
            case 5:
                month = "Thu";
                break;
            case 6:
                month = "Fri";
                break;
            case 7:
                month = "Sat";
                break;
            default:
                break;
        }
        return month;
    }

    public static ArrayList<String> getDates(String startDate, String endDate) {
        ArrayList<String> dates = new ArrayList<>();
        @SuppressLint("SimpleDateFormat")
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(startDate);
            date2 = df1.parse(endDate);
        } catch (ParseException e) {

        }

        Calendar cal1 = Calendar.getInstance();
        assert date1 != null;
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        assert date2 != null;
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.get(Calendar.DATE) + "-" + (cal1.get(Calendar.MONTH) + 1) + "-" + cal1.get(Calendar.YEAR) + "-" + cal1.get(Calendar.DAY_OF_WEEK));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    public static void RequestToVolley(final VolleyCallback callback, final String url, final Map<String, String> params) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(true, response);
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onSuccess(false, "");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params.put(Constant.AccessKey, Constant.AccessKeyVal);
                return params;
            }
        };
        getInstance().getRequestQueue().getCache().clear();
        getInstance().addToRequestQueue(stringRequest);


    }


    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isprogress) {
        Session session = new Session(activity);

        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();
        if (ApiConfig.isConnected(activity)) {
            if (isprogress)
                progressDisplay.showProgress();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callback.onSuccess(true, response);
                    if (isprogress)
                        progressDisplay.hideProgress();

                }
            },
                    error -> {
                        if (isprogress)
                            progressDisplay.hideProgress();
                        callback.onSuccess(false, "");
                        String message = VolleyErrorMessage(error);
                        if (!message.equals(""))
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();
                    params1.put(Constant.AUTHORIZATION, "Bearer " + session.getData(Constant.JWT_TOKEN));
                    return params1;
                }

                @Override
                protected Map<String, String> getParams() {
                    //params.put(Constant.AccessKey, Constant.AccessKeyVal);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
            ApiConfig.getInstance().getRequestQueue().getCache().clear();
            ApiConfig.getInstance().addToRequestQueue(stringRequest);
        }

    }

    public static void RequestToVolleyGET(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isprogress) {
        Session session = new Session(activity);
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();
        if (ApiConfig.isConnected(activity)) {
            if (isprogress)
                progressDisplay.showProgress();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callback.onSuccess(true, response);
                    if (isprogress)
                        progressDisplay.hideProgress();

                }
            },
                    error -> {
                        if (isprogress)
                            progressDisplay.hideProgress();
                        callback.onSuccess(false, "");
                        String message = VolleyErrorMessage(error);
                        if (!message.equals(""))
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();
                    params1.put(Constant.AUTHORIZATION, "Bearer " + session.getData(Constant.JWT_TOKEN));
                    return params1;
                }

                @Override
                protected Map<String, String> getParams() {
                    //params.put(Constant.AccessKey, Constant.AccessKeyVal);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
            ApiConfig.getInstance().getRequestQueue().getCache().clear();
            ApiConfig.getInstance().addToRequestQueue(stringRequest);
        }

    }


    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String createJWT(String issuer, String subject) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            byte[] apiKeySecretBytes = Constant.JWT_KEY.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            JwtBuilder builder = Jwts.builder()
                    .setIssuedAt(now)
                    .setSubject(subject)
                    .setIssuer(issuer)
                    .signWith(signatureAlgorithm, signingKey);

            return builder.compact();
        } catch (Exception e) {

        }
        return null;
    }


    public static boolean CheckValidattion(String item, boolean isemailvalidation, boolean ismobvalidation) {
        boolean result = false;
        if (item.length() == 0) {
            result = true;
        } else if (isemailvalidation) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(item).matches()) {
                result = true;
            }
        } else if (ismobvalidation) {
            if (!android.util.Patterns.PHONE.matcher(item).matches()) {
                result = true;
            }
        }
        return result;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void addMarkers(int currentPage, ArrayList<Slider> imglist, LinearLayout
            mMarkersLayout, Context context) {

        if (context != null) {
            TextView[] markers = new TextView[imglist.size()];

            mMarkersLayout.removeAllViews();

            for (int i = 0; i < markers.length; i++) {
                markers[i] = new TextView(context);
                markers[i].setText(Html.fromHtml("&#8226;"));
                markers[i].setTextSize(35);
                markers[i].setTextColor(context.getResources().getColor(R.color.overlay_white));
                mMarkersLayout.addView(markers[i]);
            }
            if (markers.length > 0)
                markers[currentPage].setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
    public static void updateNavItemCounter(NavigationView nav, @IdRes int itemId, int count) {
        TextView view = nav.getMenu().findItem(itemId).getActionView().findViewById(R.id.counter);
        view.setText(String.valueOf(count));
        if (count <= 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
    public static void GetSettings(final Activity activity) {
        Session session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.SETTINGS, Constant.GetVal);
        params.put(Constant.GET_TIMEZONE, Constant.GetVal);
        ApiConfig.RequestToVolley(new VolleyCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(boolean result, String response) {
                if (result) {
                    try {
                        JSONObject objectbject = new JSONObject(response);
                        if (!objectbject.getBoolean(Constant.ERROR)) {
                            JSONObject object = objectbject.getJSONObject(Constant.DATA);
                            //System.out.println(object.toString());
                            //Toast.makeText(activity,object.toString(),Toast.LENGTH_SHORT);

                            session.setData(Constant.minimum_version_required, object.getString(Constant.minimum_version_required));
                            session.setData(Constant.is_version_system_on, object.getString(Constant.is_version_system_on));
                            String versionName = "";
                            try {
                                PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                                versionName = packageInfo.versionName;
                                //Toast.makeText(activity,session.getData(Constant.minimum_version_required).toString(),Toast.LENGTH_SHORT);

                                if (ApiConfig.compareVersion(versionName, session.getData(Constant.minimum_version_required)) < 0) {
                                    ApiConfig.OpenBottomDialog(activity);
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, activity, Constant.SETTING_URL, params, false);
    }



    public static void OpenBottomDialog(final Activity activity) {
        try {
            View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_update_app, null);
            ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }

            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity, R.style.BottomSheetTheme);
            mBottomSheetDialog.setContentView(sheetView);
            if (!new Session(activity).getBoolean("update_skip")) {
                mBottomSheetDialog.show();
            }

            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ImageView imgclose = sheetView.findViewById(R.id.imgclose);
            Button btnNotNow = sheetView.findViewById(R.id.btnNotNow);
            Button btnUpadateNow = sheetView.findViewById(R.id.btnUpdateNow);
            if (new Session(activity).getData(Constant.is_version_system_on).equals("0")) {
                btnNotNow.setVisibility(View.VISIBLE);
                imgclose.setVisibility(View.VISIBLE);
                mBottomSheetDialog.setCancelable(true);
            } else {
                mBottomSheetDialog.setCancelable(false);
            }


            imgclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBottomSheetDialog.isShowing())
                        new Session(activity).setBoolean("update_skip", true);
                    mBottomSheetDialog.dismiss();
                }
            });
            btnNotNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Session(activity).setBoolean("update_skip", true);
                    if (mBottomSheetDialog.isShowing())
                        mBottomSheetDialog.dismiss();
                }
            });

            btnUpadateNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PLAY_STORE_LINK + activity.getPackageName())));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clearFCM(Activity activity, Session session) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.REMOVE_FCM_ID, Constant.GetVal);
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
        }, activity, Constant.REMOVE_FCM_URL, params, false);
    }

    public static String getAddress(double lat, double lng, Activity activity) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            return addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int compareVersion(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");

        int i = 0;
        while (i < arr1.length || i < arr2.length) {
            if (i < arr1.length && i < arr2.length) {
                if (Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i])) {
                    return -1;
                } else if (Integer.parseInt(arr1[i]) > Integer.parseInt(arr2[i])) {
                    return 1;
                }
            } else if (i < arr1.length) {
                if (Integer.parseInt(arr1[i]) != 0) {
                    return 1;
                }
            } else {
                if (Integer.parseInt(arr2[i]) != 0) {
                    return -1;
                }
            }

            i++;
        }

        return 0;
    }

    public static synchronized ApiConfig getInstance() {
        return mInstance;
    }

    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                try {
                    if (!isDialogOpen) {
                        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_no_internet, null);
                        ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
                        if (parentViewGroup != null) {
                            parentViewGroup.removeAllViews();
                        }

                        final Dialog mBottomSheetDialog = new Dialog(activity);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mBottomSheetDialog.show();
                        isDialogOpen = true;
                        Button btnRetry = sheetView.findViewById(R.id.btnRetry);
                        mBottomSheetDialog.setCancelable(false);

                        btnRetry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isConnected(activity)) {
                                    isDialogOpen = false;
                                    mBottomSheetDialog.dismiss();
                                }
                            }
                        });
                    }
                } catch (Exception ignored) {

                }
            }
        } catch (Exception ignored) {

        }
        return check;
    }

    public static void SetAppEnvironment(Activity activity) {
        if (Constant.PAYUMONEY_MODE.equals("production")) {
            appEnvironment = AppEnvironment.PRODUCTION;
        } else if (Constant.PAYUMONEY_MODE.equals("sandbox")) {
            appEnvironment = AppEnvironment.SANDBOX;
        } else {
            appEnvironment = AppEnvironment.SANDBOX;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //setupActivityListener();
    }
    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);            }

            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public static void transparentStatusAndNavigation(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, activity);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            //context.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
