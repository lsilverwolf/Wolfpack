package com.mobpro.wolfpack;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public void getFriendsPacks(final Session session){
        final ArrayList<String> usernames = new ArrayList<String>();
        new AsyncTask<Void,Void,ArrayList<Pack>>(){
            @Override
            protected ArrayList<Pack> doInBackground(Void... voids) {
                ArrayList<Pack> packs = new ArrayList<Pack>();

                Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
                    @Override
                    public void onCompleted(List<GraphUser> users, Response response) {
                        for (GraphUser user:users){
                            usernames.add(user.getUsername());
                            Log.d("getting friends packs", "found friend: " + user.getUsername());
                        }
                    }
                }).executeAndWait();

                try{
                Log.d("getting friends packs", "attempting to get all packs");
                String responseString ="";
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://radiant-inlet-3938.herokuapp.com/packs");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                Log.d("getting friends packs", "got all packs");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                List<List<String>> parsedResponse = JSONParse(responseString);
                for (List<String> resp:parsedResponse){
                    if (usernames.contains(resp.get(1))){
                        Pack pack = new Pack(resp.get(0), resp.get(1));
                        Log.d("getting friends packs", "found friends pack: " + pack.toString());
                    } else {
                        Log.d("getting friends packs", resp.get(1) + " is not a friend");
                    }
                }
                Log.d("getting friends packs", "got friends packs");
                } catch (Exception e){
                    Log.d("getting friends packs", "error attempting to get friends packs: " + e.toString());
                }




                return packs;
            }
        }.execute(null, null);
    }

    public List<List<String>> JSONParse(String responseString) throws JSONException {
        JSONObject obj = new JSONObject(responseString);
        List<List<String>> res = new ArrayList<List<String>>();
        JSONArray array;
        array = obj.getJSONArray("packs");

        for (int j = 0; j<array.length(); j++){
            List<String> inner = new ArrayList<String>();
            inner.add(array.getJSONObject(j).getString("pack"));
            inner.add(array.getJSONObject(j).getString("creator"));
            res.add(inner);
        }
        return res;
    }

    class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
