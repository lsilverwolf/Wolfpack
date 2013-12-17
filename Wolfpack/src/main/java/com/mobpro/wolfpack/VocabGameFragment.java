package com.mobpro.wolfpack;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chloe Local on 12/17/13.
 */
public class VocabGameFragment extends Fragment {
    MainActivity activity;
    public void onCreate(Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }


    View v;
    int score=0;
    int serverVal;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vocabgame_fragment, null);
        Button done = (Button)v.findViewById(R.id.vDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to game view
                // Create new fragment and transaction

                Fragment newFragment = new QuestFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                StringBuilder fileTextScore = new StringBuilder();
                try{
                    FileInputStream fisPoints = activity.openFileInput("VocabScore");
                    InputStreamReader inputStreamReader = new InputStreamReader(fisPoints);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        fileTextScore.append(line);
                    }
                    fisPoints.close();
                    serverVal = Integer.parseInt(fileTextScore.toString()) + score;
                }catch (IOException e){
                    Log.e("IOException", e.getMessage());
                }
                try {
                    FileOutputStream fosScore = activity.openFileOutput("VocabScore", Context.MODE_PRIVATE);
                    fosScore.write(Integer.toString(serverVal).getBytes());
                    fosScore.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                HERE IS THE BIG COMMENT. IF A USER CLICKS HERE THAT MEANS THEY ARE
                DONE PLAYING FOR NOW. ADD THIS SCORE TO THE EXISTING TOTAL SCORE ON THE
                SURVER.
                 */

                transaction.commit();
            }
        });
        HashMap<String,String> hash = makeVocabPairs();
        ArrayList<String> vals = chooseValues(hash);
        setView(vals, v);
        return v;
    }


    private void start(){
        TextView scoreText = (TextView) v.findViewById(R.id.vscore);
        scoreText.setText("Score: "+ String.valueOf(score));
        HashMap<String,String> hash = makeVocabPairs();
        ArrayList<String> vals = chooseValues(hash);
        setView(vals, v);
    }


    private void setView (ArrayList<String> group, View v0){
        Button v1 = (Button) v0.findViewById(R.id.Syn1);
        Button v2 = (Button) v0.findViewById(R.id.Syn2);
        Button v3 = (Button) v0.findViewById(R.id.Syn3);
        Button v4 = (Button) v0.findViewById(R.id.Syn4);
        TextView word = (TextView) v0.findViewById(R.id.word);
        TextView scoreText = (TextView) v0.findViewById(R.id.vscore);
        word.setText(group.get(0));
        int correct;
        switch((int) (Math.random()*4)){
            case 0:
                v1.setText(group.get(1));
                v2.setText(group.get(2));
                v3.setText(group.get(3));
                v4.setText(group.get(4));
                v1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        score++;
                        start();
                    }
                });
                correct =0;
                break;
            case 1:
                v2.setText(group.get(1));
                v1.setText(group.get(2));
                v3.setText(group.get(3));
                v4.setText(group.get(4));
                v2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        score++;
                        start();
                    }
                });
                correct=1;
                break;
            case 2:
                v3.setText(group.get(1));
                v1.setText(group.get(2));
                v2.setText(group.get(3));
                v4.setText(group.get(4));
                v3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        score++;
                        start();
                    }
                });
                correct=2;
                break;
            case 3:
                v4.setText(group.get(1));
                v1.setText(group.get(2));
                v2.setText(group.get(3));
                v3.setText(group.get(4));
                v4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        score++;
                        start();
                    }
                });
                correct =3;
                break;
        }
    }

    private ArrayList<String> chooseValues(HashMap<String,String> hash){
        ArrayList<String> allKeys = new ArrayList<String>();
        ArrayList<String> allValues = new ArrayList<String>();
        for (String v: hash.keySet()){
            allKeys.add(v);
        }
        for(String v: hash.values()){
            allValues.add(v);
        }
        String word = allKeys.get((int) (Math.random()*allKeys.size()));
        String syn = hash.get(word);
        ArrayList<String> group = new ArrayList<String>();
        group.add(word);
        group.add(syn);
        group.add(allValues.get((int) (Math.random() * allValues.size())));
        group.add(allValues.get((int) (Math.random()*allValues.size())));
        group.add(allValues.get((int) (Math.random()*allValues.size())));
        return group;
    }

    private String choose(ArrayList<String> all){
        return all.get((int) (Math.random()*all.size()));
    }

    private ArrayList<String>allKeysAndValues(HashMap<String, String> hash) {
        ArrayList<String> all = new ArrayList<String>();
        for(String v: hash.values()){
            all.add(v);
        }
        for (String v: hash.keySet()){
            all.add(v);
        }
        return all;
    }

    private HashMap<String, String> makeVocabPairs(){
        HashMap<String, String> pairs = new HashMap<String, String>();
        pairs.put("pretty","beautiful");
        pairs.put("hypocrisy","duplicity");
        pairs.put("pacify","placate");
        pairs.put("recalcitrant", "obstinate");
        pairs.put("turbulent","disordered");
        pairs.put("obsolete","antiquated");
        pairs.put("factual","genuine");
        pairs.put("requisite","vital");
        pairs.put("sanguine", "optimistic");
        pairs.put("auspicious", "fortunate");
        pairs.put("impartial","unbiased");
        pairs.put("mirthful","content");
        pairs.put("industrious", "diligent");
        pairs.put("introverted","bashful");
        /*
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();
        pairs.put();*/

        return pairs;
    }

}
