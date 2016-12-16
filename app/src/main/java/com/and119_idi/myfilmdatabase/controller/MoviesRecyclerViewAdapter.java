package com.and119_idi.myfilmdatabase.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.CustomViewHolder> {
    private List<Film> moviesList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MoviesRecyclerViewAdapter(Context context, List<Film> moviesList) {
        this.moviesList = moviesList;
        this.mContext = context;
    }



    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Film film = moviesList.get(i);

        //Conseguimos los datos para meterlos al view holder

        customViewHolder.titulo.setText(film.getTitle());
        customViewHolder.director.setText(film.getDirector());
        customViewHolder.actor.setText(film.getProtagonist());
        customViewHolder.country.setText(film.getCountry());
        customViewHolder.rate.setText(String.valueOf(film.getCritics_rate()));
        customViewHolder.year.setText(String.valueOf(film.getYear()));

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (moviesList != null) {
            ret = moviesList.size();
        }
        return ret;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        protected TextView titulo;
        protected TextView director;
        protected TextView actor;
        protected TextView country;
        protected TextView rate;
        protected TextView year;


        public CustomViewHolder(View v) {
            super(v);
            this.titulo = (TextView) v.findViewById(R.id.title);
            this.director = (TextView) v.findViewById(R.id.director);
            this.actor = (TextView) v.findViewById(R.id.actor);
            this.country = (TextView) v.findViewById(R.id.country);
            this.rate = (TextView) v.findViewById(R.id.mark);
            this.year = (TextView) v.findViewById(R.id.year);
        }

        //implementamos  OnClickListener. En el onClick() usamos nuestro propio interface OnItemClickListener
        //(que hemos seteado a esta clase con una implementación desde MoviesFragment)
        //llamando a onItemClickListener.onItemClick(film).
        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
        }
    }

}
