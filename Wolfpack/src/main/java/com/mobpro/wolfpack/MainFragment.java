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

import java.util.Calendar;

/**
 * Created by wolflyra on 12/11/13.
 */
public class MainFragment extends Fragment {
    final int[] points = {0};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ScoreDbAdapter dbAdapter = new ScoreDbAdapter(this.getActivity());


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton wolf = (ImageButton)rootView.findViewById(R.id.puppybutton);
        final TextView clickTime = (TextView) rootView.findViewById(R.id.timeToClick);
        final TextView pointsDisplay = (TextView)rootView.findViewById(R.id.pointsCounter);
        pointsDisplay.setText("Howl Points: "+Integer.toString(points[0]));
        timerClass(rootView);
        wolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickTime.getText() == "done!") {
                    points[0] += 1;
                    pointsDisplay.setText("Howl Points: "+Integer.toString(points[0]));
                    timerClass(rootView);}
//                Calendar c = Calendar.getInstance();
//                int seconds = c.get(Calendar.SECOND);
//                clickTime.setText(Integer.toString(seconds));

            }
        });


        return rootView;

    }
    public void timerClass(final View view) {
        new CountDownTimer(60000, 1000) {
            final TextView clickTime = (TextView) view.findViewById(R.id.timeToClick);
            public void onTick(long millisUntilFinished) {
                clickTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                clickTime.setText("done!");
            }
        }.start();
    }
}
