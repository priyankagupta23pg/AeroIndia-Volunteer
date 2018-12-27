package com.aeroindia.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell1 on 17-09-2018.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       /* switch (position) {
            case 0:
                Fragment f1 = new Fragment_Exhibitors();
                Bundle bundle=new Bundle();
                bundle.putString(AppConstant.SCREENNAME,AppConstant.B2BSCREEN);
                f1.setArguments(bundle);
                return f1;
            case 1:
                Fragment f2 = new BlankFragment();
                return f2;

        }
        return null ;*/
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
