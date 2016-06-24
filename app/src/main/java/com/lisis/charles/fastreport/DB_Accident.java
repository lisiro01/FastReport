package com.lisis.charles.fastreport;


public class DB_Accident {

    private long user_id;
    private long vehicle_id;
    private String date;
    private String hour;
    private String location;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;
    private String email_addressee;


    public DB_Accident(int user_id, int vehicle_id, String date, String hour, String location, byte[] image1, byte[] image2, byte[] image3, String email_addressee) {
        this.user_id = user_id;
        this.vehicle_id = vehicle_id;
        this.date = date;
        this.hour = hour;
        this.location = location;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.email_addressee = email_addressee;
    }

    public DB_Accident (){
        this.user_id = 0;
        this.vehicle_id = 0;
        this.date = null;
        this.hour = null;
        this.location = null;
        this.image1 = null;
        this.image2 = null;
        this.image3 = null;
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getEmail_addressee() {
        return email_addressee;
    }

    public void setEmail_addressee(String email_addressee) {
        this.email_addressee = email_addressee;
    }
}
