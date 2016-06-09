package com.example.t.petconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * reads the parks file and makes a park object for each park
 * adds the park to the CSV adapter ArrayList
 * sets the layout of  multiple TextViews and buttons in each position of the TextView
 * on clicking google maps icon, google maps of showing the park's location shows up
 * on clicking additional info icon, the website of the park opens up
 */
public class CSVAdapter extends ArrayAdapter<Parks> {

    // initializes all the variables
    private View ConvertView;
    private Context ctx;
    private String[] RowData;
    private int layout;

    /**
     *  set the layout of each position in the ListView and the context and calls the loadArrayFromFule method
     * @param context- gets the context of the Parks Fragment
     * @param textViewResourceId - gets the layout of each position of the ListView
     */
    public CSVAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.layout = textViewResourceId;
        this.ctx = context;
        loadArrayFromFile();
    }

    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //sets the view including the layout of each position of the ListView
        ConvertView = inflater.inflate(layout, parent, false);
        ParkHolder viewHolder = new ParkHolder();

        //identifies all the textViews and ImageButton in the layout
        viewHolder.parkName = (TextView) ConvertView.findViewById(R.id.parkName) ;
        viewHolder.park = (ImageButton) ConvertView.findViewById(R.id.Map);
        viewHolder.url = (ImageView) ConvertView.findViewById(R.id.url);
        viewHolder.time = (TextView) ConvertView.findViewById(R.id.time);

        // sets the parkName to the name of the park
        viewHolder.parkName.setText(getItem(pos).getName());

        // sets the time to the timings of the park
        viewHolder.time.setText("Park Timings:  " + getItem(pos).getTime());

        // if the maps icon is clicked, then it shows the location of the park in google map
        viewHolder.park.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // passes the latitude and the longitude of the park to the maps activity with the intent
                // intent switches the activity to maps Activity
                Intent i = new Intent(ctx, MapsActivity.class);
                i.putExtra("latitude",getItem(pos).getLatitude());
                i.putExtra("longitude",getItem(pos).getLongitude());
                ctx.startActivity(i);
            }
        });

        // if the more info icon is clicked, then it opens a new window in google chrome giving more information on the park
        viewHolder.url.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switched the activity to the url link
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(getItem(pos).getUrl()));
                ctx.startActivity(intent);
            }
        });

        // sets the tag associated with this convertView
        ConvertView.setTag(viewHolder);
        return ConvertView;
    }

    /**
     * reads the Parks csv file using Buffer reader and makes a Parks object for each Park and adds that park to the Adapter ArrayList
     */
    private void loadArrayFromFile() {
        try {
            // Get input stream and Buffered Reader for our data file.
            InputStream is = ctx.getAssets().open("Parks");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            // reads the header
            String header = reader.readLine();

            //Read each line
            while ((line = reader.readLine()) != null) {

                //separate the line from commas
                RowData = line.split(",");
                System.out.println(RowData.length);
                System.out.println(RowData[0]);
                //creates a new park object with park name, latitude, longitude, url and time
                Parks park = new Parks(RowData[0],Double.parseDouble(RowData[1]),Double.parseDouble(RowData[2]),RowData[3],RowData[4]);
                System.out.println(park.getLatitude() + "Vishwaa");
                //adds the park to the adapter ArrayList
                this.add(park);
            }
        } catch (IOException e) {
        }
    }


    private class ParkHolder {
        // initializes the TextView and ImageButton
        public ImageButton park;
        public ImageView url;
        public TextView parkName;
        public TextView time;
    }
}
