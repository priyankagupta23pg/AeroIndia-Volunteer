package com.aeroindia.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.response.AnnouncementItemsModel;
import com.aeroindia.pojos.response.AnnouncemnetResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.EnhancedWrapContentViewPager;
import com.aeroindia.view.fragment.ExhibitorUserMyDashboard;
import com.aeroindia.view.fragment.ImageSliderFragment;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ir.apend.slider.ui.Slider;

//public class DashboardExhibitorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
public class DashboardExhibitorActivity extends AppCompatActivity {
    /*Toolbar*/
    private Toolbar toolbar;
    private TextView toolbar_title, txt_toolbar_cmpny, txt_toolbar_email;

    /*Navigation Drawer*/
    private ActionBarDrawerToggle toggle;
    public static NavigationView navigationView;
    private DrawerLayout drawer;
    Activity activity;
    private Button titleButton;
    private LoginResponse loginResponse;
    private TextView username_TV, place_TV;
    private Slider slider;
    private Context context;

    private ViewPagerAdapter viewPagerAdapter;
    private boolean mustLoopSlides;
    private LinearLayout btnViewPagerLayout, pagerlayout;
    private EnhancedWrapContentViewPager pager;
    private LayoutInflater layoutInflater;
    private int slideShowInterval = 2000;
    private Handler handler = new Handler();
    private int currentPageNumber;
    private int slideCount;
    private AnnouncementItemsModel announcementItemsModel;
    private ArrayList<AnnouncementItemsModel> announcementItemsModels;
    private AnnouncemnetResponseModel announcemnetResponse;
    private int NUM_PAGES = 0;
    private ImageView logoutIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        activity = this;
        context = this;
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, activity));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_title = (TextView) findViewById(R.id.txt_toolbar_title);
        txt_toolbar_cmpny = (TextView) findViewById(R.id.txt_toolbar_cmpny);
        txt_toolbar_email = (TextView) findViewById(R.id.txt_toolbar_email);
        titleButton = (Button) findViewById(R.id.titleButton);
        titleButton.setText(getResources().getString(R.string.login));
        logoutIV = (ImageView) findViewById(R.id.logoutIV);
        toolbar_title.setVisibility(View.VISIBLE);
        if (loginResponse != null) {
            if (loginResponse.getCompany() != null) {
                if (loginResponse.getCompany().getName() != null) {
                    toolbar_title.setText(getResources().getString(R.string.welcom) + "  " + loginResponse.getCompany().getName());
                }
                if (loginResponse.getCompany().getCountry() != null) {
                    txt_toolbar_cmpny.setVisibility(View.VISIBLE);
                    txt_toolbar_cmpny.setText(loginResponse.getCompany().getCountry());
                }
            }
            if (loginResponse.getUser().getUserName() != null) {
                txt_toolbar_email.setVisibility(View.GONE);
                txt_toolbar_email.setText(loginResponse.getUser().getUserName());
            }
        } else {
            toolbar_title.setText(getResources().getString(R.string.welcom) + " " + loginResponse.getUser().getUserName());

        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // initNavigation();

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle(context.getResources().getString(com.customComponent.R.string.Alert));

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you really want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        layoutInflater = LayoutInflater.from(context);

        pager = (EnhancedWrapContentViewPager) findViewById(R.id.pager);

        btnViewPagerLayout = (LinearLayout) findViewById(R.id.btnViewPagerLayout);
        pagerlayout = (LinearLayout) findViewById(R.id.pagerlayout);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                btnAction(position);

                // perform functioning

            }

            @Override
            public void onPageSelected(int position) {
                currentPageNumber = position;
                Log.d("current slide", currentPageNumber + "");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        announcementItemsModels = new ArrayList<>();
 /*       announcementItemsModels.add(new AnnouncementItemsModel(0,
                "https://aeroindia.gov.in/site/images/banner.jpg", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));*/
        announcementItemsModels.add(new AnnouncementItemsModel(0, R.drawable.banner_default, getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

        announcemnetResponse = announcemnetResponse.create(loadAnnouncementJSONFromAsset());

        if (announcemnetResponse != null && announcemnetResponse.getAnnouncementItemsModels() != null
                && announcemnetResponse.getAnnouncementItemsModels().size() > 0) {
            for (int i = 0; i < announcemnetResponse.getAnnouncementItemsModels().size(); i++) {
                announcementItemsModels.add(i + 1, announcemnetResponse.getAnnouncementItemsModels().get(i));


            }
         /*   for(AnnouncementItemsModel itemsModel : announcemnetResponse.getAnnouncementItemsModels()){
                announcementItemsModels.add(1,itemsModel);
            }*/

            refreshAnnouncementResult(announcementItemsModels);
        } else {
            refreshAnnouncementResult(announcementItemsModels);
        }


        //if (savedInstanceState == null) {
        String title = "";
        Fragment fragment = new ExhibitorUserMyDashboard();
        title = getString(R.string.head_dashboard);
        CallFragmnet(fragment, title);
        // }
    }

//    private void initNavigation() {
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        });
//
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        // setContentView(R.layout.nav_header_main);
//        View header = navigationView.getHeaderView(0);
//        username_TV = (TextView) header.findViewById(R.id.username_TV1);
//        place_TV = (TextView) header.findViewById(R.id.place_TV);
//        if (loginResponse.getCompany() != null) {
//
//            if (loginResponse.getCompany().getCountry() != null) {
//                place_TV.setVisibility(View.VISIBLE);
//                place_TV.setText(loginResponse.getCompany().getCountry());
//            }
//        }
//        if(loginResponse!=null) {
//            username_TV.setText(getResources().getString(R.string.welcom) + "  " + loginResponse.getUser().getUserName());
//        }
//        navigationView.setItemIconTintList(null);
//        navigationView.setNavigationItemSelectedListener(this);
//        drawer.closeDrawer(navigationView);
//
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        Fragment fragment = null;
//        String title = getString(R.string.app_name);
////        if (id == R.id.nav_event) {
////            fragment = new Fragment_Main();
////            title = getString(R.string.events);
////           // CallFragmnet(fragment, title);
////            CustomAlert.alertWithOk(context,"Under development");
////        }
//        if (id == R.id.nav_logout) {
//            File dir = new File(Environment.getExternalStorageDirectory(),File.separator+context.getPackageName()+File.separator);
//            try {
//                boolean b = AppUtilityFunction.deleteDirectory(dir);
//                Log.d("FILE","deleted"+b);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, activity);
//            ProjectPrefrence.removeSharedPrefrenceData(AppUtilityFunction.MY_PREFERENCES, "is_first", activity);
//            PreferenceManager.getDefaultSharedPreferences(activity).edit().clear().apply();
//            Intent logoutIntent = new Intent(activity, NewDashboardActivity.class);
//            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(logoutIntent);
//            finish();
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        }
//        else
//        {
//            CustomAlert.alertWithOk(context,"Under development");
//
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    /* ======== Call to replace fragment for drawer item ========*/
    private void CallFragmnet(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  fragmentTransaction.setCustomAnimations(R.anim.push_in_from_left, 0, 0, R.anim.push_out_to_left);
            fragmentTransaction.replace(R.id.container_body, fragment);
            //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
            fragmentTransaction.commit();
            //Set the Toolbar Title
            //getSupportActionBar().setTitle(title);
        }
    }

    public String loadAnnouncementJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("Announcements.json");
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

    // Image Slider
    private void refreshAnnouncementResult(ArrayList<AnnouncementItemsModel>
                                                   announcementItemsModel) {


        imageList();
        if (viewPagerAdapter == null) {
            pagerlayout.setVisibility(View.GONE);
        } else {

            pagerlayout.setVisibility(View.VISIBLE);
            btnViewPagerLayout.removeAllViews();

            for (int i = 0; i < announcementItemsModel.size(); i++) {
                View view = layoutInflater.inflate(R.layout.button_layout, null);
                Button button = (Button) view.findViewById(R.id.btn1);
                if (i == 0) {
                    button.setBackgroundResource(R.drawable.circle);
                }
                btnViewPagerLayout.addView(view);
            }

        }

    }


    private void btnAction(int action) {
        for (int i = 0; i < btnViewPagerLayout.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) btnViewPagerLayout.getChildAt(i);
            Button button = (Button) layout.findViewById(R.id.btn1);
            button.setBackgroundResource(R.drawable.circle2);
            if (action == i) {
                button.setBackgroundResource(R.drawable.circle);
            }
        }

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<AnnouncementItemsModel> announcementList;
        AnnouncementItemsModel announcementItemsModel;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<AnnouncementItemsModel> announcementItemsModels) {
            super(fm);
            announcementList = announcementItemsModels;
        }

        public ViewPagerAdapter(FragmentManager fm, AnnouncementItemsModel announcementItemsModels) {
            super(fm);
            announcementItemsModels = announcementItemsModels;
        }

        @Override
        public Fragment getItem(int index) {

            ImageSliderFragment fragment = new ImageSliderFragment();
            fragment.setImageListPos(index);
            fragment.setAnnouncementItemsModel(announcementList.get(index));

            return fragment;
        }

        @Override
        public int getCount() {

            return announcementList.size();
        }


    }

    private void timer_update() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPageNumber == NUM_PAGES) {
                    currentPageNumber = 0;
                }
                pager.setCurrentItem(currentPageNumber++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);
    }


    private void imageList() {
        if (announcementItemsModels.size() > 0) {

            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                    announcementItemsModels);

            pager.setAdapter(viewPagerAdapter);
            NUM_PAGES = announcementItemsModels.size();
            if (announcementItemsModels.size() > 1) {
                Log.d("list animation:", "true list size");
                //  setupTimer();
                timer_update();

            }


        }

    }

    public void logout() {
        File dir = new File(Environment.getExternalStorageDirectory(), File.separator + context.getPackageName() + File.separator);
        try {
            boolean b = AppUtilityFunction.deleteDirectory(dir);
            Log.d("FILE", "deleted" + b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.MSGRCVD_DETAILS, context);

        ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context);
        ProjectPrefrence.removeSharedPrefrenceData(AppUtilityFunction.MY_PREFERENCES, "is_first", context);
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
        Intent logoutIntent = new Intent(context, NewDashboardActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}
