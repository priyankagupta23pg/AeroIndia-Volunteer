package com.aeroindia.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aeroindia.R;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Login extends Fragment {

    private View rootView;
    private Slider slider;
    private FrameLayout main_frame;
    private RelativeLayout event_relative;
    private Fragment_Main fragment_main;
    private LinearLayout dash_menu_linear;
    public Fragment_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_login, container, false);
        setup();
        return rootView;
    }


    private void setup() {
        //create list of slides
        slider = (Slider) rootView.findViewById(R.id.slider);
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0, "http://test.globalitpoint.com/images/banner1.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1, "http://test.globalitpoint.com/images/banner2.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2, "http://test.globalitpoint.com/images/banner3.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        //add slides to slider
        slider.addSlides(slideList);
        main_frame = (FrameLayout) rootView.findViewById(R.id.main_frame);
        event_relative=(RelativeLayout)rootView.findViewById(R.id.event_relative);
       // dash_menu_linear=(LinearLayout)rootView.findViewById(R.id.dash_menu_linear);
        event_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainFragment();
            }
        });
        event_relative.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
        fragment_main = new Fragment_Main();
        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment_main).commit();


    }
    private  void callMainFragment(){
        fragment_main = new Fragment_Main();
        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment_main).commit();
    }

}
