package com.mobpro.wolfpack;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PackFragment extends Fragment {
    MainActivity activity;
    ListView packList;
    private MyArrayAdapter packListAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        if (activity.user == null) {
            displayNewUser();
        } else {
            displayReturningUser(activity.user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pack, container, false);
        return rootView;
    }

    public void displayNewUser() {
        Log.d("packFragment", "displaying new user");

        activity.findViewById(R.id.returning_user_display).setVisibility(View.GONE);
        activity.findViewById(R.id.new_user_display).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.create_pack_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packName = ((EditText) activity.findViewById(R.id.pack_name)).getText().toString();
                activity.service.createPack(packName, activity.fbUser.getUsername(), PackFragment.this);
            }
        });
        packList = (ListView) activity.findViewById(R.id.friends_pack_list);
        packListAdapter = new MyArrayAdapter(activity, android.R.layout.simple_list_item_1, activity.packs);
        packList.setAdapter(packListAdapter);
        packList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pack pack = packListAdapter.packs.get(i);
                activity.service.joinPack(activity, pack, PackFragment.this);
            }
        });

        if (activity.packs.size() == 0) {
            activity.service.getFriendsPacks(activity.session, this, activity);
        }
    }

    public void updatePackList(){
        Log.d("packFragment", "updating pack list");
        packListAdapter.clear();
        packListAdapter.addAll(activity.packs);
        packListAdapter.notifyDataSetChanged();
    }

    public void displayReturningUser(User user) {
        Log.d("packFragment", "displaying return user");
        activity.findViewById(R.id.returning_user_display).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.new_user_display).setVisibility(View.GONE);

        if (activity.pack != null) {
            updatePackDisplay();
            if (activity.alpha == null) {
                activity.service.getAlpha(activity);
            } else {
                updateAlphaDisplay();
            }
        }
    }

    public void updatePackDisplay(){
        Log.d("packFragment", "updating pack display");
        ((TextView) activity.findViewById(R.id.hello_pack)).setText("Hello " + activity.pack.name + " pack!");
        ((TextView) activity.findViewById(R.id.pack_points)).setText("" + activity.pack.points + " points!");
    }

    public void updateAlphaDisplay() {
        if (activity.alpha.equals(activity.user.username)){
            ((TextView) activity.findViewById(R.id.alpha)).setText("You are the alpha wolf!");
        } else {
            ((TextView) activity.findViewById(R.id.hello_pack)).setText(activity.alpha + " is the alpha wolf!");
        }
    }

    class MyArrayAdapter extends ArrayAdapter<Pack> {
        ArrayList<Pack> packs;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Pack> objects) {
            super(context, textViewResourceId, objects);
            packs = objects;
        }

    }
}
