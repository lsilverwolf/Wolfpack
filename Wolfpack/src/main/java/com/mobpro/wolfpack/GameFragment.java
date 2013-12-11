package com.mobpro.wolfpack;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.util.ArrayList;

/**
 * Created by Chloe Local on 12/9/13.
 */
public class GameFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.game_fragment);
    }

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.game_fragment, null);
        return v;
    }

    /*
    Methods to set up the game
     */

    //Starts the game
    public void beginGame(){
        beginRound(0);
    }

    //Starts a round
    public int[] beginRound(int roundNum){
        int[] colorList = chooseOrder(roundNum+3);
        return null;
    }

    //generates a list of colors
    public int[] chooseOrder(int size){
        int[] colorList =new int[size];
        for(int i=0; i<size; i++){
            colorList[i] = (int) Math.random()*4;
        }
        return colorList;
    }

    //blinks the
    public void flashAll(int[] colorList){
        for (int i: colorList){
            flashOne(i);
        }
    }

    public void flashOne(int color){
        switch (color){
            case 0:
                ImageButton red = (ImageButton) v.findViewById(R.id.red);
                red.setBackgroundResource(R.drawable.orange_highlight);
                new MyAsyncTask().execute();
                red.setBackgroundResource(R.drawable.red);
                break;
            case 1:
                ImageButton yellow = (ImageButton) v.findViewById(R.id.yellow);
                yellow.setBackgroundResource(R.drawable.yellow_highlight);
                new MyAsyncTask().execute();
                yellow.setBackgroundResource(R.drawable.yellow);
                break;
            case 2:
                ImageButton green = (ImageButton) v.findViewById(R.id.green);
                green.setBackgroundResource(R.drawable.green_highlight);
                new MyAsyncTask().execute();
                green.setBackgroundResource(R.drawable.green);
                break;
            case 3:
                ImageButton blue = (ImageButton) v.findViewById(R.id.blue);
                blue.setBackgroundResource(R.drawable.blue_highlight);
                new MyAsyncTask().execute();
                blue.setBackgroundResource(R.drawable.blue);

                break;
        }
    }
    /*
    Methods to run the game
     */

    /*
    Async task to wait while button flashes a different color
     */
    private class MyAsyncTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.d("wolf", "doInBackground");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            Log.d("wolf", "onCancelled");
        }
    }
}
