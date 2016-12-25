package com.and119_idi.myfilmdatabase.controller;

import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    MovieViewHolder(View v, final SortedList<Film> moviesList, final OnItemClickListener onItemClickListener) {
        super(v);
        CardView card = (CardView) v.findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
            }
        });
        title = (TextView) v.findViewById(R.id.title);
    }

    void bindTo(Film film) {
        title.setText(film.getTitle());
    }
}
