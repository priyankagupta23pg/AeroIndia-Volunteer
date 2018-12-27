package com.aeroindia.view.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aeroindia.R;
import com.aeroindia.adapter.ExhibitorsAdapter;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.DatabaseHelpers;
import com.aeroindia.db.DatabaseOpration;
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.request.ExhibitorsListItem;
import com.aeroindia.pojos.response.B2BListResponse;
import com.aeroindia.view.activity.Base;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.GPSTracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Exhibitors extends Fragment {

    private RecyclerView recyclerView;
    private View rootView;
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private ExhibitorsListItem exhibitors = new ExhibitorsListItem();
    private ArrayList<ExhibitorsItem> exhibitorsItemList = new ArrayList<>();
    private ExhibitorsAdapter adapter;
    private Bundle bundle;
    private String screenName;
    private B2BListResponse b2BListResponse;
    private GPSTracker gps;
    private double latitude, longitude;
    private ImageView search, searchCancelIV, clearTextIV;
    Button refreshBtn;
    private RelativeLayout search_layout;
    EditText edit_query;
    SharedPreferences preferences;
    long current_time, last_upload_time;
    private DatabaseHelpers dbHelper;

    public Fragment_Exhibitors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exhibitors, container, false);
        activity = getActivity();
        bundle = getArguments();
        if (bundle != null) {
            screenName = bundle.getString(AppConstant.SCREENNAME);
        }
        setup();
        return rootView;
    }

    private void setup() {
        dbHelper = DatabaseHelpers.getInstance(getActivity());

        gps = new GPSTracker(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        search = (ImageView) rootView.findViewById(R.id.search);
        search_layout = (RelativeLayout) rootView.findViewById(R.id.search_layout);
        searchCancelIV = (ImageView) rootView.findViewById(R.id.searchCancelIV);
        clearTextIV = (ImageView) rootView.findViewById(R.id.clearTextIV);
        edit_query = (EditText) rootView.findViewById(R.id.edit_query);
        refreshBtn = (Button) rootView.findViewById(R.id.refreshBtn);
//        final SwipeRefreshLayout pullToRefresh =(SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData(); // your code
//                pullToRefresh.setRefreshing(false);
//            }
//        });
        refreshData();
    }

    private void refreshData() {
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // setAdapter();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        current_time = Calendar.getInstance().getTimeInMillis();
        last_upload_time = preferences.getLong(AppConstant.LOCALTIME, 0);

        if (last_upload_time == 0) {
            preferences.edit().putLong(AppConstant.LOCALTIME, current_time).commit();
            //return;
        }


        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (AppUtilityFunction.isFirst(getActivity())) {
                refreshBtn.setEnabled(true);
                refreshBtn.setClickable(true);


            } else {
                int min_difference = (int) ((current_time - last_upload_time) / (1000 * 60));
                if (min_difference >= 1) {
                    refreshBtn.setEnabled(true);
                    refreshBtn.setClickable(true);
                    // preferences.edit().putLong("show_message_time",current_time).commit();
                } else {
                    refreshBtn.setEnabled(false);
                    refreshBtn.setClickable(false);
                    refreshBtn.setAlpha(.5f);
                }

            }
            ArrayList<ExhibitorsItem> exhibitorsItems = DatabaseOpration.getExhibitors(dbHelper);

            if (exhibitorsItems.size() > 0) {
                exhibitorsItemList = exhibitorsItems;
                refreshExhibitorList(exhibitorsItems);
            } else {
                //ArrayList<ExhibitorsItem> exhibitorsItems1=  DatabaseOpration.getExhibitors(dbHelper);
                getExhibitorData(new EmptyRequest());

            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.VISIBLE);
                edit_query.setHint("Search by company/country");
                searchCancelIV.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                //  CustomAlert.alertWithOk(activity,"Under development");

            }
        });

        searchCancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
                searchCancelIV.setVisibility(View.GONE);

            }
        });

        clearTextIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_query.setText("");
                edit_query.setHint("Search by company/country");
            }
        });
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equalsIgnoreCase("")) {
                    ArrayList<ExhibitorsItem> exhibitorList = new ArrayList<>();
                    for (ExhibitorsItem item : exhibitorsItemList) {
                        if (item.getName() != null || item.getCountry() != null) {
                            if (item.getName().toUpperCase().contains(charSequence.toString().toUpperCase()) || item.getCountry().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                                exhibitorList.add(item);
                            }
                        }
                    }
                    refreshExhibitorList(exhibitorList);
                } else {
                    refreshExhibitorList(exhibitorsItemList);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.d("REFERSH","clicked");
                if(AppUtilityFunction.isNetworkAvailable(getActivity())) {
                search_layout.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
                searchCancelIV.setVisibility(View.GONE);
                edit_query.setText("");
                refreshBtn.setEnabled(false);
                refreshBtn.setClickable(false);
                refreshBtn.setAlpha(.5f);
                preferences.edit().putLong(AppConstant.LOCALTIME, current_time).commit();

                    DatabaseOpration.deleteAllExhibitors(dbHelper);
                    getExhibitorData(new EmptyRequest());
                }
                else
                {
                    CustomAlert.alertWithOk(getActivity(), "Please connect internet to refresh exhibitors listing data.");

                }
//                last_upload_time = preferences.getLong("show_message_time", 0);
//
//                if (last_upload_time == 0) {
//                    preferences.edit().putLong("show_message_time", current_time).commit();
//                    //return;
//                }
//                preferences.edit().putLong("show_message_time",current_time).commit();
//                int min_difference = (int) ((current_time - last_upload_time) / (1000 * 60));
//                if (min_difference >= 5) {
//                    refreshBtn.setEnabled(true);
//                    refreshBtn.setClickable(true);
//                    preferences.edit().putLong("show_message_time",current_time).commit();
//                }
//
//                else
//                {
//                    refreshBtn.setEnabled(false);
//                    refreshBtn.setClickable(false);
//                    refreshBtn.setAlpha(.5f);
//
//                }
            }
        });

    }

    private void setAdapter() {
//        exhibitors=ExhibitorsListItem.create(loadJSONFromAsset());
//        exhibitorsItemList=new ArrayList<>();
//        exhibitorsItemList=exhibitors.getList();

    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("exhibitorList.json");
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

    private void getExhibitorData(EmptyRequest emptyRequest) {
        ((Base) getActivity()).showHideProgressDialog(true);
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                b2BListResponse = B2BListResponse.create(response);
                if (b2BListResponse != null) {
                    if (getActivity() != null) {

                        if (b2BListResponse.isStatus()) {
                            if (b2BListResponse.companyHallList != null) {
                                exhibitorsItemList.clear();
                                ExhibitorsItem exhibitorsItem;
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < b2BListResponse.companyHallList.size(); i++) {
                                    if (b2BListResponse.companyHallList.get(i).hall != null) {
                                         exhibitorsItem = new ExhibitorsItem(b2BListResponse.companyHallList.get(i).hall.getName(), b2BListResponse.companyHallList.get(i).hall.getStall(), b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());
                                    }
                                    else
                                    {
                                         exhibitorsItem = new ExhibitorsItem("N.A.", "N.A.", b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());

                                    }
                                        exhibitorsItem.setUniqueId(b2BListResponse.companyHallList.get(i).company.getUniqueId());
                                        exhibitorsItem.setCompanyId(b2BListResponse.companyHallList.get(i).company.getCompanyId());
                                        exhibitorsItem.setEmail(b2BListResponse.companyHallList.get(i).company.getContactEmailId());
                                        exhibitorsItem.setPhone(b2BListResponse.companyHallList.get(i).company.getMobileNumber());
                                        exhibitorsItemList.add(exhibitorsItem);

                                        ExhibitorsItem existExhibitorItem = DatabaseOpration.getExhibitor(b2BListResponse.companyHallList.get(i).company.getUniqueId(), dbHelper);
                                        if (existExhibitorItem == null) {
                                            DatabaseOpration.saveExhibitor(exhibitorsItem, dbHelper);
                                        } else {
                                            DatabaseOpration.updateExhibitor(exhibitorsItem, existExhibitorItem.getUniqueId(), dbHelper);

                                        }


                                        refreshExhibitorList(exhibitorsItemList);

                                }
                            } else {
                                CustomAlert.alertWithOk(getActivity(), "There is no exhibitor data");

                            }
                        }
                        ((Base) getActivity()).showHideProgressDialog(false);
                    }
                }
            }


            @Override
            public void onError(VolleyError error) {
                if (getActivity() != null) {

                    ((Base) getActivity()).showHideProgressDialog(false);
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
        CustomVolley volley = new CustomVolley(taskListener, AppConstant.EXHIBITOR_API, emptyRequest.serialize(), null, null, getActivity());
        volley.execute();
    }

    private void refreshExhibitorList(ArrayList<ExhibitorsItem> exhibitorsItemList) {

        Collections.sort(exhibitorsItemList, new Comparator<ExhibitorsItem>() {
            @Override
            public int compare(ExhibitorsItem lhs, ExhibitorsItem rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        adapter = new ExhibitorsAdapter(exhibitorsItemList, activity, screenName, latitude, longitude);
        recyclerView.setAdapter(adapter);
    }

}
