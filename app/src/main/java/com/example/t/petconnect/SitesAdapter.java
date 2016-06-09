package com.example.t.petconnect;

/**
 * Created by Harsh on 2016-05-15.
 */
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 * Custom Adapter class that is responsible for holding the list of sites after they
 * get parsed out of XML and building row views to display them on the screen.
 * sets the text for each row of the listView
 */
public class SitesAdapter extends ArrayAdapter<StackSite> {

    /**
     * Constructor method
     * @param ctx
     * @param textViewResourceId
     * @param sites
     */
    public SitesAdapter(Context ctx, int textViewResourceId, List<StackSite> sites) {
        super(ctx, textViewResourceId, sites);
    }


    /**
     * This method is responsible for creating row views out of a StackSite object that can be put
     * into our ListView
     * displays a different event name, date, and time in each row
     * @param pos
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        if(null == row){
            //No recycled View, we have to inflate one.
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout)inflater.inflate(R.layout.row_site, null); //set layout to row_site.xml
        }

        //Get our View References
        TextView nameTxt = (TextView)row.findViewById(R.id.nameTxt);
        TextView startDateTxt = (TextView)row.findViewById(R.id.aboutTxt);
        TextView startTimeTxt = (TextView)row.findViewById(R.id.timeTxt);


        //Set the relavent text in our TextViews
        nameTxt.setText(getItem(pos).getName()); //gets the event name from the arraylist of event information
        startDateTxt.setText("DATE: " + getItem(pos).getStartDate()); //gets the event date from the arraylist of event information
        startTimeTxt.setText("      TIME: " + getItem(pos).getStartTime()); //gets the event time from the arraylist of event information

        //returns current row
        return row;


    }

}