package com.bitshift.saams.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bitshift.saams.R;
import com.bitshift.saams.activity.NoticeBoardActivity;
import com.bitshift.saams.helper.ApiConfig;
import com.bitshift.saams.helper.Constant;
import com.bitshift.saams.helper.Session;
import com.bitshift.saams.model.Notice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NoticeDetailedActivity extends AppCompatActivity {

    public Session session;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detailed);

        Notice myObject = (Notice) this.getIntent().getSerializableExtra("CLICKED_NOTICE");
        session = new Session(getApplicationContext());
        activity = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Notice Details");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(getApplicationContext(), NoticeBoardActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView tvTitle = findViewById(R.id.tvNoticeTitle);
        TextView tvDateTime = findViewById(R.id.tvDateTime);
        TextView tvBy = findViewById(R.id.tvBy);
        TextView tvCircular = findViewById(R.id.tvCircular);
        TextView tvDescription = findViewById(R.id.tvDescription);

        tvTitle.setText(myObject.getTitle());
        tvDateTime.setText(myObject.getDatetime());
        tvBy.setText("By : "+ myObject.getNoticeBy());
        tvCircular.setText("Circular : " + myObject.getCircularNo());
        tvDescription.setText(myObject.getDescription());

        String noticeId = myObject.getId();
        setReadNotification(noticeId);
    }

    private void setReadNotification(String noticeId) {

        Map<String, String> params = new HashMap<>();
        if (session.getBoolean(Constant.IS_USER_LOGIN)) {
            params.put("uid", session.getData(Constant.ID));
        }
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        System.out.println("Notice Read");
                    }
                } catch (JSONException ignored) {
                    System.out.println(ignored.toString());
                }

            }
        }, activity, Constant.READ_NOTICE_URL + noticeId, params, false);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), NoticeBoardActivity.class);
        startActivity(i);
        finish();
    }
}