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
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.MessageItem;
import com.aeroindia.pojos.response.B2BListResponse;
import com.aeroindia.view.activity.ViewMessageDetailsActivity;
import com.customComponent.utility.ProjectPrefrence;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessageRcvdFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MessageItem> data=new ArrayList<>();
    private MessageRcvdAdapter adapter;
private B2BListResponse b2BListResponse;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.msgsentrcvd, container, false);
        Log.d("String", "YESS");
        initializeView(v);

        return v;
    }


    private void initializeView(View view) {
        recyclerView = (RecyclerView)view. findViewById(R.id.recyclerView);
        data = new ArrayList<MessageItem>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
      //  data.add(new ExhibitorModel("company1","Hall A","#12"));
      //  data.add(new ExhibitorModel("company2","Hall B","#13"));




    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        String msgRcvd=ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.MSGRCVD_DETAILS, getActivity());
        Gson gson = new Gson();
        MessageItem[] rcvdMessages = gson.fromJson(msgRcvd,
                MessageItem[].class);
        if(rcvdMessages!=null) {
            List<MessageItem> data1 = new ArrayList<>();
            data1 = Arrays.asList(rcvdMessages);
            data = new ArrayList<MessageItem>(data1);
            adapter = new MessageRcvdAdapter(getActivity(), data);
            recyclerView.setAdapter(adapter);
        }

    }

    public class MessageRcvdAdapter extends RecyclerView.Adapter<MessageRcvdAdapter.MyViewHolder> {

        private ArrayList<MessageItem> dataSet;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder   {

            private TextView companyNmTv,viewDetailLabel,dateTimeTv,purposeTv;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.companyNmTv = (TextView) itemView.findViewById(R.id.companyNmTv);
                this.dateTimeTv = (TextView) itemView.findViewById(R.id.dateTimeTv);
                this.purposeTv = (TextView) itemView.findViewById(R.id.purposeTv);
                this.viewDetailLabel = (TextView) itemView.findViewById(R.id.viewDetailLabel);

            }


        }
        public void addAll(List<MessageItem> list) {

            dataSet.addAll(list);
            notifyDataSetChanged();
        }
        public MessageRcvdAdapter(Context context, ArrayList<MessageItem> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public MessageRcvdAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                      int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.messageitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MessageRcvdAdapter.MyViewHolder myViewHolder = new MessageRcvdAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final MessageRcvdAdapter.MyViewHolder holder, final int listPosition) {


            final MessageItem messageItem=dataSet.get(listPosition);
            holder.companyNmTv.setText(messageItem.getName());
            holder.dateTimeTv.setText(AppUtilityFunction.getDate(messageItem.getDateTime(), AppConstant.MSG_DATE_FORMAT));
            if(messageItem.getPurpose()!=null && !messageItem.getPurpose().equalsIgnoreCase("")) {
                holder.purposeTv.setText(messageItem.getPurpose());
            }
            else
            {
                holder.purposeTv.setText("N.A.");
            }


            holder.viewDetailLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ViewMessageDetailsActivity.class);
                    intent.putExtra("fromm",1);
                    intent.putExtra("MessageItem", dataSet.get(listPosition).serialize());
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
//    private void getExhibitorData(EmptyRequest emptyRequest) {
//        ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(true);
//        VolleyTaskListener taskListener=new VolleyTaskListener() {
//            @Override
//            public void postExecute(String response) {
//                b2BListResponse = B2BListResponse.create(response);
//                if (b2BListResponse != null) {
//                    if (getActivity() != null) {
//
//                        if (b2BListResponse.isStatus()) {
//                            if (b2BListResponse.companyList != null) {
//                                data.clear();
//                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
//                                for (int i = 0; i < b2BListResponse.companyList.size(); i++) {
//
//                                        data.add(new ExhibitorModel(b2BListResponse.companyList.get(i).getName(), b2BListResponse.companyList.get(i).getHallNo(), b2BListResponse.companyList.get(i).getStalNo(),  b2BListResponse.companyList.get(i).getAddress(),  b2BListResponse.companyList.get(i).getCountry()));
//
//
//
//
//                               }
//
//                                adapter.notifyDataSetChanged();
//                            } else {
//                                CustomAlert.alertWithOk(getActivity(), "There is no company data in B2B listing");
//
//                            }
//                        }
//                        ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
//                    }
//                }
//            }
//
//
//            @Override
//            public void onError(VolleyError error) {
//                if (getActivity() != null) {
//
//                    ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(false);
//                    if (error.getMessage() != null) {
//                        if (error.getMessage().contains("java.net.UnknownHostException")) {
//                            CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.internet_connection_message));
//
//                        } else {
//                            CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.server_issue));
//
//                        }
//                    } else {
//                        CustomAlert.alertWithOk(getActivity(), getResources().getString(R.string.server_issue));
//
//                    }
//                }
//
//
//            }
//        };
//        CustomVolley volley=new CustomVolley(taskListener, AppConstant.EXHIBITOR_API,emptyRequest.serialize(),null,null,getActivity());
//        volley.execute();
//    }

}
