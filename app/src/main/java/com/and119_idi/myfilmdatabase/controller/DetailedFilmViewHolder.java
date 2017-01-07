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
class DetailedFilmViewHolder extends FilmViewHolder {

    private TextView director, actor, country, year, rate;

    public DetailedFilmViewHolder(View v, final @NonNull SortedList<Film> moviesList, final @NonNull OnItemClickListener onItemClickListener) {
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
        rate.setText(String.valueOf(film.getCriticsRate()));
        year.setText(String.valueOf(film.getYear()));
    }
}
