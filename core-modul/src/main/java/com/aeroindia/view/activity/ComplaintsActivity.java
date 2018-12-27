package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.GetComplaintRequest;
import com.aeroindia.pojos.response.ClaimListModel;
import com.aeroindia.pojos.response.GetComplaintResponse;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsActivity extends BaseAppCompatActivity {
RelativeLayout backLayout;
private TextView headerTV;
private Context context;
private RecyclerView recyclerView;
    private ArrayList<ClaimListModel> data=new ArrayList<>();
    private ComplaintsAdapter adapter;
    private GetComplaintResponse getComplaintResponse;
   // private SwipeRefreshLayout serviceRefreshLayout;
    private String type;
    private LoginResponse loginResponse;
    private int userDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        context=this;
        getSupportActionBar().hide();
        if(getIntent()!=null)
        {
            type=getIntent().getStringExtra("type");
        }
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
if(loginResponse!=null) {
    if (loginResponse.getCompany() != null) {
        if (loginResponse.getCompany().getCompanyId() != 0) {
            userDetailId=loginResponse.getCompany().getCompanyId();
        }
    }
}
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        if(type.equalsIgnoreCase("P")) {
            headerTV.setText(getResources().getString(R.string.pendinglist));
        }
        else if(type.equalsIgnoreCase("R"))
        {
            headerTV.setText(getResources().getString(R.string.resolvelist));

        }
        else if(type.equalsIgnoreCase("A"))
        {
            headerTV.setText(getResources().getString(R.string.attendedlist));

        }
        else
        {
            headerTV.setText(getResources().getString(R.string.nonattendedlist));

        }
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DashboardVolunteerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<ClaimListModel>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        GetComplaintRequest getComplaintRequest=new GetComplaintRequest();
        getComplaintRequest.setStatus(type);
        getComplaintRequest.setUserDetailId(userDetailId);
        getComplaintList(getComplaintRequest);

      //  serviceRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.serviceRefreshLayout);
//        serviceRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if(AppUtilityFunction.isNetworkAvailable(context)) {
//
//                    getAllServicesList();
//                }else {
//                    Toast.makeText(context,"Please connect internet to refresh data",Toast.LENGTH_LONG).show();
//                }
//                serviceRefreshLayout.setRefreshing(false);
//            }
//        });
//        if(AppUtilityFunction.isNetworkAvailable(context)) {
//            servicesLocal= DatabaseOpration.getServiceData(context);
//            if(servicesLocal.size()>0)
//            {
//
//                for (int i = 0; i < servicesLocal.size(); i++) {
//
//                    data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));
//
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//            getAllServicesList();
//        }
//        else
//        {
//            servicesLocal= DatabaseOpration.getServiceData(context);
//            if(servicesLocal.size()>0)
//            {
//
//                for (int i = 0; i < servicesLocal.size(); i++) {
//
//                    data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));
//
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//        }
    }
    @Override
    public void onBackPressed()
    {
        backLayout.performClick();
    }
     class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.MyViewHolder> {

        private ArrayList<ClaimListModel> dataSet;
        Context context;

         class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView serviceTv,statusTv,servicedate,attendBtn,resolveBtn,notAttendedBtn,resolvedbyVal,resolvedbyTv;
            private LinearLayout linear;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.serviceTv = (TextView) itemView.findViewById(R.id.serviceTv);
                this.statusTv = (TextView) itemView.findViewById(R.id.statusTv);
                this.servicedate = (TextView) itemView.findViewById(R.id.servicedate);
                this.attendBtn = (TextView) itemView.findViewById(R.id.attendBtn);
                this.resolveBtn = (TextView) itemView.findViewById(R.id.resolveBtn);
                this.notAttendedBtn = (TextView) itemView.findViewById(R.id.notAttendedBtn);
                this.resolvedbyVal = (TextView) itemView.findViewById(R.id.resolvedbyVal);
                this.resolvedbyTv = (TextView) itemView.findViewById(R.id.resolvedbyTv);
                this.linear = (LinearLayout) itemView.findViewById(R.id.linear);

            }


        }
        public void addAll(List<ClaimListModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public ComplaintsAdapter(Context context, ArrayList<ClaimListModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.complaint_item_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final ComplaintsAdapter.MyViewHolder holder, final int listPosition) {


            final ClaimListModel claimListModel=dataSet.get(listPosition);
            if(claimListModel.getServiceName()!=null) {
                holder.serviceTv.setText(claimListModel.getServiceName());
            }
            if(claimListModel.getStatus().equalsIgnoreCase("P")) {
                holder.statusTv.setText("Pending");
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
            }
            else if(claimListModel.getStatus().equalsIgnoreCase("R")) {
                holder.statusTv.setText("Resolved");
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));


            }
            else if(claimListModel.getStatus().equalsIgnoreCase("A")) {

                holder.statusTv.setText("Attended");
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.primary_color_yellow));

            }
            else if(claimListModel.getStatus().equalsIgnoreCase("NA")) {

                holder.statusTv.setText("Not Attended");
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.purple));

            }
                if(type.equalsIgnoreCase("P"))
{
   holder.servicedate.setText(AppUtilityFunction.getDate(claimListModel.getCreatedOn(), AppConstant.MSG_DATE_FORMAT));
holder.attendBtn.setVisibility(View.VISIBLE);
holder.resolveBtn.setVisibility(View.GONE);
holder.notAttendedBtn.setVisibility(View.GONE);
    holder.resolvedbyVal.setVisibility(View.GONE);
    holder.resolvedbyTv.setVisibility(View.GONE);
}
else if(type.equalsIgnoreCase("A")) {
    holder.attendBtn.setVisibility(View.GONE);
    holder.resolveBtn.setVisibility(View.VISIBLE);
    holder.notAttendedBtn.setVisibility(View.VISIBLE);
    holder.resolvedbyVal.setVisibility(View.GONE);
    holder.resolvedbyTv.setVisibility(View.GONE);
    if (claimListModel.getUpdatedOn() != 0) {
        holder.servicedate.setText(AppUtilityFunction.getDate(claimListModel.getUpdatedOn(), AppConstant.MSG_DATE_FORMAT));
    }
    else
    {
        holder.servicedate.setVisibility(View.GONE);

    }
}
else if(type.equalsIgnoreCase("R"))
    {
        holder.attendBtn.setVisibility(View.GONE);
        holder.resolveBtn.setVisibility(View.GONE);
        holder.notAttendedBtn.setVisibility(View.GONE);
        holder.resolvedbyVal.setVisibility(View.VISIBLE);
        holder.resolvedbyTv.setVisibility(View.VISIBLE);
        holder.resolvedbyVal.setText(""+claimListModel.getUpdatedBy());

        if (claimListModel.getUpdatedOn() != 0) {
            holder.servicedate.setText(AppUtilityFunction.getDate(claimListModel.getUpdatedOn(), AppConstant.MSG_DATE_FORMAT));
        }
        else
        {
            holder.servicedate.setVisibility(View.GONE);

        }
    }
                else
                {
                    holder.attendBtn.setVisibility(View.GONE);
                    holder.resolveBtn.setVisibility(View.GONE);
                    holder.notAttendedBtn.setVisibility(View.GONE);
                    holder.resolvedbyVal.setVisibility(View.GONE);
                    holder.resolvedbyTv.setVisibility(View.GONE);
                   // holder.resolvedbyVal.setText(""+claimListModel.getUpdatedBy());

                    if (claimListModel.getUpdatedOn() != 0) {
                        holder.servicedate.setText(AppUtilityFunction.getDate(claimListModel.getUpdatedOn(), AppConstant.MSG_DATE_FORMAT));
                    }
                    else
                    {
                        holder.servicedate.setVisibility(View.GONE);

                    }
                }
            holder.linear.removeAllViews();

            if(claimListModel.getClaimList()!=null) {
        String[] issuesList = claimListModel.getClaimList().split(",");
        for (String issue : issuesList) {
            TextView textView = new TextView(context);
            textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            textView.setText("-> "+issue);
            holder.linear.addView(textView);
        }
    }
    else
    {
        holder.linear.removeAllViews();
    }
            holder.attendBtn.setVisibility(View.GONE);
//            holder.attendBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context,ProviderScanQRActivity.class);
//                    startActivity(intent);
//                }
//            });
            holder.resolveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,VolunteerScanQRResolveActivity.class);
                    intent.putExtra("claimId",claimListModel.getId());
                    intent.putExtra("type",type);
                    intent.putExtra("claimList",claimListModel.getClaimList());
                    intent.putExtra("serviceNm",claimListModel.getServiceName());
                    startActivity(intent);
                    finish();
                }
            });
            holder.notAttendedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,VolunteerScanQRNotattendActivity.class);
                    intent.putExtra("claimId",claimListModel.getId());
                    intent.putExtra("type",type);
                    intent.putExtra("claimList",claimListModel.getClaimList());
                    intent.putExtra("serviceNm",claimListModel.getServiceName());
                    startActivity(intent);
                    finish();

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

    private void getComplaintList(GetComplaintRequest request) {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {

                getComplaintResponse = GetComplaintResponse.create(response);
                if (getComplaintResponse != null) {


                    if (getComplaintResponse.isStatus()) {
                        if (getComplaintResponse.claimList != null) {
                            if(getComplaintResponse.claimList.size()>0) {
                                data.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < getComplaintResponse.claimList.size(); i++) {

                                    data.add(new ClaimListModel(getComplaintResponse.claimList.get(i).getId(), getComplaintResponse.claimList.get(i).getBarcodeId(),getComplaintResponse.claimList.get(i).getStarring(),getComplaintResponse.claimList.get(i).getCreatedBy(),getComplaintResponse.claimList.get(i).getComment(),getComplaintResponse.claimList.get(i).getClaimList(),getComplaintResponse.claimList.get(i).getStatus(),getComplaintResponse.claimList.get(i).getRemarks(),getComplaintResponse.claimList.get(i).getCreatedOn(),getComplaintResponse.claimList.get(i).getUpdatedOn(),getComplaintResponse.claimList.get(i).getUpdatedBy(),getComplaintResponse.claimList.get(i).getServiceName()));
//                                    AllServicesResponse.services servicesModel= CommonDatabaseAero.updateService(context,allServicesResponse.services.get(i));
//                                    if(servicesModel==null)
//                                    {
//                                        CommonDatabaseAero.saveService(context,allServicesResponse.services.get(i));
//
//                                    }
                                }

                                adapter = new ComplaintsAdapter(context, data);
                                recyclerView.setAdapter(adapter);
                            }
                            else
                            {
                                CustomAlert.alertWithOk(context, "There is no complaints available for now",new Intent(context,DashboardVolunteerActivity.class));

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no services available for now",new Intent(context,DashboardVolunteerActivity.class));

                        }
                    }
                    showHideProgressDialog(false);

                }
            }


            @Override
            public void onError(VolleyError error) {

               showHideProgressDialog(false);
                if (error.getMessage() != null) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }
                } else {
                    CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                }



            }
        };
        CustomVolley volley = new CustomVolley(taskListener, null, AppConstant.COMPLAINTS_API, request.serialize(), null, null, context);
        volley.execute();
    }
}
