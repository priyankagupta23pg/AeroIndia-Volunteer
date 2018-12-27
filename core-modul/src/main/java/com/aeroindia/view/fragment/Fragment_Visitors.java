package com.aeroindia.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroindia.R;
import com.aeroindia.adapter.VisitorsAdapter;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.request.ExhibitorsListItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Visitors extends Fragment {

    private RecyclerView recyclerView;
    private View rootView;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private ExhibitorsListItem exhibitors=new ExhibitorsListItem();
    private ArrayList<ExhibitorsItem> exhibitorsItemList;
    private VisitorsAdapter adapter;
    public Fragment_Visitors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_exhibitors, container, false);
        activity=getActivity();
        setup();
        return rootView;
    }

    private void setup(){
            recyclerView= (RecyclerView) rootView.findViewById(R.id.recycleView);
            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            setAdapter();
    }

    private void setAdapter(){
        exhibitors=ExhibitorsListItem.create(loadJSONFromAsset());
        exhibitorsItemList=new ArrayList<>();
        exhibitorsItemList=exhibitors.getList();
        adapter = new VisitorsAdapter(exhibitorsItemList, activity);
        recyclerView.setAdapter(adapter);
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("visitor.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
