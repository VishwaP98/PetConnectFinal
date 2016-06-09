package com.example.t.petconnect;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * switched the activity to the log in screen when the log out button is clicked
 */
public class LogOutFragment extends Fragment {

    // initializes the intent
    private Intent intent;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // switches the Activity from Fragment to Main Activity
        intent = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(intent);

        //returns the view of Log out fragment containing fragment_log_out
        return inflater.inflate(R.layout.fragment_log_out, container, false);
    }
}
