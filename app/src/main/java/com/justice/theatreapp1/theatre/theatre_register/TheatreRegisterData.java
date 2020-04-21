package com.justice.theatreapp1.theatre.theatre_register;

import java.io.Serializable;

public class TheatreRegisterData implements Serializable {
    private String name;
    private String mobile;
    private String city;
    private String address_landmark;
    private String aadharNo_VoterId_license;

    public TheatreRegisterData() {
    }

    public TheatreRegisterData(String name, String mobile, String city, String address_landmark, String aadharNo_VoterId_license) {
        this.name = name;
        this.mobile = mobile;
        this.city = city;
        this.address_landmark = address_landmark;
        this.aadharNo_VoterId_license = aadharNo_VoterId_license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public void setAddress_landmark(String address_landmark) {
        this.address_landmark = address_landmark;
    }

    public String getAadharNo_VoterId_license() {
        return aadharNo_VoterId_license;
    }

    public void setAadharNo_VoterId_license(String aadharNo_VoterId_license) {
        this.aadharNo_VoterId_license = aadharNo_VoterId_license;
    }


}
