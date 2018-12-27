package com.aeroindia.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroindia.R;
import com.aeroindia.adapter.TabAdapter;
import com.aeroindia.custom.utility.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_B2BMeeting extends Fragment {

    private View rootView;
    private TabLayout tablayout;
    private ViewPager pager;
    private TabAdapter adapter;
    public Fragment_B2BMeeting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_b2bmeeting, container, false);
        setup();
        return rootView;
    }

    private void setup(){
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        adapter = new TabAdapter(this.getActivity().getSupportFragmentManager());
        tablayout.setTabTextColors(R.color.black_shine,R.color.darker_gray);
        Fragment f1 = new Fragment_Exhibitors();
        Bundle bundle=new Bundle();
        bundle.putString(AppConstant.SCREENNAME,AppConstant.B2BSCREEN);
        f1.setArguments(bundle);
        adapter.addFragment(f1, getResources().getString(R.string.exhibitors));
        adapter.addFragment(new Fragment_Visitors(), getResources().getString(R.string.delegators));
        pager.setAdapter(adapter);
        tablayout.setupWithViewPager(pager);
    }

}
