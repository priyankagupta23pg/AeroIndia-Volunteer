package com.aeroindia.custom.utility;

/**
 * Created by PSQ on 11/4/2017.
 */

public interface AppConstant {
    public final static int REQ_CAMERA = 1003;
    public final static int BACK_REQ_CAMERA = 1004;
    public static final int FRONT_CAMREA_OPEN = 1;
    public static final int BACK_CAMREA_OPEN = 2;

    String EDIT="EDIT",ADD="ADD",VIEW="VIEW",DELETE_FLAG="D";
    String DATE_FORMAT = "dd-MMM-yyyy h:mm a";
    String MSG_DATE_FORMAT = "dd-MMM-yyyy";

    String DATABASE="AeroIndiaDb.sqlite";
    String TABLE_EXHIBITOR="ExhibitorData";
    int EMAIL=1;

    String TEST_DOMAIN="http://103.241.181.83:8080/aero";
    String PROD_DOMAIN="";
    String MAIN_DOMAIN=TEST_DOMAIN;
    String LOGIN_API=MAIN_DOMAIN+"/login/user";
    String REGISTER_API=MAIN_DOMAIN+"/login/registerUser";
    String OTPVERIFY_API=MAIN_DOMAIN+"/login/otpAuthentication";
    String EXHIBITOR_API=MAIN_DOMAIN+"/exhibitor/getall";
    String VISITOR_API=MAIN_DOMAIN+"/businessvisitor/getall";
    String CONTACT_EMAIL=MAIN_DOMAIN+"/exhibitor/sendmessage";
    String ACTIVITIES_API=MAIN_DOMAIN+"/activity/myActivities?senderId=";
    String ACTIVITIES1_API="&recieverId=";
    String GETFEEDBACK_API=MAIN_DOMAIN+"/service/getFeedback";
    String SUBMITFEEDBACK_API=MAIN_DOMAIN+"/service/submitFeedback";
    String ALLSERVICES_API=MAIN_DOMAIN+"/service/allServices";
    String SUBSERVICES_API=MAIN_DOMAIN+"/service/allSubServices";
    String SUBMIT_REPORT=MAIN_DOMAIN+"/service/submitReport";
    String GET_QR_CODE=MAIN_DOMAIN+"/service/getQrCode";
    String UPCOMING_EVENTS_API=MAIN_DOMAIN+"/service/getAllEvents";
    String DASHBOARDCOUNT_API=MAIN_DOMAIN+"/service/getDashboardCount";
    String GET_QR_CODE_PROVIDER_VOLUNTEER_API=MAIN_DOMAIN+"/service/getQrCodeDetailByServiceProviderOrVolunteer";
    String UPDATEREPORT_VOLUNTEER=MAIN_DOMAIN+"/service/updateReportByVolunteer";
    String UPDATEREPORT_PROVIDER=MAIN_DOMAIN+"/service/updateReportByServiceProvider";
    String COMPLAINTS_API=MAIN_DOMAIN+"/service/serviceComplaintList";


    String PROJECT_PREF="AEROINDIA_VOLUNTEER";
    String LOGIN_DETAILS="login_detail";
    String MSGRCVD_DETAILS="RcvdMsg_detail";

    String SCREENNAME="screenname";
    String B2BSCREEN="b2bMeetings";
    String LOCALTIME="show_message_time";

    String CREATE_PROFILE=MAIN_DOMAIN+"/login/createProfile";
    String GET_PROFILE=MAIN_DOMAIN+"/login/getProfile";
    String UPDATE_PROFILE=MAIN_DOMAIN+"/login/updateProfile";

    //public static final String GENERAL_USER="General_Visitor";
    //public static final String BUSINESS_VISITOR="Business_Visitor";
    public static final String EXHIBITOR="Exhibitor";
    public static final String OTHER="Other_Visitor";
    public static final String PROVIDER="Service_Provider";
    public static final String VOLUNTEER="Volunteer";


    String TABLE_SERVICES="ServicesData";
    String TABLE_UPCOMING_EVENTS="UpcomingEventsData";
    String TABLE_MY_WALL="MyWallData";
    String TABLE_SUBMIT_FEEDBACK="SubmitFeedback";
    String TABLE_CONTACT_EXHIBITOR_REQUEST="ContactExhibitorRequestData";
    String TABLE_SERVICE_COMPLAINT="SubmitServiceComplaint";
}
