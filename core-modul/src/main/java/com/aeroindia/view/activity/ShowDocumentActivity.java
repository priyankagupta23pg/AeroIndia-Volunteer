package com.aeroindia.view.activity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.customComponent.CustomAsyncTask;
import com.customComponent.utility.BaseAppCompatActivity;
//import com.github.barteksc.pdfviewer.PDFView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import pl.polidea.view.ZoomView;

public class ShowDocumentActivity extends BaseAppCompatActivity {
    private LinearLayout uploadedImageLayout;
    private ImageView imageIV;
    private Context context;
    private CustomAsyncTask asyncTask;
    private Object fileItem;
    private String downloadUrl;
    private Bitmap downloadedBitmap;
    public static String FILE_CONTENT = "fileContent";
    private WebView webView;
    private TextView headerTV;

    private String downloadedFileExtension;
    //private PDFView pdfView;
    private TextView textView;
    private LinearLayout mZoomLinearLayout;
    private ZoomView zoomView;
    private RelativeLayout backLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_document);
        setupScreen();

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setupScreen() {
        context = this;
      //  loginDetail = LoginResponseItem.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_CONTENT, context));
        uploadedImageLayout = (LinearLayout) findViewById(R.id.uploadedImageLayout);

        mZoomLinearLayout = (LinearLayout) findViewById(R.id.mZoomLinearLayout);
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        fileItem = getIntent().getSerializableExtra(FILE_CONTENT);
        webView = (WebView) findViewById(R.id.webView);
        headerTV = (TextView) findViewById(R.id.headerTV);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // pdfView = (PDFView) findViewById(R.id.pdfView);
        textView = (TextView) findViewById(R.id.textView);

        downloadImageFromServer();
    }

    private void downloadImageFromServer() {

if(downloadUrl!=null && !downloadUrl.equalsIgnoreCase("")) {
    downloadAndOpenPDF(context, downloadUrl);
}
      /*  TaskListener taskListener = new TaskListener() {
            @Override
            public void execute() {
                if (fileItem instanceof PrescriptionFileItemResponse) {
                    PrescriptionFileItemResponse item = (PrescriptionFileItemResponse) fileItem;
                    downloadUrl = AppConstant.GET_PRESCRIPTION_FILE + item.getId();
                }
                downloadedBitmap = CustomHttp.getBitmapFromURL(downloadUrl, loginDetail.getAuthToken());
            }

            @Override
            public void updateUI() {
                if (downloadedBitmap != null) {
                    imageIV.setImageBitmap(downloadedBitmap);
                }

            }
        };
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
        asyncTask = new CustomAsyncTask(taskListener, "Please wait..", context);
        asyncTask.execute();*/
    }


    // get File name from url
    public class GetFileInfo extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            URL url;
            String filename = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestProperty("authToken", loginDetail.getAuthToken());
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setInstanceFollowRedirects(false);
                conn.connect();
                if (conn.getHeaderField("Content-Disposition") != null) {
                    String depo = conn.getHeaderField("Content-Disposition");
                    String depoSplit[] = depo.split("filename=");
                    filename = depoSplit[1].replace("filename=", "").replace("\"", "").trim();
                } else {
                    filename = "";
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            }
            return filename;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // use result as file name
        }
    }


    public void openDownloadedFile(File fileName) {

        if (downloadedFileExtension != null && !downloadedFileExtension.equalsIgnoreCase("")) {

            if (downloadedFileExtension.equalsIgnoreCase(".pdf")) {
                uploadedImageLayout.setVisibility(View.GONE);
//                pdfView.setVisibility(View.VISIBLE);
//                pdfView.fromFile(fileName).load();
//                pdfView.bringToFront();
                imageIV.setVisibility(View.GONE);
            } else if (downloadedFileExtension.equalsIgnoreCase(".xls") || downloadedFileExtension.equalsIgnoreCase(".xlsx") || downloadedFileExtension.equalsIgnoreCase(".doc") || downloadedFileExtension.equalsIgnoreCase(".docx")) {
                uploadedImageLayout.setVisibility(View.VISIBLE);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(fileName), "application/msword");
                startActivity(i);
               // pdfView.setVisibility(View.GONE);
            } else if (downloadedFileExtension.equalsIgnoreCase(".jpeg") || downloadedFileExtension.equalsIgnoreCase(".png") || downloadedFileExtension.equalsIgnoreCase(".jpg")) {
                Bitmap bitmap = BitmapFactory.decodeFile(fileName.getPath());
                uploadedImageLayout.setVisibility(View.VISIBLE);
                //pdfView.setVisibility(View.GONE);
                View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoomable_layout, null, false);
                v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                mZoomLinearLayout.setVisibility(View.VISIBLE);
                zoomView = new ZoomView(context);
                zoomView.setMaxZoom(200);
                zoomView.addView(v);
                mZoomLinearLayout.addView(zoomView);
                imageIV.setImageBitmap(bitmap);
                imageIV.setVisibility(View.VISIBLE);

            } else if (downloadedFileExtension.equalsIgnoreCase(".txt")) {
                uploadedImageLayout.setVisibility(View.VISIBLE);
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                } catch (IOException e) {
                    //You'll need to add proper error handling here
                }
                textView.setVisibility(View.VISIBLE);
                imageIV.setVisibility(View.GONE);
               // pdfView.setVisibility(View.GONE);
                textView.setText(text);

            }
        }

    }


    public void downloadAndOpenPDF(Context context, String pdfUrl) {
        String filename = "";
        try {
            filename = new GetFileInfo().execute(pdfUrl).get();
            if (!filename.equalsIgnoreCase("")) {
                headerTV.setText(filename);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!filename.equalsIgnoreCase("")) {
            if (filename.contains(".pdf")) {
                downloadedFileExtension = ".pdf";
            } else if (filename.contains(".doc")) {
                downloadedFileExtension = ".doc";
            } else if (filename.contains(".docx")) {
                downloadedFileExtension = ".docx";
            } else if (filename.contains(".xlsx")) {
                downloadedFileExtension = ".xlsx";
            } else if (filename.contains(".xls")) {
                downloadedFileExtension = ".xls";
            } else if (filename.contains(".txt")) {
                downloadedFileExtension = ".txt";
            } else if (filename.contains(".png")) {
                downloadedFileExtension = ".png";
            } else if (filename.contains(".jpg")) {
                downloadedFileExtension = ".jpg";
            } else if (filename.contains(".jpeg")) {
                downloadedFileExtension = ".jpeg";
            }
        }
        if (!filename.equalsIgnoreCase("")) {
            final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);

            if (tempFile.exists()) {
                openDownloadedFile(tempFile);
                return;
            }
            final ProgressDialog progressDia = ProgressDialog.show(context, "Downloading..", "Downloading " + filename + "\nPlease wait...", true);
            progressDia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDia.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Create the download request
//            DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl)).addRequestHeader("authToken", loginDetail.getAuthToken());
          DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl));
            r.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOCUMENTS, filename);
            final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (!progressDia.isShowing()) {
                        return;
                    }
                    context.unregisterReceiver(this);

                    progressDia.dismiss();
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));

                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        int sizeIndex = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                        int downloadedIndex = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                        final long size = c.getInt(sizeIndex);
                        final long downloaded = c.getInt(downloadedIndex);
                        double progress = 0.0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDia.setProgress((int) (downloaded * 100.0 / size));
                            }
                        });
                        if (size != -1) progress = downloaded * 100.0 / size;
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            openDownloadedFile(tempFile);
                        }
                    }
                    c.close();
                }
            };
            context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            // Enqueue the request
            dm.enqueue(r);
        } else {
            alertWithOk(context,context.getResources().getString(R.string.datadownloading_failed));
        }
    }
    public void alertWithOk(Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Alert");
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
