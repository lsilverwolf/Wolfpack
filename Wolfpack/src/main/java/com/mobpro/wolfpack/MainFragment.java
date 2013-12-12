package com.mobpro.wolfpack;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by wolflyra on 12/11/13.
 */
public class MainFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton wolf = (ImageButton)rootView.findViewById(R.id.puppybutton);
        final TextView clickTime = (TextView) rootView.findViewById(R.id.timeToClick);
        final int[] points = {0};
        final TextView pointsDisplay = (TextView)rootView.findViewById(R.id.pointsCounter);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                clickTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                clickTime.setText("done!");
            }
        }.start();
        wolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points[0] += 1;
                pointsDisplay.setText("Howl Points: "+Integer.toString(points[0]));
            }
        });


        return rootView;

    }
}
