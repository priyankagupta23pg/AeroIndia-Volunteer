package com.aeroindia.view.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.generic.DashboardProviderModel;
import com.aeroindia.pojos.request.DashboardCountRequest;
import com.aeroindia.pojos.response.DashboardCountResponse;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.activity.ComplaintsActivity;
import com.aeroindia.view.activity.NewDashboardActivity;
import com.aeroindia.view.activity.VolunteerScanQRActivity;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.ui.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerDashboard extends Fragment {

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
    private static ArrayList<DashboardProviderModel> data;
private LoginResponse loginResponse;
private DashboardCountResponse dashboardCountResponse;
private StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;
private SwipeRefreshLayout dashRefreshLayout;
    public VolunteerDashboard() {
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

        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, getActivity()));

        if(loginResponse!=null) {
            if (loginResponse.getCompany() != null) {
                if(loginResponse.getCompany().getCompanyId()!=0) {
                    DashboardCountRequest dashboardCountRequest = new DashboardCountRequest(loginResponse.getCompany().getCompanyId(),loginResponse.getUser().getCategory());
                    getCounts(dashboardCountRequest);
                }
            }
        }

        dashRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.dashRefreshLayout);
        dashRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtilityFunction.isNetworkAvailable(context)) {
                    if(loginResponse!=null) {
                        if (loginResponse.getCompany() != null) {
                            if(loginResponse.getCompany().getCompanyId()!=0) {
                                DashboardCountRequest dashboardCountRequest = new DashboardCountRequest(loginResponse.getCompany().getCompanyId(),loginResponse.getUser().getCategory());
                                getCounts(dashboardCountRequest);
                            }
                        }
                    }
                }else {
                    Toast.makeText(context,"Please connect internet to refresh data",Toast.LENGTH_LONG).show();
                }
                dashRefreshLayout.setRefreshing(false);
            }
        });

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


        private ArrayList<DashboardProviderModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {


            ImageView img_IV;
            RelativeLayout lay;
            private TextView txt_TV,countTV;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.txt_TV = (TextView) itemView.findViewById(R.id.txt_TV);
                this.countTV = (TextView) itemView.findViewById(R.id.countTV);
                this.img_IV = (ImageView) itemView.findViewById(R.id.img_IV);
                this.lay = (RelativeLayout) itemView.findViewById(R.id.lay);


            }


        }

        public void addAll(List<DashboardProviderModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }

        public DashboardAdapter(Context context, ArrayList<DashboardProviderModel> data) {

            this.dataSet = data;
            this.context = context;

        }

        @Override
        public DashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dashboardprovider_item_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            DashboardAdapter.MyViewHolder myViewHolder = new DashboardAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final DashboardAdapter.MyViewHolder holder, final int listPosition) {

            holder.img_IV.setTag(dataSet.get(listPosition));
//            if(AppUtility.isTablet(context))
//            {
//                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder.imageViewIcon.getLayoutParams();
//                params.height = 400;
//// existing height is ok as is, no need to edit it
//                holder.imageViewIcon.setLayoutParams(params);
//            }
//
            holder.txt_TV.setText(dataSet.get(listPosition).getLabel());
           // holder.img_IV.setBackgroundResource(dataSet.get(listPosition).getImg());
            if(dataSet.get(listPosition).getCount()!=-1)
            {
                holder.countTV.setVisibility(View.VISIBLE);
                holder.countTV.setText(dataSet.get(listPosition).getCount()+"");
                holder.img_IV.setVisibility(View.GONE);

            }
            else
            {
                holder.countTV.setVisibility(View.GONE);
                holder.img_IV.setVisibility(View.VISIBLE);
                if(listPosition==3)
                {
                     holder.img_IV.setBackgroundResource(R.drawable.icon_scan);

                }
                else
                {
                    holder.img_IV.setBackgroundResource(R.drawable.logout);

                }
            }
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listPosition == 0) {
                        Intent intent=new Intent(context,ComplaintsActivity.class);
                        intent.putExtra("type","P");
                        startActivity(intent);
                        getActivity().finish();


                    } else if (listPosition == 1) {
                        Intent intent=new Intent(context,ComplaintsActivity.class);
                        intent.putExtra("type","R");
                        startActivity(intent);
                        getActivity().finish();

                    }
                    else if (listPosition == 2) {
                        Intent intent=new Intent(context,ComplaintsActivity.class);
                        intent.putExtra("type","A");
                        startActivity(intent);
                        getActivity().finish();
              }
                    else if (listPosition == 3) {
                        Intent intent=new Intent(context,ComplaintsActivity.class);
                        intent.putExtra("type","NA");
                        startActivity(intent);
                        getActivity().finish();
                    }
//  else if (listPosition == 3) {
//
//Intent intent=new Intent(context,VolunteerScanQRActivity.class);
//startActivity(intent);
//getActivity().finish();
//
//                    }
//                    else if(listPosition ==3)
//                    {
//
//
//                    }



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


    private void getCounts(DashboardCountRequest request){
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                dashboardCountResponse= DashboardCountResponse.create(response);

                data = new ArrayList<DashboardProviderModel>();
                staggeredGridLayoutManagerVertical =
                        new StaggeredGridLayoutManager(
                                2, //The number of Columns in the grid
                                LinearLayoutManager.VERTICAL);
                if(dashboardCountResponse.isStatus()){
                    data.add(new DashboardProviderModel("Pending Complaints", dashboardCountResponse.getPendingCount()));
                    data.add(new DashboardProviderModel("Resolve Complaints", dashboardCountResponse.getResolveCount()));
                    data.add(new DashboardProviderModel("Attended Complaints", dashboardCountResponse.getAttendedCount()));
                    data.add(new DashboardProviderModel("NonAttended Complaints", dashboardCountResponse.getNoattendedCount()));
                   // data.add(new DashboardProviderModel("Scan QR Code", -1));
                   // data.add(new DashboardProviderModel("Logout", -1));





                    recyclerView.setLayoutManager(staggeredGridLayoutManagerVertical);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new DashboardAdapter(getActivity(), data);
                    recyclerView.setAdapter(adapter);

                }else{
                    data.add(new DashboardProviderModel("Pending Complaints",0));
                    data.add(new DashboardProviderModel("Resolve Complaints", 0));
                    data.add(new DashboardProviderModel("Attended Complaints",0));
                    data.add(new DashboardProviderModel("NonAttended Complaints",0));
                   // data.add(new DashboardProviderModel("Scan QR Code", -1));
                   // data.add(new DashboardProviderModel("Logout", -1));





                    recyclerView.setLayoutManager(staggeredGridLayoutManagerVertical);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new DashboardAdapter(getActivity(), data);
                    recyclerView.setAdapter(adapter);
                   // Toast.makeText(context,dashboardCountResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
                   // return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        CustomVolley volley=new CustomVolley(taskListener,null,AppConstant.DASHBOARDCOUNT_API,request.serialize(),null,null,context);
        volley.execute();
    }

}
