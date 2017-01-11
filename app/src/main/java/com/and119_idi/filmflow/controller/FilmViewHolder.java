package com.and119_idi.filmflow.controller;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.model.Film;

/**
 * Created by albert on 29/11/16.
 * Refactored by Carlos on 16/12/16 and 25/12/16.
 */
class FilmViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = FilmViewHolder.class.getSimpleName();

    private TextView mTitle;

    /* IMPLEMENTATION NOTE: This constructor must be public (accessed with reflection) */
    public FilmViewHolder(View v, final @NonNull SortedList<Film> moviesList, final @NonNull OnItemClickListener onItemClickListener) {
        super(v);
        CardView card = (CardView) v.findViewById(R.id.card);
        //https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder.html#getAdapterPosition()
        //(NO POSITION)
        card.setOnClickListener((view) -> onItemClickListener.onItemClick(((getAdapterPosition() >= 0)?moviesList.get(getAdapterPosition()):null)));
        mTitle = (TextView) v.findViewById(R.id.title);
    }

    void bindTo(Film film) {
        mTitle.setText(film.getTitle());
    }
}
