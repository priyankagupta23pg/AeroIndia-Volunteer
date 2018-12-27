package com.aeroindia.view.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.customComponent.utility.BaseAppCompatActivity;

public class WebViewMapActivity extends BaseAppCompatActivity {
RelativeLayout backLayout;
TextView headerTV;
WebView wView;
private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_map);
        context=this;
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText("Exhibition Layout");
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().hide();
        wView = (WebView) findViewById(R.id.webhome);
        wView.getSettings().setJavaScriptEnabled(true);
       // wView.getSettings().setPluginState(WebSettings.PluginState.ON);
       // wView.setScrollbarFadingEnabled(true);
       // wView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        //wView.getSettings().setDefaultTextEncodingName("utf-8");
        //wView.getSettings().setDomStorageEnabled(true);
       // wView.getSettings().setUseWideViewPort(false);
        //wView.getSettings().setLoadWithOverviewMode(false);

        //wView.getSettings().setLoadsImagesAutomatically(true);
        //wView.getSettings().setAppCacheMaxSize(1024*1024*8);
        //wView.getSettings().setAppCachePath("/data/data/com.aero.india/cache"‌​);
        wView.getSettings().setAppCacheEnabled(true);
        wView.getSettings().setAppCachePath(context.getCacheDir().getPath());

        wView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        //for camera opening
//        wView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        wView.getSettings().setAllowFileAccessFromFileURLs(true);
//        wView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }


       // if ((AppUtilityFunction.isNetworkAvailable(context))) {
           // wView.loadUrl("https://www.google.com/maps/d/embed?mid=1qIwd_n7sWa1Sd2bFjdL9kyHoWF-rYwuh");
            wView.loadUrl("https://fortress.maptive.com/ver4/1ef089c645eb79efaff04eac388d7256/238494");
           // wView.loadUrl("http://developer.android.com/guide/index.html");
            wView.setWebViewClient(new MyWebViewClient(context));
            wView.setWebChromeClient(new WebChromeClient() {
                // Need to accept permissions to use the camera
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    Log.d("Camera", "Permission");
                    request.grant(request.getResources());
                }
            });
//        } else {
//            CustomAlert.alertOkWithFinish(context, "Please connect to internet.");
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wView.canGoBack()) {
            //if Back key pressed and webview can navigate to previous page
            wView.goBack();
            // go back to previous page
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            //Do something
            return true;

        } else {
            finish();
            // finish the activity
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyWebViewClient extends WebViewClient {
        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("URLL", url);

            return super.shouldOverrideUrlLoading(view, url);





        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showHideProgressDialog(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showHideProgressDialog(false);
        }
    }

}

