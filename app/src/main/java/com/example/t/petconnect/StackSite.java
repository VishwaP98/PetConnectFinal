package com.example.t.petconnect;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Harsh on 2016-05-15.
 */
/*
 * Data object that holds all of our information about a StackExchange Site.
 */
public class StackSite {

    //initializes all the variables
    private String name;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String description;

    /**
     * returns the name of the event
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * sets the name of the event
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the start date of the event
     * @return StartDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * sets the start date of the event
     * @param link
     */
    public void setStartDate(String link) {
        this.startDate = link;
    }

    /**
     * returns the start time of the event
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * sets the start time of the event
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * sets the end date of the event
     * @param endDate
     */
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    /**
     * returns the end date of the event
     * @return endDate
     */
    public String getEndDate(){
        return endDate;
    }

    /**
     * sets the end time of the event
     * @param endTime
     */
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    /**
     * returns the end time of the event
     * @return endTime
     */
    public String getEndTime(){
        return endTime;
    }

    /**
     * sets the description of the event
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * returns the description of the event
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     *  overrides the toString method
     * @return the event name, start date, start time, end time
     */
    public String toString() {
        return "StackSite [name=" + name + ", startDate=" + startDate + ", startTime="
                + startTime + ", endDate=" + endDate + "]";
    }
}