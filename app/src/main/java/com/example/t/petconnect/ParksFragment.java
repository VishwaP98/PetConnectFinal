package com.example.t.petconnect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * shows the ListView of the parks with its name, google map icon, and additional info icon
 */
public class ParksFragment extends Fragment {

    // initializes all the variables
    private CSVAdapter CSVAdapter;
    private ListView ParksList;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // sets the view containing the fragment_parks layout
        view = inflater.inflate(R.layout.fragment_parks, container, false);

        // identifies the ListView
        ParksList = (ListView) view.findViewById(R.id.listView);

        // initializes the CSVAdapter and passes the context of the fragment and the layout that consists in each position of the text view
        CSVAdapter = new CSVAdapter(getActivity(), R.layout.item_layout);

        // sets the adapter to the listview
        ParksList.setAdapter(CSVAdapter);

        // returns view
        return view;

    }
}
