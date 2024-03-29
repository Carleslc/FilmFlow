package com.and119_idi.filmflow.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by pr_idi on 10/11/16.
 * Modified by Carlos on 26/12/16.
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
    /**
     * From 0 to 10
     */
    private int mCriticsRate;

    public Film(@NonNull String title, @NonNull String director,
                @NonNull String country, int year,
                @NonNull String protagonist) {
        this(title, director, country, year, protagonist, null, 10);
    }

    public Film(@NonNull String title, @NonNull String director,
                @NonNull String country, int year,
                @NonNull String protagonist,
                @Nullable String description, int criticsRate) {
        mTitle = title;
        mDirector = director;
        mCountry = country;
        mYear = year;
        mProtagonist = protagonist;
        mDescription = description;
        mCriticsRate = criticsRate;
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

    /** From 0 to 10 */
    public int getCriticsRate() {
        return mCriticsRate;
    }

    /** From 0 to 10 */
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
