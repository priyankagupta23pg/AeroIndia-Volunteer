package com.aeroindia.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.request.ExhibitorsItem;

import java.util.ArrayList;


/**
 * Created by psqit on 6/13/2016.
 */
public class ExhibitorInfoDaoImpl implements ExhibitorInfoDao {
    final String TAG="ACTIVITY INFO DAO IMPLE";

    @Override
   public long save(ExhibitorsItem item,
                                 DatabaseHelpers helper) {
        SQLiteDatabase db =null;
        long insertFlag = 0;
        try {
            helper.createOrOpenDatabase();

            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("hallNo", item.getHallNo());
            values.put("stalNo", item.getStalNo());
            values.put("company", item.getCompany());
            values.put("country", item.getCountry());
            values.put("phone", item.getPhone());
            values.put("name", item.getName());
            values.put("email", item.getEmail());
            values.put("companyId", item.getCompanyId());
            values.put("uniqueId", item.getUniqueId());

             insertFlag = db.insert(AppConstant.TABLE_EXHIBITOR, null,
                    values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(db!=null)

                db.close();
            helper.close();
        }
        return insertFlag;
    }


    @Override
    public ExhibitorsItem getExhibitorItem(String uniqueId, DatabaseHelpers helpers) {
        SQLiteDatabase mDatabase=null;
        ExhibitorsItem exhibitorsItem = null;
        try {
            mDatabase   = helpers.createOrOpenDatabase();


            // ArrayList<StoreVersionItem> loanList = new ArrayList<>();
            Cursor cur = mDatabase.rawQuery("Select * from "
                    + AppConstant.TABLE_EXHIBITOR + " where uniqueId='" + uniqueId + "'", null);
            if (cur != null) {
                if (cur.getCount() > 0) {
                    cur.moveToFirst();
                    while (cur.isAfterLast() == false) {
                        exhibitorsItem = new ExhibitorsItem();
                        exhibitorsItem.setHallNo(cur.getString(cur.getColumnIndex("hallNo")));
                        exhibitorsItem.setStalNo(cur.getString(cur.getColumnIndex("stalNo")));
                        exhibitorsItem.setCompany(cur.getString(cur.getColumnIndex("company")));
                        exhibitorsItem.setCountry(cur.getString(cur.getColumnIndex("country")));
                        exhibitorsItem.setPhone(cur.getString(cur.getColumnIndex("phone")));
                        exhibitorsItem.setName(cur.getString(cur.getColumnIndex("name")));
                        exhibitorsItem.setEmail(cur.getString(cur.getColumnIndex("email")));
                        exhibitorsItem.setCompanyId(cur.getInt(cur.getColumnIndex("companyId")));
                        exhibitorsItem.setUniqueId(cur.getString(cur.getColumnIndex("uniqueId")));
                        cur.moveToNext();
                    }
                }
                cur.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(mDatabase!=null)

                mDatabase.close();
            helpers.close();
        }

        return exhibitorsItem;
    }
    @Override
    public long update(ExhibitorsItem item,String uniqueId,DatabaseHelpers helper) {
        SQLiteDatabase db=null;
        int updateFlag=0;
        try {
            helper.createOrOpenDatabase();
            ContentValues values = new ContentValues();
            values.put("hallNo", item.getHallNo());
            values.put("stalNo", item.getStalNo());
            values.put("company", item.getCompany());
            values.put("country", item.getCountry());
            values.put("phone", item.getPhone());
            values.put("name", item.getName());
            values.put("email", item.getEmail());
            values.put("companyId", item.getCompanyId());
            values.put("uniqueId", item.getUniqueId());

            db = helper.getWritableDatabase();
            updateFlag = db.update(AppConstant.TABLE_EXHIBITOR , values,
                    "uniqueId= '" + uniqueId + "'", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(db!=null)

                db.close();
            helper.close();
        }
        //   Log.i(TAG ," Update Enum Info value : " + updateFlag);

        return updateFlag;
    }



    @Override
    public ArrayList<ExhibitorsItem> getExhibitors(DatabaseHelpers helpers) {
        SQLiteDatabase mDatabase=null;
        ArrayList<ExhibitorsItem> exhibitorsItemArrayList = new ArrayList<>();
try {
    mDatabase=helpers.createOrOpenDatabase();
    ExhibitorsItem exhibitorsItem = null;

    Cursor cur = mDatabase.rawQuery("Select * from "
            + AppConstant.TABLE_EXHIBITOR , null);
    if (cur != null) {
        if (cur.getCount() > 0) {

            cur.moveToFirst();
            while (cur.isAfterLast() == false) {
                exhibitorsItem = new ExhibitorsItem();
                exhibitorsItem.setHallNo(cur.getString(cur.getColumnIndex("hallNo")));
                exhibitorsItem.setStalNo(cur.getString(cur.getColumnIndex("stalNo")));
                exhibitorsItem.setCompany(cur.getString(cur.getColumnIndex("company")));
                exhibitorsItem.setCountry(cur.getString(cur.getColumnIndex("country")));
                exhibitorsItem.setPhone(cur.getString(cur.getColumnIndex("phone")));
                exhibitorsItem.setName(cur.getString(cur.getColumnIndex("name")));
                exhibitorsItem.setEmail(cur.getString(cur.getColumnIndex("email")));
                exhibitorsItem.setCompanyId(cur.getInt(cur.getColumnIndex("companyId")));
                exhibitorsItem.setUniqueId(cur.getString(cur.getColumnIndex("uniqueId")));
                exhibitorsItemArrayList.add(exhibitorsItem);
                cur.moveToNext();
            }
        }
        cur.close();

    }
}
catch (Exception e)
{
    e.printStackTrace();
}
finally {
    if(mDatabase!=null)

        mDatabase.close();
    helpers.close();
}

        return exhibitorsItemArrayList;
    }

    public void deleteAllExhibitors(DatabaseHelpers helpers) {
        SQLiteDatabase db = helpers.getWritableDatabase();
        db.execSQL("delete from "+AppConstant.TABLE_EXHIBITOR);
    }
}
