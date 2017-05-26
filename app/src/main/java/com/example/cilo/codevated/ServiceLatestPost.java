package com.example.cilo.codevated;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;

public class ServiceLatestPost extends Service {
    String url;
    HashMap<String,String> requestDataFromServer;
    User user;
    LocalUserStorage localUserStorage;

    public ServiceLatestPost() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        localUserStorage = new LocalUserStorage(getApplicationContext());
        user = localUserStorage.getSignedinUser();
        url = "/latestPosts.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);
        Log.d("cilo",""+user.userId);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            Log.d("cilo#",response);
                            Intent i = new Intent("latestPosts");
                            i.putExtra("data",response);
                            sendBroadcast(i);
                        }
                    }
                }).execute();
                handler.postDelayed(this,2000);
            }
        },2000);
    }
}
