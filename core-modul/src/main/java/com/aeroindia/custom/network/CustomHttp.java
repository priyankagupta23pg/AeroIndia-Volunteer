package com.penpencil.core.custom.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by Anand on 27-04-2016.
 */
public class CustomHttp {
    public static String RESPONSE_TAG="response";

    public static HashMap<String,String> httpGet(String url,String[] header) throws Exception{
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        String inputStream = null;
        //String[] str = new String[2];
        HashMap<String,String> responseMap=new HashMap<>();
        // try {
        HttpGet mHttpPost = new HttpGet(url);
        if(header!=null){
            for(int i=0;i<header.length;i++){
                String strHeader=header[i];
                String arr[]=strHeader.split("/");
                mHttpPost.addHeader(arr[0], arr[1]);
            }
        }
       /* StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
        se.setContentType("application/json");
        mHttpPost.setEntity(se);*/
        HttpResponse response = mHttpClient.execute(mHttpPost);
        //HttpEntity resEntity = response.getEntity();
        inputStream = EntityUtils.toString(response.getEntity());

        responseMap.put("response",inputStream);
       /* } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("exception",e.toString());
           // Log.i(TAG, "ffffffffffffffffffffffffff" + e.toString());
        }*/
        return responseMap;
    }

    public static HashMap<String,String> httpPost1(String url,String requestBody,String requestHeader)throws Exception{
        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        // The default value is zero, that means the timeout is not used.
        int timeoutConnection = 600000;
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 5000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        mHttpClient.setParams(httpParameters);

        String inputStream = null;
        String[] str = new String[2];
        HashMap<String,String> responseMap=new HashMap<>();
       // try {
            HttpPost mHttpPost = new HttpPost(url);
            StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
            se.setContentType("application/json");
            mHttpPost.setEntity(se);
            HttpResponse response = mHttpClient.execute(mHttpPost);
        if(requestHeader!=null) {
            Header[] headers = response.getHeaders(requestHeader);
            String headerVal = null;
            for (Header header : headers) {
                if (header != null) {
                    headerVal = header.getValue();
                }
            }
            responseMap.put(requestHeader,headerVal);
        }
           // HttpEntity resEntity = response.getEntity();
      /*  BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, "UTF-8"), 8);
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

*/


        /*} catch (Exception e) {
            e.printStackTrace();
            responseMap.put("exception",e.toString());
        }*/
        inputStream = EntityUtils.toString(response.getEntity());
        responseMap.put("response",inputStream);
        return responseMap;
    }


  /*public static HashMap<String,String> httpPost(String url,String requestBody,ArrayList<HeaderItem> header) throws Exception{
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        String inputStream = null;
        String[] str = new String[2];
        HashMap<String,String> responseMap=new HashMap<>();
       // try {
            HttpPost mHttpPost = new HttpPost(url);
           if(header!=null){

                    for(HeaderItem item : header) {
                        mHttpPost.addHeader(item.getHeadername(), item.getHeaderValue());
                    }
            }
            if(requestBody!=null) {
                StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
                se.setContentType("application/json");
                mHttpPost.setEntity(se);
            }

            HttpResponse response = mHttpClient.execute(mHttpPost);
            HttpEntity resEntity = response.getEntity();
            inputStream = EntityUtils.toString(response.getEntity());

            responseMap.put("response",inputStream);
       *//* } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("exception",e.toString());
           // Log.i(TAG, "ffffffffffffffffffffffffff" + e.toString());
        }*//*
      return responseMap;
    }*/



    public static HashMap<String,String> httpPut(String url,String requestBody,String[] header) throws Exception{
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        String inputStream = null;
        String[] str = new String[2];
        HashMap<String,String> responseMap=new HashMap<>();
        // try {
        HttpPut mHttpPost = new HttpPut(url);
        if(header!=null){
            for(int i=0;i<header.length;i++){
                String strHeader=header[i];
                String arr[]=strHeader.split("/");
                mHttpPost.addHeader(arr[0], arr[1]);
            }
        }
        StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
        se.setContentType("application/json");
        mHttpPost.setEntity(se);
        HttpResponse response = mHttpClient.execute(mHttpPost);
        HttpEntity resEntity = response.getEntity();
        inputStream = EntityUtils.toString(response.getEntity());

        responseMap.put("response",inputStream);
       /* } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("exception",e.toString());
           // Log.i(TAG, "ffffffffffffffffffffffffff" + e.toString());
        }*/
        return responseMap;
    }
    /*public static String postStringRequestWithHeader(String url, String json) {
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        String inputStream = null;
        String[] str = new String[2];
        try {
            HttpPost mHttpPost = new HttpPost(url);
//			mHttpPost.addHeader("sessionId",ContentManager.sessionId);
            StringEntity se = new StringEntity(json, HTTP.UTF_8);
            se.setContentType("application/json");
            //		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //	    nameValuePairs.add(new BasicNameValuePair("xml",xml));
            mHttpPost.setEntity(se);
            //	    form = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
            //		mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            HttpResponse response = mHttpClient.execute(mHttpPost);
            Header[] headers = response.getHeaders("sessionId");
            String headerVal = null;
            for (Header header : headers) {
                if (header != null) {
                    ContentManager.sessionId = header.getValue();
                    *//*SalesUser user=SettingPrefrence.getUserPrefrence(AppUtility.mContext);
                    user.setSessionId(header.getValue());
					SettingPrefrence.saveUserPrefrence(user, AppUtility.mContext);*//*
                }

            }


            HttpEntity resEntity = response.getEntity();

           // Log.i("", TAG + "here...2");

			*//*String resp = EntityUtils.toString(resEntity);
        Log.i(TAG,"postSyncXML srv response:"+resp);*//*
            inputStream = EntityUtils.toString(response.getEntity());
            Log.i("", TAG + "here...3" + inputStream);

        } catch (Exception e) {
            Log.i("", TAG + "ffffffffffffffffffffffffff" + e.toString());
        }
        return inputStream;
    }*/

    /*public static String postStringRequest(String url, String json) {
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        String inputStream = null;
        String[] str = new String[2];
        try {
            HttpPost mHttpPost = new HttpPost(url);
            mHttpPost.addHeader("sessionId", AppUtility.sessionId);
//			mHttpPost.addHeader("sessionId","faghsdfha");

            StringEntity se = new StringEntity(json, HTTP.UTF_8);
            *//*List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("json", json.toString()));
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(postParams,HTTP.UTF_8);
			ent.setContentType("application/json");
			mHttpPost.setEntity(ent);*//*
            se.setContentType("application/json");
            //		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //	    nameValuePairs.add(new BasicNameValuePair("xml",xml));
            Log.i(TAG, "here..." + AppUtility.sessionId);
            mHttpPost.setEntity(se);
            //	    form = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
            //		mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            HttpResponse response = mHttpClient.execute(mHttpPost);
			*//*Header[] headers=response.getHeaders("sessionId");
			String headerVal = null;
			for(Header header: headers){
				if(header!=null){
					ContentManager.sessionId=header.getValue();
				}

			}*//*


            HttpEntity resEntity = response.getEntity();

            Log.i("", TAG + "here...2");

			*//*String resp = EntityUtils.toString(resEntity);
        Log.i(TAG,"postSyncXML srv response:"+resp);*//*
            inputStream = EntityUtils.toString(response.getEntity());
            Log.i(TAG, "here...3" + inputStream);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "ffffffffffffffffffffffffff" + e.toString());
        }
        return inputStream;
    }*/


    /*public static HashMap<String,String> httpGetWithTokken(String url,ArrayList<HeaderItem> header) throws Exception{
            DefaultHttpClient mHttpClient = new DefaultHttpClient();
            String inputStream = null;
            //String[] str = new String[2];
            HashMap<String,String> responseMap=new HashMap<>();
            // try {
            HttpGet httpGet = new HttpGet(url);
        if(header!=null){
           *//* for(int i=0;i<header.length;i++){
                String strHeader=header[i];
                String arr[]=strHeader.split("/");
                httpGet.addHeader(arr[0], arr[1]);
            }*//*
            if(header!=null){

                for(HeaderItem item : header) {
                    httpGet.addHeader(item.getHeadername(), item.getHeaderValue());
                }
            }
        }
       *//* StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
        se.setContentType("application/json");
        mHttpPost.setEntity(se);*//*
        HttpResponse response = mHttpClient.execute(httpGet);
        //HttpEntity resEntity = response.getEntity();
        inputStream = EntityUtils.toString(response.getEntity());

        responseMap.put("response",inputStream);
       *//* } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("exception",e.toString());
           // Log.i(TAG, "ffffffffffffffffffffffffff" + e.toString());
        }*//*
        return responseMap;
    }*/

   /* public static String sendMultiPart(String uploadUrl,String[] headers,MultipartEntity entity){

        String responseString;
        try
        {

            System.setProperty("http.keepAlive", "true");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uploadUrl);
            // Log.d("SERVICE SESSIONID", "5b5a1c1f-5825-4e7a-a180-

            for(String header : headers) {
                String headerName=header.split("/")[0];
                String headerValue=header.split("/")[1];
                httppost.addHeader(headerName, headerValue);
            }
            // httppost.addHeader("Content-Type", "multipart/form-data; charset=UTF-8");

            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                Log.d("RESPONSE",responseString);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }

        } catch (Exception e) {
            responseString = e.toString();
        }

        return responseString;
    }*/


    public static Bitmap getBitmapFromURL(String src,String authToken) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("authToken",authToken);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
