package com.aeroindia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.request.ContactEmailRequestModel;
import com.aeroindia.pojos.request.ReportUsRequestModel;
import com.aeroindia.pojos.request.SubmitFeedbackRequest;
import com.aeroindia.pojos.response.AllServicesResponse;
import com.aeroindia.pojos.response.EventModel;
import com.aeroindia.pojos.response.FeedbackResponse;

import java.util.ArrayList;

/**
 * Created by SUNAINA on 17-12-2018.
 */

public class CommonDatabaseAero {
    private static String TAG = "Common Database Log";

    public static void delete(Context context, String query){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        mDatabase.execSQL(query);
        mDatabase.close();
        helpers.close();
    }

    public static long saveContactRequest(Context context, ContactEmailRequestModel item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("CommType", item.getCommType());
        values.put("Message ", item.getMessage());
        values.put("Purpose ", item.getPurpose());
        values.put("ReceiverId ", item.getReceiverId());
        values.put("ReceiverType ", item.getReceiverType());
        values.put("SenderCompanyName ", item.getSenderCompanyName());
        values.put("SenderCountry ", item.getSenderCountry());
        values.put("SenderEmail ", item.getSenderEmail());
        values.put("SenderId ", item.getSenderId());
        values.put("SenderName ", item.getSenderName());
        values.put("SenderType ", item.getSenderType());
        values.put("Status ", item.getStatus());
        long insertFlag = mDatabase.insert(AppConstant.TABLE_CONTACT_EXHIBITOR_REQUEST, null, values);
        mDatabase.close();
        return insertFlag;
    }


    public static long saveServiceComplaintRequest(Context context, ReportUsRequestModel item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("QRCodeNo", item.getQrcodeNumber());
        values.put("ServiceIssue ", item.getServiceIssue());
        values.put("Comment ", item.getComment());
        values.put("DateTime ", item.getDateTime());
        values.put("CreatedBy ", item.getCreatedBy());
        values.put("Status ", item.getStatus());
        values.put("ScanStatus ", item.getScanStatus());
        long insertFlag = mDatabase.insert(AppConstant.TABLE_SERVICE_COMPLAINT, null, values);
        mDatabase.close();
        return insertFlag;
    }

    public static ArrayList<ReportUsRequestModel> getServiceComplaint(Context context, String query) {
        ArrayList<ReportUsRequestModel> reportUsRequestModels = new ArrayList<>();
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ReportUsRequestModel item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new ReportUsRequestModel();
                    item.setQrcodeNumber(cur.getString(cur.getColumnIndex("QRCodeNo")));
                    item.setServiceIssue(cur.getString(cur.getColumnIndex("ServiceIssue")));
                    item.setComment(cur.getString(cur.getColumnIndex("Comment")));
                    item.setComplaintId(cur.getInt(cur.getColumnIndex("ComplaintId")));
                    item.setScanStatus(cur.getString(cur.getColumnIndex("ScanStatus")));
                    item.setStatus(cur.getInt(cur.getColumnIndex("Status")));
                    item.setDateTime(cur.getLong(cur.getColumnIndex("DateTime")));
                    item.setCreatedBy(cur.getLong(cur.getColumnIndex("CreatedBy")));
                    reportUsRequestModels.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return reportUsRequestModels;

    }

    public static ArrayList<ContactEmailRequestModel> getContactData(Context context, String query) {
        ArrayList<ContactEmailRequestModel> contactEmailRequestModels = new ArrayList<>();
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContactEmailRequestModel item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new ContactEmailRequestModel();
                    item.setReceiverId(cur.getString(cur.getColumnIndex("ReceiverId")));
                    item.setSenderCountry(cur.getString(cur.getColumnIndex("SenderCountry")));
                    item.setPurpose(cur.getString(cur.getColumnIndex("Purpose")));
                    item.setCommType(cur.getString(cur.getColumnIndex("CommType")));
                    item.setSenderCompanyName(cur.getString(cur.getColumnIndex("SenderCompanyName")));
                    item.setStatus(cur.getInt(cur.getColumnIndex("Status")));
                    item.setSenderEmail(cur.getString(cur.getColumnIndex("SenderEmail")));
                    item.setSenderName(cur.getString(cur.getColumnIndex("SenderName")));
                    item.setMessage(cur.getString(cur.getColumnIndex("Message")));
                    item.setSenderType(cur.getInt(cur.getColumnIndex("SenderType")));
                    item.setReceiverType(cur.getInt(cur.getColumnIndex("ReceiverType")));
                    item.setSenderId(cur.getInt(cur.getColumnIndex("SenderId")));
                    item.setContactExhibitorId(cur.getInt(cur.getColumnIndex("ContactExhibitorId")));

                    contactEmailRequestModels.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return contactEmailRequestModels;

    }


    public static void saveService(Context context, AllServicesResponse.services item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        // values.put("Id", item.getId());
        values.put("ServiceId", item.getId());
        values.put("ServiceCode ", item.getServiceCode());
        values.put("ServiceName ", item.getServiceName());
        long insertFlag = mDatabase.insert(AppConstant.TABLE_SERVICES, null, values);
        mDatabase.close();
    }


    public static long saveFeedbackRequest(Context context, SubmitFeedbackRequest item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Comment", item.getComment());
        values.put("ServiceType ", item.getServiceType());
        values.put("TotalStar ", item.getTotalStar());
        values.put("WhatItemCode ", item.getWhatItemCode());
        values.put("WhoGiven ", item.getWhoGiven());
        values.put("WhoGivenName ", item.getWhoGivenName());
        values.put("Status ", item.getStatus());
        long insertFlag = mDatabase.insert(AppConstant.TABLE_SUBMIT_FEEDBACK, null, values);
        mDatabase.close();
        return insertFlag;
    }


    public static ArrayList<SubmitFeedbackRequest> getFeedbackData(Context context, String query) {
        ArrayList<SubmitFeedbackRequest> feedbackRequestArrayList = new ArrayList<>();
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        SubmitFeedbackRequest item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new SubmitFeedbackRequest();
                    item.setComment(cur.getString(cur.getColumnIndex("Comment")));
                    item.setWhoGivenName(cur.getString(cur.getColumnIndex("WhoGivenName")));
                    item.setWhoGiven(cur.getInt(cur.getColumnIndex("WhoGiven")));
                    item.setWhatItemCode(cur.getInt(cur.getColumnIndex("WhatItemCode")));
                    item.setTotalStar(cur.getInt(cur.getColumnIndex("TotalStar")));
                    item.setStatus(cur.getInt(cur.getColumnIndex("Status")));
                    item.setServiceType(cur.getInt(cur.getColumnIndex("ServiceType")));
                    item.setFeedbackId(cur.getInt(cur.getColumnIndex("FeedbackId")));
                    feedbackRequestArrayList.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return feedbackRequestArrayList;

    }

    public static ArrayList<AllServicesResponse.services> getServiceList(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        AllServicesResponse.services item = null;
        ArrayList<AllServicesResponse.services> list = new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new AllServicesResponse.services();
                    item.setId(cur.getInt(cur.getColumnIndex("ServiceId")));
                    item.setServiceCode(cur.getInt(cur.getColumnIndex("ServiceCode")));
                    item.setServiceName(cur.getString(cur.getColumnIndex("ServiceName")));
                    list.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return list;
    }

    public static void saveUpcomingEvents(Context context, EventModel item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("EventId", item.getId());
        values.put("EventDate ", item.getDateTime());
        values.put("EventName ", item.getEventName());
        values.put("EventType ", item.getEventType());
        values.put("Latitude ", item.getLatitude());
        values.put("Longitude ", item.getLongitude());
        values.put("EventVenue ", item.getVenue());
        long insertFlag = mDatabase.insert(AppConstant.TABLE_UPCOMING_EVENTS, null, values);
        Log.d("Events saved", insertFlag + "");
        mDatabase.close();

    }


    public static ArrayList<EventModel> getEventList(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        EventModel item = null;
        ArrayList<EventModel> list = new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new EventModel();
                    item.setId(cur.getInt(cur.getColumnIndex("EventId")));
                    item.setDateTime(cur.getLong(cur.getColumnIndex("EventDate")));
                    item.setEventName(cur.getString(cur.getColumnIndex("EventName")));
                    item.setEventType(cur.getString(cur.getColumnIndex("EventType")));
                    item.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                    item.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                    item.setVenue(cur.getString(cur.getColumnIndex("EventVenue")));
                    list.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return list;
    }

    public static AllServicesResponse.services updateService(Context context, AllServicesResponse.services item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("ServiceId", item.getId());
        values.put("ServiceCode ", item.getServiceCode());
        values.put("ServiceName ", item.getServiceName());

        int updateFlag = mDatabase.update(AppConstant.TABLE_SERVICES, values,
                "ServiceId='" + item.getId() + "'", null);

        String getQuery = "Select * from " + AppConstant.TABLE_SERVICES + " where ServiceId='" + item.getId() + "'";
        item = getServiceItemDetails(context, getQuery);
        mDatabase.close();
        return item;
    }

    public static EventModel updateUpcomingEvents(Context context, EventModel item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("EventId", item.getId());
        values.put("EventDate ", item.getDateTime());
        values.put("EventName ", item.getEventName());
        values.put("EventType ", item.getEventType());
        values.put("Latitude ", item.getLatitude());
        values.put("Longitude ", item.getLongitude());
        values.put("EventVenue ", item.getVenue());
        int updateFlag = mDatabase.update(AppConstant.TABLE_UPCOMING_EVENTS, values,
                "EventId='" + item.getId() + "'", null);

        String getQuery = "Select * from " + AppConstant.TABLE_UPCOMING_EVENTS + " where EventId='" + item.getId() + "'";
        item = getUpcomingEventsItemDetail(context, getQuery);
        mDatabase.close();
        return item;
    }

    public static EventModel getUpcomingEventsItemDetail(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        EventModel item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new EventModel();
                    item.setId(cur.getInt(cur.getColumnIndex("EventId")));
                    item.setDateTime(cur.getLong(cur.getColumnIndex("EventDate")));
                    item.setEventName(cur.getString(cur.getColumnIndex("EventName")));
                    item.setEventType(cur.getString(cur.getColumnIndex("EventType")));
                    item.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                    item.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                    item.setVenue(cur.getString(cur.getColumnIndex("EventVenue")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }

    public static AllServicesResponse.services getServiceItemDetails(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        AllServicesResponse.services item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new AllServicesResponse.services();
                    item.setId(cur.getInt(cur.getColumnIndex("ServiceId")));
                    item.setServiceCode(cur.getInt(cur.getColumnIndex("ServiceCode")));
                    item.setServiceName(cur.getString(cur.getColumnIndex("ServiceName")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }


    public static void saveMyWall(Context context, FeedbackResponse.feedbackList item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("MyWallId", item.getId());
        values.put("Comment ", item.getComment());
        values.put("CreatedOn ", item.getCreatedOn());
        values.put("ServiceType ", item.getServiceType());
        values.put("TotalStar ", item.getTotalStar());
        values.put("WhatItemCode ", item.getWhatItemCode());
        values.put("WhoGiven ", item.getWhoGiven());
        values.put("WhoGivenName ", item.getWhoGivenName());

        long insertFlag = mDatabase.insert(AppConstant.TABLE_MY_WALL, null, values);
        mDatabase.close();
    }

    public static FeedbackResponse.feedbackList updateMyWall(Context context, FeedbackResponse.feedbackList item) {
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("MyWallId", item.getId());
        values.put("Comment ", item.getComment());
        values.put("CreatedOn ", item.getCreatedOn());
        values.put("ServiceType ", item.getServiceType());
        values.put("TotalStar ", item.getTotalStar());
        values.put("WhatItemCode ", item.getWhatItemCode());
        values.put("WhoGiven ", item.getWhoGiven());
        values.put("WhoGivenName ", item.getWhoGivenName());

        int updateFlag = mDatabase.update(AppConstant.TABLE_MY_WALL, values,
                "MyWallId='" + item.getId() + "'", null);

        String getQuery = "Select * from " + AppConstant.TABLE_MY_WALL + " where MyWallId='" + item.getId() + "'";
        item = getMyWallItemDetails(context, getQuery);
        mDatabase.close();
        return item;
    }

    public static FeedbackResponse.feedbackList getMyWallItemDetails(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        FeedbackResponse.feedbackList item = null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new FeedbackResponse.feedbackList();
                    item.setId(cur.getInt(cur.getColumnIndex("MyWallId")));
                    item.setComment(cur.getString(cur.getColumnIndex("Comment")));
                    item.setCreatedOn(cur.getLong(cur.getColumnIndex("CreatedOn")));
                    item.setServiceType(cur.getInt(cur.getColumnIndex("ServiceType")));
                    item.setTotalStar(cur.getInt(cur.getColumnIndex("TotalStar")));
                    item.setWhatItemCode(cur.getInt(cur.getColumnIndex("WhatItemCode")));
                    item.setWhoGiven(cur.getInt(cur.getColumnIndex("WhoGiven")));
                    item.setWhoGivenName(cur.getString(cur.getColumnIndex("WhoGivenName")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }


    public static ArrayList<FeedbackResponse.feedbackList> getMyWallList(Context context, String query) {

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        FeedbackResponse.feedbackList item = null;
        ArrayList<FeedbackResponse.feedbackList> list = new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new FeedbackResponse.feedbackList();
                    item.setId(cur.getInt(cur.getColumnIndex("MyWallId")));
                    item.setComment(cur.getString(cur.getColumnIndex("Comment")));
                    item.setCreatedOn(cur.getLong(cur.getColumnIndex("CreatedOn")));
                    item.setServiceType(cur.getInt(cur.getColumnIndex("ServiceType")));
                    item.setTotalStar(cur.getInt(cur.getColumnIndex("TotalStar")));
                    item.setWhatItemCode(cur.getInt(cur.getColumnIndex("WhatItemCode")));
                    item.setWhoGiven(cur.getInt(cur.getColumnIndex("WhoGiven")));
                    item.setWhoGivenName(cur.getString(cur.getColumnIndex("WhoGivenName")));
                    list.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return list;
    }
}
