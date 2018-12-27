package com.aeroindia.view.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.CommonDatabaseAero;
import com.aeroindia.db.DatabaseOpration;
import com.aeroindia.pojos.request.GetFeedbackRequest;
import com.aeroindia.pojos.request.WallModel;
import com.aeroindia.pojos.response.FeedbackResponse;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

import java.util.ArrayList;
import java.util.List;

public class MyWallActivity extends BaseAppCompatActivity {
    RelativeLayout backLayout;
    private TextView headerTV;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<WallModel> data=new ArrayList<>();
    private WallAdapter adapter;

    ArrayList<FeedbackResponse.feedbackList> feedbackLists=new ArrayList<>();
    private FeedbackResponse feedbackResponse;
    private LoginResponse loginResponse;
    private SwipeRefreshLayout wallRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall);
        context = this;
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.my_wall));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<WallModel>();
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
//        data.add(new WallModel("Your service was awesome", "Sunaina",2,"10 Dec 2018 2:00 PM"));
//        data.add(new WallModel("Great Collection", "Wahid",5,"17 Jan 2018 7:00 PM"));
//        data.add(new WallModel("Nice service", "Vishnu",1,"10 Oct 2018 2:00 AM"));
//        data.add(new WallModel("Good products", "Ankur",3,"10 Nov 2018 1:00 PM"));
//        data.add(new WallModel("Bad service", "Jyotsna",0,"1 Dec 2018 5:30 PM"));

        adapter = new WallAdapter(context, data);
        recyclerView.setAdapter(adapter);
        wallRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.wallRefreshLayout);
        wallRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtilityFunction.isNetworkAvailable(context)) {

                   /* feedbackLists= DatabaseOpration.getMyWallData(context);
                    if(feedbackLists.size()>0)
                    {

                        for (int i = 0; i < feedbackLists.size(); i++) {

                            //  data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));
                            data.add(new WallModel(feedbackLists.get(i).getComment(), feedbackLists.get(i).getWhoGivenName(), feedbackLists.get(i).getTotalStar(), AppUtilityFunction.getDate(feedbackLists.get(i).getCreatedOn(), AppConstant.MSG_DATE_FORMAT)));

                        }

                        adapter.notifyDataSetChanged();
                    }
*/
                    if (loginResponse.getCompany() != null) {
                        if (loginResponse.getCompany().getCompanyId() != 0) {
                            GetFeedbackRequest getFeedbackRequest = new GetFeedbackRequest(loginResponse.getCompany().getCompanyId());
                            getFeedbacksList(getFeedbackRequest);
                        }
                    }
                }else {
                    Toast.makeText(context,"Please connect internet to refresh data",Toast.LENGTH_LONG).show();

                }
                wallRefreshLayout.setRefreshing(false);
            }
        });

        if(AppUtilityFunction.isNetworkAvailable(context)) {

            feedbackLists= DatabaseOpration.getMyWallData(context);
            if(feedbackLists.size()>0)
            {

                for (int i = 0; i < feedbackLists.size(); i++) {

                    //  data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));
                    data.add(new WallModel(feedbackLists.get(i).getComment(), feedbackLists.get(i).getWhoGivenName(), feedbackLists.get(i).getTotalStar(), AppUtilityFunction.getDate(feedbackLists.get(i).getCreatedOn(), AppConstant.MSG_DATE_FORMAT)));

                }

                adapter.notifyDataSetChanged();
            }

            if (loginResponse.getCompany() != null) {
                if (loginResponse.getCompany().getCompanyId() != 0) {
                    GetFeedbackRequest getFeedbackRequest = new GetFeedbackRequest(loginResponse.getCompany().getCompanyId());
                    getFeedbacksList(getFeedbackRequest);
                }
            }
        }
        else
        {
            feedbackLists= DatabaseOpration.getMyWallData(context);
            if(feedbackLists.size()>0)
            {

                for (int i = 0; i < feedbackLists.size(); i++) {

                  //  data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));
                    data.add(new WallModel(feedbackLists.get(i).getComment(), feedbackLists.get(i).getWhoGivenName(), feedbackLists.get(i).getTotalStar(), AppUtilityFunction.getDate(feedbackLists.get(i).getCreatedOn(), AppConstant.MSG_DATE_FORMAT)));

                }

                adapter.notifyDataSetChanged();
            }
        }
    }

    public class WallAdapter extends RecyclerView.Adapter<WallAdapter.MyViewHolder> {

        private ArrayList<WallModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView titleTv,dateTv,venuTv,feedbackbyTxt,dateTimeTxt;
            private ImageView img_star_1,img_star_2,img_star_3,img_star_4,img_star_5;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
                this.feedbackbyTxt = (TextView) itemView.findViewById(R.id.feedbackbyTxt);
                this.dateTimeTxt = (TextView) itemView.findViewById(R.id.dateTimeTxt);
                this.img_star_1 = (ImageView) itemView.findViewById(R.id.img_star_1);
                this.img_star_2 = (ImageView) itemView.findViewById(R.id.img_star_2);
                this.img_star_3 = (ImageView) itemView.findViewById(R.id.img_star_3);
                this.img_star_4 = (ImageView) itemView.findViewById(R.id.img_star_4);
                this.img_star_5 = (ImageView) itemView.findViewById(R.id.img_star_5);


            }


        }
        public void addAll(List<WallModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public WallAdapter(Context context, ArrayList<WallModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public WallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                         int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.wallitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            WallAdapter.MyViewHolder myViewHolder = new WallAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final WallAdapter.MyViewHolder holder, final int listPosition) {


            final WallModel wallModel=dataSet.get(listPosition);
            int rating=wallModel.getRating();
            holder.titleTv.setText(wallModel.getFeedbackTxt());
            holder.feedbackbyTxt.setText(wallModel.getFeedbackBy());
            holder.dateTimeTxt.setText(wallModel.getDateTimeTxt());
            if (rating == 1) {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.blue_star));
            } else if (rating == 2) {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.blue_star));
            } else if (rating == 3) {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.blue_star));
            } else if (rating == 4) {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.blue_star));
            } else if (rating == 5) {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.yellow_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.yellow_star));
            } else {
                holder.img_star_1.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_2.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_3.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_4.setBackground(getResources().getDrawable(R.drawable.blue_star));
                holder.img_star_5.setBackground(getResources().getDrawable(R.drawable.blue_star));

        }

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
    private void getFeedbacksList(GetFeedbackRequest getFeedbackRequest) {
     //showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                feedbackResponse = FeedbackResponse.create(response);
                if (feedbackResponse != null) {


                        if (feedbackResponse.isStatus()) {
                            if (feedbackResponse.feedbackList != null) {
                                if(feedbackResponse.feedbackList.size()>0) {
                                    data.clear();
                                    for (int i = 0; i < feedbackResponse.feedbackList.size(); i++) {
                                        data.add(new WallModel(feedbackResponse.feedbackList.get(i).getComment(), feedbackResponse.feedbackList.get(i).getWhoGivenName(), feedbackResponse.feedbackList.get(i).getTotalStar(), AppUtilityFunction.getDate(feedbackResponse.feedbackList.get(i).getCreatedOn(), AppConstant.MSG_DATE_FORMAT)));
                                        FeedbackResponse.feedbackList feedbackList= CommonDatabaseAero.updateMyWall(context,feedbackResponse.feedbackList.get(i));
                                        if(feedbackList==null)
                                        {
                                            CommonDatabaseAero.saveMyWall(context,feedbackResponse.feedbackList.get(i));

                                        }
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    CustomAlert.alertOkWithFinish(context, "There is no feedback available for now");

                                }
                            } else {
                                CustomAlert.alertWithOk(context, "There is no feedback available for now");

                            }
                        }
                    //  showHideProgressDialog(false);

                }
            }


            @Override
            public void onError(VolleyError error) {

 //  showHideProgressDialog(false);
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
        CustomVolley volley=new CustomVolley(taskListener, AppConstant.GETFEEDBACK_API,getFeedbackRequest.serialize(),null,null,context);
        volley.execute();
    }

}
