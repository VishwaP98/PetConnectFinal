package com.example.t.petconnect;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * sets the layout of the Fragment to fragment_pet_licensing containing multiple TextViews and buttons
 * gives information on pet licensing and link to city of toronto pet licensing website
 */
public class PetLicensingFragment extends Fragment {

    // initializes all the Textviews
    private Button visit;
    private TextView benefit;
    private TextView benefit1;
    private TextView benefit2;
    private TextView benefit3;
    private  TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pet_licensing, container, false);

        // identifies all the TextViews and Buttons
        visit = (Button)view.findViewById(R.id.visit);
        benefit = (TextView)view.findViewById(R.id.textView9);
        benefit1 = (TextView)view.findViewById(R.id.textView10);
        benefit2 = (TextView)view.findViewById(R.id.textView11);
        benefit3 = (TextView)view.findViewById(R.id.textView12);
        textview1 = (TextView)view.findViewById(R.id.textView13);
        textview2 = (TextView)view.findViewById(R.id.textView14);
        textview3 = (TextView)view.findViewById(R.id.textView15);
        textview4 = (TextView)view.findViewById(R.id.textView16);


        // sets the text of each of the TextView
        benefit.setText("Key Benefits of Pet Licensing");
        benefit1.setText("1. Pet Protection");
        textview1.setText("Lost pets can be easily reunited with their owners");
        textview2.setText("Pets can be easily tracked and also licensed pets are held longer at animal shelters.");
        benefit2.setText("2. Peace of Mind");
        benefit3.setText("3. Public Health");
        textview3.setText("Pet Licensing reduces the chances of rabies amongst animals by providing revenue to prevention programs\n" +
                "\tand ensuring household pets are vaccinated.");
        textview4.setText("Visit the City of Toronto website for more information");

        // if visit is clicked, then pet licensing website opens up on the internet
        visit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www1.toronto.ca/wps/portal/contentonly?vgnextoid=66437729050f0410VgnVCM10000071d60f89RCRD"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //returns view
        return view;
    }
}
