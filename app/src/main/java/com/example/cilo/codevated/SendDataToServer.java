package com.example.cilo.codevated;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cilo on 4/26/17.
 */

public class SendDataToServer extends AsyncTask<Void,Void, String> {
    HashMap<String,String> data;
    UrlCallBack urlCallBack;
    String baseUrl = "http://10.0.2.2/codevated/android";
    String url;
    String response = null;

    public SendDataToServer(HashMap<String,String> data,String url,UrlCallBack urlCallBack){
        this.data = data;
        this.url = url;
        this.urlCallBack = urlCallBack;
    }

    @Override
    protected String doInBackground(Void... params) {

        Log.d("cilo",data.get("pass"));
        url = baseUrl+url;
        try{

            URL durl = new URL(url);
            Log.d("cecil",url);

            HttpURLConnection conn = (HttpURLConnection)
                    durl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(data));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            Log.d("cecil",""+responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=
                        new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
                Log.d("cecil",response);
            }
            else {
                response="Error Posting";
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        urlCallBack.done(result);
    }

    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.
                    encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.
                    encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
