package com.example.t.petconnect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * sets the layout of the Fragment to fragment_home containing multiple TextViews
 * gives information on who we are
 */
public class HomeFragment extends Fragment {

    // initializes all the variables
    private View view;
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;
    private TextView textview5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // sets the View containing fragment_home layout

        view = inflater.inflate(R.layout.fragment_home, container, false);

        // identifies all the textviews
        textview1 = (TextView)view.findViewById(R.id.textView4);
        textview2 = (TextView)view.findViewById(R.id.textView5);
        textview3 = (TextView)view.findViewById(R.id.textView6);
        textview4 = (TextView)view.findViewById(R.id.textView7);
        textview5 = (TextView)view.findViewById(R.id.textView8);

        // sets the text for all the text

        textview1.setText("Who are We?");
        textview2.setText("We are a high school Android developer group building applications for welfare of the community.");
        textview3.setText("What are we doing?");
        textview4.setText("Pet owners with each other and at the same time inform them of events planned by the City\n" +
                "to relieve pet owners of the hassle of looking up the events and its timings. In addition, we have\n" +
                "included featured parks that pet owners can visit around the city.");
        textview5.setText("Our mission is to aware and preserve a happy environment in the community");

        // returns view
        return view;
    }
}
