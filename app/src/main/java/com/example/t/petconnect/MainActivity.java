package com.example.t.petconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signUpPetConnect;
    private EditText username;
    private EditText password;
    private static String user = "";
    private static String pass = "";
    private CloudantClient client;
    private Database db;
    private ExampleDocument doc = new ExampleDocument();
    ActionBar actionBar;
    View decorView;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        username = (EditText) findViewById(R.id.username);

        if(MainActivity.getString(getApplicationContext(),"user") != null)
        {
            Log.e("Vishwa","Not null");
            user = MainActivity.getString(getApplicationContext(),"user");
            username.setText(user);
        }
        //decorView = getWindow().getDecorView();
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);
        //actionBar = getSupportActionBar();
        //actionBar.hide();
        login = (Button) findViewById(R.id.login);
        signUpPetConnect = (Button) findViewById(R.id.sign_up_pet_connect);
        password = (EditText) findViewById(R.id.password);

        signUpPetConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openWelcome = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(openWelcome);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                Log.e("user",user);
                Log.e("pass",pass);
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    try {

                    URL url = new URL("https://vishwapatel.cloudant.com");
                    client = ClientBuilder.url(url).username("vishwapatel").password("petconnect").build();

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                 }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"No Internet Connection Available. Try Again",Toast.LENGTH_LONG);
                    toast.show();
                }

                new DownloadWebpageTask().execute("");
            }
        });
    }


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
            Log.e("Vishwa",String.valueOf(doc.getAge()));

                if(getUserIndex(user) == -1 || checkEmpty(user,pass))
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(checkEmpty(user,pass))
                            {
                                username.setError("Empty Field");
                                password.setError("Empty Field");
                            }
                            else
                            {
                                username.setError("Invalid Username");
                            }
                            pass ="" ;user="";username.setText("");
                            password.setText("");
                            Toast toast = Toast.makeText(getApplicationContext(),"Log In Unsuccessful",Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                }
                if(pass.equals(getUserPass(getUserIndex(user))))
                {
                    Log.e("Vishwa","pass");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(),"Successfully Logged in",Toast.LENGTH_LONG);
                            toast.show();
                            MainActivity.setString(getApplicationContext(),"user",user);
                            Intent intent = new Intent(MainActivity.this,NavigationDrawer.class);
                            startActivity(intent);

                        }
                    });

                }
                else
                {
                    Log.e("Vishwa","No pass");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            username.setText("");
                            password.setText("");
                            password.setError("Invalid Username or Password");
                            Toast toast = Toast.makeText(getApplicationContext(),"Log In Unsuccessful.",Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                }

            Log.e("Vishwa", "Fine");

            return "Done";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.e("Vishwa","Done");

        }

    }

    public int getUserIndex(String user)
    {
        ArrayList<String> userList = doc.getUserList();
        int index = userList.indexOf(user);
        return index;
    }
    public String getUserPass(int index) {
        ArrayList<String> pass = doc.getPassWords();
        String password;
        if (index == -1) {
            password = "1";
        } else
        {
            password = pass.get(index);
        }
        return password;
    }

    public boolean checkEmpty(String user, String pass)
    {
        if(user.equals("") || pass.equals(""))
        {
            return true;
        }
        return false;
    }
    public static String getString(Context context, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        String value = settings.getString(key,"");
        return value;
    }
    private static void setString(Context context,String key,String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        // Commit the edits
        editor.apply();
    }
    public void onBackPressed(){

    }


}
