package com.mobpro.wolfpack;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class PackFragment extends Fragment {
    MainActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pack, container, false);

        rootView.findViewById(R.id.create_pack_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packName = ((EditText) rootView.findViewById(R.id.pack_name)).getText().toString();
                activity.service.createPack(packName, "exampleUser");
            }
        });

        return rootView;
    }
}
