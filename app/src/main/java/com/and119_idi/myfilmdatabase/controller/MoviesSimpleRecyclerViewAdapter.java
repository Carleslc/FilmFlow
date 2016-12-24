package com.and119_idi.myfilmdatabase.controller;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 16/12/16.
 */
public class MoviesSimpleRecyclerViewAdapter
        extends RecyclerView.Adapter<MoviesSimpleRecyclerViewAdapter.MovieViewHolder> {

    private SortedList<Film> moviesList;
    private OnItemClickListener onItemClickListener;

    public MoviesSimpleRecyclerViewAdapter(@NonNull List<Film> moviesList) {

        //The reference is lost, so list cant be updated outside :(
        this.moviesList = new SortedList<>(Film.class, new SortedListAdapterCallback<Film>(this) {
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
        });
        for (Film f : moviesList) this.moviesList.add(f);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int i) {
        final Film film = moviesList.get(i);

        //Conseguimos los datos para meterlos al view holder
        viewHolder.bindTo(film);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row_simple, null);
        return new MovieViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SortedList getList() {
        return moviesList;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;

        MovieViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
        }

        void bindTo(Film film) {
            title.setText(film.getTitle());
        }
    }

}
