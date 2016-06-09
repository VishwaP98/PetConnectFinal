package com.example.t.petconnect;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Document;

import org.codehaus.jackson.annotate.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Provides more information to the user about each event and opens them to other features
 * like sharing, informing and notifications
 */
public class InfoActivity extends AppCompatActivity {

    // Defines all the variables
    private int pos;
    private ImageButton notify;
    private Button share;
    private CheckBox checkBox;
    private CloudantClient client;
    private Database db;
    private ExampleDocument doc;
    private Boolean hideBox;
    private static String attends = "Hi";
    private TextView eventName;
    private TextView sDate;
    private TextView sTime;
    private TextView eDate;
    private TextView eTime;
    private TextView description;
    private String[] months;
    private ImageButton people;
    private static ArrayList<String> checkedList;
    private static ArrayList<String> notifyList;
    private SitesAdapter adapter = EventsFragment.getAdapter();
    private ViewFlipper viewFlipper;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        // identifies the toolbar and makes a toolbar object
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // sets the toolbar in the action bar, displays the back button in the toolbar and sets the title of the action bar to events
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");
        pos = getIntent().getExtras().getInt("position");
        final String name = getIntent().getExtras().getString("name");
        final String startDate = getIntent().getExtras().getString("startdate");
        final String startTime = getIntent().getExtras().getString("starttime");
        String endDate = getIntent().getExtras().getString("enddate");
        String endTime = getIntent().getExtras().getString("endtime");
        final String desc = getIntent().getExtras().getString("description");

        // identifies the image button. textview, and
        months = getResources().getStringArray(R.array.months);
        people = (ImageButton)findViewById(R.id.imageButton);
        notify = (ImageButton)findViewById(R.id.button);
        checkedList = EventsFragment.loadArray(getApplicationContext(),"checked","checked_Status",checkedList);
        notifyList = EventsFragment.loadArray(getApplicationContext(),"notify","notify_Status",notifyList);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

        if(!EventsFragment.isChecked(pos))
        {
            Log.e("Vishwa","Check");
            Log.e("Vishwa",String.valueOf(EventsFragment.getSize()));
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setEnabled(true);
        }
        else
        {
            Log.e("Vishwa","UnCheck");
            checkBox.setVisibility(View.INVISIBLE);
            checkBox.setEnabled(false);
        }
        Log.e("Vishwa Check Again",String.valueOf(EventsFragment.getSize()));

        if(!EventsFragment.isNotified(pos))
        {
            notify.setVisibility(View.VISIBLE);
            notify.setEnabled(true);
        }
        else
        {
            notify.setEnabled(false);
            notify.setVisibility(View.INVISIBLE);
        }

        // Defines all the UI components
        eventName = (TextView) findViewById(R.id.title);
        sDate = (TextView) findViewById(R.id.start_date);
        sTime = (TextView) findViewById(R.id.start_time);
        eDate = (TextView) findViewById(R.id.end_date);
        eTime = (TextView) findViewById(R.id.end_time);
        description = (TextView) findViewById(R.id.description);

        // Makes a connection with the Cloudant Database
        try {
            URL url = new URL("https://vishwapatel.cloudant.com");
            client = ClientBuilder.url(url).username("vishwapatel").password("petconnect").build();


        }catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // sets the text of the textviews
        eventName.setText(name);
        sDate.setText(startDate);
        sTime.setText(startTime);
        eDate.setText(endDate);
        eTime.setText(endTime);
        description.setText(desc);
        description.setMovementMethod(new ScrollingMovementMethod());


        // if the people button is clicked, then a toast pops up showing the number of people attending the event
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),doc.getAttendes(pos) + " people are attending", Toast.LENGTH_SHORT);
                //positions the toast at a specified location
                toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 0,200);
                toast.show();
            }
        });

        // identifies the share button
        share = (Button) findViewById(R.id.button2);

        // if the share button is clicked, then opens up all the apps that can be used to share information
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"");
                shareIntent.putExtra(Intent.EXTRA_TEXT,name);
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
            }
        });

        // if the notify button is clicked, then checks if the date of the event is more than one day, then sets the notification
        // else it pops up that the notification cannot be set for that event
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsFragment.addNotfiy(pos, getApplicationContext());
                Log.e("Vishwa", "Hello");
                String[] array = getComponents(startDate);
                Log.e("array check", array[0]);
                Log.e("array check 2", array[1]);
                Log.e("array check 3", array[2]);
                String[] timeComp = getTime(startTime);
                int index = 0;
                Calendar calendar = Calendar.getInstance();
                for (int i = 0; i < months.length; i++) {
                    if (months[i].equals(array[0])) {
                        index = i;
                    }
                }
                if (calendar.get(Calendar.MONTH) < index) {
                    calendar.set(Calendar.MONTH, index);
                    Log.e("Month", String.valueOf(index));


                } else if (calendar.get(Calendar.MONTH) == index)
                {
                    if((calendar.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(array[1]) - 1))
                    {
                        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(array[1]) - 1);
                        Log.e("Day",String.valueOf(array[1]));

                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeComp[0]));
                        Log.e("Hour",timeComp[0]);

                        calendar.set(Calendar.MINUTE, Integer.parseInt(timeComp[1]));
                        Log.e("Minute",timeComp[1]);
                        calendar.set(Calendar.SECOND, 0);
                        if(timeComp[2].equals("AM"))
                        {
                            calendar.set(Calendar.AM_PM,Calendar.AM);
                            Log.e("Am_Pm","AM");
                        }
                        else if(timeComp[2].equals("PM"))
                        {
                            calendar.set(Calendar.AM_PM,Calendar.PM);
                            Log.e("Am_Pm","PM");
                        }

                    }
                    else if((calendar.get(Calendar.DAY_OF_MONTH) <= Integer.parseInt(array[1]) - 1))
                    {
                        if((calendar.get(Calendar.AM_PM) == 0 && timeComp[2].equals("AM")) || ((calendar.get(Calendar.AM_PM) == 1) && timeComp[2].equals("PM")) || (calendar.get(Calendar.AM_PM) == 0 && timeComp[2].equals("PM")))
                        {
                            if(calendar.get(Calendar.HOUR) < Integer.parseInt(timeComp[0]))
                            {
                                calendar.set(Calendar.HOUR,Integer.parseInt(timeComp[0]));
                            }
                            if(calendar.get(Calendar.HOUR) == Integer.parseInt(timeComp[0]) && calendar.get(Calendar.MINUTE) < Integer.parseInt(timeComp[1]))
                            {
                                calendar.set(Calendar.MINUTE,Integer.parseInt(timeComp[1]));
                            }
                            else
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),"Error. Notifications are only scheduled 1 Day prior to Event ",Toast.LENGTH_LONG);
                                toast.show();
                            }

                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),"Error. Notifications are only scheduled 1 Day prior to Event ",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(),"Error. Notifications are only scheduled 1 Day prior to Event ",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Error. Notifications are only scheduled 1 Day prior to Event ",Toast.LENGTH_LONG);
                    toast.show();
                }


                Long time = calendar.getTimeInMillis();
                if(time > System.currentTimeMillis())
                {
                    Log.e("Time",time.toString());
                    Intent notification = new Intent(InfoActivity.this,Receiver.class);
                    AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP,time,PendingIntent.getBroadcast(InfoActivity.this,1,notification,PendingIntent.FLAG_UPDATE_CURRENT));
                    Toast toast = Toast.makeText(getApplicationContext(),"Notification Scheduled",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Error. Notifications are only scheduled 1 Day prior to Event",Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

        // if the checkbox is clicked, then
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                checkBox.setVisibility(View.INVISIBLE);
                                checkBox.toggle();
                                EventsFragment.addCheck(pos,getApplicationContext());
                                new DownloadWebpageTask().execute();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                checkBox.toggle();

                                break;
                        }
                    }
                };
                AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
                dialog.setMessage("Are you sure you are attending this event?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Attending " + eventName.getText().toString() + " event")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();

                Log.e("Vishwa", "Alert working");
            }
        });

        new RunTask().execute();



    }

    /**
     * Updates the cloudant database with user authentication information and information on
     * number of people attending each event
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            db = client.database("petconnect",false);
            if(db == null)
            {
                Log.e("Vishwa","Error");
            }
            else
            {
                Log.e("Vishwa","Hi");
            }
            doc = db.find(ExampleDocument.class,"UserIdentity");
            doc.addAttendes(pos,SitesXmlPullParser.getStackSize());
            db.update(doc);



            Log.e("Vishwa", "Fine");

            return "Done";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.e("Vishwa","Done");

        }


    }

    /**
     *  Gets the number of people attending each event
     */
    private class RunTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            db = client.database("petconnect",false);
            doc = db.find(ExampleDocument.class,"UserIdentity");

            attends = doc.getAttendes(pos);

            return "";
        }
        @Override
        protected void onPreExecute()
        {
            eventName.setVisibility(View.INVISIBLE);
            sDate.setVisibility(View.INVISIBLE);
            sTime.setVisibility(View.INVISIBLE);
            eDate.setVisibility(View.INVISIBLE);
            eTime.setVisibility(View.INVISIBLE);
            description.setVisibility(View.INVISIBLE);
            share.setVisibility(View.INVISIBLE);
            if(checkBox.getVisibility() == View.VISIBLE)
            {
                checkBox.setVisibility(View.INVISIBLE);
                hideBox = true;
            }
            else
            {
                hideBox = false;
            }
            //view.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onPostExecute(String result) {

            share.setVisibility(View.VISIBLE);
            if(hideBox == true)
            {
                checkBox.setVisibility(View.VISIBLE);
            }
            //view.setVisibility(View.VISIBLE);
            eventName.setVisibility(View.VISIBLE);
            sDate.setVisibility(View.VISIBLE);
            sTime.setVisibility(View.VISIBLE);
            eDate.setVisibility(View.VISIBLE);
            eTime.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            //view.setText(attends);
            Log.e("Vishwa","Done");

        }
    }
    public static String getAttends()
    {
        return attends;
    }
    public String[] getComponents(String date)
    {
        StringBuilder builder = new StringBuilder(date);
        int index = builder.indexOf(",");
        builder.deleteCharAt(index);
        date = builder.toString();
        String[] components = date.split(" ");
        return components;
    }
    public String[] getTime(String time)
    {
        StringBuilder builder = new StringBuilder(time);
        int index = builder.indexOf(":");
        builder.deleteCharAt(index);
        builder.insert(index, " ");
        time = builder.toString();
        String[] components = time.split(" ");
        return components;
    }

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {
                    if (pos>=1) {
                        pos--;

                        // sets the text of all the textviews by getting the value from the ArrayList of the adapter

                        eventName.setText(adapter.getItem(pos).getName());
                        sDate.setText(adapter.getItem(pos).getStartDate());
                        sTime.setText(adapter.getItem(pos).getStartTime());
                        eDate.setText(adapter.getItem(pos).getEndDate());
                        eTime.setText(adapter.getItem(pos).getEndTime());
                        description.setText(adapter.getItem(pos).getDescription());

                        //if the user has already clicked the checkbox, then sets it invisible, else sets it visible
                        if(!EventsFragment.isChecked(pos))
                        {
                            checkBox.setVisibility(View.VISIBLE);
                            checkBox.setEnabled(true);
                        }
                        else
                        {
                            checkBox.setVisibility(View.INVISIBLE);

                        }

                        // if the user has already clicked the notification button then sets it invisible, else sets it visible

                        if(!EventsFragment.isNotified(pos))
                        {
                            notify.setVisibility(View.VISIBLE);
                        }
                        else {
                            notify.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        break;
                    }
                    // If there aren't any other children, just break.
                    /*if (viewFlipper.getDisplayedChild() == 0)
                        break;*/

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);



                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {
                    pos++;
                    // sets the text of all the textviews by getting the value from the ArrayList of the adapter
                    eventName.setText(adapter.getItem(pos).getName());
                    sDate.setText(adapter.getItem(pos).getStartDate());
                    sTime.setText(adapter.getItem(pos).getStartTime());
                    eDate.setText(adapter.getItem(pos).getEndDate());
                    eTime.setText(adapter.getItem(pos).getEndTime());
                    description.setText(adapter.getItem(pos).getDescription());

                    //if the user has already clicked the checkbox, then sets it invisible, else sets it visible
                    if(!EventsFragment.isChecked(pos))
                    {
                        checkBox.setVisibility(View.VISIBLE);
                        checkBox.setEnabled(true);
                    }
                    else
                    {
                        checkBox.setVisibility(View.INVISIBLE);

                    }

                    // if the user has already clicked the notification button then sets it invisible, else sets it visible
                    if(!EventsFragment.isNotified(pos))
                    {
                        notify.setVisibility(View.VISIBLE);
                    }
                    else {
                        notify.setVisibility(View.INVISIBLE);
                    }
                    // If there is a child (to the left), kust break.
                    /*if (viewFlipper.getDisplayedChild() == 1)
                        break;*/

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }

    /**
     *
     * @param menu
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * if the back button is pressed, then does not do anything
     */
    public void onBackPressed(){

    }

    /**
     * if the back button in the action bar is clicked, then goes back to the events fragment
     * @param item
     * @return true if the back button is clicked, else calls the super method
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}