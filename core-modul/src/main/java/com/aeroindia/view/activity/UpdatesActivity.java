package com.aeroindia.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.response.AnnouncementModel;

import java.util.ArrayList;
import java.util.List;

public class UpdatesActivity extends AppCompatActivity {

    RelativeLayout backLayout;
    private TextView headerTV;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<AnnouncementModel> data=new ArrayList<>();
    private AnnouncementAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        context = this;
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.update));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<AnnouncementModel>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        data.add(new AnnouncementModel("We are technology focused organization with expertise in Cloud, Mobility, Integration, Data Analytics and SaaS products - Salesforce & Service Now", "25 Jan 2019 2:30 PM","HALL A"));

        adapter = new AnnouncementAdapter(context, data);
        recyclerView.setAdapter(adapter);

    }
    public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

        private ArrayList<AnnouncementModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView titleTv,dateTv,venuTv,venuLabel;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
                this.dateTv = (TextView) itemView.findViewById(R.id.dateTv);
                this.venuTv = (TextView) itemView.findViewById(R.id.venuTv);
                this.venuLabel = (TextView) itemView.findViewById(R.id.venuLabel);

            }


        }
        public void addAll(List<AnnouncementModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public AnnouncementAdapter(Context context, ArrayList<AnnouncementModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public AnnouncementAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.announcementitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            AnnouncementAdapter.MyViewHolder myViewHolder = new AnnouncementAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final AnnouncementAdapter.MyViewHolder holder, final int listPosition) {


            final AnnouncementModel serviceModel=dataSet.get(listPosition);
            holder.titleTv.setText(serviceModel.getTitle());
            holder.dateTv.setText(serviceModel.getDateTime());
            holder.venuTv.setText(serviceModel.getVenue());
            holder.venuLabel.setVisibility(View.GONE);
            holder.venuTv.setVisibility(View.GONE);
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

}
