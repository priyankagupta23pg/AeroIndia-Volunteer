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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.DocCamera.ImageUtil;
import com.aeroindia.R;
import com.aeroindia.Utility.AppUtility;
import com.aeroindia.Utility.PermissionUtil;
import com.aeroindia.adapter.DocumentUploadAdapter;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.CreateProfileRequestModel;
import com.aeroindia.pojos.request.SupportDocsItemModel;
import com.aeroindia.pojos.response.CreateProfileResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.FileChooser.afilechooser.utils.FileUtils;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aeroindia.DocCamera.ImageUtil.rotateImage;

/**
 * Created by SUNAINA on 27-11-2018.
 */

public class CreateExhibitorProfileActivity extends AppCompatActivity {
    private CreateExhibitorProfileActivity activity;
    private Context context;
    private EditText aboutMeET, weblinkET;
    private CustomVolley volley;
    private CreateProfileResponseModel createProfileResponseModel,
            getProfileResponseModel, updateProfileResponseModel;
    private LoginResponse loginResponse;
    private Button submitBTN;
    private LinearLayout companyLogoLL, documentLL, errorDocTV;
    private RecyclerView logoRV, docRV;
    private String[] list = {"Take a photo", "Gallery"};
    private Bitmap bitmap;
    private ImageView logoIV;
    private String logoImg = "";
    private String purpose = "";
    private static int UPLOAD_DOC_REQUEST = 1;
    private static int UPLOAD_DOC_REQUEST_LOGO = 2;
    private ArrayList<SupportDocsItemModel> uploadFileList;
    private DocumentUploadAdapter docAdapter;
    private RelativeLayout backLayout;
    private TextView headerTV;
    private LinearLayout docListLL;
    private String webUrl = "", screenName = "";

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exhibitor_profile_updated);
        activity = this;
        context = this;
        setupScreen();
    }

    private void setupScreen() {
        getSupportActionBar().hide();
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.company_profile));
        aboutMeET = (EditText) findViewById(R.id.aboutMeET);
        weblinkET = (EditText) findViewById(R.id.weblinkET);
        submitBTN = (Button) findViewById(R.id.submitBTN);
        logoIV = (ImageView) findViewById(R.id.logoIV);
        companyLogoLL = (LinearLayout) findViewById(R.id.companyLogoLL);
        documentLL = (LinearLayout) findViewById(R.id.documentLL);
        errorDocTV = (LinearLayout) findViewById(R.id.errorDocTV);
        errorDocTV.setVisibility(View.VISIBLE);
        docListLL = (LinearLayout) findViewById(R.id.docListLL);
        docListLL.setVisibility(View.GONE);
        docRV = (RecyclerView) findViewById(R.id.docRV);
        // docRV.setVisibility(View.GONE);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        docRV.setLayoutManager(new LinearLayoutManager(activity));
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(docRV.getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.gradient_line));
        docRV.addItemDecoration(itemDecoration);

        uploadFileList = new ArrayList<>();
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
                        if (i == 0) {
                            if (!PermissionUtil.checkCameraPermission(context) || !PermissionUtil.checkStoragePermission(context)) {
                                PermissionUtil.askAllPermissionCamera(activity);
                            }
                            //Camera
                            if (PermissionUtil.checkCameraPermission(context) && PermissionUtil.checkStoragePermission(context)) {
                                AppUtility.openCameraForGovtId(activity, AppConstant.BACK_CAMREA_OPEN, "ForStore", "DummyImagePreviewClass");
                                dialog.dismiss();
                            }


                        } else if (i == 1) {
                            //Gallery
                            if (PermissionUtil.checkExternalStoragePermission(activity)) {
                                galleryIntentLogo();
                                dialog.dismiss();
                            }
                        }
                    }
                });

                dialog.show();


            }
        });

        documentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PermissionUtil.checkExternalStoragePermission(activity)) {
                    galleryIntent();

                }

    /*            final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation_popup;
                dialog.setContentView(R.layout.cutom_camera_list);
                ListView cameraList = (ListView) dialog.findViewById(R.id.lv);
                ImageView cancelIV = (ImageView) dialog.findViewById(R.id.cancelIV);
                cancelIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                cameraList.setAdapter(adapter);
                cameraList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
//Camera
                            AppUtility.openCameraForGovtId(activity, AppConstant.BACK_CAMREA_OPEN, "ForStore", "DummyImagePreviewClass");
                            dialog.dismiss();
                        } else if (i == 1) {
                            //Gallery
                            galleryIntent();
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();*/
            }
        });


        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUrl = weblinkET.getText().toString();

                if (webUrl.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter company url", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isValidUrl(webUrl)) {
                    Toast.makeText(context, "Your company url is not valid", Toast.LENGTH_LONG).show();
                    return;

                }
            }
        });

       screenName= getIntent().getStringExtra("Screen");
        if (screenName!=null && !screenName.equalsIgnoreCase("") &&
                screenName.equalsIgnoreCase("ExhibitorViewProfile")) {
            viewProfile();
        }
        // createProfileRequest();

    }

    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    private void galleryIntent() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, UPLOAD_DOC_REQUEST);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
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

    private void createProfileRequest() {

        CreateProfileRequestModel getProfileRequest = new CreateProfileRequestModel();
        if (loginResponse.getCompany() != null) {
            if (loginResponse.getCompany().getCompanyId() != 0) {
                getProfileRequest.setExhibitorId(loginResponse.getCompany().getCompanyId());
            }
        }
        getProfile(getProfileRequest);
    }

    private void getProfile(CreateProfileRequestModel request) {
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                getProfileResponseModel = CreateProfileResponseModel.create(response);
                if (getProfileResponseModel.isStatus()) {
                    if (getProfileResponseModel != null && getProfileResponseModel.getProfile() != null
                            && getProfileResponseModel.getProfile().getAboutMe() != null) {
                        setProfileData(getProfileResponseModel.getProfile().getAboutMe());
                    }
                    /*ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, loginResponse.serialize(), activity);

                    Intent intent=new Intent(activity,DashboardActivity.class);
                    startActivity(intent);*/
                    // dialog.dismiss();
                    //  Toast.makeText(context, "Create Profile Successfully.", Toast.LENGTH_LONG).show();


                    return;
                } else {

                    Toast.makeText(context, getProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley = new CustomVolley(taskListener, "Please wait..", AppConstant.GET_PROFILE, request.serialize(), null, null, context);
        volley.execute();
    }

    private void setProfileData(String aboutCompany) {
        // Static data
        // weblinkET.setText("www.psquickit.com");
        aboutMeET.setText(aboutCompany);
        aboutMeET.requestFocus();
        aboutMeET.setSelection(aboutCompany.length());
        // logoIV.setImageResource(R.drawable.psquickit_logo);

        submitBTN.setText("Update");
    }

    //static data
    private void viewProfile() {
        String aboutCompany = "PS QuickIT has extensive experience building native & hybrid mobility solutions on Android, iPhone, iPad, and Windows.";
        weblinkET.setText("www.psquickit.com");
        aboutMeET.setText(aboutCompany);
        aboutMeET.requestFocus();
        //aboutMeET.setSelection(aboutCompany.length());
        logoIV.setImageResource(R.drawable.psquickit_logo);
        submitBTN.setVisibility(View.GONE);
        companyLogoLL.setVisibility(View.GONE);
        documentLL.setVisibility(View.GONE);

        weblinkET.setEnabled(false);
        aboutMeET.setEnabled(false);

        SupportDocsItemModel itemModel=new SupportDocsItemModel();
        itemModel.setName("File1.jpg");
        itemModel.setDocFile("File1.jpg");
        itemModel.setDocID("1");
        itemModel.setScreen("ViewProfile");

        uploadFileList.add(0,itemModel);
        refreshList();

    }

    private void createProfile(CreateProfileRequestModel request) {
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                createProfileResponseModel = CreateProfileResponseModel.create(response);
                if (createProfileResponseModel.isStatus()) {
                    Toast.makeText(context, "Profile Created Successfully.", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    Toast.makeText(context, createProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley = new CustomVolley(taskListener, "Please wait..", AppConstant.CREATE_PROFILE, request.serialize(), null, null, context);
        volley.execute();
    }

    private void updateProfile(CreateProfileRequestModel request) {
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                updateProfileResponseModel = CreateProfileResponseModel.create(response);
                if (updateProfileResponseModel.isStatus()) {
                    Toast.makeText(context, "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    Toast.makeText(context, updateProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley = new CustomVolley(taskListener, "Please wait..", AppConstant.UPDATE_PROFILE, request.serialize(), null, null, context);
        volley.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.BACK_REQ_CAMERA && resultCode == RESULT_OK) {
            final Intent intent = data;//new Intent();
            String path = intent.getStringExtra("response");
            Uri uri = Uri.fromFile(new File(path));
            if (uri == null) {
                Log.d("uri", "null");
            } else {
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File mediaFile = null;
                if (bitmap != null) {
                    byte[] imageBytes = ImageUtil.bitmapToByteArray(rotateImage(bitmap, 270));

                    File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "");
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator + purpose + ".jpg");
                    if (mediaFile != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(mediaFile);
                            fos.write(imageBytes);
                            fos.close();
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {

                        }
                    }
                }
            }

            logoImg = AppUtility.converBitmapToBase64(bitmap);
            if (logoImg != null) {
                logoIV.setImageBitmap(bitmap);
            } else {
                logoIV.setImageBitmap(null);
            }
        }


        if (requestCode == UPLOAD_DOC_REQUEST && resultCode == RESULT_OK) {
            final SupportDocsItemModel fileObj = new SupportDocsItemModel();
            boolean fileShow = true;
            final Uri uri = data.getData();
            String encodeFileToBase64Binary = null;
            if (data != null) {
                String path = data.getStringExtra("path");
                System.out.print(path);
                Uri uploadedFilePath = data.getData();
                String filename = AppUtility.getFileName(uploadedFilePath, context);
                filename = filename.toLowerCase();
                String fileDesc = AppUtility.getFileName(uploadedFilePath, context);
                String[] extList = filename.split("\\.");
                System.out.print(extList[1].toString());
                String extension = "." + extList[extList.length - 1];
                //  encodeFileToBase64Binary = Utility.fileToBase64Conversion(data.getData(), context);
                // Log.d("TAG", "RAR Base 64 :" + encodeFileToBase64Binary);
               /* extensionList = Arrays.asList(advanceRequestResponseModel.getGetAdvancePageInitResult().getDocValidation().getExtensions());
                if (!extensionList.contains(extension.toLowerCase())) {
                    CustomDialog.alertWithOk(context, advanceRequestResponseModel.getGetAdvancePageInitResult().getDocValidation().getMessage());
                    return;
                }*/
                if (AppUtility.calculateBitmapSize(data.getData(), context) > AppUtility.maxLimit) {
                    CustomAlert.alertWithOk(context, AppUtility.sizeMsg);
                    return;
                }
                fileObj.setDocPathUri(uploadedFilePath);

               /* if (filename.contains(".pdf")) {
                    try {
                        encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                        fileObj.setDocFile(filename);
                        fileObj.setName(fileDesc);
                        fileObj.setExtension(extension);

                    } catch (Exception e) {
                        System.out.print(e.toString());
                    }
                } else*/
                if (filename.contains(".jpg") || filename.contains(".png") ||
                        filename.contains(".jpeg") || filename.contains(".bmp") ||
                        filename.contains(".BMP")) {

                    bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File mediaFile = null;
                    if (bitmap != null) {
                        encodeFileToBase64Binary = AppUtility.converBitmapToBase64(bitmap);
                        byte[] imageBytes = ImageUtil.bitmapToByteArray(rotateImage(bitmap, 270));

                        File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "");
                        mediaFile = new File(mediaStorageDir.getPath() + File.separator + purpose + ".jpg");
                        if (mediaFile != null) {
                            try {
                                FileOutputStream fos = new FileOutputStream(mediaFile);
                                fos.write(imageBytes);
                                fileObj.setDocFile(filename);
                                fileObj.setName(fileDesc);
                                fileObj.setExtension(extension);
                                fos.close();
                            } catch (FileNotFoundException e) {

                            } catch (IOException e) {

                            }
                        }
                    }
                } else {
                    CustomAlert.alertWithOk(context, "Please upload valid image");
                    return;
                }

                /*else if (filename.contains(".docx") || filename.contains(".doc")) {
                    try {
                        encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                        fileObj.setDocFile(filename);
                        fileObj.setName(fileDesc);
                        fileObj.setExtension(extension);
                    } catch (Exception e) {

                    }
                } else if (filename.contains(".xlsx") || filename.contains(".xls")) {
                    try {
                        encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                        fileObj.setDocFile(filename);
                        fileObj.setName(fileDesc);
                        fileObj.setExtension(extension);

                    } catch (Exception e) {

                    }
                } else if (filename.contains(".txt")) {
                    try {
                        encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                        fileObj.setDocFile(filename);
                        fileObj.setName(fileDesc);
                        fileObj.setExtension(extension);
                    } catch (Exception e) {

                    }
                } else if (filename.contains(".gif")) {
                    encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                    fileObj.setDocFile(filename);
                    fileObj.setName(fileDesc);
                    fileObj.setExtension(extension);
                } else if (filename.contains(".rar")) {
                    encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                    fileObj.setDocFile(filename);
                    fileObj.setName(fileDesc);
                    fileObj.setExtension(extension);
                } else if (filename.contains(".zip")) {
                    encodeFileToBase64Binary = AppUtility.fileToBase64Conversion(data.getData(), context);
                    fileObj.setDocFile(filename);
                    fileObj.setName(fileDesc);
                    fileObj.setExtension(extension);
                }*/


               /* if (Utility.calcBase64SizeInKBytes(encodeFileToBase64Binary) > Utility.maxLimit) {
                    CustomDialog.alertWithOk(context, Utility.sizeMsg);
                    return;
                }*/
                if (fileShow) {
                    if (uploadFileList.size() > 0) {
                        for (int i = 1; i <= uploadFileList.size(); i++) {
                            fileObj.setBase64Data(encodeFileToBase64Binary);
                            fileObj.setFlag("N");
                            String seqNo = String.valueOf(i + 1);
                            Log.d("seqNo", "seqNo");
                            uploadFileList.add(fileObj);
                            break;
                        }
                    } else {
                        fileObj.setBase64Data(encodeFileToBase64Binary);
                        fileObj.setFlag("N");
                        uploadFileList.add(fileObj);
                    }
                }

                refreshList();

            }
        }

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
                                logoIV.setImageBitmap(bitmap);
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

    private void refreshList() {
        if (uploadFileList != null && uploadFileList.size() > 0) {
            errorDocTV.setVisibility(View.GONE);
            //docRV.setVisibility(View.VISIBLE);
            docListLL.setVisibility(View.VISIBLE);
            docAdapter = new DocumentUploadAdapter(uploadFileList, context, AppConstant.EDIT, errorDocTV, activity);
            docRV.setAdapter(docAdapter);
            docAdapter.notifyDataSetChanged();
        } else {
            errorDocTV.setVisibility(View.VISIBLE);
            //  docRV.setVisibility(View.GONE);
            docListLL.setVisibility(View.GONE);
        }
    }
}
