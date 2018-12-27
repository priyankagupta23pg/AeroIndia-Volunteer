package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.aeroindia.pojos.generic.ServiceModel;
import com.aeroindia.pojos.response.AllServicesResponse;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolleyGet;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends BaseAppCompatActivity {
RelativeLayout backLayout;
private TextView headerTV;
private Context context;
private RecyclerView recyclerView;
    private ArrayList<ServiceModel> data=new ArrayList<>();
    private ServiceAdapter adapter;
    private AllServicesResponse allServicesResponse;
    ArrayList<AllServicesResponse.services> servicesLocal=new ArrayList<>();
    private SwipeRefreshLayout serviceRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        context=this;
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.services));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<ServiceModel>();
//        StaggeredGridLayoutManager staggeredGridLayoutManagerVertical =
//                new StaggeredGridLayoutManager(
//                        3, //The number of Columns in the grid
//                        LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
//         data.add(new ServiceModel("1","Restaurant"));
//         data.add(new ServiceModel("2","Restroom"));
//         data.add(new ServiceModel("3","Coffee Shop"));
//         data.add(new ServiceModel("4","Entry Gate"));
//         data.add(new ServiceModel("5","Wi-fi"));
//         data.add(new ServiceModel("6","Other"));
        //  data.add(new ExhibitorModel("company2","Hall B","#13"));
        adapter = new ServiceAdapter(context, data);
        recyclerView.setAdapter(adapter);
        serviceRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.serviceRefreshLayout);
        serviceRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtilityFunction.isNetworkAvailable(context)) {
                   /* servicesLocal= DatabaseOpration.getServiceData(context);
                    if(servicesLocal.size()>0)
                    {

                        for (int i = 0; i < servicesLocal.size(); i++) {

                            data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));

                        }

                        adapter.notifyDataSetChanged();
                    }*/
                    getAllServicesList();
                }else {
                    Toast.makeText(context,"Please connect internet to refresh data",Toast.LENGTH_LONG).show();
                }
                serviceRefreshLayout.setRefreshing(false);
            }
        });
        if(AppUtilityFunction.isNetworkAvailable(context)) {
            servicesLocal= DatabaseOpration.getServiceData(context);
            if(servicesLocal.size()>0)
            {

                for (int i = 0; i < servicesLocal.size(); i++) {

                    data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));

                }

                adapter.notifyDataSetChanged();
            }
            getAllServicesList();
        }
        else
        {
            servicesLocal= DatabaseOpration.getServiceData(context);
            if(servicesLocal.size()>0)
            {

                for (int i = 0; i < servicesLocal.size(); i++) {

                    data.add(new ServiceModel(servicesLocal.get(i).getId(), servicesLocal.get(i).getServiceCode(),servicesLocal.get(i).getServiceName()));

                }

                adapter.notifyDataSetChanged();
            }
        }
    }
    public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

        private ArrayList<ServiceModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView serviceTv;
            private RelativeLayout lay2;
            private ImageView icon;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.serviceTv = (TextView) itemView.findViewById(R.id.serviceTv);
                this.lay2 = (RelativeLayout) itemView.findViewById(R.id.lay2);
                this.icon = (ImageView) itemView.findViewById(R.id.icon);

            }


        }
        public void addAll(List<ServiceModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public ServiceAdapter(Context context, ArrayList<ServiceModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.service_item_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final ServiceAdapter.MyViewHolder holder, final int listPosition) {


            final ServiceModel serviceModel=dataSet.get(listPosition);
holder.serviceTv.setText(serviceModel.getServiceName());
if(serviceModel.getServiceName().toLowerCase().contains("restaurant"))
{
holder.icon.setBackgroundResource(R.drawable.restaurant);
}
else if(serviceModel.getServiceName().toLowerCase().contains("hall"))
{
    holder.icon.setBackgroundResource(R.drawable.hall);

}
else if(serviceModel.getServiceName().toLowerCase().contains("toilets"))
{
    holder.icon.setBackgroundResource(R.drawable.restroom);

}
else
{
    holder.icon.setBackgroundResource(R.drawable.hall);

}
holder.lay2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

            Intent intent = new Intent(context, AirforceServicesActivity.class);
            intent.putExtra("servicename",serviceModel.getServiceName());
            startActivity(intent);




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

    private void getAllServicesList() {
       // showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                allServicesResponse = AllServicesResponse.create(response);
                if (allServicesResponse != null) {


                    if (allServicesResponse.isStatus()) {
                        if (allServicesResponse.services != null) {
                            if(allServicesResponse.services.size()>0) {
                                data.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < allServicesResponse.services.size(); i++) {

                                    data.add(new ServiceModel(allServicesResponse.services.get(i).getId(), allServicesResponse.services.get(i).getServiceCode(),allServicesResponse.services.get(i).getServiceName()));
                                    AllServicesResponse.services servicesModel= CommonDatabaseAero.updateService(context,allServicesResponse.services.get(i));
                                    if(servicesModel==null)
                                    {
                                        CommonDatabaseAero.saveService(context,allServicesResponse.services.get(i));

                                    }
                                }

                                adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                CustomAlert.alertOkWithFinish(context, "There is no services available for now");

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no services available for now");

                        }
                    }
                   // showHideProgressDialog(false);

                }
            }


            @Override
            public void onError(VolleyError error) {

               // showHideProgressDialog(false);
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
        CustomVolleyGet volley=new CustomVolleyGet(taskListener, null,AppConstant.ALLSERVICES_API,context);
        volley.execute();
    }
}
