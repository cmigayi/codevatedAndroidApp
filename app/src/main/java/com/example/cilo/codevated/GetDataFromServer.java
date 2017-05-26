package com.example.cilo.codevated;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cilo on 4/26/17.
 */

public class GetDataFromServer extends AsyncTask<Void,Void,String> {
    String baseUrl = "http://10.0.2.2/codevated/android";
    String url;
    UrlCallBack urlCallBack;
    HashMap<String,String> data;
    String response;

    public GetDataFromServer(HashMap<String,String> data, String url, UrlCallBack urlCallBack){
        this.data = data;
        this.url = url;
        this.urlCallBack = urlCallBack;
    }

    @Override
    protected String doInBackground(Void... params) {
        response = null;
        url = baseUrl+url;
        try{

            URL durl = new URL(url);
            Log.d("cecil",""+data);
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
                    Log.d("cecil3",response);
                }
                else {
                    response="Error Posting";
                }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("cilo100",""+e);
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
