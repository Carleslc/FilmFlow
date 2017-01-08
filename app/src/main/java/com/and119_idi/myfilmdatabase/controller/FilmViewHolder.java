package com.and119_idi.myfilmdatabase.controller;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by Carlos Lázaro Costa on 16/12/16.
 */
class FilmViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;

    /* IMPLEMENTATION NOTE: This constructor must be public (accessed with reflection) */
    public FilmViewHolder(View v, final @NonNull SortedList<Film> moviesList, final @NonNull OnItemClickListener onItemClickListener) {
        super(v);
        CardView card = (CardView) v.findViewById(R.id.card);
        card.setOnClickListener((view) -> onItemClickListener.onItemClick(moviesList.get(getAdapterPosition())));
        mTitle = (TextView) v.findViewById(R.id.title);
    }

    void bindTo(Film film) {
        mTitle.setText(film.getTitle());
    }
}
