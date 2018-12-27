package com.aeroindia.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.adapter.PersonaliseAdapter;
import com.aeroindia.pojos.request.MeetingItem;
import com.aeroindia.pojos.request.PersonaliseItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPersonalise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPersonalise extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private View rootView;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private PersonaliseItem personalise=new PersonaliseItem();
    private ArrayList<MeetingItem> personaliseList;
    private PersonaliseAdapter adapter;
    private TextView hallNoTV,stalNoTV,companyTV,addressTV,phoneTV,emailTV;
    public FragmentPersonalise() {
        // Required empty public constructor
    }


    public static FragmentPersonalise newInstance(String param1, String param2) {
        FragmentPersonalise fragment = new FragmentPersonalise();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_personalise, container, false);
        activity=getActivity();
        setup();
        return rootView;
    }


    private void setup(){
        hallNoTV=(TextView)rootView.findViewById(R.id.hallNoTV);
        stalNoTV=(TextView)rootView.findViewById(R.id.stalNoTV);
        companyTV=(TextView)rootView.findViewById(R.id.companyTV);
        addressTV=(TextView)rootView.findViewById(R.id.addressTV);
        phoneTV=(TextView)rootView.findViewById(R.id.phoneTV);
        emailTV=(TextView)rootView.findViewById(R.id.emailTV);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();
    }

    private void setAdapter(){
        personalise= PersonaliseItem.create(loadJSONFromAsset());
        hallNoTV.setText(personalise.getPossesions().getHallNo());
        stalNoTV.setText(personalise.getPossesions().getStalNo());
        companyTV.setText(personalise.getPossesions().getCompany());
        addressTV.setText(personalise.getPossesions().getAddress());
        phoneTV.setText(personalise.getPossesions().getPhone());
        emailTV.setText(personalise.getPossesions().getEmail());
        personaliseList=new ArrayList<>();
        personaliseList=personalise.getMeetings();
        adapter = new PersonaliseAdapter(personaliseList, activity);
        recyclerView.setAdapter(adapter);

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("personalise.json");
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
