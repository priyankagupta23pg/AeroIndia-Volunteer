package com.aeroindia.pojos.response;

import java.io.Serializable;

/**
 * Created by SUNAINA on 17-08-2018.
 */

public class AnnouncementItemsModel extends GenericResponse implements Serializable {
    private String Desc="";
    private String FilePath="";
    private int drawable;
    private String Type="";
    private int imageCorner;
    private int id;


    public AnnouncementItemsModel(int id, String filePath, int imageCorner) {
        FilePath = filePath;
        this.imageCorner = imageCorner;
        this.id = id;
    }

    public AnnouncementItemsModel(int id, int drawable, int imageCorner) {
        drawable = drawable;
        this.imageCorner = imageCorner;
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getImageCorner() {
        return imageCorner;
    }

    public void setImageCorner(int imageCorner) {
        this.imageCorner = imageCorner;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }




}
