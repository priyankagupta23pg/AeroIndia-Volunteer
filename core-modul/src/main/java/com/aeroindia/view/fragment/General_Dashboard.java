package com.aeroindia.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aeroindia.R;
import com.customComponent.CustomAlert;

import ir.apend.slider.ui.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class General_Dashboard extends Fragment {

    private View rootView;
    private Slider slider;
    private FrameLayout main_frame;
    private RelativeLayout event_relative;
    private Fragment_Main fragment_main;
    private LinearLayout dash_menu_linear;
    private ImageView event_IV,personalise_IV,b2b_IV,exhibitor_IV,nearby_IV,more_IV;
    private Boolean selectedEvent;
    private ImageView[] imageViews=new ImageView[4];
    private Context context;

    public General_Dashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.general_dashboard, container, false);
        setup();
        return rootView;
    }


    private void setup() {
        //create list of slides
        context=getActivity();
        main_frame = (FrameLayout) rootView.findViewById(R.id.main_frame);
        event_relative=(RelativeLayout)rootView.findViewById(R.id.event_relative);
      //  dash_menu_linear=(LinearLayout)rootView.findViewById(R.id.dash_menu_linear);
        event_IV=(ImageView)rootView.findViewById(R.id.event_IV);
        personalise_IV=(ImageView)rootView.findViewById(R.id.personalise_IV);
        b2b_IV=(ImageView)rootView.findViewById(R.id.b2b_IV);
        nearby_IV=(ImageView)rootView.findViewById(R.id.nearby_IV);
        exhibitor_IV=(ImageView)rootView.findViewById(R.id.exhibitor_IV);
       // more_IV=(ImageView)rootView.findViewById(R.id.more_IV);
        //event_IV.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
        //refreshTab(0);
       /* fragment_main = new Fragment_Main();
        CallFragmnet(fragment_main);*/
        event_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  event_IV.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
                //refreshTab(0);
               // callMainFragment();
                CustomAlert.alertWithOk(context,"Under development");
            }
        });
        personalise_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  refreshTab(1);
                //callPersonaliseFragment();
                CustomAlert.alertWithOk(context,"Under development");
            }
        });

        b2b_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // refreshTab(2);
                //callB2bFragment();
                CustomAlert.alertWithOk(context,"Under development");
            }
        });

        exhibitor_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exhibitor_IV.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
                //refreshTab(3);
                //callExhibitorFragment();
                CustomAlert.alertWithOk(context,"Under development");
            }
        });
        nearby_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exhibitor_IV.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
                //refreshTab(3);
                //callExhibitorFragment();
                CustomAlert.alertWithOk(context,"Under development");
            }
        });

    }
    private  void callMainFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment_main != null) {
            fragmentTransaction.detach(fragment_main);
            fragment_main=null;

        }
        fragment_main = new Fragment_Main();
        fragmentTransaction.replace(R.id.main_frame, fragment_main);
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //CallFragmnet(fragment);
    }
    private  void callExhibitorFragment(){
        Fragment_Exhibitors fragment = new Fragment_Exhibitors();
        CallFragmnet(fragment);
    }
    private  void callB2bFragment(){
        Fragment_B2BMeeting fragment = new Fragment_B2BMeeting();
        CallFragmnet(fragment);
    }
    private  void callPersonaliseFragment(){
        FragmentPersonalise fragment = new FragmentPersonalise();
        CallFragmnet(fragment);
    }

    /* ======== Call to replace fragment ========*/
    private void CallFragmnet(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragment);
            //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void refreshTab(int clickedIndex){
        imageViews[0]=event_IV;
        imageViews[1]=personalise_IV;
        imageViews[2]=b2b_IV;
        imageViews[3]=exhibitor_IV;
        for(int i=0;i<imageViews.length;i++){
            ImageView imageView=imageViews[i];
            imageView.setBackgroundColor(getResources().getColor(R.color.white));
            if(clickedIndex==i){
                imageView.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
            }
        }

    }

}
