package com.mobpro.wolfpack;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

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
        Button done = (Button)v.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to game view
                // Create new fragment and transaction
                Fragment newFragment = new QuestFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button start = (Button) v.findViewById(R.id.startGame);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        return v;
    }

    /*
    Methods to set up the game
     */

    //Starts the game
    private int[] colorList;
    private int turn;
    public int score;

    public void startGame (){
        int[] myColors = generateColors();
        runRound(0, myColors);
    }

    public int[] generateColors(){
      int max = 1000;
      colorList = new int[max];
      for (int i=0; i<max; i++){
          colorList[i] = (int) (Math.random()*4);
      }
        return colorList;
    }

    public void runRound(int round, int[] myColors){
        //flash appropriate colors
        flashAll(round, myColors);
        //getUserInput(round, 0);
    }

    public void getUserInput(int round, int c, int[] myColors){
        //final int[] userResponse = new int[0];
        final int[] counter = new int[0];
        final int[] round1 = new int[0];
        final int[] myColors1 = myColors;
        round1[0]=round;
        counter[0]=c;
        ImageButton red = (ImageButton) v.findViewById(R.id.red);
        ImageButton yellow = (ImageButton) v.findViewById(R.id.yellow);
        ImageButton green = (ImageButton) v.findViewById(R.id.green);
        ImageButton blue = (ImageButton) v.findViewById(R.id.blue);
        if (c>round){
            runRound(round+1,myColors);
        }
        else {
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],0,counter[0],round1[0], myColors1);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],1,counter[0],round1[0], myColors1);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],2,counter[0],round1[0], myColors1);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],3,counter[0],round1[0], myColors1);
            }
        });
        }

    }

    public void isCorrect(int expected, int actual, int round, int counter, int[] myColors){
        if (expected==actual){
            score += 1;
            TextView score = (TextView) v.findViewById(R.id.score);
            score.setText("Score:" + String.valueOf(score));
            getUserInput(round, counter + 1, myColors);
        }
        else {
            startGame();
        }
    }

    int z = 0;

    public void flashAll(int round, int[] myColors){
        round = 5;
        /*
        for (int i=0;i<=5; i++){
            new MyAsyncWait().execute();
            flashOne(myColors[i]);
        }
        Log.d("wolfpack","should flash all");*/
        new MyAsyncWait().execute();
    }

    public void flashOne(int color){
        Log.d("wolfpack","should flash");
        //Log.d("wolfpack", getString(color));
        switch (color){
            case 0:
                ImageButton red = (ImageButton) v.findViewById(R.id.red);
                Log.d("wolfpack","should red");
               new MyAsyncRed().execute();
                break;
            case 1:
                Log.d("wolfpack","should yellow");
                new MyAsyncYellow().execute();
                break;
            case 2:
                Log.d("wolfpack","should green");
                new MyAsynGreen().execute();
                break;
            case 3:
                Log.d("wolfpack","should blue");
                new MyAsyncBlue().execute();
                break;
        }
    }

    /*
    Async task to wait while button flashes a different color
     */
    private class MyAsyncRed extends AsyncTask {
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
            ImageButton red = (ImageButton) v.findViewById(R.id.red);
            red.setBackgroundResource(R.drawable.orange_highlight);
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
            ImageButton red = (ImageButton) v.findViewById(R.id.red);
            red.setBackgroundResource(R.drawable.red);
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
    private class MyAsyncYellow extends AsyncTask{
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
            ImageButton yellow = (ImageButton) v.findViewById(R.id.yellow);
            yellow.setBackgroundResource(R.drawable.yellow_highlight);
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
            ImageButton yellow = (ImageButton) v.findViewById(R.id.yellow);
            yellow.setBackgroundResource(R.drawable.yellow);
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

    private class MyAsynGreen extends AsyncTask{
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
            ImageButton green = (ImageButton) v.findViewById(R.id.green);
            green.setBackgroundResource(R.drawable.green_highlight);
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
            ImageButton green = (ImageButton) v.findViewById(R.id.green);
            green.setBackgroundResource(R.drawable.green);
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
    private class MyAsyncBlue extends AsyncTask{
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
            ImageButton blue = (ImageButton) v.findViewById(R.id.blue);
            blue.setBackgroundResource(R.drawable.blue_highlight);
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
            ImageButton blue = (ImageButton) v.findViewById(R.id.blue);
            blue.setBackgroundResource(R.drawable.blue);
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
    private class MyAsyncWait extends AsyncTask{
        @Override
        protected void onPreExecute() {
            Log.d("wolf", "onPreExecute");
        }
        @Override
        protected void onPostExecute(Object o) {
            Log.d("wolf", "onPostExecute");
            if (z<5){
            z++;
            flashOne(colorList[z]);
            }
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
