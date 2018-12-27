package com.aeroindia.view.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aeroindia.R;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;


public class Home extends Base {

    private EditText et;
    private Slider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backNavigation(null, this, (RelativeLayout)
                findViewById(R.id.backLayout), (TextView) findViewById(R.id.headerTV), R.string.login);
        setup();
    }

    private void setup() {
        //create list of slides
        slider = (Slider) findViewById(R.id.slider);
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0, "https://aeroindia.gov.in/site/images/banner.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1, "https://aeroindia.gov.in/site/images/banner4.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2, "https://aeroindia.gov.in/site/images/banner3.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        //add slides to slider
        slider.addSlides(slideList);

    }
}
