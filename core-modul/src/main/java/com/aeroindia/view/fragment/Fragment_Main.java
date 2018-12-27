package com.aeroindia.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aeroindia.R;
import com.aeroindia.pojos.request.EventItem;
import com.aeroindia.pojos.request.EventListItem;
import com.aeroindia.view.EnhancedWrapContentViewPager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Main extends Fragment {
    private View rootView;
    private EnhancedWrapContentViewPager pager;
    private LinearLayout btnViewPagerLayout, pagerlayout;
    private LayoutInflater layoutInflater;
    private EventListItem eventList = new EventListItem();
    private ArrayList<EventItem> eventItemArrayList;
    Activity activity;
    private int currentPageNumber, slideCount;
    private ViewPagerAdapter viewPagerAdapter;
    private Handler handler;

    public Fragment_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        activity = getActivity();
        setup();
        return rootView;
    }

    private void setup() {
        //Slides for events using pager
        eventList = EventListItem.create(loadJSONFromAsset());
        eventItemArrayList = eventList.getEventList();
        pager = (EnhancedWrapContentViewPager) rootView.findViewById(R.id.pager);
        btnViewPagerLayout = (LinearLayout) rootView.findViewById(R.id.btnViewPagerLayout);
        // pagerlayout = (LinearLayout) rootView.findViewById(R.id.pagerlayout);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPageNumber = position;
                btnAction(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        layoutInflater = LayoutInflater.from(activity);

        for (int i = 0; i < eventItemArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.button_layout, null);
            Button button = (Button) view.findViewById(R.id.btn1);
            if (i == 0) {
                button.setBackgroundResource(R.drawable.circle2);
            }
            btnViewPagerLayout.addView(view);
        }

        initEventLayout();
    }

    private void initEventLayout() {
        viewPagerAdapter = new ViewPagerAdapter(this.getChildFragmentManager(), eventItemArrayList);
        pager.setAdapter(viewPagerAdapter);
        slideCount = eventItemArrayList.size();
        pager.setCurrentItem(0);
        if (slideCount > 1) {
            setupTimer();
        }
    }


    private void btnAction(int action) {
        for (int i = 0; i < btnViewPagerLayout.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) btnViewPagerLayout.getChildAt(i);
            Button button = (Button) layout.findViewById(R.id.btn1);
            button.setBackgroundResource(R.drawable.circle);
            if (action == i) {
                button.setBackgroundResource(R.drawable.circle2);
            }
        }

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<EventItem> eventList;
        public ViewPagerAdapter(FragmentManager fm, ArrayList<EventItem> eventArrayList) {
            super(fm);
            eventList = eventArrayList;
        }

        @Override
        public Fragment getItem(int index) {
            Fragment_Event fragment = new Fragment_Event();
            fragment.setEvent(eventList.get(index));
            fragment.setEventList(eventList);
            return fragment;
        }

        @Override
        public int getCount() {

            return eventList.size();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("eveentList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setupTimer() {
        try {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (currentPageNumber < slideCount)
                            currentPageNumber += 1;
                        else
                            currentPageNumber = 1;

                        pager.setCurrentItem(currentPageNumber - 1, true);
                        handler.removeCallbacksAndMessages(null);
                        handler.postDelayed(this, 3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 3000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
