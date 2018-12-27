package com.aeroindia.view.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.fragment.FragmentOrganizer;
import com.aeroindia.view.fragment.Fragment_Exhibitors;
import com.aeroindia.view.fragment.NewUserMyDashboard;
import com.customComponent.utility.ProjectPrefrence;

public class ContactExhibitor extends Base {
    private Fragment fragment_main;
    private int header,headerskipcase;
    private int TAG;
    private Dialog dialog;
    private Context context;
    private LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_exhibitor);
        setupScreen();
    }

    @Override
    public void setupScreen() {
        context=this;
        TAG=getIntent().getIntExtra(NewUserMyDashboard.TAG,0);
        if(TAG== NewUserMyDashboard.CONTACT_ORGANIZER){
            header=R.string.contact_organizer;
            contactOrgFragment();
        }else  if(TAG== NewUserMyDashboard.CONTACT_EXHIBITOR){
            header=R.string.contact_exhibitor;
            headerskipcase=R.string.exhibitor;
            callMainFragment();
        }

        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));

        if(loginResponse!=null) {
            backNavigation(null, this, findRelativeLayout(R.id.backLayout), findTextView(R.id.headerTV), header);
            //super.setupScreen();
        }
        else
        {
            backNavigation(null, this, findRelativeLayout(R.id.backLayout), findTextView(R.id.headerTV), headerskipcase);

        }
    }

    private  void callMainFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment_main != null) {
            fragmentTransaction.detach(fragment_main);
            fragment_main=null;

        }
        fragment_main = new Fragment_Exhibitors();
        fragmentTransaction.replace(R.id.main_frame, fragment_main);
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //CallFragmnet(fragment);
    }
    private  void contactOrgFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment_main != null) {
            fragmentTransaction.detach(fragment_main);
            fragment_main=null;

        }
        fragment_main = new FragmentOrganizer();
        fragmentTransaction.replace(R.id.main_frame, fragment_main);
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //CallFragmnet(fragment);
    }
   public void initiatePopupWindow(final String email) {
        dialog = new Dialog(context);
        // dialog = new Dialog(this, R.style.NewDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //  dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.6f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(R.layout.contact_option);
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        Window window = dialog.getWindow();
        window.setLayout(widthPixels, heightPixels);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView emailBT=(TextView)dialog.findViewById(R.id.emailBT) ;
        emailBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail(email);
            }
        });
        dialog.show();
    }

    private void sendMail(String mailTo){
        String mailto = "mailto:" +mailTo;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        try {
            context.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

}
