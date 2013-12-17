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
                //go to game view
                // Create new fragment and transaction
                Fragment newFragment = new GameFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }
        });


        return rootView;


    }

    private int getStatus(){
        return 0;
    }
}
