package com.and119_idi.myfilmdatabase.controller;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

import java.util.Collection;
import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 16/12/16.
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    SortedList<Film> moviesList;
    OnItemClickListener onItemClickListener;

    public MoviesRecyclerViewAdapter() {
        this.moviesList = new SortedList<>(Film.class, getSortCallback());
    }

    MoviesRecyclerViewAdapter(@NonNull List<Film> moviesList) {
        this();
        setFilms(moviesList);
    }

    protected SortedList.Callback<Film> getSortCallback() {
        return new SortedListAdapterCallback<Film>(this) {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitle().compareToIgnoreCase(f2.getTitle());
            }

            @Override
            public boolean areContentsTheSame(Film f1, Film f2) {
                return f1.getTitle().equals(f2.getTitle());
            }

            @Override
            public boolean areItemsTheSame(Film f1, Film f2) {
                return f1.getId() == f2.getId();
            }
        };
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int i) {
        viewHolder.bindTo(moviesList.get(i));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row_simple, null);
        return new MovieViewHolder(view, moviesList, onItemClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFilms(Collection<Film> films) {
        moviesList.clear();
        moviesList.addAll(films);
        notifyDataSetChanged();
    }

}
