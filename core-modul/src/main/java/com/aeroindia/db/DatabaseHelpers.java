package com.aeroindia.db;

/**
 * Created by psqit on 6/13/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;


import com.aeroindia.custom.utility.AppConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelpers extends SQLiteOpenHelper {


    private static String DB_NAME = AppConstant.DATABASE;
   // private static String DB_NAME1 = Environment.getExternalStorageDirectory() + "/Fred360/database/" + DB_NAME;//"

    private static DatabaseHelpers dataBaseRef;
    private final Context mContext;
    private final String TAG = "DatabaseHelpers class";

private int mOpenCounter=0;


    private SQLiteDatabase mDatabase;

    public DatabaseHelpers(Context context) {

        super(context, Environment.getExternalStorageDirectory()+File.separator+context.getPackageName()+File.separator+"database"+File.separator+DB_NAME, null,2);

        this.mContext = context;

    }
    public synchronized  static DatabaseHelpers getInstance(Context mContext) {
        if (dataBaseRef != null) {
            return dataBaseRef;
        } else {
            return new DatabaseHelpers(mContext);
        }
    }
    @Override
    public  void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

      //  Log.d(TAG, "On Create is calling...");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
       // Log.d(TAG, "onUpgrade is calling...");
//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE pop_secc_output_idea_schema_main_filter ADD COLUMN surveyed_status INTEGER DEFAULT 0");
//        }
    }
    public synchronized  SQLiteDatabase createOrOpenDatabase() {
        boolean dbExist = checkDataBase1();
        if (dbExist) {
         //   Log.i( TAG ," Create db1 :" + dbExist);
            openDataBase1();
        } else {

        }
        return mDatabase;
    }
    public synchronized  SQLiteDatabase openWriteableDB() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            mDatabase = this.getWritableDatabase();
        }
        return mDatabase;
    }
    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }
    private void copyDataBase1() {

        // Open your local db as the input stream
       // Log.i("", "Coppying data base .." + mContext);
        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);

           // Log.i("", "Coppying data base .." + mContext);

            File outFileName = new File(Environment.getExternalStorageDirectory()+File.separator+mContext.getPackageName()+File.separator+"database"+File.separator+DB_NAME);
           // Log.i("", "Coppying data base .." + mContext);

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }


            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
           // Log.i("", "Coppying data base .." + e.toString());
        }

    }
    public synchronized  void openDataBase1() throws SQLException {

        mDatabase = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory()+File.separator+mContext.getPackageName()+File.separator+"database"+File.separator+DB_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
      //  Log.i( TAG , " Data base is open : " + mDatabase);


    }
    @Override
    public synchronized void close() {

        if (mDatabase != null)
            mDatabase.close();

        super.close();

    }
    private boolean checkDataBase1() {
        SQLiteDatabase checkDB = null;

        try {
String DB_NAME1=Environment.getExternalStorageDirectory()+ File.separator+mContext.getPackageName()+File.separator+"database"+File.separator+DB_NAME;
            File file = new File(DB_NAME1);
            if (file.exists() && !file.isDirectory()) {
                checkDB = SQLiteDatabase.openDatabase(DB_NAME1, null,
                        SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
           //    Log.i( TAG , " Database exist in sd card : " + checkDB);

            } else {

                File dbfile = new File(Environment.getExternalStorageDirectory()+File.separator+mContext.getPackageName()+File.separator+"database");
                dbfile.mkdirs();

               // Log.i(TAG, "Media Scanner Null pointer excpetion.." + mContext);
                MediaScannerConnection.scanFile(mContext, new String[]{dbfile.toString()}, null, null);
                File dbfile1 = new File(DB_NAME1);
              //  Log.i(TAG, "Media Scanner Null pointer excpetion.." + mContext);

                checkDB = SQLiteDatabase.openOrCreateDatabase(dbfile1, null);
               // Log.i(TAG, "Media Scanner Null pointer excpetion.." + checkDB);
                try {
                    copyDataBase1();
                    Uri uri = Uri.fromFile(dbfile1);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    mContext.sendBroadcast(intent);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                  //  Log.i( TAG , " New Database is created " + e.toString());
                    e.printStackTrace();
                }
               // Log.i(TAG ," New Database is created " + checkDB);
            }



        } catch (SQLiteException e) {
          //  Log.i("", TAG + " Data base exist : " + e.toString());

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }


}

