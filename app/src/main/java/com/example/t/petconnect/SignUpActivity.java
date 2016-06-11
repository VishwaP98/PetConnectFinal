package com.example.t.petconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

/**
 * Created by Vishwa on 5/29/2016.
 */

/**
 * Take user input on new user information and validates the information
 * if information is validated, then database is updated
 */
public class SignUpActivity extends Activity {

    // initializes all the variables
    private Button signUp;
    private EditText username;
    private EditText password;
    private EditText confirmPass;
    private String pass;
    private CloudantClient client;
    private Database db;
    private String user;
    private EditText email;
    private String emailText;
    private String confirmPassWord;
    private ExampleDocument doc = new ExampleDocument();
    public static final String PREFS_NAME = "MyPrefsFile";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        // identifies all the editTexts abd buttons
        signUp = (Button)findViewById(R.id.sign_up);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmPass = (EditText)findViewById(R.id.confirm_password);

        // if sign up button is clickes then
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // takes the input from all the editText
                emailText = email.getText().toString();
                user = username.getText().toString();
                pass = password.getText().toString();
                confirmPassWord = confirmPass.getText().toString();
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // connects to the database else shows that no internet connection available
                if (networkInfo != null && networkInfo.isConnected()) {

                    try {

                        // makes a connection to the cloudant database
                        URL url = new URL("https://vishwapatel.cloudant.com");
                        client = ClientBuilder.url(url).username("vishwapatel").password("petconnect").build();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection Available. Try Again", Toast.LENGTH_LONG);
                    toast.show();
                }

                new ValidateSignUp().execute();


            }
        });

    }

    /**
     * checks if the editTexts are left blank
     * @param user gets the input in the user EditText
     * @param pass gets the input in the password EditText
     * @param emailText gets the input in the email EditText
     * @return true if it is empty, else false
     */
    public boolean checkEmpty(String user, String pass,String emailText)
    {
        if(user.equals("") || pass.equals("") || emailText.equals(""))
        {
            return true;
        }
        return false;
    }

    /**
     * checks if the email is already been used by another user
     * @param emailText
     *          String indicating user's inputted email
     * @return boolean indicating true if email is used or invalid and false if email is not used
     */
    public boolean checkEmail(String emailText)
    {
        ArrayList<String> emails = doc.getEmails();
        if(emails.contains(emailText) || !isEmailValid(email.getText())){
            if(!isEmailValid(email.getText()))
            {
                Log.e("Vishwa","NO Email");
            }
            return true;
        }
        return false;

    }

    /**
     * Checks if the email user entered is valid or not
     * @param email
     *        String indicating user's inputted email
     * @return boolean indicating true if email is valid and false if not
     */
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Checks if username is already used by another user
     * @param user
     *    String indicating user's inputted username
     *
     * @return boolean true if username is repeated and false if not
     */
    public boolean checkRepeatUser(String user)
    {
        ArrayList<String> userList = doc.getUserList();

        if(userList.contains(user))
        {
            return true;
        }
        Log.e("Vishwa","No Contain");
        return false;
    }

    /**
     * Gets information from the database and updates it with user's sign up credentials for authentication
     */
    private class ValidateSignUp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            db = client.database("petconnect",false);
            if(db == null)
            {
                Log.e("Vishwa","Error");
            }
            else
            {
                Log.e("Vishwa","Hi");
            }
            // Gets document from database
            doc = db.find(ExampleDocument.class,"UserIdentity");
            Log.e("Vishwa",String.valueOf(doc.getAge()));

            if(checkRepeatUser(user) || checkEmail(emailText) || !pass.equals(confirmPassWord) || checkEmpty(user,pass,emailText))
            {
                SignUpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!pass.equals(confirmPassWord))
                        {
                            confirmPass.setError("Passwords don't match");
                        }
                        if(checkRepeatUser(user))
                        {
                            username.setError("Username is already taken");
                        }
                        if(checkEmail(emailText))
                        {
                            Log.e("Vishwa","Invalid");
                            email.setError("Email is Invalid");
                        }
                        if(checkEmpty(user,pass,emailText))
                        {
                            email.setError("Empty Field");
                            username.setError("Empty Field");
                            password.setError("Empty Field");
                        }
                        Log.e("Vishwa","Check");
                        email.setText("");
                        username.setText("");
                        password.setText("");
                        confirmPass.setText("");
                        emailText = ""; user = ""; pass = "";
                    }
                });
            }
            else
            {
                // updates database with new user information
                doc.addUsername(user);
                doc.addEmails(emailText);
                doc.addPassword(pass);
                SignUpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getApplicationContext(),"Sign Up Successful",Toast.LENGTH_LONG);
                        toast.show();
                        SignUpActivity.setString(getApplicationContext(),"user",user);
                        Intent openWelcome = new Intent(SignUpActivity.this,NavigationDrawer.class);
                        startActivity(openWelcome);
                    }
                });
            }
            db.update(doc);

            return "";
        }
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected void onPostExecute(String result) {
            Log.e("Vishwa","Done");

        }
    }
    // Saves string in shared preferences
    private static void setString(Context context, String key, String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
