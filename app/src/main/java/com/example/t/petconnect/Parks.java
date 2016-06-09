package com.example.t.petconnect;

/**
 * makes a park object containing the required information for the aprks such as the location of the park, timings, url to the extra info
 */
public class Parks {


    // initializes all the variables
    private String parkName;
    private double latitude;
    private double longitude;
    private String url;
    private String time;

    /**
     * initializes all the values of the park
     * @param parkName
     *          the name of the park
     * @param latitude
     *          latitude of the park
     * @param longitude
     *              longitude of the park
     * @param url
     *          url of the addition info of the park
     * @param time
     *          timings of the park
     */
    public Parks(String parkName, double latitude, double longitude, String url, String time){
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.time = time;

    }

    /**
     * gets the latitude of the park
     * @return latitude
     */
    public double getLatitude(){

        return latitude;
    }

    /**
     * gets the longitude of the park
     * @return longitude
     */
    public double getLongitude(){

        return longitude;
    }

    /**
     * gets the name of the park
     * @return name
     */
    public String getName(){

        return parkName;
    }

    /**
     *@param name of the park
     *sets the name of the park
     */
    public void setName(String name){

        parkName = name;
    }

    /**
     * returns the Url of the park for more information
     * @return url
     */

    public String getUrl(){

        return url;
    }

    /**
     * returns the timings of the park
     * @return timings
     */
    public String getTime(){

        return time;
    }
}
