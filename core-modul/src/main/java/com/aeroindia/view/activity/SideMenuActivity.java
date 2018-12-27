package com.aeroindia.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeroindia.R;

public class SideMenuActivity extends AppCompatActivity {
    private ImageView backIV;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        init();
    }


    private void init(){
        backIV=(ImageView)findViewById(R.id.backIV);
        title=(TextView)findViewById(R.id.title);
    }

    /* ======== Call to replace fragment for drawer item ========*/
    private void CallFragmnet(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  fragmentTransaction.setCustomAnimations(R.anim.push_in_from_left, 0, 0, R.anim.push_out_to_left);
            fragmentTransaction.replace(R.id.container, fragment);
            //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //Set the Toolbar Title
            getSupportActionBar().setTitle(title);
        }
    }
}
