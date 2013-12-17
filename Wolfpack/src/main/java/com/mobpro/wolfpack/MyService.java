package com.mobpro.wolfpack;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceBinder();
    }

    public void createPack(final String packName, final String creatorName){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Log.d("create pack", "attempting to create pack");
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://radiant-inlet-3938.herokuapp.com/createpack");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("pack", packName));
                    nameValuePairs.add(new BasicNameValuePair("username", creatorName));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httppost);
                    Log.d("create pack", "pack created");
                } catch (Exception e){
                    Log.d("create pack", "error attempting to create pack: " + e.toString());

                }
                return null;
            }
        }.execute(null, null);

    }


    class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
