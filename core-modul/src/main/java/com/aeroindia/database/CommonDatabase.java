package com.aeroindia.database;

/**
 * Created by PSQ on 4/24/2017.
 */

public class CommonDatabase {
    private static String TAG="Common Database Log";

/*    public static void delete(Context context, String query){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        mDatabase.execSQL(query);
        mDatabase.close();
        helpers.close();
    }

    public static ArrayList<StateItem> getStateList(Context context, String query){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        StateItem item=null;
        ArrayList<StateItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new StateItem();
                    item.setStateCode(cur.getString(cur.getColumnIndex("STOCode")));
                    item.setStateName(cur.getString(cur.getColumnIndex("STOStateName")));
                    list.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return list;

    }*/

    /*public static LocationItem getLocationDetails(Context context, String query){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        LocationItem item=null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new LocationItem();
                    item.setStateCode(cur.getString(cur.getColumnIndex("StateCode")));
                    item.setStateName(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(cur.getString(cur.getColumnIndex("DistrictCode")));
                    item.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(cur.getString(cur.getColumnIndex("SubDistrictCode")));
                    item.setSubDistrictName(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(cur.getString(cur.getColumnIndex("VillageCensusCode")));
                    item.setVillageName(cur.getString(cur.getColumnIndex("VillageName")));

                    item.setPostOfficeCode(cur.getString(cur.getColumnIndex("PostOfficeCode")));
                    item.setPostOfficeName(cur.getString(cur.getColumnIndex("PostOfficeName")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }
    public  static void addHabitation(Context context, HabitationItem item){
        Log.d("TAG","HabitationItem" +item.serialize());
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("Name", item.getName());
        values.put("VillageCensusCode ", item.getVillageCensusCode());
        values.put("VillageName",item.getVillageCensusCodeText());
        values.put("IsElectrified",item.getIsElectrified());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateCodeText());
        values.put("DistrictCode", item.getDistrictCode());
        values.put("DistrictName",item.getDistrictCodeText());
        values.put("SubDistrictCode", item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictCodeText());
        values.put("ApproxDistanceOfNearestPole",item.getApproxDistanceOfNearestPole());
        values.put("ApproxDistanceOfNearestDT", item.getApproxDistanceOfNearestDT());

        values.put("SyncStatus",item.getSyncStatus());
        values.put("NoOfElectrifiedHhs",item.getCapacityOfNearestDT());
        long insertFlag= mDatabase.insert(AppConstant.TABLE_HABITATION, null, values);
        mDatabase.close();
    }

    public static HabitationItem getHabitationDetail(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HabitationItem item=null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new HabitationItem();
                    item.setId(cur.getLong(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setName(cur.getString(cur.getColumnIndex("Name")));
                    item.setVillageCensusCode(Long.parseLong(cur.getString(cur.getColumnIndex("VillageCensusCode"))));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setIsElectrified(cur.getInt(cur.getColumnIndex("IsElectrified")));
                    item.setStateCode(Integer.parseInt(cur.getString(cur.getColumnIndex("StateCode"))));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("DistrictCode"))));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("SubDistrictCode"))));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setApproxDistanceOfNearestPole(Double.parseDouble(cur.
                            getString(cur.getColumnIndex("ApproxDistanceOfNearestPole"))));
                    item.setApproxDistanceOfNearestDT(Double.parseDouble(cur.getString(cur
                            .getColumnIndex("ApproxDistanceOfNearestDT"))));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));
                    item.setCapacityOfNearestDT(cur.getInt(cur.getColumnIndex("NoOfElectrifiedHhs")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }
    public static ArrayList<HabitationHouseholdSummary> getHabitationHouseholdSummaryList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HabitationHouseholdSummary item=null;
        ArrayList<HabitationHouseholdSummary> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new HabitationHouseholdSummary();
                    item.setId(cur.getLong(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setName(cur.getString(cur.getColumnIndex("Name")));
                    item.setVillageCensusCode(cur.getInt(cur.getColumnIndex("VillageCensusCode")));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setIsElectrified(cur.getInt(cur.getColumnIndex("IsElectrified")));
                    item.setStateCode(Integer.parseInt(cur.getString(cur.getColumnIndex("StateCode"))));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("DistrictCode"))));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("SubDistrictCode"))));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setApproxDistanceOfNearestPole(Double.parseDouble(cur.
                            getString(cur.getColumnIndex("ApproxDistanceOfNearestPole"))));
                    item.setApproxDistanceOfNearestDT(Double.parseDouble(cur.getString(cur
                            .getColumnIndex("ApproxDistanceOfNearestDT"))));
                    item.setNoOfElectrifiedHhs(cur.getInt(cur.getColumnIndex("NoOfElectrifiedHhs")));

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

    public static ArrayList<HabitationItem> getHabitationList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HabitationItem item=null;
        ArrayList<HabitationItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new HabitationItem();
                    item.setId(cur.getLong(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setName(cur.getString(cur.getColumnIndex("Name")));
                    item.setVillageCensusCode(Long.parseLong(cur.getString(cur.getColumnIndex("VillageCensusCode"))));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setIsElectrified(cur.getInt(cur.getColumnIndex("IsElectrified")));
                    item.setStateCode(Integer.parseInt(cur.getString(cur.getColumnIndex("StateCode"))));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("DistrictCode"))));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("SubDistrictCode"))));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setApproxDistanceOfNearestPole(Double.parseDouble(cur.
                            getString(cur.getColumnIndex("ApproxDistanceOfNearestPole"))));
                    item.setApproxDistanceOfNearestDT(Double.parseDouble(cur.getString(cur
                            .getColumnIndex("ApproxDistanceOfNearestDT"))));
                    item.setRemoteId(cur.getLong(cur.getColumnIndex("RemoteId")));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));
                    item.setCapacityOfNearestDT(cur.getInt(cur.getColumnIndex("NoOfElectrifiedHhs")));
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
    public static HabitationItem updateHabitate(Context context, HabitationItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("Name", item.getName());
        values.put("VillageCensusCode ", item.getVillageCensusCode());
        values.put("VillageName",item.getVillageCensusCodeText());
        values.put("IsElectrified",item.getIsElectrified());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateCodeText());
        values.put("DistrictCode", item.getDistrictCode());
        values.put("DistrictName",item.getDistrictCodeText());
        values.put("SubDistrictCode", item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictCodeText());
        values.put("ApproxDistanceOfNearestPole",item.getApproxDistanceOfNearestPole());
        values.put("ApproxDistanceOfNearestDT", item.getApproxDistanceOfNearestDT());
        values.put("SyncStatus",item.getSyncStatus());
        values.put("NoOfElectrifiedHhs",item.getCapacityOfNearestDT());
        //  values.put("RemoteId", item.getRemoteId());
        int updateFlag = mDatabase.update(AppConstant.TABLE_HABITATION, values,
                "LocalId='" + item.getLocalId() + "'", null);

        String getQuery="Select * from "+AppConstant.TABLE_HABITATION+" where LocalId='"+updateFlag+"'";
        item=getHabitationDetail(context,getQuery);
        mDatabase.close();
        return item;
    }

    public  static void addSarpanch(Context context, SarpanchItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("StateCode", item.getStateCode());
        values.put("StateName ", item.getStateName());
        values.put("DistrictCode",item.getDistrictCode());
        values.put("DistrictName",item.getDistrictName());
        values.put("SubDistrictCode", item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictName());
        values.put("VillageCensusCode", item.getVillageCensusCode());
        values.put("VillageName",item.getVillageName());
        values.put("Mobile", item.getMobile());
        values.put("PostOfficeCode",item.getPostOfficeCode());
        values.put("PostOfficeName",item.getPostOfficeName());
        values.put("FullName",item.getFullName());
        values.put("SyncStatus",item.getSyncStatus());
        //  values.put("RemoteId", item.getRemoteId());
        long insertFlag= mDatabase.insert(AppConstant.TABLE_SARPANCH, null, values);
        mDatabase.close();
    }

    public static SarpanchItem getSarpanchDetails(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        SarpanchItem item=null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new SarpanchItem();
                    item.setId(cur.getInt(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setStateCode(Integer.parseInt(cur.getString(cur.getColumnIndex("StateCode"))));
                    item.setStateName(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("DistrictCode"))));
                    item.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("SubDistrictCode"))));
                    item.setSubDistrictName(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(Integer.parseInt(cur.getString(cur.getColumnIndex("VillageCensusCode"))));
                    item.setVillageName(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setMobile(Long.parseLong(cur.getString(cur.getColumnIndex("Mobile"))));
                    item.setPostOfficeCode(cur.getLong(cur.getColumnIndex("PostOfficeCode")));
                    item.setPostOfficeName(cur.getString(cur.getColumnIndex("PostOfficeName")));
                    item.setFullName(cur.getString(cur.getColumnIndex("FullName")));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));


                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }

    public static ArrayList<SarpanchItem> getSarpanchList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        SarpanchItem item=null;
        ArrayList<SarpanchItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new SarpanchItem();
                    item.setId(cur.getInt(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setStateCode(Integer.parseInt(cur.getString(cur.getColumnIndex("StateCode"))));
                    item.setStateName(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("DistrictCode"))));
                    item.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(Integer.parseInt(cur.getString(cur.getColumnIndex("SubDistrictCode"))));
                    item.setSubDistrictName(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(Integer.parseInt(cur.getString(cur.getColumnIndex("VillageCensusCode"))));
                    item.setVillageName(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setMobile(Long.parseLong(cur.getString(cur.getColumnIndex("Mobile"))));
                    item.setPostOfficeCode(cur.getLong(cur.getColumnIndex("PostOfficeCode")));
                    item.setPostOfficeName(cur.getString(cur.getColumnIndex("PostOfficeName")));
                    item.setFullName(cur.getString(cur.getColumnIndex("FullName")));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));

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

    public static SarpanchItem updateSarpanch(Context context, SarpanchItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateName());
        values.put("DistrictCode",item.getDistrictCode());
        values.put("DistrictName", item.getDistrictName());
        values.put("SubDistrictCode",item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictName());
        values.put("VillageCensusCode",item.getVillageCensusCode());
        values.put("VillageName", item.getVillageName());
        values.put("Mobile",item.getMobile());
        values.put("PostOfficeCode",item.getPostOfficeCode());
        values.put("PostOfficeName",item.getPostOfficeName());
        values.put("FullName",item.getFullName());
        values.put("SyncStatus",item.getSyncStatus());

        //  values.put("RemoteId",item.getRemoteId());
        int updateFlag = mDatabase.update(AppConstant.TABLE_SARPANCH, values,
                "LocalId='" + item.getLocalId() + "'", null);

        String getQuery="Select * from "+AppConstant.TABLE_SARPANCH+" where LocalId='"+updateFlag+"'";
        item=getSarpanchDetails(context,getQuery);
        mDatabase.close();
        return item;
    }

    public  static void addHousehold(Context context, HouseholdItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateCodeText());
        values.put("DistrictCode",item.getDistrictCode());
        values.put("DistrictName",item.getDistrictCodeText());
        values.put("SubDistrictCode", item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictCodeText());
        values.put("VillageCensusCode", item.getVillageCensusCode());
        values.put("VillageName",item.getVillageCensusCodeText());
        values.put("HabitationId", item.getHabitationId());
        values.put("HabitationLocalId", item.getHabitationLocalId());
        values.put("HabitationName", item.getHabitationName());
        values.put("ConnectionStatus",item.getConnectionStatus());
        values.put("PovertyStatus", item.getPovertyStatus());
        values.put("HeadOfFamily", item.getHeadOfFamily());
        values.put("BPLCardNumber", item.getBPLCardNumber());
        values.put("Mobile", item.getMobile());
        values.put("Aadhar", item.getAadhar());
        values.put("Address", item.getAddress());
        values.put("Remark", item.getRemark());
        values.put(" SyncStatus", item.getSyncStatus());
        long insertFlag= mDatabase.insert(AppConstant.TABLE_HOUSEHOLD, null, values);
        mDatabase.close();
    }
    public static HouseholdItem updateHousehold(Context context, HouseholdItem item){
        Log.d(TAG,"Household : "+item.serialize());
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", item.getId());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateCodeText());
        values.put("DistrictCode",item.getDistrictCode());
        values.put("DistrictName",item.getDistrictCodeText());
        values.put("SubDistrictCode", item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictCodeText());
        values.put("VillageCensusCode", item.getVillageCensusCode());
        values.put("VillageName",item.getVillageCensusCodeText());
        values.put("HabitationId", item.getHabitationId());
        values.put("HabitationLocalId", item.getHabitationLocalId());
        values.put("HabitationName", item.getHabitationName());
        values.put("ConnectionStatus",item.getConnectionStatus());
        values.put("PovertyStatus", item.getPovertyStatus());
        values.put("HeadOfFamily", item.getHeadOfFamily());
        values.put("BPLCardNumber", item.getBPLCardNumber());
        values.put("Mobile", item.getMobile());
        values.put("Aadhar", item.getAadhar());
        values.put("Address", item.getAddress());
        values.put("Remark", item.getRemark());
        values.put("SyncStatus", item.getSyncStatus());



        Log.d("Common Database","Local Id : "+item.getLocalId());

        int updateFlag = mDatabase.update(AppConstant.TABLE_HOUSEHOLD, values, "LocalId='"+item.getLocalId()+"'", null);

        String getQuery="Select * from "+AppConstant.TABLE_HOUSEHOLD+" where LocalId='"+updateFlag+"'";
        item=getHouseholdDetails(context,getQuery);
        mDatabase.close();
        return item;
    }


    public static HouseholdItem getHouseholdDetails(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HouseholdItem item=null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new HouseholdItem();
                    item.setId(cur.getInt(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setStateCode(cur.getInt(cur.getColumnIndex("StateCode")));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(cur.getInt(cur.getColumnIndex("DistrictCode")));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(cur.getInt(cur.getColumnIndex("SubDistrictCode")));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(cur.getInt(cur.getColumnIndex("VillageCensusCode")));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setHabitationId(cur.getLong(cur.getColumnIndex("HabitationId")));
                    item.setHabitationLocalId(cur.getLong(cur.getColumnIndex("HabitationLocalId")));
                    item.setHabitationName(cur.getString(cur.getColumnIndex("HabitationName")));
                    item.setConnectionStatus(cur.getString(cur.getColumnIndex("ConnectionStatus")));
                    item.setPovertyStatus(cur.getString(cur.getColumnIndex("PovertyStatus")));
                    item.setHeadOfFamily(cur.getString(cur.getColumnIndex("HeadOfFamily")));
                    item.setBPLCardNumber(cur.getString(cur.getColumnIndex("BPLCardNumber")));
                    item.setMobile(cur.getLong(cur.getColumnIndex("Mobile")));
                    item.setAadhar(cur.getLong(cur.getColumnIndex("Aadhar")));
                    item.setAddress(cur.getString(cur.getColumnIndex("Address")));
                    item.setRemark(cur.getString(cur.getColumnIndex("Remark")));
                    item.setRemoteId(cur.getInt(cur.getColumnIndex("RemoteId")));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));

                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }


    public static ArrayList<HouseholdItem> getHouseholdHabitationSummaryList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HouseholdItem item=null;
        ArrayList<HouseholdItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item= new HouseholdItem();
                    item.setId(cur.getInt(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setStateCode(cur.getInt(cur.getColumnIndex("StateCode")));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(cur.getInt(cur.getColumnIndex("DistrictCode")));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(cur.getInt(cur.getColumnIndex("SubDistrictCode")));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(cur.getInt(cur.getColumnIndex("VillageCensusCode")));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setHabitationId(cur.getLong(cur.getColumnIndex("HabitationId")));
                    item.setHabitationLocalId(cur.getLong(cur.getColumnIndex("HabitationLocalId")));
                    item.setHabitationName(cur.getString(cur.getColumnIndex("HabitationName")));
                    item.setConnectionStatus(cur.getString(cur.getColumnIndex("ConnectionStatus")));
                    item.setPovertyStatus(cur.getString(cur.getColumnIndex("PovertyStatus")));
                    item.setHeadOfFamily(cur.getString(cur.getColumnIndex("HeadOfFamily")));
                    item.setBPLCardNumber(cur.getString(cur.getColumnIndex("BPLCardNumber")));
                    item.setMobile(cur.getLong(cur.getColumnIndex("Mobile")));
                    item.setAadhar(cur.getLong(cur.getColumnIndex("Aadhar")));
                    item.setAddress(cur.getString(cur.getColumnIndex("Address")));
                    item.setRemark(cur.getString(cur.getColumnIndex("Remark")));
                    item.setRemoteId(cur.getInt(cur.getColumnIndex("RemoteId")));
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

    public static ArrayList<HouseholdItem> getHouseholdList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HouseholdItem item=null;
        ArrayList<HouseholdItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item= new HouseholdItem();
                    item.setId(cur.getInt(cur.getColumnIndex("Id")));
                    item.setLocalId(cur.getLong(cur.getColumnIndex("LocalId")));
                    item.setStateCode(cur.getInt(cur.getColumnIndex("StateCode")));
                    item.setStateCodeText(cur.getString(cur.getColumnIndex("StateName")));
                    item.setDistrictCode(cur.getInt(cur.getColumnIndex("DistrictCode")));
                    item.setDistrictCodeText(cur.getString(cur.getColumnIndex("DistrictName")));
                    item.setSubDistrictCode(cur.getInt(cur.getColumnIndex("SubDistrictCode")));
                    item.setSubDistrictCodeText(cur.getString(cur.getColumnIndex("SubDistrictName")));
                    item.setVillageCensusCode(cur.getInt(cur.getColumnIndex("VillageCensusCode")));
                    item.setVillageCensusCodeText(cur.getString(cur.getColumnIndex("VillageName")));
                    item.setHabitationId(cur.getLong(cur.getColumnIndex("HabitationId")));
                    item.setHabitationLocalId(cur.getLong(cur.getColumnIndex("HabitationLocalId")));
                    item.setHabitationName(cur.getString(cur.getColumnIndex("HabitationName")));
                    item.setConnectionStatus(cur.getString(cur.getColumnIndex("ConnectionStatus")));
                    item.setPovertyStatus(cur.getString(cur.getColumnIndex("PovertyStatus")));
                    item.setHeadOfFamily(cur.getString(cur.getColumnIndex("HeadOfFamily")));
                    item.setBPLCardNumber(cur.getString(cur.getColumnIndex("BPLCardNumber")));
                    item.setMobile(cur.getLong(cur.getColumnIndex("Mobile")));
                    item.setAadhar(cur.getLong(cur.getColumnIndex("Aadhar")));
                    item.setAddress(cur.getString(cur.getColumnIndex("Address")));
                    item.setRemark(cur.getString(cur.getColumnIndex("Remark")));
                    item.setRemoteId(cur.getInt(cur.getColumnIndex("RemoteId")));
                    item.setSyncStatus(cur.getInt(cur.getColumnIndex("SyncStatus")));
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

    public static long getHouseholdCount(Context context, String query){
        Log.d(TAG,"Household Count query : "+query);
        long count;
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        HouseholdItem item=null;
        ArrayList<HouseholdItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        count=cur.getCount();
        Log.d(TAG,"Count : "+count);
        if (cur != null) {
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return count;
    }

   *//* public static SarpanchItem updateSarpanch(Context context,SarpanchItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        //  values.put("Id", item.getId());
        values.put("StateCode", item.getStateCode());
        values.put("StateName", item.getStateName());
        values.put("DistrictCode",item.getDistrictCode());
        values.put("DistrictName", item.getDistrictName());
        values.put("SubDistrictCode",item.getSubDistrictCode());
        values.put("SubDistrictName", item.getSubDistrictName());
        values.put("VillageCensusCode",item.getVillageCensusCode());
        values.put("VillageName", item.getVillageName());
        values.put("Mobile",item.getMobile());
        values.put("FullName",item.getFullName());
        values.put("RemoteId",item.getRemoteId());
        int updateFlag = mDatabase.update(AppConstant.TABLE_SARPANCH, values,
                "id='" + item.getId() + "'", null);

        String getQuery="Select * from "+AppConstant.TABLE_SARPANCH+" where id='"+updateFlag+"'";
        item=getSarpanchDetails(context,getQuery);
        mDatabase.close();
        return item;
    }*//*


    public  static void saveVillages(Context context, VillageItem item){
        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        ContentValues values = new ContentValues();
        // values.put("Id", item.getId());
        values.put("villageName", item.getText());
        values.put("villageCode ", item.getValue());
        long insertFlag= mDatabase.insert(AppConstant.TABLE_VILLAGE, null, values);
        mDatabase.close();
    }


    public static VillageItem getVillageDetails(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        VillageItem item=null;
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item = new VillageItem();
                    item.setText(cur.getString(cur.getColumnIndex("villageName")));
                    item.setValue(cur.getString(cur.getColumnIndex("villageCode")));
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return item;

    }

    public static ArrayList<VillageItem> getVillageList(Context context, String query){

        DatabaseHelpers helpers = DatabaseHelpers.getInstance(context);
        SQLiteDatabase mDatabase = helpers.createOrOpenDatabase();
        VillageItem item=null;
        ArrayList<VillageItem> list=new ArrayList<>();
        Cursor cur = mDatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    item= new VillageItem();
                    item.setText(cur.getString(cur.getColumnIndex("villageName")));
                    item.setValue(cur.getString(cur.getColumnIndex("villageCode")));
                    list.add(item);
                    cur.moveToNext();
                }
            }
            cur.close();
        }
        mDatabase.close();
        helpers.close();
        return list;
    }*/



}
