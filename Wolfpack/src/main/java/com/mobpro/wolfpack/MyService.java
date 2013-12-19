package com.mobpro.wolfpack;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.facebook.Session;

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
import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceBinder();
    }

    public void createPack(final String packName, final String creatorName, final PackFragment packFragment){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://radiant-inlet-3938.herokuapp.com/createpack");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("pack", packName));
                    nameValuePairs.add(new BasicNameValuePair("username", creatorName));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httppost);
                    final User user = new User(creatorName, packName, 0);
                    packFragment.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            packFragment.displayReturningUser(user);
                        }
                    });

                } catch (Exception e){
                    Log.d("create pack", "error attempting to create pack: " + e.toString());
                }
                return null;
            }
        }.execute(null, null);
    }

    public void getFriendsPacks(final Session session, final PackFragment packFragment, final MainActivity activity){
//        final ArrayList<String> usernames = new ArrayList<String>();
        new AsyncTask<Void,Void,ArrayList<Pack>>(){
            @Override
            protected ArrayList<Pack> doInBackground(Void... voids) {
                final ArrayList<Pack> packs = new ArrayList<Pack>();
//
//                Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
//                    @Override
//                    public void onCompleted(List<GraphUser> users, Response response) {
//                        for (GraphUser user:users){
//                            usernames.add(user.getUsername());
//                            Log.d("getting friends packs", "found friend: " + user.getUsername());
//                        }
//                    }
//                }).executeAndWait();

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
                List<Pack> parsedResponse = JSONParse(responseString);
                for (Pack pack:parsedResponse){
//                    if (usernames.contains(resp.get(1))){
                        packs.add(pack);
                        Log.d("getting friends packs", "found friends pack: " + pack.toString());
//                    } else {
//                        Log.d("getting friends packs", resp.get(1) + " is not a friend");
//                    }
                }
                Log.d("getting friends packs", "got friends packs");
                } catch (Exception e){
                    Log.d("getting friends packs", "error attempting to get friends packs: " + e.toString());
                }

                activity.packs = packs;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getting friends packs", "updating view");
                        packFragment.updatePackList();
                    }
                });

                return packs;
            }
        }.execute(null, null);
    }

    public void joinPack(final MainActivity activity, final Pack pack, final PackFragment packFragment){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://radiant-inlet-3938.herokuapp.com/joinpack");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("pack", pack.name));
                    nameValuePairs.add(new BasicNameValuePair("username", activity.fbUser.getUsername()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    final User user = new User(activity.fbUser.getUsername(), pack.name, 0);
                    packFragment.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            packFragment.displayReturningUser(user);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute(null,null);
    }

    public void getPack(final String packName, final MainActivity activity) {

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://radiant-inlet-3938.herokuapp.com/pack/" + packName);
                    HttpResponse response = httpClient.execute(httpGet);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    final Pack pack = parsePack(responseString);

                    activity.pack = pack;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.updateDisplayForPack();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(null,null);

    }

    private Pack parsePack(String responseString) throws JSONException {
        Log.d("get packs", responseString);
        JSONObject obj = new JSONObject(responseString);
        List<User> res = new ArrayList<User>();
        JSONArray array;
        array = obj.getJSONArray("packs");
        if (array.length() == 0) {
            return null;
        } else {
            String pack = array.getJSONObject(0).getString("pack");
            String creator = array.getJSONObject(0).getString("creator");
            int points = array.getJSONObject(0).getInt("points");
            return new Pack(pack, creator, points);
        }
    }

    public void getUser(final String username, final MainActivity activity){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://radiant-inlet-3938.herokuapp.com/" + username);
                    HttpResponse response = httpClient.execute(httpGet);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    final User user = parseUser(responseString);
                    activity.user = user;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.updateDisplayForUser();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(null,null);

    }

    public User parseUser(String responseString) throws JSONException {
        JSONObject obj = new JSONObject(responseString);
        List<User> res = new ArrayList<User>();
        JSONArray array;
        array = obj.getJSONArray("users");
        if (array.length() == 0) {
            return null;
        } else {
            String username = array.getJSONObject(0).getString("username");
            String pack = array.getJSONObject(0).getString("pack");
            int points = array.getJSONObject(0).getInt("points");
            return new User(username, pack, points);
        }
    }

    public void updatePackPoints(final int points, final String packName) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://radiant-inlet-3938.herokuapp.com/pack/score");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("pack", packName));
                    nameValuePairs.add(new BasicNameValuePair("points", String.valueOf(points)));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httppost);
                } catch (Exception e){
                    Log.d("updatePackPoints", "error attempting to update pack points: " + e.toString());
                }
                return null;
            }
        }.execute(null, null);
    }

    public void updateUserPoints(final int points, final String username) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://radiant-inlet-3938.herokuapp.com/user/score");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("points", String.valueOf(points)));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httppost);
                } catch (Exception e){
                    Log.d("updatePackPoints", "error attempting to update pack points: " + e.toString());
                }
                return null;
            }
        }.execute(null, null);
    }

    public void getAlpha(final MainActivity activity) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://radiant-inlet-3938.herokuapp.com/alpha/" + activity.pack.name);
                    HttpResponse response = httpClient.execute(httpGet);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    final User alpha = parseUser(responseString);

                    activity.alpha = alpha.username;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.updateDisplayForAlpha();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(null,null);


    }

    public List<Pack> JSONParse(String responseString) throws JSONException {
        JSONObject obj = new JSONObject(responseString);
        List<Pack> res = new ArrayList<Pack>();
        JSONArray array;
        array = obj.getJSONArray("packs");

        for (int j = 0; j<array.length(); j++){
            String pack = array.getJSONObject(0).getString("pack");
            String creator = array.getJSONObject(0).getString("creator");
            int points = array.getJSONObject(0).getInt("points");
            res.add(new Pack(pack, creator, points));

        }
        return res;
    }




    class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
