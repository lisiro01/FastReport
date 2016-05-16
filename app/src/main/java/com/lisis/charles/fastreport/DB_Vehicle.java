package com.lisis.charles.fastreport;

/**
 * Created by Lis on 13/5/16.
 */
public class DB_Vehicle {

    private String brand;
    private String model;
    private String registrationNumber;
    private String insurance;
    private String policyNumber;

    public DB_Vehicle() {
        this.brand = null;
        this.model = null;
        this.registrationNumber = null;
        this.insurance = null;
        this.policyNumber = null;
    }

    public DB_Vehicle(String brand, String model, String registrationNumber, String insurance, String policyNumber, String ownerId) {
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.insurance = insurance;
        this.policyNumber = policyNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

}






