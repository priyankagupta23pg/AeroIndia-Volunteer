package com.aeroindia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.request.OrganizerItem;
import com.aeroindia.view.activity.ContactExhibitor;

import java.util.List;

/**
 * Created by PSQ on 11/5/2017.
 */

public class OrganizerAdapter extends RecyclerView.Adapter<OrganizerAdapter.MyViewHolder> {

    private List<OrganizerItem> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private String screenName;
    private ContactExhibitor activity;


    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        OrganizerAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView company_name_TV, address_name_TV;
        private LinearLayout button_layout,main_layout;
        private RelativeLayout contactLayout;


        public MyViewHolder(View view) {
            super(view);
            company_name_TV = (TextView) view.findViewById(R.id.company_name_TV);
           // address_name_TV = (TextView) view.findViewById(R.id.address_name_TV);
           // button_layout = (LinearLayout) view.findViewById(R.id.button_layout);
            main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
            contactLayout=(RelativeLayout)view.findViewById(R.id.contactLayout);
        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public OrganizerAdapter(List<OrganizerItem> list, Context context, String screenName) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.screenName = screenName;
    }

    public OrganizerAdapter(List<OrganizerItem> list, Context context, String screenName,ContactExhibitor activity) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.screenName = screenName;
        this.activity=activity;

    }


    public OrganizerAdapter(List<OrganizerItem> list, Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.organizer_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OrganizerItem item = list.get(position);
            holder.company_name_TV.setText(item.getCompany());
           // holder.address_name_TV.setText(item.getAddress());
            if (screenName != null && screenName.equalsIgnoreCase(AppConstant.B2BSCREEN)) {
                holder.button_layout.setVisibility(View.VISIBLE);
            }
            holder.contactLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.initiatePopupWindow("wahidali1987@gmail.com");
                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}