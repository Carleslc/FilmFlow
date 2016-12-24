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
 * Created by albert on 29/11/16.
 */
public class MoviesRecyclerViewAdapter
        extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {

    private SortedList<Film> moviesList;
    private OnItemClickListener onItemClickListener;

    public MoviesRecyclerViewAdapter(@NonNull List<Film> moviesList) {
        this.moviesList = new SortedList<>(Film.class, new SortedListAdapterCallback<Film>(this) {
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
                        && f1.getCritics_rate() == f2.getCritics_rate();
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row_details, null);
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
        private TextView director;
        private TextView actor;
        private TextView country;
        private TextView rate;
        private TextView year;

        MovieViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            director = (TextView) v.findViewById(R.id.director);
            actor = (TextView) v.findViewById(R.id.actor);
            country = (TextView) v.findViewById(R.id.country);
            rate = (TextView) v.findViewById(R.id.mark);
            year = (TextView) v.findViewById(R.id.year);
        }

        //implementamos  OnClickListener. En el onClick() usamos nuestro propio interface OnItemClickListener
        //(que hemos seteado a esta clase con una implementación desde MoviesFragment)
        //llamando a onItemClickListener.onItemClick(film).
        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
        }

        void bindTo(Film film) {
            title.setText(film.getTitle());
            director.setText(film.getDirector());
            actor.setText(film.getProtagonist());
            country.setText(film.getCountry());
            rate.setText(String.valueOf(film.getCritics_rate()));
            year.setText(String.valueOf(film.getYear()));
        }
    }

}
