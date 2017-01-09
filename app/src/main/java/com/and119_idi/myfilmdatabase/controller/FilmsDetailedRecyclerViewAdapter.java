package com.and119_idi.myfilmdatabase.controller;

import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 29/11/16.
 * Refactored by Carlos on 25/12/16.
 */
public class FilmsDetailedRecyclerViewAdapter extends FilmsRecyclerViewAdapter {

    public FilmsDetailedRecyclerViewAdapter() {
        super(R.layout.film_row_details, DetailedFilmViewHolder.class);
    }

    @Override
    protected SortedList.Callback<Film> getSortCallback() {
        return new SortedListAdapterCallback<Film>(this) {
            @Override
            public int compare(Film f1, Film f2) {
                return Integer.compare(f1.getYear(), f2.getYear());
            }

            @Override
            public boolean areContentsTheSame(Film f1, Film f2) {
                return f1.getTitle().equals(f2.getTitle())
                        && f1.getYear() == f2.getYear()
                        && f1.getDirector().equals(f2.getDirector())
                        && f1.getProtagonist().equals(f2.getProtagonist())
                        && f1.getCountry().equals(f2.getCountry())
                        && f1.getCriticsRate() == f2.getCriticsRate();
            }

            @Override
            public boolean areItemsTheSame(Film f1, Film f2) {
                return f1.getId() == f2.getId();
            }
        };
    }

}
