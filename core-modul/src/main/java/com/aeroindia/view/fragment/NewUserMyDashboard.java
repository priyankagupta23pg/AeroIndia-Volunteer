package com.aeroindia.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.DashboardModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.activity.AirforceActivity;
import com.aeroindia.view.activity.AnnouncementsActivity;
import com.aeroindia.view.activity.ContactExhibitor;
import com.aeroindia.view.activity.NewDashboardActivity;
import com.aeroindia.view.activity.ServicesActivity;
import com.aeroindia.view.activity.UpcomingEventsActivity;
import com.aeroindia.view.activity.WriteFeedbackActivity;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.ui.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserMyDashboard extends Fragment {

    private View rootView;
    private Slider slider;
    private FrameLayout main_frame;
    private RelativeLayout event_relative;
    private Fragment_Main fragment_main;
    private LinearLayout dash_menu_linear;
    private ImageView event_IV, personalise_IV, b2b_IV, exhibitor_IV, nearby_IV, more_IV;
    private Boolean selectedEvent;
    private ImageView[] imageViews = new ImageView[4];
    private Context context;
    private RelativeLayout contactExhibitorRel, contactOrgRel, b2bMeetingRel, myWallLay, bookServicesLay, notiLay;
    public static int CONTACT_EXHIBITOR = 1, CONTACT_ORGANIZER = 2;
    public static String TAG = "TAG";
    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private static ArrayList<DashboardModel> data;
private LoginResponse loginResponse;
    public NewUserMyDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.my_dashboard, container, false);
        setup();
        return rootView;
    }


    private void setup() {
        context = getActivity();
        main_frame = (FrameLayout) rootView.findViewById(R.id.main_frame);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        3, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, getActivity()));

        data = new ArrayList<DashboardModel>();

if(loginResponse!=null) {
    data.add(new DashboardModel("Where am I?", R.drawable.ic_where_ami));
   // data.add(new DashboardModel("Exhibition Layout", R.drawable.ic_where_ami));
    data.add(new DashboardModel("Upcoming Events", R.drawable.ic_upcoming_event));

    data.add(new DashboardModel("Services", R.drawable.ic_services));


    data.add(new DashboardModel("Notice Board", R.drawable.ic_announcement));
    data.add(new DashboardModel("Service Complaint", R.drawable.ic_feedback));
    data.add(new DashboardModel("Contact Exhibitor", R.drawable.ic_contact_exhibitor));
   // data.add(new DashboardModel("Logout", R.drawable.logout));
}
else
{
    data.add(new DashboardModel("Where am I?", R.drawable.ic_where_ami));
   // data.add(new DashboardModel("Exhibition Layout", R.drawable.ic_where_ami));
    data.add(new DashboardModel("Upcoming Events", R.drawable.ic_upcoming_event));

    data.add(new DashboardModel("Services", R.drawable.ic_services));


    data.add(new DashboardModel("Notice Board", R.drawable.ic_announcement));
//    data.add(new DashboardModel("Report Us", R.drawable.ic_feedback));
    data.add(new DashboardModel("Exhibitor", R.drawable.ic_contact_exhibitor));
}



        recyclerView.setLayoutManager(staggeredGridLayoutManagerVertical);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new DashboardAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);


    }

    private void callMainFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment_main != null) {
            fragmentTransaction.detach(fragment_main);
            fragment_main = null;

        }
        fragment_main = new Fragment_Main();
        fragmentTransaction.replace(R.id.main_frame, fragment_main);
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //CallFragmnet(fragment);
    }

    private void callExhibitorFragment() {
        Fragment_Exhibitors fragment = new Fragment_Exhibitors();
        CallFragmnet(fragment);
    }

    private void callB2bFragment() {
        Fragment_B2BMeeting fragment = new Fragment_B2BMeeting();
        CallFragmnet(fragment);
    }

    private void callPersonaliseFragment() {
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

    private void refreshTab(int clickedIndex) {
        imageViews[0] = event_IV;
        imageViews[1] = personalise_IV;
        imageViews[2] = b2b_IV;
        imageViews[3] = exhibitor_IV;
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = imageViews[i];
            imageView.setBackgroundColor(getResources().getColor(R.color.white));
            if (clickedIndex == i) {
                imageView.setBackgroundColor(getResources().getColor(R.color.lighter_gray));
            }
        }

    }

    public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {


        private ArrayList<DashboardModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {


            ImageView img_IV;
            RelativeLayout lay;
            private TextView txt_TV;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.txt_TV = (TextView) itemView.findViewById(R.id.txt_TV);
                this.img_IV = (ImageView) itemView.findViewById(R.id.img_IV);
                this.lay = (RelativeLayout) itemView.findViewById(R.id.lay);


            }


        }

        public void addAll(List<DashboardModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }

        public DashboardAdapter(Context context, ArrayList<DashboardModel> data) {

            this.dataSet = data;
            this.context = context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dashboard_item_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            holder.img_IV.setTag(dataSet.get(listPosition));
//            if(AppUtility.isTablet(context))
//            {
//                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder.imageViewIcon.getLayoutParams();
//                params.height = 400;
//// existing height is ok as is, no need to edit it
//                holder.imageViewIcon.setLayoutParams(params);
//            }
//
            holder.txt_TV.setText(dataSet.get(listPosition).getLabelTxt());
            holder.img_IV.setBackgroundResource(dataSet.get(listPosition).getImg());
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loginResponse!=null) {
                        if (listPosition == 0) {

                            Intent theIntent = new Intent(context, AirforceActivity.class);
                            startActivity(theIntent);

                        }
//                        else if (listPosition == 1) {
//                            Intent theIntent = new Intent(context, WebViewMapActivity.class);
//                            startActivity(theIntent);
//
//                        }
                        else if (listPosition == 1) {
                            Intent theIntent = new Intent(context, UpcomingEventsActivity.class);
                            startActivity(theIntent);
                        } /*else if (listPosition == 3) {
                        Intent theIntent = new Intent(context, MyActivities.class);
                        startActivity(theIntent);
                    }*/ else if (listPosition == 2) {

                            Intent theIntent = new Intent(context, ServicesActivity.class);
                            startActivity(theIntent);


                        } else if (listPosition == 3) {

                            Intent theIntent = new Intent(context, AnnouncementsActivity.class);
                            startActivity(theIntent);


                            //CustomAlert.alertWithOk(context,"Under development");
                        } else if (listPosition == 4) {

                            Intent theIntent = new Intent(context, WriteFeedbackActivity.class);
                            startActivity(theIntent);

                        } else if (listPosition == 5) {
                            Intent theIntent = new Intent(context, ContactExhibitor.class);
                            theIntent.putExtra(TAG, CONTACT_EXHIBITOR);
                            startActivity(theIntent);
                        } /*else if (listPosition == 6) {
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


                        }*/
                    }
                    else
                    {
                        if (listPosition == 0) {

                            Intent theIntent = new Intent(context, AirforceActivity.class);
                            startActivity(theIntent);

                        }
//                        else if (listPosition == 1) {
//                            Intent theIntent = new Intent(context, WebViewMapActivity.class);
//                            startActivity(theIntent);
//
//                        }
                        else if (listPosition == 1) {
                            Intent theIntent = new Intent(context, UpcomingEventsActivity.class);
                            startActivity(theIntent);
                        } /*else if (listPosition == 3) {
                        Intent theIntent = new Intent(context, MyActivities.class);
                        startActivity(theIntent);
                    }*/ else if (listPosition == 2) {

                            Intent theIntent = new Intent(context, ServicesActivity.class);
                            startActivity(theIntent);


                        } else if (listPosition == 3) {

                            Intent theIntent = new Intent(context, AnnouncementsActivity.class);
                            startActivity(theIntent);
                        }

                            //CustomAlert.alertWithOk(context,"Under development");
//                        } else if (listPosition == 5) {
//
//                            Intent theIntent = new Intent(context, WriteFeedbackActivity.class);
//                            startActivity(theIntent);
//
//                        }
                       else if (listPosition == 4) {
                            Intent theIntent = new Intent(context, ContactExhibitor.class);
                            theIntent.putExtra(TAG, CONTACT_EXHIBITOR);
                            startActivity(theIntent);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void clearDataSource() {

            dataSet.clear();
            notifyDataSetChanged();
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
        ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.MSGRCVD_DETAILS, getActivity());

        ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, getActivity());
        ProjectPrefrence.removeSharedPrefrenceData(AppUtilityFunction.MY_PREFERENCES, "is_first", getActivity());
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().clear().apply();
        Intent logoutIntent = new Intent(getActivity(), NewDashboardActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}
