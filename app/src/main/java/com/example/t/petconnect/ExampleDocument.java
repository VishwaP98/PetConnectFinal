package com.example.t.petconnect;

import com.cloudant.client.api.model.Document;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Vishwa on 5/30/2016.
 *
 * Document Class that contains user authentication information and information
 * regarding how many people are attending each event
 */
public class ExampleDocument extends Document {

    // initializes all the variables
    @JsonProperty("age")
    private int age;
    @JsonProperty("userList")
    private ArrayList<String> userList;
    @JsonProperty("emails")
    private ArrayList<String> emails;
    @JsonProperty("passWords")
    private ArrayList<String> passWords;
    @JsonProperty("attendes")
    private ArrayList<String> attendes;

    /**
     * adds the user name in the user list ArrayList
     * if the ArrayList is null, then it initializes the ArrayList first and then adds the username to it
     * @param userName
     */
    public void addUsername(String userName) {
        if(userList == null)
        {
            userList = new ArrayList<>();
        }
        userList.add(userName);
    }

    /**
     * adds the emails in the emails arrayList
     * if the ArrayList is null, then it initializes the ArrayList first and then adds the emails to it
     * @param addemails
     */
    public void addEmails(String addemails)
    {
        if(emails == null)
        {
            emails = new ArrayList<>();
        }
        emails.add(addemails);
    }

    /**
     * adds the password in the passwords ArrayList
     * if the ArrayList is null, then it initializes the ArrayList first and then adds the password to it
     * @param password
     */
    public void addPassword(String password)
    {
        if(passWords == null)
        {
            passWords = new ArrayList<>();
        }
        passWords.add(password);
    }

    /**
     * increases the toll of attendees attending an event by 1 once
     * the user checks the attending the event checkbox
     * @param index
     *          int indicating the event whose attendees have to be incremented by 1
     * @param size
     *        int indicating the size of the attendes arraylist
     */
    public void addAttendes(int index,int size)
    {
        if(attendes == null || attendes.size() == 0)
        {
            attendes = new ArrayList<>();
            for(int i=0; i<size;i++)
            {
                attendes.add(i,String.valueOf(0));
            }
        }
        int attendents = Integer.parseInt(attendes.get(index));
        attendents++;
        attendes.add(index,String.valueOf(attendents));
        attendes.remove(index + 1);
    }

    /**
     * returns the attendee at the location index from the Arraylist
     * @param index
     * @return
     */
    public String getAttendes(int index)
    {
        return attendes.get(index);
    }

    /**
     * returns the age
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * sets the age
     * @param age
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * returns the ArrayList emails
     * @return
     */
    public ArrayList<String> getEmails()
    {
        return emails;
    }

    /**
     * returns the ArrayList userList
     * @return
     */
    public ArrayList<String> getUserList()
    {
        return userList;
    }

    /** returns the ArrayList passwords
     * @return
     */
    public ArrayList<String> getPassWords()
    {
        return passWords;
    }



}