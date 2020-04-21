package com.justice.theatreapp1.theatre.theatre_login;

import java.io.Serializable;

public class TheatreLoginData implements Serializable {
    private String email;
    private String password;
    public TheatreLoginData(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
