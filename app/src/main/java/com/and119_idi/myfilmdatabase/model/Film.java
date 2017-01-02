package com.and119_idi.myfilmdatabase.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by pr_idi on 10/11/16.
 */
public class Film implements Serializable {

    // Basic film data manipulation class
    // Contains basic information on the film

    private long mId;
    private String mTitle;
    private String mDirector;
    private String mCountry;
    private String mDescription;
    private int mYear;
    private String mProtagonist;
    private int mCriticsRate; // from 0 to 10

    public Film(@NonNull String title, @NonNull String director,
                @NonNull String country, int year,
                @NonNull String protagonist) {
        mTitle = title;
        mDirector = director;
        mCountry = country;
        mYear = year;
        mProtagonist = protagonist;
        mCriticsRate = 10;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        this.mTitle = title;
    }

    @NonNull
    public String getDirector() {
        return mDirector;
    }

    @NonNull
    public String getCountry() {
        return mCountry;
    }

    public int getYear() {
        return mYear;
    }

    @NonNull
    public String getProtagonist() {
        return mProtagonist;
    }

    public int getCriticsRate() {
        return mCriticsRate;
    }

    public void setCriticsRate(int criticsRate) {
        this.mCriticsRate = criticsRate;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", mTitle, mDirector);
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String description) {
        this.mDescription = description;
    }
}
