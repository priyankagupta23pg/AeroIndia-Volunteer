package com.aeroindia.view.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.adapter.UpdateViewPagerAdapter;
import com.aeroindia.custom.utility.SlidingTabLayout;
import com.customComponent.utility.BaseAppCompatActivity;

public class B2BActivity extends BaseAppCompatActivity {
RelativeLayout backLayout;
private TextView headerTV;
    CharSequence Titles[] = {"Exhibitors" , "Business Visitors"};
    int Numboftabs = 2;
    ViewPager pager;
    SlidingTabLayout tabs;
    UpdateViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b2_b);
        getSupportActionBar().hide();
        backLayout=(RelativeLayout)findViewById(R.id.backLayout);
        headerTV=(TextView)findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.bbmeeting));
        adapter = new UpdateViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);


        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.themecolor));

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
