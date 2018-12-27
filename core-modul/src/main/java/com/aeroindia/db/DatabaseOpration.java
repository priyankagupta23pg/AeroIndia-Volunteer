package com.aeroindia.db;

import android.content.Context;

import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.pojos.request.ContactEmailRequestModel;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.request.ReportUsRequestModel;
import com.aeroindia.pojos.request.SubmitFeedbackRequest;
import com.aeroindia.pojos.response.AllServicesResponse;
import com.aeroindia.pojos.response.EventModel;
import com.aeroindia.pojos.response.FeedbackResponse;

import java.util.ArrayList;

/**
 * Created by PSQ on 4/24/2017.
 */

public class DatabaseOpration {


    public static ArrayList<ExhibitorsItem> getExhibitors(DatabaseHelpers dbHelper)
    {
        ArrayList<ExhibitorsItem> exhibitorsItems=new ArrayList<>();
        ExhibitorInfoDao exhibitorInfoDao=new ExhibitorInfoDaoImpl();
        exhibitorsItems=exhibitorInfoDao.getExhibitors(dbHelper);
        return exhibitorsItems;
    }
    public static void deleteAllExhibitors(DatabaseHelpers dbHelper)
    {
        ExhibitorInfoDao exhibitorInfoDao=new ExhibitorInfoDaoImpl();
        exhibitorInfoDao.deleteAllExhibitors(dbHelper);

    }
    public static long saveExhibitor(ExhibitorsItem exhibitorsItem,DatabaseHelpers dbHelper) {
        ExhibitorInfoDao exhibitorInfoDao=new ExhibitorInfoDaoImpl();
        return  exhibitorInfoDao.save(exhibitorsItem, dbHelper);
    }

    public static long updateExhibitor(ExhibitorsItem exhibitorsItem,String uniqueId,DatabaseHelpers dbHelper) {
        ExhibitorInfoDao exhibitorInfoDao=new ExhibitorInfoDaoImpl();
        return  exhibitorInfoDao.update(exhibitorsItem,uniqueId, dbHelper);
    }

    public static ExhibitorsItem getExhibitor(String uniqueId,DatabaseHelpers dbHelper) {
        ExhibitorInfoDao exhibitorInfoDao=new ExhibitorInfoDaoImpl();
        return  exhibitorInfoDao.getExhibitorItem(uniqueId, dbHelper);
    }

    public static ArrayList<AllServicesResponse.services> getServiceData(Context context) {
        String query = "Select * from " + AppConstant.TABLE_SERVICES;
        return CommonDatabaseAero.getServiceList(context, query);
    }

    public static ArrayList<EventModel> getEventData(Context context) {
        String query = "Select * from " + AppConstant.TABLE_UPCOMING_EVENTS;
        return CommonDatabaseAero.getEventList(context, query);
    }
    public static ArrayList<FeedbackResponse.feedbackList> getMyWallData(Context context) {
        String query = "Select * from " + AppConstant.TABLE_MY_WALL;
        return CommonDatabaseAero.getMyWallList(context, query);
    }

    public static ArrayList<SubmitFeedbackRequest> getFeedbackData(Context context) {
        String query = "Select * from " + AppConstant.TABLE_SUBMIT_FEEDBACK+" where Status='"+0+"'";
        return CommonDatabaseAero.getFeedbackData(context, query);
    }

    public static ArrayList<ContactEmailRequestModel> getContactData(Context context) {
        String query = "Select * from " + AppConstant.TABLE_CONTACT_EXHIBITOR_REQUEST+" where Status='"+0+"'";
        return CommonDatabaseAero.getContactData(context, query);
    }


    public static void deleteFeedbackData(Context context, long feedbackId){
        String query="DELETE FROM " + AppConstant.TABLE_SUBMIT_FEEDBACK+ " WHERE FeedbackId='"+feedbackId+"'";
        CommonDatabaseAero.delete(context, query);
    }

    public static void deleteContactData(Context context, long contactExhibitorId){
        String query="DELETE FROM " + AppConstant.TABLE_CONTACT_EXHIBITOR_REQUEST+ " WHERE ContactExhibitorId='"+contactExhibitorId+"'";
        CommonDatabaseAero.delete(context, query);
    }

    public static void deleteServiceComplaintData(Context context, long complaintId){
        String query="DELETE FROM " + AppConstant.TABLE_SERVICE_COMPLAINT+ " WHERE ComplaintId='"+complaintId+"'";
        CommonDatabaseAero.delete(context, query);
    }

    public static ArrayList<ReportUsRequestModel> getServiceComplaint(Context context) {
        String query = "Select * from " + AppConstant.TABLE_SERVICE_COMPLAINT+" where Status='"+0+"'";
        return CommonDatabaseAero.getServiceComplaint(context, query);
    }

}
