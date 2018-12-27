package com.aeroindia.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aeroindia.R;
import com.customComponent.utility.TransDialog;


public class Base extends AppCompatActivity {
    private Activity prevAtivity,currentActivity;
    private RelativeLayout backBT;
    private boolean logEnable=true;
    public Context context;
    TransDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();
        context=this;
        hideSoftKeyboard();
        pd = new TransDialog(this, com.customComponent.R.drawable.loading);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
    }

    public void setupScreen(){

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public String getEditText(EditText editText){
       return editText.getText().toString();
    }
    public Button findButton(int id){
        return (Button) findViewById(id);
    }
    public EditText findEditText(int id){
        return (EditText) findViewById(id);
    }
    public TextView findTextView(int id){
        return (TextView) findViewById(id);
    }
    public LinearLayout findLinearLayout(int id){
        return (LinearLayout) findViewById(id);
    }

    public RelativeLayout findRelativeLayout(int id){
        return (RelativeLayout) findViewById(id);
    }
    public RecyclerView findRecyclerView(int id){
        return (RecyclerView) findViewById(id);
    }
    public Spinner findSpinner(int id){

        return (Spinner)findViewById(id);
    }

    public void backNavigation(final Class t, final Activity currentActivity, RelativeLayout backBT, TextView headerTV,int textRes){
        this.backBT=backBT;
        this.prevAtivity=prevAtivity;
        this.currentActivity=currentActivity;
        backBT.setVisibility(View.VISIBLE);
        headerTV.setText(textRes);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t!=null){
                    Intent theIntent=new Intent(context,t);
                    startActivity(theIntent);
                    currentActivity.finish();
                }else{
                    currentActivity.finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(backBT!=null) {
            backBT.performClick();
        }
    }

    public Intent getIntent(Class t){
        Intent theIntent=new Intent(context,t);
        return theIntent;
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connec = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }
    public  void showLog(Class t,String msg){
        if(logEnable) {
            Log.d(t.getName(), msg);
        }
    }

    public void showHideProgressDialog(boolean isShow) {
        showHideProgressDialog(isShow, getString(com.customComponent.R.string.please_wait));
    }

    public void showHideProgressDialog(boolean isShow, String message) {
        if (isShow) {
            //  _progressDialog.setMessage(message);
            //  _progressDialog.show();
            pd.show();
        } else {
            // _progressDialog.dismiss();
            pd.dismiss();
        }
    }
}
