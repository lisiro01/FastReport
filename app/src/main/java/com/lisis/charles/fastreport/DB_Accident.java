package com.lisis.charles.fastreport;

/**
 * Created by Lis on 17/5/16.
 */
public class DB_Accident {

    private long user_id;
    private long vehicle_id;
    private String date;
    private String location;
    private String email_addressee;


    public DB_Accident(int user_id, int vehicle_id, String date, String location, String email_addressee) {
        this.user_id = user_id;
        this.vehicle_id = vehicle_id;
        this.date = date;
        this.location = location;
        this.email_addressee = email_addressee;
    }

    public DB_Accident (){
        this.user_id = 0;
        this.vehicle_id = 0;
        this.date = null;
        this.location = null;
        this.email_addressee = null;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail_addressee() {
        return email_addressee;
    }

    public void setEmail_addressee(String email_addressee) {
        this.email_addressee = email_addressee;
    }
}
