package com.aeroindia.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aeroindia.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private boolean logEnable=true;
    public void setupScreen(View view){
    this.view=view;
    }


    public void setView(View view){
        this.view=view;
    }

    public String getEditText(EditText editText){
        return editText.getText().toString();
    }
    public Button findButton(int id){
        return (Button) view.findViewById(id);
    }
    public EditText findEditText(int id){
        return (EditText) view.findViewById(id);
    }
    public TextView findTextView(int id){
        return (TextView) view.findViewById(id);
    }
    public LinearLayout findLinearLayout(int id){
        return (LinearLayout)view. findViewById(id);
    }

    public RelativeLayout findRelativeLayout(int id){
        return (RelativeLayout) view.findViewById(id);
    }
    public RecyclerView findRecyclerView(int id){
        return (RecyclerView)view. findViewById(id);
    }
    public Spinner findSpinner(int id){

        return (Spinner)view.findViewById(id);
    }

    public CheckBox findCheckbox(int id){

        return (CheckBox) view.findViewById(id);
    }

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
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
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    public  void showLog(Class t,String msg){
        if(logEnable) {
            Log.d(t.getName(), msg);
        }
    }

}
