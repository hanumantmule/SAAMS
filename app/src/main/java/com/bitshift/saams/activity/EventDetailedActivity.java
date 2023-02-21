package com.bitshift.saams.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitshift.saams.R;
import com.bitshift.saams.adapter.SliderAdapter;
import com.bitshift.saams.model.Events;
import com.bitshift.saams.model.Slider;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class EventDetailedActivity extends AppCompatActivity {

    SliderView sliderView;
    ArrayList<Slider> sliderDataArrayList = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailed);

        Events myObject = (Events) this.getIntent().getSerializableExtra("CLICKED_EVENT");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Event Details");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(getApplicationContext(), EventsActivity.class);
                startActivity(i);
                finish();
            }
        });


        ImageView imgCover = findViewById(R.id.imgCover);
        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvDateTime = findViewById(R.id.tvDateTime);
        TextView tvLocation = findViewById(R.id.tvLocation);
        TextView tvDescription = findViewById(R.id.tvDescription);
        sliderView = findViewById(R.id.eventGallerySlider);
        TextView tvHostName = findViewById(R.id.tvHostName);
        TextView tvHostDetails = findViewById(R.id.tvHostDetails);

        Glide.with(this)
                .load(myObject.getcoverImage())
                .fitCenter()
                .placeholder(R.drawable.logo_login)
                .centerCrop()
                .into(imgCover);

        tvEventTitle.setText(myObject.getTitle());
        tvDateTime.setText(myObject.getDate());
        tvLocation.setText(myObject.getLocation());
        tvDescription.setText(myObject.getDescription());
        tvHostName.setText(tvHostName.getText() + " " + myObject.getHostedBy());
        tvHostDetails.setText(tvHostDetails.getText() + " " + myObject.getHostDetails());

        for (int i = 0; i < myObject.getImageGallery().size(); i++) {
            sliderDataArrayList.add(new Slider(i, myObject.getImageGallery().get(i)));
        }
        String filePath = "";
        SliderAdapter slideradapter = new SliderAdapter(this, sliderDataArrayList,filePath);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}