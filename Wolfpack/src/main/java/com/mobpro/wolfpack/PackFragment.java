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
    User user;
    MainActivity activity;
    ArrayList<Pack> packs = new ArrayList<Pack>();
    ListView packList;
    private MyArrayAdapter packListAdapter;
    private Pack pack;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        activity.service.getUser(activity.user.getUsername(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pack, container, false);
        if (user != null) {
            displayReturningUser(user);
        }
        return rootView;
    }

    public void updatePackList(ArrayList<Pack> packs){
        Log.d("packFragment", "updating pack list");
        this.packs = packs;
        packListAdapter.clear();
        packListAdapter.addAll(packs);
        packListAdapter.notifyDataSetChanged();
    }

    public void displayNewUser() {
        Log.d("packFragment", "displaying new user");

        activity.findViewById(R.id.returning_user_display).setVisibility(View.GONE);
        activity.findViewById(R.id.new_user_display).setVisibility(View.VISIBLE);

        activity.findViewById(R.id.create_pack_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packName = ((EditText) activity.findViewById(R.id.pack_name)).getText().toString();
                activity.service.createPack(packName, activity.user.getUsername(), PackFragment.this);
            }
        });

        activity.service.getFriendsPacks(activity.session, this, activity);
        packList = (ListView) activity.findViewById(R.id.friends_pack_list);
        packListAdapter = new MyArrayAdapter(activity, android.R.layout.simple_list_item_1, packs);
        packList.setAdapter(packListAdapter);
        packList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pack pack =packListAdapter.packs.get(i);
                activity.service.joinPack(activity, pack, PackFragment.this);
            }
        });

    }

    public void displayReturningUser(User user) {
        Log.d("packFragment", "displaying return user");
        activity.findViewById(R.id.returning_user_display).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.new_user_display).setVisibility(View.GONE);
        this.user = user;
        activity.service.getPack(user.pack, this);
        //show pack info
    }

    public void updatePackDisplay(Pack pack){
        Log.d("packFragment", "updating pack display");
        this.pack = pack;
        ((TextView) activity.findViewById(R.id.hello_pack)).setText("Hello " + pack.name + " pack!");
        ((TextView) activity.findViewById(R.id.pack_points)).setText("" + pack.points + " points!");
    }

    class MyArrayAdapter extends ArrayAdapter<Pack> {
        ArrayList<Pack> packs;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Pack> objects) {
            super(context, textViewResourceId, objects);
            packs = objects;
        }

    }
}
