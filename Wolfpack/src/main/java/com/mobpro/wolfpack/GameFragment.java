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
        return v;
    }

    /*
    Methods to set up the game
     */

    //Starts the game
    private int[] colorList;
    private int turn;
    public int score;
    //private int[] userResponse;

    public void startGame (){
        generateColors();
        runRound(0);
    }

    public int[] generateColors(){
      int max = 1000;
      colorList = new int[max];
      for (int i=0; i<15; i++){
          colorList[i] = (int) Math.random()*4;
      }
        return colorList;
    }

    public void runRound(int round){
        //flash appropriate colors
        flashAll(round);
        getUserInput(round, 0);
    }

    public void getUserInput(int round, int c){
        //final int[] userResponse = new int[0];
        final int[] counter = new int[0];
        final int[] round1 = new int[0];
        round1[0]=round;
        counter[0]=c;
        ImageButton red = (ImageButton) v.findViewById(R.id.red);
        ImageButton yellow = (ImageButton) v.findViewById(R.id.yellow);
        ImageButton green = (ImageButton) v.findViewById(R.id.green);
        ImageButton blue = (ImageButton) v.findViewById(R.id.blue);
        if (c>round){
            runRound(round+1);
        }
        else {
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],0,counter[0],round1[0]);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],1,counter[0],round1[0]);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],2,counter[0],round1[0]);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCorrect(colorList[counter[0]],3,counter[0],round1[0]);
            }
        });
        }

    }

    public void isCorrect(int expected, int actual, int round, int counter){
        if (expected==actual){
            score += 1;
            TextView score = (TextView) v.findViewById(R.id.score);
            score.setText("Score:" + String.valueOf(score));
            getUserInput(round, counter + 1);
        }
        else {
            startGame();
        }
    }



    public void flashAll(int round){
        for (int i=0;i<round; i++){
            flashOne(colorList[i]);
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
