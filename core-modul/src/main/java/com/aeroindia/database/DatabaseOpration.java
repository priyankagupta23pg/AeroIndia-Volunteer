package com.aeroindia.database;

/**
 * Created by PSQ on 4/24/2017.
 */

public class DatabaseOpration {


  /*  public static LocationItem getLocationItem(Context context) {
        String query = "Select * from "
                + AppConstant.TABLE_LOCATION;
        return CommonDatabase.getLocationDetails(context, query);
    }*/

   /* public static ArrayList<StateItem> getStateList(Context context) {
        String query = "Select * from "
                + AppConstant.STATE_TABLE;
        return CommonDatabase.getStateList(context, query);
    }
*/
  /*  public static HabitationItem getHabitationItem(Context context, long localId) {
        String query = "Select * from "
                + AppConstant.TABLE_HABITATION + " where LocalId='" + localId + "'";
        Log.d("Database Operation", "Query : " + query);
        return CommonDatabase.getHabitationDetail(context, query);
    }

    public static SarpanchItem getSarpanchItem(Context context, long localId) {
        String query = "Select * from "
                + AppConstant.TABLE_SARPANCH + " where LocalId='" + localId + "'";
        Log.d("Database Operation", "Query : " + query);
        return CommonDatabase.getSarpanchDetails(context, query);
    }

    public static HabitationItem getHabitationByRemoteId(Context context, long remoteId) {
        String query = "Select * from "
                + AppConstant.TABLE_HABITATION + " where Id='" + remoteId + "'";
        return CommonDatabase.getHabitationDetail(context, query);
    }

    public static SarpanchItem getSarpanchByRemoteId(Context context, long remoteId) {
        String query = "Select * from "
                + AppConstant.TABLE_SARPANCH + " where id='" + remoteId + "'";
        return CommonDatabase.getSarpanchDetails(context, query);
    }

    public static SarpanchItem getSarpanchByPostOfficeCode(Context context, Long postOfficeCode) {
        String query = "Select * from "
                + AppConstant.TABLE_SARPANCH + " where PostOfficeCode='" + postOfficeCode + "'";
        return CommonDatabase.getSarpanchDetails(context, query);
    }

    public static SarpanchItem getSarpanchByLocation(Context context, int stateCode, int distCode, int blockCode, Long villageCode) {

        String query = "Select * from "
                + AppConstant.TABLE_SARPANCH + " where StateCode='" + stateCode +
                "' and DistrictCode='"+distCode+"' and SubDistrictCode='"+blockCode+"' and VillageCensusCode='"+villageCode+"'";
        return CommonDatabase.getSarpanchDetails(context, query);
    }

    public static HabitationItem getHabitationByLocation(Context context, int stateCode, int distCode, int blockCode, Long villageCode) {

        String query = "Select * from "
                + AppConstant.TABLE_HABITATION + " where StateCode='" + stateCode +
                "' and DistrictCode='"+distCode+"' and SubDistrictCode='"+blockCode+"' and VillageCensusCode='"+villageCode+"'";
        return CommonDatabase.getHabitationDetail(context, query);
    }


    public static HouseholdItem getHouseholdByRemoteId(Context context, long remoteId) {
        String query = "Select * from "
                + AppConstant.TABLE_HOUSEHOLD + " where id='" + remoteId + "'";
        return CommonDatabase.getHouseholdDetails(context, query);
    }

    public static ArrayList<HabitationItem> getHabitationList(Context context) {
        String query = "Select * from " + AppConstant.TABLE_HABITATION;
        return CommonDatabase.getHabitationList(context, query);
    }

    public static ArrayList<HabitationItem> getHabitationListByVillage(Context context, String villageCode) {
        String query = "Select * from " + AppConstant.TABLE_HABITATION + " where VillageCensusCode='" + villageCode + "'";
        return CommonDatabase.getHabitationList(context, query);
    }

    public static void addHabitation(Context context, HabitationItem item) {
        CommonDatabase.addHabitation(context, item);
    }

    public static ArrayList<SarpanchItem> getSarpanchList(Context context) {
        String query = "Select * from " + AppConstant.TABLE_SARPANCH;
        return CommonDatabase.getSarpanchList(context, query);
    }

    public static void addSarpanch(Context context, SarpanchItem item) {
        CommonDatabase.addSarpanch(context, item);
    }

    public static void updateSarpanch(Context context, SarpanchItem item) {
        CommonDatabase.updateSarpanch(context, item);
    }

    public static void updateHabitaion(Context context, HabitationItem item) {
        CommonDatabase.updateHabitate(context, item);
    }

    public static void addHousehold(Context context, HouseholdItem item) {
        CommonDatabase.addHousehold(context, item);
    }

    public static HouseholdItem updateHousehold(Context context, HouseholdItem item) {
        return CommonDatabase.updateHousehold(context, item);
    }

    public static ArrayList<HouseholdItem> getHouseholdList(Context context) {
        String query = "Select * from " + AppConstant.TABLE_HOUSEHOLD;
        return CommonDatabase.getHouseholdList(context, query);
    }

    public static ArrayList<HouseholdItem> getNonSyncHouseholdList(Context context, long syncStatus) {
        String query = "Select * from " + AppConstant.TABLE_HOUSEHOLD + " where SyncStatus='" + syncStatus + "'";
        return CommonDatabase.getHouseholdList(context, query);
    }


    public static ArrayList<HouseholdItem> getHouseholdListByHabitateId(Context context, long habitatId) {
        String query = "Select * from " + AppConstant.TABLE_HOUSEHOLD + " WHERE HabitationLocalId='" + habitatId + "'";
        return CommonDatabase.getHouseholdList(context, query);
    }

    public static HouseholdItem getHouseholdByHabitateId(Context context, long habitatId) {
        String query = "Select * from " + AppConstant.TABLE_HOUSEHOLD + " WHERE HabitationLocalId='" + habitatId + "'";
        return CommonDatabase.getHouseholdDetails(context, query);
    }

    public static ArrayList<HabitationHouseholdSummary> getHouseholdHabitationSummary(Context context) {
        String query = "Select * from " + AppConstant.TABLE_HABITATION + " where LocalId in (select HabitationLocalId from " + AppConstant.TABLE_HOUSEHOLD + ")";
        return CommonDatabase.getHabitationHouseholdSummaryList(context, query);
    }

    public static long habitationHouseholdCount(Context context, String condition, long habitationId) {
        String query = "SELECT Id FROM " + AppConstant.TABLE_HOUSEHOLD + " WHERE ConnectionStatus='" + condition + "' AND HabitationLocalId='" + habitationId + "'";
        return CommonDatabase.getHouseholdCount(context, query);
    }

    public static ArrayList<VillageItem> getVillageList(Context context) {
        String query = "Select * from " + AppConstant.TABLE_VILLAGE;
        return CommonDatabase.getVillageList(context, query);
    }

    public static VillageItem getVillageDetail(Context context, long villageCode) {
        String query = "Select * from " + AppConstant.TABLE_VILLAGE + " where villageCode='" + villageCode + "'";
        return CommonDatabase.getVillageDetails(context, query);
    }

    public static void addVillage(Context context, VillageItem item) {
        CommonDatabase.saveVillages(context, item);
    }

    public static void deleteHousehold(Context context, long localId){
        String query="DELETE FROM " + AppConstant.TABLE_HOUSEHOLD+ " WHERE LocalId='"+localId+"'";
        CommonDatabase.delete(context, query);
    }

    public static void deleteHabitation(Context context, long habitationLocalId){
        String habitationDelete="DELETE FROM " + AppConstant.TABLE_HABITATION+ " WHERE LocalId='"+habitationLocalId+"'";
        CommonDatabase.delete(context,habitationDelete);
      *//*  String householdDelete="DELETE FROM " + AppConstant.TABLE_HOUSEHOLD+ " WHERE HabitationLocalId='"+habitationLocalId+"'";
        CommonDatabase.delete(context,householdDelete);

        if(CommonDatabase.getHouseholdCount(context,"Select * from " + AppConstant.TABLE_HOUSEHOLD + " WHERE HabitationLocalId='" + habitationLocalId + "'")==0){

        }*//*
    }

    public static void deleteSarpanch(Context context, long localId){
        String query="DELETE FROM " + AppConstant.TABLE_SARPANCH+ " WHERE LocalId='"+localId+"'";
        CommonDatabase.delete(context, query);
    }
    public static void deleteSarpanch(Context context){
        String query="DELETE FROM " + AppConstant.TABLE_SARPANCH;
        CommonDatabase.delete(context, query);
    }
*/

}
