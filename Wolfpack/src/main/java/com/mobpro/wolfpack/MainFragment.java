package com.mobpro.wolfpack;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by wolflyra on 12/11/13.
 */
public class MainFragment extends Fragment {
    MainActivity activity;
    View rootView;
    int[] points = {0};
    public void onCreate(Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton wolf = (ImageButton)rootView.findViewById(R.id.puppybutton);
        TextView clickTime = (TextView) rootView.findViewById(R.id.timeToClick);
        clickTime.setText("Click the wolf to get started!");
        final TextView pointsDisplay = (TextView)rootView.findViewById(R.id.pointsCounter);

        //Getting points from the file
        StringBuilder fileTextPoints = new StringBuilder();
        try{
            FileInputStream fisPoints = activity.openFileInput("Points");
            InputStreamReader inputStreamReader = new InputStreamReader(fisPoints);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                fileTextPoints.append(line);
            }
            fisPoints.close();
            pointsDisplay.setText("Howl Points: "+Long.parseLong(fileTextPoints.toString()));
        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }


        wolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points[0] += 1;
                pointsDisplay.setText("Howl Points: " + Integer.toString(points[0]));

                Calendar c = Calendar.getInstance();
                long timeWhenClicked = c.getTimeInMillis();

                try {
                    FileOutputStream fosPoints = activity.openFileOutput("Points", Context.MODE_PRIVATE);
                    fosPoints.write(Long.toString(points[0]).getBytes());
                    fosPoints.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fosTime = activity.openFileOutput("ClickTime", Context.MODE_PRIVATE);
                    fosTime.write(Long.toString(timeWhenClicked).getBytes());
                    fosTime.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                threadCreator();
            }
        });

        return rootView;

    }

    private void threadCreator() {
        Thread t = new Thread() {

            @Override
            public void run() {

                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };

        t.start();
    }

    public void updateTextView() {
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        TextView clickTime = (TextView) rootView.findViewById(R.id.timeToClick);
        ImageButton wolf = (ImageButton) rootView.findViewById(R.id.puppybutton);

        StringBuilder fileTextTime = new StringBuilder();
        try{
            FileInputStream fisTime = activity.openFileInput("ClickTime");
            InputStreamReader inputStreamReader = new InputStreamReader(fisTime);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                fileTextTime.append(line);
            }
            fisTime.close();
        }catch (IOException e){
            Log.e("IOException", e.getMessage());
        }

        long timeRemaining = 60 - ((currentTime - Long.parseLong(fileTextTime.toString())))/1000;
        if (timeRemaining > 0) {
            clickTime.setText("seconds remaining: " + Long.toString(timeRemaining));
            wolf.setEnabled(false);
        }
        else {
            clickTime.setText("done!");
            wolf.setEnabled(true);
        }

    }

}
