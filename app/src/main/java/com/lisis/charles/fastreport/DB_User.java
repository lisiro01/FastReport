package com.lisis.charles.fastreport;


public class DB_User {

    private String name;
    private String lastname;
    private String email;
    private String pass;
    private String phoneNumber;
    private String driverLicense;
    private String expiration_date;
    private String address;

    public DB_User() {
        this.name = null;
        this.lastname = null;
        this.email = null;
        this.pass = null;
        this.phoneNumber = null;
        this.driverLicense = null;
        this.expiration_date = null;
        this.address = null;
    }

    public DB_User(String name, String lastname, String email, String pass, String phoneNumber, String driverLicense, String expiration_date, String address) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.driverLicense = driverLicense;
        this.expiration_date = expiration_date;
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DB_User(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }



}
