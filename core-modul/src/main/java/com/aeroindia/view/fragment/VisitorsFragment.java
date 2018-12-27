package com.aeroindia.view.fragment;

/**
 * Created by Priyanka PC on 29-01-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.generic.VisitorModel;
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.response.VisitorListResponse;
import com.aeroindia.view.activity.SendRequestActivity;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;

import java.util.ArrayList;
import java.util.List;


public class VisitorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<VisitorModel> data=new ArrayList<>();
    private VisitorAdapter adapter;
    private VisitorListResponse visitorListResponse;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exhibitor, container, false);
        Log.d("String", "YESS");
        initializeView(v);

        return v;
    }


    private void initializeView(View view) {
        recyclerView = (RecyclerView)view. findViewById(R.id.recyclerView);
        data = new ArrayList<VisitorModel>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        data.add(new VisitorModel("Mr. Shekhar Dravid","Allterewe, Heol Tynton, Llangeinor,  UK"));
//        data.add(new VisitorModel("Salil Gupta","411 Elegance Tower 8 District Centre Jasola New, New Delhi"));
//        data.add(new VisitorModel("Armand Carlier","411 Elegance Tower 8 District Centre Jasola New, New Delhi"));

//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new VisitorAdapter(getActivity(), data);

        recyclerView.setAdapter(adapter);
        getVisitorData(new EmptyRequest());
//adapter.notifyDataSetChanged();
//adapter.addAll(data);
        Log.d("SIZE OF ADAPTER",adapter.getItemCount()+"");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.MyViewHolder> {



        private ArrayList<VisitorModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView visitorNmTv,addTv,sendReqLabel;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.visitorNmTv = (TextView) itemView.findViewById(R.id.visitorNmTv);
                this.addTv = (TextView) itemView.findViewById(R.id.addTv);
                this.sendReqLabel = (TextView) itemView.findViewById(R.id.sendReqLabel);
            }


        }
        public void addAll(List<VisitorModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public VisitorAdapter(Context context, ArrayList<VisitorModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public VisitorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.visitoritem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            VisitorAdapter.MyViewHolder myViewHolder = new VisitorAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final VisitorAdapter.MyViewHolder holder, final int listPosition) {


            final VisitorModel visitorModel=dataSet.get(listPosition);
            holder.visitorNmTv.setText(visitorModel.getVisitorNm());
            holder.addTv.setText(visitorModel.getVisitorAdd());

            holder.sendReqLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,SendRequestActivity.class);
                    intent.putExtra("from","2");
                    intent.putExtra("visitorNm",visitorModel.getVisitorNm());
                    intent.putExtra("visitorAdd",visitorModel.getVisitorAdd());
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

    private void getVisitorData(EmptyRequest emptyRequest) {
      //  ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                visitorListResponse = VisitorListResponse.create(response);
                if (visitorListResponse != null) {
                    if (getActivity() != null) {

                        if (visitorListResponse.isStatus()) {
                            if (visitorListResponse.businessVisitorList != null) {
                                data.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < visitorListResponse.businessVisitorList.size(); i++) {
                                    data.add(new VisitorModel(visitorListResponse.businessVisitorList.get(i).getName(),""+visitorListResponse.businessVisitorList.get(i).getAddresss()+""+visitorListResponse.businessVisitorList.get(i).getCountry()));
                                }

                                adapter.notifyDataSetChanged();
                            } else {
                                CustomAlert.alertWithOk(getActivity(), "There is no data in visitor's listing");

                            }
                        }
                    //    ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
                    }
                }
            }


            @Override
            public void onError(VolleyError error) {
                if (getActivity() != null) {

                  //  ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
                    if (error.getMessage() != null) {
                        if (error.getMessage().contains("java.net.UnknownHostException")) {
                            CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.internet_connection_message));

                        } else {
                            CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.server_issue));

                        }
                    } else {
                        CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.server_issue));

                    }
                }


            }
        };
        CustomVolley volley=new CustomVolley(taskListener, AppConstant.VISITOR_API,emptyRequest.serialize(),null,null,getActivity());
        volley.execute();
    }

}
