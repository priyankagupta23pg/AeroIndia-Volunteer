package com.aeroindia.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.view.activity.DocCameraActivity;
import com.customComponent.CustomAlert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AppUtility {
    public static double maxLimit=5;
    public static String sizeMsg ="File size should not be greater than 2 MB";
    public static String sizeMsg1 ="File size should not be greater than 200 KB";
    public static double maxLimit1=200;

    public static void openCamera(Activity activity, int cameraMode, String purpose, String screenName) {
        Intent i = new Intent(activity, DocCameraActivity.class);
        i.putExtra("camera_key", cameraMode);
        i.putExtra("purpose", purpose);
        i.putExtra("screen",screenName);
        activity.startActivityForResult(i, AppConstant.REQ_CAMERA);
    }

    public static void openCameraForGovtId(Activity activity, int cameraMode, String purpose, String screenName) {
        Intent i = new Intent(activity, DocCameraActivity.class);
        i.putExtra("camera_key", cameraMode);
        i.putExtra("purpose", purpose);
        i.putExtra("screen",screenName);
        activity.startActivityForResult(i, AppConstant.BACK_REQ_CAMERA);
    }

    public static void openCamera(Activity activity, Fragment fragment, int cameraMode, String purpose, String screenName) {
        Intent i = new Intent(activity, DocCameraActivity.class);
        i.putExtra("camera_key", cameraMode);
        i.putExtra("purpose", purpose);
        i.putExtra("screen",screenName);
        fragment.startActivityForResult(i, AppConstant.REQ_CAMERA);
    }

    public static String converBitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result.toLowerCase();
    }

    public static String fileToBase64Conversion(Uri file,Context context) {

        String attachedFile;
        InputStream inputStream = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            inputStream = context.getContentResolver().openInputStream(file);
            byte[] buffer = new byte[8192];
            int bytesRead;
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output64.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            output64.close();
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
        attachedFile = output.toString();
        return attachedFile;
    }



    public static long calculateBitmapSize(Uri file,Context context){
        Bitmap original = null;
        try {
            original = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(file));
            // Log.d("TAG","Compressed size : "+original.getByteCount());
            // original = Bitmap.createScaledBitmap(original,768,1024,false);
            Matrix m = new Matrix();
            m.postRotate(90);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // original.compress(Bitmap.CompressFormat.JPEG, 70, out);
            //original=decodeFile(new File(file.toString()));

            original.compress(Bitmap.CompressFormat.JPEG,90,out);
            byte[] byteArray = out.toByteArray();
            long size=(byteArray.length/1024)/1024;
            return size;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            InputStream inputStream = null;//You can get an inputStream using any IO API
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                inputStream = context.getContentResolver().openInputStream(file);
                byte[] buffer = new byte[8192];
                int bytesRead;
                Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output64.write(buffer, 0, bytesRead);
                    }
                    long size=(buffer.length/1024)/1024;
                    return size;
                } catch (IOException ex) {
                    e.printStackTrace();
                }
                output64.close();
            } catch (Exception ex) {
                System.out.print(ex.toString());
            }
        }

        return 0;
    }
    public static long calculateBitmapSizeForProfilePic(Uri file,Context context){
        Bitmap original = null;
        try {
            File img = new File(file.getPath());
            long length = img.length();
            long size=(length/1024);
            Log.d("TAG","Image Size : "+size);
            return size;
        }catch (Exception e){
           CustomAlert.alertWithOk(context,"Please upload valid image");
            return 0;
        }

    }

}
