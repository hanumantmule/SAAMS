package com.bitshift.saams.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitshift.saams.R;
import com.bitshift.saams.fragment.HomeFragment;
import com.bitshift.saams.fragment.NewsFeedFragment;
import com.bitshift.saams.fragment.ProfileFragment;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.DatabaseHelper;
import com.bitshift.saams.helper.Session;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends DrawerActivity {

    static final String TAG = "MAIN ACTIVITY";
    public static Toolbar toolbar;
    public static SmoothBottomBar bottomNavigationView;
    public static Fragment active;
    public static FragmentManager fm = null;
    public static Fragment homeFragment, notificationFragment, profileFragment;
    public static boolean homeClicked = false, categoryClicked = false, favoriteClicked = false, notificationClicked = false, profileClicked=false;
    public static Activity activity;
    public static Session session;
    boolean doubleBackToExitPressedOnce = false;
    Menu menu;
    DatabaseHelper databaseHelper;
    String from;
    CardView cardViewHamburger;
    TextView toolbarTitle;
    ImageView imageMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiConfig.transparentStatusAndNavigation(this);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        cardViewHamburger = findViewById(R.id.cardViewHamburger);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        imageMenu = findViewById(R.id.imageMenu);
        actionBarDrawerToggle.syncState();

        activity = MainActivity.this;
        session = new Session(activity);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        from = getIntent().getStringExtra(Constant.FROM);
        databaseHelper = new DatabaseHelper(activity);

        drawerToggle = new ActionBarDrawerToggle
                (
                        activity,
                        drawer_layout, toolbar,
                        R.string.drawer_open,
                        R.string.drawer_close
                ) {
        };

        cardViewHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        fm = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        notificationFragment = new NewsFeedFragment();
        profileFragment = new ProfileFragment();

       if (from.equals("notification")) {
            Bundle bundle = new Bundle();
            bottomNavigationView.setItemActiveIndex(1);
            active = notificationFragment;
           notificationClicked = true;
            homeClicked = false;
           profileClicked = false;
            try {
                notificationFragment.setArguments(bundle);
                fm.beginTransaction().add(R.id.container, notificationFragment).commit();
            } catch (Exception e) {
                fm.beginTransaction().add(R.id.container, notificationFragment).commit();
            }

        } else {
            Bundle bundle = new Bundle();
            bottomNavigationView.setItemActiveIndex(0);
            active = homeFragment;
            homeClicked = true;
            notificationClicked = false;
            profileClicked = false;
            try {
                if (!getIntent().getStringExtra("json").isEmpty()) {
                    bundle.putString("json", getIntent().getStringExtra("json"));
                }
                homeFragment.setArguments(bundle);
                fm.beginTransaction().add(R.id.container, homeFragment).commit();
            } catch (Exception e) {
                fm.beginTransaction().add(R.id.container, homeFragment).commit();
            }
        }

        bottomNavigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                switch (i) {
                    case 0:
                           if (!homeClicked) {
                                fm.beginTransaction().add(R.id.container, homeFragment).show(homeFragment).hide(active).commit();
                                homeClicked = true;
                            } else {
                                fm.beginTransaction().show(homeFragment).hide(active).commit();
                            }
                            active = homeFragment;
                        break;

                    case 1:
                            if (!notificationClicked) {
                                fm.beginTransaction().add(R.id.container, notificationFragment).show(notificationFragment).hide(active).commit();
                                notificationClicked = true;
                            } else {
                                fm.beginTransaction().show(notificationFragment).hide(active).commit();
                            }
                            active = notificationFragment;

                        break;

                    case 2:
                        if (!profileClicked) {
                            fm.beginTransaction().add(R.id.container, profileFragment).show(profileFragment).hide(active).commit();
                            profileClicked = true;
                        } else {
                            fm.beginTransaction().show(profileFragment).hide(active).commit();
                        }
                        active = profileFragment;

                        break;
                }
                return false;
            }
        });


        fm.addOnBackStackChangedListener(() -> {
             toolbar.setVisibility(View.VISIBLE);
            Fragment currentFragment = fm.findFragmentById(R.id.container);
            currentFragment.onResume();
        });

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            try {

                if (!token.equals(session.getData(Constant.FCM_ID))) {
                    session.setData(Constant.FCM_ID, token);
                    Register_FCM(token);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    public void Register_FCM(String token) {
        Map<String, String> params = new HashMap<>();
        if (session.getBoolean(Constant.IS_USER_LOGIN)) {
            params.put(Constant.USER_ID, session.getData(Constant.ID));
        }
        else return;
        params.put(Constant.FCM_ID, token);

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        session.setData(Constant.FCM_ID, token);
                    }
                } catch (JSONException ignored) {
                    System.out.println(ignored.toString());
                }

            }
        }, activity, Constant.REGISTER_DEVICE_URL, params, false);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(navigationView))
            drawer_layout.closeDrawers();
        else
            doubleBack();
    }

    public void doubleBack() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        if (fm.getBackStackEntryCount() == 0) {
            if (active != homeFragment) {

                this.doubleBackToExitPressedOnce = false;
                bottomNavigationView.setItemActiveIndex(0);
                homeClicked = true;
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
            } else {
                Toast.makeText(this, getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
      case R.id.toolbar_logout:
                session.logoutUserConfirmation(activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (fm.getBackStackEntryCount() > 0) {

            drawerToggle.onDrawerClosed(drawer_layout);

            toolbarTitle.setText(Constant.TOOLBAR_TITLE);
            bottomNavigationView.setVisibility(View.GONE);
            imageMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back));
            cardViewHamburger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fm.popBackStack();
                }
            });
            lockDrawer();

        } else {
            if (session.getBoolean(Constant.IS_USER_LOGIN)) {
                toolbarTitle.setText(getString(R.string.hi) + session.getData(Constant.NAME) + "!");
            } else {
                toolbarTitle.setText(getString(R.string.hi_user));
            }
            bottomNavigationView.setVisibility(View.VISIBLE);
            imageMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
            cardViewHamburger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
            });
            unLockDrawer();
        }

        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    public void lockDrawer() {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).requestDisallowInterceptTouchEvent(true);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
    }

    public void unLockDrawer() {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).requestDisallowInterceptTouchEvent(false);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        activity.invalidateOptionsMenu();
        ApiConfig.GetSettings(activity);
        hideKeyboard();
    }
    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getApplicationWindowToken(), 0);
        } catch (Exception e) {

        }
    }


    @Override
    protected void onPause() {
        invalidateOptionsMenu();
        super.onPause();
    }


}