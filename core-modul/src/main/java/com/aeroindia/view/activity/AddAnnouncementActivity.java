package com.aeroindia.view.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.DocCamera.ImageUtil;
import com.aeroindia.R;
import com.aeroindia.Utility.AppUtility;
import com.aeroindia.Utility.PermissionUtil;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.response.LoginResponse;
import com.customComponent.CustomAlert;
import com.customComponent.FileChooser.afilechooser.utils.FileUtils;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SUNAINA on 27-11-2018.
 */

public class AddAnnouncementActivity extends AppCompatActivity {
    private AddAnnouncementActivity activity;
    private Context context;
    private LoginResponse loginResponse;
    private RelativeLayout backLayout;
    private TextView headerTV;
    private LinearLayout companyLogoLL;
    private String[] list = {"Gallery"};
    private static int UPLOAD_DOC_REQUEST_LOGO = 2;
    private Bitmap bitmap;
    private ImageView announcementIV;
    private String purpose = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_announcement_layout);
        activity = this;
        context = this;
        setupScreen();
    }

    private void setupScreen() {
        getSupportActionBar().hide();
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.add_announcement));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        announcementIV = (ImageView) findViewById(R.id.announcementIV);
        companyLogoLL = (LinearLayout) findViewById(R.id.companyLogoLL);

        companyLogoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation_popup;
                dialog.setContentView(R.layout.cutom_camera_list);
                ImageView cancelIV = (ImageView) dialog.findViewById(R.id.cancelIV);
                cancelIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ListView cameraList = (ListView) dialog.findViewById(R.id.lv);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                cameraList.setAdapter(adapter);
                cameraList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (PermissionUtil.checkExternalStoragePermission(activity)) {
                            galleryIntentLogo();
                            dialog.dismiss();

                        }
                    }
                });

                dialog.show();


            }
        });

    }

    private void galleryIntentLogo() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, UPLOAD_DOC_REQUEST_LOGO);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_DOC_REQUEST_LOGO && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            String encodeFileToBase64Binary = null;
            if (data != null) {
                /*if (AppUtility.calculateBitmapSizeForProfilePic(data.getData(), context) > AppUtility.maxLimit1) {
                    CustomAlert.alertWithOk(context, AppUtility.sizeMsg1);
                    return;
                }*/
                // String path = data.getStringExtra("path");
                //System.out.print(path);

                Uri uploadedFilePath = data.getData();
                String filename = AppUtility.getFileName(uploadedFilePath, context);
                filename = filename.toLowerCase();
                String fileDesc = AppUtility.getFileName(uploadedFilePath, context);
                String[] extList = filename.split("\\.");
                System.out.print(extList[1].toString());
                String extension = "." + extList[extList.length - 1];


                if (filename.contains(".jpg") || filename.contains(".jpeg") ||
                        filename.contains(".JPEG") || filename.contains(".JPG") ||
                        filename.contains(".png") || filename.contains(".PNG")) {


                    // if (filename.contains(".jpg") || filename.contains(".jpeg")) {
                    bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File mediaFile = null;
                    if (bitmap != null) {
                        encodeFileToBase64Binary = AppUtility.converBitmapToBase64(bitmap);
                        byte[] imageBytes = ImageUtil.bitmapToByteArray(bitmap);

                        File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "");
                        mediaFile = new File(mediaStorageDir.getPath() + File.separator + purpose + ".jpg");
                        if (mediaFile != null) {
                            try {
                                FileOutputStream fos = new FileOutputStream(mediaFile);
                                fos.write(imageBytes);
                                announcementIV.setImageBitmap(bitmap);
                                fos.close();
                            } catch (FileNotFoundException e) {

                            } catch (IOException e) {

                            }
                        }
                    }
                    // }


                   /* UploadProfilePicModel uploadProfilePicModel = new UploadProfilePicModel();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setBase64Data(encodeFileToBase64Binary);
                    fileInfo.setExtension(".jpg");
                    fileInfo.setLength("0");
                    fileInfo.setName("MyPhoto");
                    uploadProfilePicModel.setFileInfo(fileInfo);

                    CommunicationManager.getInstance().sendPostRequest(this,
                            AppRequestJSONString.uploadProfileRequest(uploadProfilePicModel),
                            CommunicationConstant.API_UPLOAD_PROFILE_PIC, true);*/
                } else {
                    CustomAlert.alertWithOk(context, "Please upload valid image");
                    return;
                }
            }
        }


    }
}
