package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.aeroindia.pojos.response.EventModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.pojos.response.UpcomingEventsResponse;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolleyGet;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.GPSTracker;
import com.customComponent.utility.ProjectPrefrence;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventsActivity extends BaseAppCompatActivity {
    RelativeLayout backLayout;
    private TextView headerTV;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<EventModel> data = new ArrayList<>();
    private EventsAdapter adapter;
    private GPSTracker gps;
    private double latitude,longitude;
    private UpcomingEventsResponse upcomingEventsResponse;
    private LoginResponse loginResponse;
    private SwipeRefreshLayout eventRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        context = this;
        gps = new GPSTracker(this);
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.events));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<EventModel>();
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));

        eventRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.eventRefreshLayout);
        eventRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtilityFunction.isNetworkAvailable(context)) {
/*
                    data= DatabaseOpration.getEventData(context);
                    if(data.size()>0)
                    {

                        adapter = new EventsAdapter(context, data,latitude,longitude);
                        recyclerView.setAdapter(adapter);
                    }*/
                    getAllEvents();
                }else {
                    Toast.makeText(context,"Please connect internet to refresh data",Toast.LENGTH_LONG).show();
                }
                eventRefreshLayout.setRefreshing(false);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            adapter = new EventsAdapter(context, data,latitude,longitude);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            gps.showSettingsAlert();

        }
        if(AppUtilityFunction.isNetworkAvailable(context)) {

            data= DatabaseOpration.getEventData(context);
            if(data.size()>0)
            {

                adapter = new EventsAdapter(context, data,latitude,longitude);
                recyclerView.setAdapter(adapter);
            }
            getAllEvents();
        }
        else
        {
            data= DatabaseOpration.getEventData(context);
            if(data.size()>0)
            {

                adapter = new EventsAdapter(context, data,latitude,longitude);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

        private ArrayList<EventModel> dataSet;
        Context context;
        private double latt, longg;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView titleTv, dateTv, venuTv, venuLoc, typeTv;
            private ImageView imgIcon;
            private RelativeLayout locLayout,feedbackRL;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
                this.dateTv = (TextView) itemView.findViewById(R.id.dateTv);
                this.venuTv = (TextView) itemView.findViewById(R.id.venuTv);
                this.venuLoc = (TextView) itemView.findViewById(R.id.venuLoc);
                this.typeTv = (TextView) itemView.findViewById(R.id.typeTv);
                this.imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
                this.locLayout = (RelativeLayout) itemView.findViewById(R.id.locLayout);
                this.feedbackRL = (RelativeLayout) itemView.findViewById(R.id.feedbackRL);

            }


        }

        public void addAll(List<EventModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }

        public EventsAdapter(Context context, ArrayList<EventModel> data) {

            this.dataSet = data;
            this.context = context;

        }   public EventsAdapter(Context context, ArrayList<EventModel> data,double lattitude,double longitude) {

            this.dataSet = data;
            this.context = context;
            this.latt=lattitude;
            this.longg=longitude;

        }

        @Override
        public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.eventsitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            EventsAdapter.MyViewHolder myViewHolder = new EventsAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final EventsAdapter.MyViewHolder holder, final int listPosition) {


            final EventModel eventModel = dataSet.get(listPosition);
            holder.titleTv.setText(eventModel.getEventName());
            holder.dateTv.setText( AppUtilityFunction.getDate(eventModel.getDateTime(),AppConstant.DATE_FORMAT));
            holder.venuTv.setText(eventModel.getVenue());
            holder.typeTv.setText(eventModel.getEventType() + " - ");
            if (eventModel.getEventType().equalsIgnoreCase("Seminar")) {
                holder.imgIcon.setBackgroundResource(R.drawable.seminar_img);
            } else {
                holder.imgIcon.setBackgroundResource(R.drawable.airshow);

            }
            /*holder.venuLoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  callMapRedirectionMethod();
                }
            });*/
//            if(loginResponse==null)
//            {
//                holder.feedbackRL.setVisibility(View.GONE);
//            }
//            else {
//                holder.feedbackRL.setVisibility(View.VISIBLE);
//
//
//            }


            holder.locLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // callMapRedirectionMethod();
//                    Intent intent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+eventModel.getLatitude()+","+eventModel.getLongitude()));
//                    startActivity(intent);

                    Intent showLatLongIntent=new Intent(context,ShowLatLongActivity.class);
                    showLatLongIntent.putExtra("Latt",eventModel.getLatitude());
                    showLatLongIntent.putExtra("Longg",eventModel.getLongitude());
                    showLatLongIntent.putExtra("Venue",eventModel.getVenue());
                    startActivity(showLatLongIntent);
                }
            });

            holder.feedbackRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(loginResponse!=null) {
                        if (AppUtilityFunction.isOld(AppUtilityFunction.getDate(eventModel.getDateTime(), AppConstant.DATE_FORMAT))) {

                            Intent intent = new Intent(context, FeedbackActivity.class);
                            if (eventModel.getEventType().equalsIgnoreCase("Seminar")) {

                                intent.putExtra("seminarId", eventModel.getId());
                            } else {
                                intent.putExtra("airshowId", eventModel.getId());

                            }
                            startActivity(intent);
                        } else {
                            CustomAlert.alertWithOk(context, "You can feedback for this event once this ends.");
                        }
                    }
                    else
                    {
                        Intent logoutIntent=new Intent(context,NewDashboardActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        CustomAlert.alertWithOk(context, "Please login first for giving feedback.",logoutIntent);

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

    private void callMapRedirectionMethod() {


    }
    private void getAllEvents() {
       // showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                upcomingEventsResponse = UpcomingEventsResponse.create(response);
                Log.d("event resp:",upcomingEventsResponse.serialize());
                if (upcomingEventsResponse != null) {


                    if (upcomingEventsResponse.isStatus()) {
                        if (upcomingEventsResponse.getEventList() != null) {
                            if(upcomingEventsResponse.getEventList().size()>0) {
                                data.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < upcomingEventsResponse.getEventList().size(); i++) {
                                    data.add(new EventModel(upcomingEventsResponse.getEventList().get(i).getId(),upcomingEventsResponse.getEventList().get(i).getDateTime(), upcomingEventsResponse.getEventList().get(i).getEventName(), upcomingEventsResponse.getEventList().get(i).getEventType(), upcomingEventsResponse.getEventList().get(i).getVenue(),upcomingEventsResponse.getEventList().get(i).getLatitude(),upcomingEventsResponse.getEventList().get(i).getLongitude()));

                                    EventModel updatedEventmodel=CommonDatabaseAero.updateUpcomingEvents(context,upcomingEventsResponse.getEventList().get(i));
if(updatedEventmodel==null)
{
    CommonDatabaseAero.saveUpcomingEvents(context,upcomingEventsResponse.getEventList().get(i));

}

                                }

                                adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                CustomAlert.alertOkWithFinish(context, "There is no upcoming event available for now");

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no upcoming event available for now");

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
        CustomVolleyGet volley=new CustomVolleyGet(taskListener, null, AppConstant.UPCOMING_EVENTS_API,context);
        volley.execute();
    }
}
