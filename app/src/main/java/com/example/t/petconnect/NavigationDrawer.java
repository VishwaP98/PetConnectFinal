package com.example.t.petconnect;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * makes a navigation drawer to better organize UI and make it easy for user to 
 * use application
 */
public class NavigationDrawer extends AppCompatActivity {
    // initializes all the variables
    private ActionBar actionBar;
    private DrawerLayout DrawerLayout;
    private ActionBarDrawerToggle DrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        // inflates the view for the header of the navigation drawer
        View headerView = LayoutInflater.from(this).inflate(R.layout.navigation_drawer_header, navigationView, false);

        // identifies all the navigation view, drawer layout, and toolbar
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        DrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView Name = (TextView) headerView.findViewById(R.id.username1);

        // sets the toolbar in the action bar
        setSupportActionBar(toolbar);

        //adds the view to the navigation view
        navigationView.addHeaderView(headerView);

        // sets the user name in the textview
        Name.setText(MainActivity.getString(getApplicationContext(),"user"));
        // gets the support action bar
        actionBar = getSupportActionBar();

        // sets the navigationIcon
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_36dp);
        DrawerLayout.setDrawerListener(DrawerToggle);

        // sets the initial fragment of the drawer layout to home fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new HomeFragment());
        fragmentTransaction.commit();
        // sets the action bar title to home
        actionBar.setTitle("Home");
        // intializes the action bar drawer toggle
        DrawerToggle = new ActionBarDrawerToggle(this, DrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        // sets the item selected listener on the navigation view
        // it switches the fragment depending of the item clicked in the navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){

                    // if parks is clicked in the navigation drawer, then the fragment is replaced to parks fragment
                    // changes the action bar text to Parks
                    // closes the drawer

                    case R.id.parks:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new ParksFragment());
                        fragmentTransaction.commit();
                        actionBar.setTitle("Parks");
                        item.setChecked(true);
                        DrawerLayout.closeDrawers();
                        break;

                    // if pet licensing is clicked in the navigation drawer, then the fragment is replaced to pet licensing fragment
                    // changes the action bar text to Pet Licensing
                    // closes the drawer
                    case R.id.pet_licensing:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new PetLicensingFragment());
                        actionBar.setTitle("Pet Licensing");
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        DrawerLayout.closeDrawers();
                        break;

                    // if log out is clicked in the navigation drawer, then the fragment is replaced to log out fragment
                    // changes the action bar text to Log Out
                    // closes the drawer
                    case R.id.logout:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new LogOutFragment());
                        fragmentTransaction.commit();
                        actionBar.setTitle("Log Out");
                        item.setChecked(true);
                        DrawerLayout.closeDrawers();
                        break;

                    // if events is clicked in the navigation drawer, then the fragment is replaced to events fragment
                    // changes the action bar text to Events
                    // closes the drawer

                    case R.id.events:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new EventsFragment());
                        fragmentTransaction.commit();
                        actionBar.setTitle("Events");
                        item.setChecked(true);
                        DrawerLayout.closeDrawers();
                        break;

                    // if Home is clicked in the navigation drawer, then the fragment is replaced to Home fragment
                    // changes the action bar text to Home
                    // closes the drawer

                    case R.id.home:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                        fragmentTransaction.commit();
                        actionBar.setTitle("Home");
                        item.setChecked(true);
                        DrawerLayout.closeDrawers();
                        break;

                }
                return true;
            }
        });
    }

    // if back button is pressed on phone, then it doesnt do anything
    public void onBackPressed(){

    }


}
