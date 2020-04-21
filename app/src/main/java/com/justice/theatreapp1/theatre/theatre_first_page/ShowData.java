package com.justice.theatreapp1.theatre.theatre_first_page;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ShowData implements Serializable, Cloneable {

    private String filmName;
    private String language;
    private String category;
    private String summary;
    private String cast;
    private String showData_time;
    private int seat;
    private String cityAndLocality;
    private String objectId;
    private String showId;


    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }


    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ShowData() {

    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ShowData(String filmName, String language, String category, String summary, String cast,
                    String showData_time, int seat, String cityAndLocality) {
        super();
        this.filmName = filmName;
        this.language = language;
        this.category = category;
        this.summary = summary;
        this.cast = cast;
        this.showData_time = showData_time;
        this.seat = seat;
        this.cityAndLocality = cityAndLocality;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getShowData_time() {
        return showData_time;
    }

    public void setShowData_time(String showData_time) {
        this.showData_time = showData_time;
    }


    public String getCityAndLocality() {
        return cityAndLocality;
    }

    public void setCityAndLocality(String cityAndLocality) {
        this.cityAndLocality = cityAndLocality;
    }


}


