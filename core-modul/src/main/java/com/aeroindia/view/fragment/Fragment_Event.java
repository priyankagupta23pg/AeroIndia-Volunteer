package com.aeroindia.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.request.EventItem;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Event extends Fragment {
    private TextView day_TV, desc_TV;
    private ImageView imageView;
    private View rootView;
    private ArrayList<EventItem> eventList;
    private EventItem event;
    URL url;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Fragment_Event fragment;


    public Fragment_Event() {
        // Required empty public constructor
    }
 /*   public static Fragment_Event newInstance(String param1, String param2) {
        if(fragment==null){
            fragment = new Fragment_Event();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }

        return fragment;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event, container, false);
        setup();
        return rootView;
    }

    private void setup() {
        day_TV = (TextView) rootView.findViewById(R.id.day_TV);
        desc_TV = (TextView) rootView.findViewById(R.id.desc_TV);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        day_TV.setText(event.getDay());
        desc_TV.setText(event.getDesc());
        Picasso.with(this.getActivity()).load(event.getUrl()).into(imageView);
        //Picasso.with(this.getActivity()).load(event.getUrl()).fit().centerCrop().into(imageView);
        //Picasso.with(this.getActivity()).load(event.getUrl()).resize(100, 50).into(imageView);
    }

    public EventItem getEvent() {
        return event;
    }

    public void setEvent(EventItem event) {
        this.event = event;
    }

    public ArrayList<EventItem> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<EventItem> eventList) {
        this.eventList = eventList;
    }


}