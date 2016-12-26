package com.and119_idi.myfilmdatabase.controller;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.view.View;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 29/11/16.
 */
class DetailedMovieViewHolder extends MovieViewHolder {

    private TextView director;
    private TextView actor;
    private TextView country;
    private TextView rate;
    private TextView year;

    public DetailedMovieViewHolder(View v, final @NonNull SortedList<Film> moviesList, final @NonNull OnItemClickListener onItemClickListener) {
        super(v, moviesList, onItemClickListener);
        director = (TextView) v.findViewById(R.id.director);
        actor = (TextView) v.findViewById(R.id.actor);
        country = (TextView) v.findViewById(R.id.country);
        rate = (TextView) v.findViewById(R.id.mark);
        year = (TextView) v.findViewById(R.id.year);
    }

    @Override
    void bindTo(Film film) {
        super.bindTo(film);
        director.setText(film.getDirector());
        actor.setText(film.getProtagonist());
        country.setText(film.getCountry());
        rate.setText(String.valueOf(film.getCritics_rate()));
        year.setText(String.valueOf(film.getYear()));
    }
}
