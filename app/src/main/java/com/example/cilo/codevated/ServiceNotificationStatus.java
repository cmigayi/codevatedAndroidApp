package com.example.cilo.codevated;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;

public class ServiceNotificationStatus extends Service {
    String url;
    HashMap<String,String> requestDataFromServer;
    User user;
    LocalUserStorage localUserStorage;
    Common common;

    public ServiceNotificationStatus() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        common = new Common(getApplicationContext());
        localUserStorage = new LocalUserStorage(getApplicationContext());
        user = localUserStorage.getSignedinUser();
        url = "/getNotifications.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);
        requestDataFromServer.put("data","status");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(common.isNetworkAvailable() == false){

                }else{
                    new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else {
                                Log.d("cilo--", response);
                                Intent i = new Intent("notifications");
                                i.putExtra("data", response);
                                sendBroadcast(i);
                            }
                        }
                    }).execute();

                    handler.postDelayed(this,1000);
                }

            }
        },1000);
    }
}
