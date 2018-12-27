package com.aeroindia.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.aeroindia.R;
import com.aeroindia.adapter.OrganizerAdapter;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.request.OrganizerItem;
import com.aeroindia.pojos.request.OrganizerList;
import com.aeroindia.view.activity.ContactExhibitor;
import com.customComponent.CustomAlert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrganizer extends Fragment {

    private RecyclerView recyclerView;
    private View rootView;
    private LinearLayoutManager linearLayoutManager;
    private OrganizerList exhibitors=new OrganizerList();
    private ArrayList<OrganizerItem> exhibitorsItemList;
    private OrganizerAdapter adapter;
    private Bundle bundle;
    private String screenName;
    private Activity activity;
    private ContactExhibitor contactExhibitorAct;
    private ImageView search;
    EditText edit_query;
    public FragmentOrganizer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContactExhibitor){
            this.contactExhibitorAct = (ContactExhibitor) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_organizers, container, false);
        activity=getActivity();
        bundle = getArguments();
        if (bundle != null) {
            screenName = bundle.getString(AppConstant.SCREENNAME);
        }
        setup();
        return rootView;
    }

    private void setup(){
            recyclerView= (RecyclerView) rootView.findViewById(R.id.recycleView);
            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        search= (ImageView) rootView.findViewById(R.id.search);
        edit_query= (EditText) rootView.findViewById(R.id.edit_query);
        edit_query.setHint("Search Organizer");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlert.alertWithOk(activity,"Under development");

            }
        });
            setAdapter();
    }

    private void setAdapter(){
        exhibitors=OrganizerList.create(loadJSONFromAsset());
        exhibitorsItemList=new ArrayList<>();
        exhibitorsItemList=exhibitors.getList();
        adapter = new OrganizerAdapter(exhibitorsItemList, activity,screenName,contactExhibitorAct);
        recyclerView.setAdapter(adapter);
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("organizeList.json");
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
