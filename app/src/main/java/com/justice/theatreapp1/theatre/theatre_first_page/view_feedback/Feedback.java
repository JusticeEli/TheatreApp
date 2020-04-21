package com.justice.theatreapp1.theatre.theatre_first_page.view_feedback;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String email;
    private String filmName;
    private String name;
    private int feedback = 3;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public int getFeedback() {
        return feedback;
    }

    public Feedback() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }


}
