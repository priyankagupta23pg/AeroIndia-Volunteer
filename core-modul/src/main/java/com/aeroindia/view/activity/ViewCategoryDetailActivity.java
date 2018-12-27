package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.aeroindia.pojos.request.SubServicesRequest;
import com.aeroindia.pojos.response.ServiceCategoryItemModel;
import com.aeroindia.pojos.response.SubServicesResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewCategoryDetailActivity extends BaseAppCompatActivity {

    RelativeLayout backLayout;
    private TextView headerTV;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<ServiceCategoryItemModel> data=new ArrayList<>();
    private CategorAdapter adapter;
    private ImageView marker;
    private String headerTxt;
    private SubServicesResponse subServicesResponse;
    private int serviceCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_detail);
        context = this;
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        marker = (ImageView) findViewById(R.id.marker);
        marker.setVisibility(View.VISIBLE);
        headerTV = (TextView) findViewById(R.id.headerTV);
        if(getIntent()!=null)
        {
            headerTxt=getIntent().getStringExtra("servicename");
            serviceCode=getIntent().getIntExtra("servicecode",0);

        }
        headerTV.setText(headerTxt);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<ServiceCategoryItemModel>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
//        data.add(new ServiceCategoryItemModel("1", "Barbeque Nation","1"));
//        data.add(new ServiceCategoryItemModel("2", "Eau de Monsoon","2"));

        adapter = new CategorAdapter(context, data);
        recyclerView.setAdapter(adapter);
        if(serviceCode!=0) {
            SubServicesRequest subServicesRequest = new SubServicesRequest(serviceCode);
            getSubCategories(subServicesRequest);
        }
        marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subServicesResponse!=null) {
                    Intent intent = new Intent(context, ServiceCategoryOnMapActivity.class);
                    intent.putExtra("markers",subServicesResponse.serialize());
                    startActivity(intent);
                }
            }
        });

    }
    public class CategorAdapter extends RecyclerView.Adapter<CategorAdapter.MyViewHolder> {

        private ArrayList<ServiceCategoryItemModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView titleTv,distanceLabel;
            private ImageView icon;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
                this.distanceLabel = (TextView) itemView.findViewById(R.id.distanceLabel);
                this.icon = (ImageView) itemView.findViewById(R.id.icon);

            }


        }
        public void addAll(List<ServiceCategoryItemModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public CategorAdapter(Context context, ArrayList<ServiceCategoryItemModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.categoryitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

           MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


            final ServiceCategoryItemModel serviceModel=dataSet.get(listPosition);
holder.titleTv.setText(serviceModel.getCategoryName());
if(serviceModel.getDistance()!=null) {
    holder.distanceLabel.setText(serviceModel.getDistance());
}

            if(headerTxt.toLowerCase().contains("restaurant"))
            {
                holder.icon.setBackgroundResource(R.drawable.restaurant);
            }
            else if(headerTxt.toLowerCase().contains("hall"))
            {
                holder.icon.setBackgroundResource(R.drawable.hall);

            }
            else if(headerTxt.toLowerCase().contains("toilets"))
            {
                holder.icon.setBackgroundResource(R.drawable.restroom);

            }
            else
            {
                holder.icon.setBackgroundResource(R.drawable.hall);

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
    private void getSubCategories(SubServicesRequest request){
        showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                subServicesResponse= SubServicesResponse.create(response);

                showHideProgressDialog(false);

                if(subServicesResponse.isStatus()){
                    if(subServicesResponse.subServices.size()>0) {
                        data.clear();
                        // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                        for (int i = 0; i < subServicesResponse.subServices.size(); i++) {

                            data.add(new ServiceCategoryItemModel(subServicesResponse.subServices.get(i).getServiceCode(), subServicesResponse.subServices.get(i).getSubServceName(),subServicesResponse.subServices.get(i).getSubServiceCode(),subServicesResponse.subServices.get(i).getDistance()));

                        }

                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        CustomAlert.alertOkWithFinish(context, "There is no subservices available for now");

                    }


                }else{
                    if(subServicesResponse.getErrorMessage()!=null) {
                        Toast.makeText(context, subServicesResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                showHideProgressDialog(false);

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
        CustomVolley volley=new CustomVolley(taskListener,null, AppConstant.SUBSERVICES_API,request.serialize(),null,null,context);
        volley.execute();
    }

}
