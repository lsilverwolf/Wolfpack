package com.mobpro.wolfpack;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Chloe Local on 12/9/13.
 */
public class QuestFragment extends Fragment {
    MainActivity activity;
    TextView myPoints;
    View rootView;
    TextView nextTerritory;
    int pointsToNext = 100;
    public void onCreate(Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();

        if (activity.pack != null) {
            updatePackDisplay();
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.quest_fragment, container, false);

        Button conquer = (Button)rootView.findViewById(R.id.button);
        myPoints = (TextView) rootView.findViewById(R.id.MyPointsToTerritory);
        nextTerritory = (TextView) rootView.findViewById(R.id.textView2);

        conquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new VocabGameFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

//        ImageView map = (ImageView) rootView.findViewById(R.id.imageView);
//        switch (getStatus()){
//            case 0:
//                map.setImageResource(R.drawable.pos0);
//                break;
//            case 1:
//                map.setImageResource(R.drawable.pos1);
//                break;
//            case 2:
//                map.setImageResource(R.drawable.pos2);
//                break;
//            case 3:
//                map.setImageResource(R.drawable.pos3);
//                break;
//            case 4:
//                map.setImageResource(R.drawable.pos4);
//                break;
//            case 5:
//                map.setImageResource(R.drawable.pos5);
//                break;
//            case 6:
//                map.setImageResource(R.drawable.pos6);
//                break;
//            case 7:
//                map.setImageResource(R.drawable.pos7);
//                break;
//
//        }

        return rootView;

    }

    private int getStatus(){
//        int serverVal = 550;

        /*
        HERE IS THE OTHER BIG COMMENT. GET THE TOTAL SCORE FROM
        THE SERVER, AND RETURN THAT SCORE (NOT ACTUALLY 0!!!)
         */

//        StringBuilder fileTextScore = new StringBuilder();
//        try{
//            FileInputStream fisPoints = activity.openFileInput("VocabScore");
//            InputStreamReader inputStreamReader = new InputStreamReader(fisPoints);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line;
//            while ((line = bufferedReader.readLine()) != null){
//                fileTextScore.append(line);
//            }
//            fisPoints.close();
//
//        }catch (IOException e){
//            Log.e("IOException", e.getMessage());
//        }

        int serverVal = activity.pack.points;
        myPoints.setText("Pack Points: " + serverVal);

        //int serverVal = 550; //replace this with the real result!
        if (serverVal<=100){
            pointsToNext = 100 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 0;
        }
        else if (serverVal<=200){
            pointsToNext = 200 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 1;
        }
        else if (serverVal<=300){
            pointsToNext = 300 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 2;
        }
        else if (serverVal<=400){
            pointsToNext = 400 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 3;
        }
        else if (serverVal<=500){
            pointsToNext = 500 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 4;
        }
        else if (serverVal<=600){
            pointsToNext = 600 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 5;
        }
        else if (serverVal<=700){
            pointsToNext = 700 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 6;
        }
        else {
            pointsToNext = 800 - serverVal;
            nextTerritory.setText("Points to New Territory: " + pointsToNext);
            return 7;
        }

    }

    public void updatePackDisplay(){

        myPoints = (TextView) rootView.findViewById(R.id.MyPointsToTerritory);
        nextTerritory = (TextView) rootView.findViewById(R.id.textView2);

        ImageView map = (ImageView) rootView.findViewById(R.id.imageView);
        switch (getStatus()){
            case 0:
                map.setImageResource(R.drawable.pos0);
                break;
            case 1:
                map.setImageResource(R.drawable.pos1);
                break;
            case 2:
                map.setImageResource(R.drawable.pos2);
                break;
            case 3:
                map.setImageResource(R.drawable.pos3);
                break;
            case 4:
                map.setImageResource(R.drawable.pos4);
                break;
            case 5:
                map.setImageResource(R.drawable.pos5);
                break;
            case 6:
                map.setImageResource(R.drawable.pos6);
                break;
            case 7:
                map.setImageResource(R.drawable.pos7);
                break;

        }
    }


}
