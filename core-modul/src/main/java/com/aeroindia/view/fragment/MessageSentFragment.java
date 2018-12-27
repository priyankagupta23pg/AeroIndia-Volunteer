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
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.request.MessageItem;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.pojos.response.MyActivitiesResponse;
import com.aeroindia.view.activity.ViewMessageDetailsActivity;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolleyGet;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MessageSentFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MessageItem> data=new ArrayList<>();
    private MessageSentAdapter adapter;
private MyActivitiesResponse myActivitiesResponse;
private LoginResponse loginResponse;
private long senderId;
private String rcvrId;
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
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, getActivity()));
        senderId=loginResponse.getUser().getUserId();
        rcvrId=loginResponse.getUser().getCompanyUniqueId();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
       // data.add(new MessageItem(454545,"Purpose","message","name","type"));
        adapter = new MessageSentAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        getMyActivities(new EmptyRequest());

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public class MessageSentAdapter extends RecyclerView.Adapter<MessageSentAdapter.MyViewHolder> {

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
        public MessageSentAdapter(Context context, ArrayList<MessageItem> data) {

            this.dataSet = data;
            this.context=context;

        }

        @Override
        public MessageSentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R .layout.messageitem_layout, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MessageSentAdapter.MyViewHolder myViewHolder = new MessageSentAdapter.MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(final MessageSentAdapter.MyViewHolder holder, final int listPosition) {


            final MessageItem messageItem=dataSet.get(listPosition);
            holder.companyNmTv.setText(messageItem.getName());
            holder.dateTimeTv.setText(AppUtilityFunction.getDate(messageItem.getDateTime(), AppConstant.MSG_DATE_FORMAT));
            if(messageItem.getPurpose()!=null) {
                if (!messageItem.getPurpose().equalsIgnoreCase("")) {
                    holder.purposeTv.setText(messageItem.getPurpose());
                }
            }
            else
            {
                holder.purposeTv.setText("N.A.");
            }


holder.viewDetailLabel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,ViewMessageDetailsActivity.class);
        intent.putExtra("fromm",0);
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

    private void getMyActivities(EmptyRequest emptyRequest) {
         ((BaseAppCompatActivity) getActivity()).showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                myActivitiesResponse = MyActivitiesResponse.create(response);
                if (myActivitiesResponse != null) {
                    if (getActivity() != null) {

                        if (myActivitiesResponse.isStatus()) {
                            if (myActivitiesResponse.youContacted != null) {
                                if(myActivitiesResponse.youContacted.size()>0) {
                                    data.clear();
                                    // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                    for (int i = 0; i < myActivitiesResponse.youContacted.size(); i++) {
                                        data.add(new MessageItem(myActivitiesResponse.youContacted.get(i).getDateTime(), myActivitiesResponse.youContacted.get(i).getPurpose(), myActivitiesResponse.youContacted.get(i).getMessage(), myActivitiesResponse.youContacted.get(i).getName(), myActivitiesResponse.youContacted.get(i).getCommType()));
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    CustomAlert.alertWithOk(getActivity(), "There is no message sent.");

                                }
                            }


                            else {
                                CustomAlert.alertWithOk(getActivity(), "There is no message sent.");

                            }

                            if(myActivitiesResponse.contactedBy!=null) {
                                if (myActivitiesResponse.contactedBy.size() > 0) {
                                    Gson gson = new Gson();
                                    String rcvdGson = gson.toJson(myActivitiesResponse.contactedBy);
                                    ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.MSGRCVD_DETAILS, rcvdGson, getActivity());
                                }
                                else
                                {
                                    CustomAlert.alertWithOk(getActivity(), "There is no message received.");

                                }
                            }
                            else
                            {
                                CustomAlert.alertWithOk(getActivity(), "There is no message received.");

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
        CustomVolleyGet volley=new CustomVolleyGet(taskListener, null,AppConstant.ACTIVITIES_API+senderId+AppConstant.ACTIVITIES1_API+rcvrId,getActivity());
        volley.execute();
    }

}
