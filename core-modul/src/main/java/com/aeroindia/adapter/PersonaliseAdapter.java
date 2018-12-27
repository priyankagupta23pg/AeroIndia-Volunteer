package com.aeroindia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.request.MeetingItem;

import java.util.List;

/**
 * Created by PSQ on 11/5/2017.
 */

public class PersonaliseAdapter extends RecyclerView.Adapter<PersonaliseAdapter.MyViewHolder> {

    private List<MeetingItem> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private String screenName;

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PersonaliseAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView meetingWithTV, locationTV, timeTV, dateTV, contactNumTV;
        private LinearLayout main_layout;


        public MyViewHolder(View view) {
            super(view);
            meetingWithTV = (TextView) view.findViewById(R.id.meetingWithTV);
            locationTV = (TextView) view.findViewById(R.id.locationTV);
            timeTV = (TextView) view.findViewById(R.id.timeTV);
            dateTV = (TextView) view.findViewById(R.id.dateTV);
            contactNumTV = (TextView) view.findViewById(R.id.contactNumTV);
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


    public PersonaliseAdapter(List<MeetingItem> list, Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personalise_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MeetingItem item = list.get(position);
        holder.meetingWithTV.setText(item.getMeetingWith());
        holder.locationTV.setText(item.getLocation());
        holder.timeTV.setText(item.getTime());
        holder.dateTV.setText(item.getDate());
        holder.contactNumTV.setText(item.getContactNum());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}