package com.aeroindia.adapter;

/**
 * Created by Priyanka PC on 22-12-2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aeroindia.view.fragment.ExhibitorsFragment;
import com.aeroindia.view.fragment.VisitorsFragment;


/**
 * Created by Edwin on 15/02/2015.
 */
public class UpdateViewPagerAdapter extends FragmentPagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public UpdateViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            ExhibitorsFragment tab1 = new ExhibitorsFragment();
            return tab1;
        }
        else {
            // if the position is 0 we are returning the First tab

                VisitorsFragment tab2 = new VisitorsFragment();
                return tab2;
            }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
