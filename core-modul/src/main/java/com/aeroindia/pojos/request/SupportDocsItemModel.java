package com.aeroindia.pojos.request;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Dell3 on 11-09-2017.
 */

public class SupportDocsItemModel implements Serializable {
    private String Base64Data;
   // private Bitmap bitmap;
    private String Extension;
    private int Length ;
    private String Name;
    private String DocFile;
    private String DocID;
    private String DocPath;
    private String Flag="";
    private int SeqNo ;
    private Uri docPathUri;
    private String screen="";

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public Uri getDocPathUri() {
        return docPathUri;
    }

    public void setDocPathUri(Uri docPathUri) {
        this.docPathUri = docPathUri;
    }

   /* public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }*/

    public String getBase64Data() {
        return Base64Data;
    }

    public void setBase64Data(String base64Data) {
        Base64Data = base64Data;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDocFile() {
        return DocFile;
    }

    public void setDocFile(String docFile) {
        DocFile = docFile;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getDocPath() {
        return DocPath;
    }

    public void setDocPath(String docPath) {
        DocPath = docPath;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public int getSeqNo() {
        return SeqNo;
    }

    public void setSeqNo(int seqNo) {
        SeqNo = seqNo;
    }

    static public SupportDocsItemModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, SupportDocsItemModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
