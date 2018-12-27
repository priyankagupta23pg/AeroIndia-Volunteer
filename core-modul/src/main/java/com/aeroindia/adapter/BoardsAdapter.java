package com.aeroindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.request.BoardItem;


import java.util.List;

/**
 * Created by PSQ on 11/5/2017.
 */

public class BoardsAdapter extends RecyclerView.Adapter<BoardsAdapter.MyViewHolder> {

    private List<BoardItem> list;
    private static ClickListener clickListener;
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        BoardsAdapter.clickListener = clickListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
          //  title = (TextView) view.findViewById(R.id.title);
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


    public BoardsAdapter(List<BoardItem> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classes_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BoardItem item = list.get(position);
       // holder.title.setText(item.getBoard_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}