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

    private TextView mDirector, mActor, mCountry, mYear, mRate;

    public DetailedFilmViewHolder(View v, final @NonNull SortedList<Film> moviesList, final @NonNull OnItemClickListener onItemClickListener) {
        super(v, moviesList, onItemClickListener);
        mDirector = (TextView) v.findViewById(R.id.director);
        mActor = (TextView) v.findViewById(R.id.actor);
        mCountry = (TextView) v.findViewById(R.id.country);
        mRate = (TextView) v.findViewById(R.id.mark);
        mYear = (TextView) v.findViewById(R.id.year);
    }

    @Override
    void bindTo(Film film) {
        super.bindTo(film);
        mDirector.setText(film.getDirector());
        mActor.setText(film.getProtagonist());
        mCountry.setText(film.getCountry());
        mRate.setText(String.valueOf(film.getCriticsRate()));
        mYear.setText(String.valueOf(film.getYear()));
    }
}
