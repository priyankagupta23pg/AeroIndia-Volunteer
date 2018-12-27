package com.aeroindia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.request.ExhibitorsItem;

import java.util.List;

/**
 * Created by PSQ on 11/5/2017.
 */

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.MyViewHolder> {

    private List<ExhibitorsItem> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private String screenName;
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        VisitorsAdapter.clickListener = clickListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView name_TV,contact_TV,email_TV,address_name_TV;
        private LinearLayout main_layout;


        public MyViewHolder(View view) {
            super(view);
            name_TV = (TextView) view.findViewById(R.id.name_TV);
            contact_TV = (TextView) view.findViewById(R.id.contact_TV);
            email_TV=(TextView) view.findViewById(R.id.email_TV);
            address_name_TV=(TextView) view.findViewById(R.id.address_name_TV);
            main_layout=(LinearLayout)view.findViewById(R.id.main_layout);
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
    public VisitorsAdapter(List<ExhibitorsItem> list, Context context, String screenName) {
        this.context=context;
        this.layoutInflater= LayoutInflater.from(context);
        this.list = list;
        this.screenName=screenName;
    }


    public VisitorsAdapter(List<ExhibitorsItem> list, Context context) {
        this.context=context;
        this.layoutInflater= LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ExhibitorsItem item = list.get(position);
            holder.name_TV.setText(item.getName());
            holder.address_name_TV.setText(item.getAddress());
            holder.contact_TV.setText(item.getPhone());
            holder.email_TV.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}