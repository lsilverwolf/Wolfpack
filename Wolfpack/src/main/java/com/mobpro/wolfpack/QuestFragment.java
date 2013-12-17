package com.mobpro.wolfpack;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chloe Local on 12/9/13.
 */
public class QuestFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.quest_fragment, container, false);
        //ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        Button conquer = (Button)rootView.findViewById(R.id.conquer);

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

        return rootView;


    }

    private int getStatus(){
        /*
        HERE IS THE OTHER BIG COMMENT. GET THE TOTAL SCORE FROM
        THE SERVER, AND RETURN THAT SCORE (NOT ACTUALLY 0!!!)
         */
        int serverVal = 550; //replace this with the real result!
        if (serverVal<=100){
            return 0;
        }
        else if (serverVal<=200){
            return 1;
        }
        else if (serverVal<=300){
            return 2;
        }
        else if (serverVal<=400){
            return 3;
        }
        else if (serverVal<=500){
            return 4;
        }
        else if (serverVal<=600){
            return 5;
        }
        else if (serverVal<=700){
            return 6;
        }
        else {
            return 7;
        }
    }
}
