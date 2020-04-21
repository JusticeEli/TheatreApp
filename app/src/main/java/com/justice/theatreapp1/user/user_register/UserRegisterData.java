package com.justice.theatreapp1.user.user_register;

import java.io.Serializable;

public class UserRegisterData implements Serializable {
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String bestFriend;

    public UserRegisterData() {

    }

    public UserRegisterData(String name, String email, String mobile, String address, String bestFriend) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.bestFriend = bestFriend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBestFriend() {
        return bestFriend;
    }

    public void setBestFriend(String bestFriend) {
        this.bestFriend = bestFriend;
    }
}
