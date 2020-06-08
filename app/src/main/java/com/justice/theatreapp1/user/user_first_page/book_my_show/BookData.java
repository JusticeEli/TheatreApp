package com.justice.theatreapp1.user.user_first_page.book_my_show;

import java.io.Serializable;

public class BookData implements Serializable {

    private String showId;
    private String filmName;
    private String email;
    private int noOfSeats;

    public BookData() {

    }

    public BookData(String showId, String filmName, String email, int noOfSeats) {
        this.showId = showId;
        this.filmName = filmName;
        this.email = email;
        this.noOfSeats = noOfSeats;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }


}
