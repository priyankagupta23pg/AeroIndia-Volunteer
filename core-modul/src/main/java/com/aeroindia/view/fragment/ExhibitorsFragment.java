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
import com.aeroindia.pojos.generic.ExhibitorModel;
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.response.B2BListResponse;
import com.aeroindia.view.activity.SendRequestActivity;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ExhibitorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ExhibitorModel> data=new ArrayList<>();
    private ExhibitorAdapter adapter;
private B2BListResponse b2BListResponse;

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
        data = new ArrayList<ExhibitorModel>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
      //  data.add(new ExhibitorModel("company1","Hall A","#12"));
      //  data.add(new ExhibitorModel("company2","Hall B","#13"));
        adapter = new ExhibitorAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);

        getExhibitorData(new EmptyRequest());

        //Log.d("SIZE OF ADAPTER",adapter.getItemCount()+"");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public class ExhibitorAdapter extends RecyclerView.Adapter<ExhibitorAdapter.MyViewHolder> {

        private ArrayList<ExhibitorModel> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView companyNmTv,hallTv,sendReqLabel,stallTv,addTv,cntryTv;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.companyNmTv = (TextView) itemView.findViewById(R.id.companyNmTv);
                this.hallTv = (TextView) itemView.findViewById(R.id.hallTv);
                this.sendReqLabel = (TextView) itemView.findViewById(R.id.sendReqLabel);
                this.stallTv = (TextView) itemView.findViewById(R.id.stallTv);
                this.addTv = (TextView) itemView.findViewById(R.id.addTv);
                this.cntryTv = (TextView) itemView.findViewById(R.id.cntryTv);
            }


        }
        public void addAll(List<ExhibitorModel> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public ExhibitorAdapter(Context context, ArrayList<ExhibitorModel> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public ExhibitorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.exhibitoritem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            ExhibitorAdapter.MyViewHolder myViewHolder = new ExhibitorAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final ExhibitorAdapter.MyViewHolder holder, final int listPosition) {


            final ExhibitorModel exhibitorModel=dataSet.get(listPosition);
            holder.companyNmTv.setText(exhibitorModel.getCmpnyNm());
            if(exhibitorModel.getHallNm()!=null) {
                holder.hallTv.setText(exhibitorModel.getHallNm());
            }
            else
            {
                holder.hallTv.setText("N.A.");
            }
            if(exhibitorModel.getStallNo()!=null) {
                holder.stallTv.setText(exhibitorModel.getStallNo());
            }
            else
            {
                holder.stallTv.setText("N.A.");

            }
            if(exhibitorModel.getAddress()!=null) {
                holder.addTv.setText(exhibitorModel.getAddress());
            }
            else
            {
                holder.addTv.setText("N.A.");

            }
            holder.cntryTv.setText(exhibitorModel.getCntry());

holder.sendReqLabel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,SendRequestActivity.class);
        intent.putExtra("from","1");
        intent.putExtra("company",exhibitorModel.getCmpnyNm());
        intent.putExtra("add",exhibitorModel.getAddress());
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

    private void getExhibitorData(EmptyRequest emptyRequest) {
        ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                b2BListResponse = B2BListResponse.create(response);
                if (b2BListResponse != null) {
                    if (getActivity() != null) {

                        if (b2BListResponse.isStatus()) {
                            if (b2BListResponse.companyHallList != null) {
                                data.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < b2BListResponse.companyHallList.size(); i++) {
if(b2BListResponse.companyHallList.get(i).hall!=null) {
    data.add(new ExhibitorModel(b2BListResponse.companyHallList.get(i).company.getName(), b2BListResponse.companyHallList.get(i).hall.getName(), b2BListResponse.companyHallList.get(i).hall.getStall(), b2BListResponse.companyHallList.get(i).company.getAddress(), b2BListResponse.companyHallList.get(i).company.getCountry()));
}
else
{
    data.add(new ExhibitorModel(b2BListResponse.companyHallList.get(i).company.getName(), "N.A.", "N.A.", b2BListResponse.companyHallList.get(i).company.getAddress(), b2BListResponse.companyHallList.get(i).company.getCountry()));

}



                               }

                                adapter.notifyDataSetChanged();
                            } else {
                                CustomAlert.alertWithOk(getActivity(), "There is no company data in B2B listing");

                            }
                        }
                        ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
                    }
                }
            }


            @Override
            public void onError(VolleyError error) {
                if (getActivity() != null) {

                    ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
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
        CustomVolley volley=new CustomVolley(taskListener, AppConstant.EXHIBITOR_API,emptyRequest.serialize(),null,null,getActivity());
        volley.execute();
    }

}
