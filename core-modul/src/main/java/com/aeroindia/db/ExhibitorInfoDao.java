package com.aeroindia.db;


import com.aeroindia.pojos.request.ExhibitorsItem;

import java.util.ArrayList;

/**
 * Created by psqit on 6/13/2016.
 */
public interface ExhibitorInfoDao {
    long save(ExhibitorsItem item,
              DatabaseHelpers helper);
        ArrayList<ExhibitorsItem> getExhibitors(DatabaseHelpers helpers);
     void deleteAllExhibitors(DatabaseHelpers helpers);

     ExhibitorsItem getExhibitorItem(String uniqueId, DatabaseHelpers helpers);
     long update(ExhibitorsItem item,String uniqueId,DatabaseHelpers helper);

    }
