package com.and119_idi.myfilmdatabase.model;

import java.io.Serializable;

/**
 * Film
 * Created by pr_idi on 10/11/16.
 */
public class Film implements Serializable {

    // Basic film data manipulation class
    // Contains basic information on the film

    private long id;
    private String title;
    private String director;
    private String country;
    private String description;
    private int year;
    private String protagonist;
    private int critics_rate;

    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(String protagonist) {
        this.protagonist = protagonist;
    }

    public int getCritics_rate() {
        return critics_rate;
    }

    public void setCritics_rate(int critics_rate) {
        this.critics_rate = critics_rate;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return String.format("%s - %s", title, director);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}