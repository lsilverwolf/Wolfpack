package com.mobpro.wolfpack;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class PackFragment extends Fragment {
    MainActivity activity;
    ArrayList<Pack> packs = new ArrayList<Pack>();
    ListView packList;
    private ArrayAdapter<Pack> packListAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        activity.service.getFriendsPacks(activity.session, this, activity);
        packList = (ListView) activity.findViewById(R.id.friends_pack_list);
        packListAdapter = new ArrayAdapter<Pack>(activity, android.R.layout.simple_list_item_1, packs);
        packList.setAdapter(packListAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pack, container, false);

        rootView.findViewById(R.id.create_pack_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packName = ((EditText) rootView.findViewById(R.id.pack_name)).getText().toString();
                activity.service.createPack(packName, activity.user.getUsername());
            }
        });

        return rootView;
    }

    void updatePackList(ArrayList<Pack> packs){
        this.packs = packs;
        packListAdapter.clear();
        packListAdapter.addAll(packs);
        Log.d("getting friends packs", "adapter contents changed");
        packListAdapter.notifyDataSetChanged();
        Log.d("getting friends packs", "adapter notifyDataSetChanged called");
    }
}
